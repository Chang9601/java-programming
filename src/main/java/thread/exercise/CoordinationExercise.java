package thread.exercise;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoordinationExercise {
	public static void main(String[] args) throws InterruptedException {
		List<Long> inputs = Arrays.asList(0L, 1200L, 500000000000L, 431L, 29582L, 4392L, 10L, 869L);
		List<Factorial> threads = new ArrayList<CoordinationExercise.Factorial>();
		
		for (long input: inputs) {
			threads.add(new Factorial(input));
		}
		
		for (Thread thread: threads) {
			thread.setDaemon(true);
			thread.start();
		}
		
		for (Thread thread: threads) {
			thread.join(3000);
		}
		
		for (int i = 0; i < inputs.size(); i++) {
			Factorial thread = threads.get(i);
			
			if (thread.isDone()) {
				System.out.println(inputs.get(i) + "!=" + thread.getResult());
			} else {
				System.out.println(inputs.get(i) + "! " +"현재 진행 중");
			}
		}
	}

	public static class Factorial extends Thread {
		private long input;
		private BigInteger result = BigInteger.ZERO;
		private boolean isDone = false;
		
		public Factorial(long input) {
			this.input = input;
		}
		
		public BigInteger getResult() {
			return result;
		}

		public boolean isDone() {
			return isDone;
		}

		@Override
		public void run() {
			this.result = compute(input);
			this.isDone = true;
		}
		
		public BigInteger compute(long number) {
			BigInteger result = BigInteger.ONE;
			
			for (long i = number; i > 0; i--) {
				result = result.multiply(new BigInteger(Long.toString(i)));
			}
			
			return result;
		}
	}
}