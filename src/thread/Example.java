package thread;

public class Example {
	public static void main(String[] args) {
		
	}
	
	private static class Locker {
		private int password;
		
		public Locker(int password) {
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
	
	private static abstract class Thief extends Thread {
		protected Locker locker;
		
		public Thief(Locker locker) {
			this.locker = locker;
			this.setName(this.getClass().getSimpleName());
			this.setPriority(Thread.MAX_PRIORITY);
		}
		
		@Override
		public void start() {
			System.out.println("스레드 실행: " + this.getName());
			super.start();
		}
	}
	
	private static class UpTheif extends Thief {
		public UpTheif(Locker locker) {
			super(locker);
		}
		
		@Override
		public void run() {
			
		}
	}
}
