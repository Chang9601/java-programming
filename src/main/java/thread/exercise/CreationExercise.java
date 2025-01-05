package thread.exercise;

public class CreationExercise {
	public static void main(String[] args) throws InterruptedException {
		//Thread thread = new Thread(new Runnable() {	
		//	@Override
		//	public void run() {
		//		System.out.println("스레드: " + Thread.currentThread().getName());
		//		System.out.println("우선순위: " + Thread.currentThread().getPriority());
		//	}
		//});
		
		Thread thread = new FooThread();
		
		thread.setName("자식 스레드");
		thread.setPriority(Thread.MAX_PRIORITY);
		/**
		 * Java에서 발생하는 체크되지 않은 예외는 명시적으로 예외를 잡아 특정 방식으로 처리하지 않으면 전체 스레드를 중단시킨다.
		 * 예외 처리기를 설정하여 이러한 스레드에서 예외를 처리할 수 있다.
		 * 스레드가 생성될 때 전체 스레드에 대한 예외 처리기를 설정할 수 있다.
		 * 처리기는 스레드 내에서 예외가 발생했지만 어디에서도 잡히지 않은 경우 호출된다.
		 */
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("스레드에서 심각한 오류 발생!! " + t.getName() + ", 오류: " + e.getMessage());
			}
		});
		
		System.out.println("스레드: " + Thread.currentThread().getName() + " 새로운 스레드 실행 전");
		thread.start();
		System.out.println("스레드: " + Thread.currentThread().getName() + " 새로운 스레드 실행 후");
		
		Thread.sleep(10000);
	}
	
	private static class FooThread extends Thread {
		@Override
		public void run() {
			System.out.println("스레드: " + Thread.currentThread().getName());
			System.out.println("우선순위: " + Thread.currentThread().getPriority());			
		}
	}
}
