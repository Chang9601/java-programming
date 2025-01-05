package thread.hacker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	public static final int MAX_PASSWORD = 10000;
	
	public static void main(String[] args) {
		Random random = new Random();
		
		Storage storage = new Storage(random.nextInt(MAX_PASSWORD));
		
		List<Thread> threads = new ArrayList<Thread>();
		
		threads.add(new FrontHacker(storage));
		threads.add(new BackHacker(storage));
		threads.add(new Security());
		
		for (Thread thread: threads) {
			thread.start();
		}
	}
	
	private static class Storage {
		private int password;
		
		public Storage(int password) {
			this.password = password;
		}
		
		public boolean isPasswordCorrect(int guess) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				
			}
			
			return this.password == guess;
		}
	}
	
	private static abstract class Hacker extends Thread {
		protected Storage storage;
		
		public Hacker(Storage storage) {
			this.storage = storage;
			this.setName(this.getClass().getSimpleName());
			this.setPriority(Thread.MAX_PRIORITY);
		}
		
		@Override
		public void start() {
			System.out.println("스레드 실행: " + this.getName());
			super.start();
		}
	}
	
	private static class FrontHacker extends Hacker {
		public FrontHacker(Storage storage) {
			super(storage);
		}
		
		@Override
		public void run() {
			for (int guess = 0; guess < MAX_PASSWORD; guess++) {
				if (storage.isPasswordCorrect(guess)) {
					System.out.println(this.getName() + "가 비밀번호 " + guess + " 추출");
					System.exit(0);
				}
			}
		}
	}
	
	private static class BackHacker extends Hacker {
		public BackHacker(Storage storage) {
			super(storage);
		}
		
		@Override
		public void run() {
			for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
				if (storage.isPasswordCorrect(guess)) {
					System.out.println(this.getName() + "가 비밀번호" + guess + " 추출");
					System.exit(0);
				}
			}
		}
	}

	private static class Security extends Thread {
		@Override
		public void run() {
			for (int i = 10; i > 0; i--) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}

				System.out.println(i);
			}
			
			System.out.println("해커 검거!");
			System.exit(0);
		}
	}
}