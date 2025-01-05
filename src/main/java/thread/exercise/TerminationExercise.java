package thread.exercise;

import java.math.BigInteger;

public class TerminationExercise {
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(new BlockingTask());
		Thread thread2 = new Thread(new LongComputation(new BigInteger("100000"), new BigInteger("300000000000")));
		
		//thread1.start();
		//thread1.interrupt();
		
		//thread2.setDaemon(true);
		thread2.start();
		thread2.interrupt();
	}

	private static class BlockingTask implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(500000);
			} catch (InterruptedException e) {
				System.out.println("블록킹 스레드 종료");
			}
		}
	}
	
	private static class LongComputation implements Runnable {
		private BigInteger base;
		private BigInteger power;

		public LongComputation(BigInteger base, BigInteger power) {
			this.base = base;
			this.power = power;
		}

		@Override
		public void run() {
			System.out.println(base + "^" + power + "=" + pow(base, power));
		}
		
		private BigInteger pow(BigInteger base, BigInteger power) {
			BigInteger result = BigInteger.ONE;
			
			for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("조기 종료");
					return BigInteger.ZERO;
				}
				
				result = result.multiply(base);
			}
			
			return result;
		}
	}
}