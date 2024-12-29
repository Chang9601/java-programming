package reflection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClassExercise {
	public static void main(String[] args) throws ClassNotFoundException {
		Class<String> stringClass = String.class;
		
		Map<String, Integer> map = new HashMap<>();
		Class<?> mapClass = map.getClass();
		
		Class<?> squareClass = Class.forName("reflection.Exercise$Square");
		
		printClassInformation(stringClass, mapClass, squareClass);
		
		var circle = new Shape() {
			@Override
			public int getNumberOfVertices() {
				return 0;
			}
		};
				
		printClassInformation(Collection.class, boolean.class, int [][].class, Color.class, circle.getClass());
	}

	private static void printClassInformation(Class<?> ...classes) {
		for (Class<?> clazz: classes) {
			System.out.println(String.format("클래스 이름: %s, 클래스 패키지 이름: %s", clazz.getSimpleName(), clazz.getPackageName()));
			
			System.out.println(clazz.getTypeName());
			
			Class<?>[] implementedInterfaces = clazz.getInterfaces();
			
			for (Class<?> implementedInterface: implementedInterfaces) {
				System.out.println(String.format("클래스 %s가 구현한 인터페이스 %s", clazz.getSimpleName(), implementedInterface.getSimpleName()));
			}
			
			System.out.println("배열? " + clazz.isArray());
			System.out.println("원시형? " + clazz.isPrimitive());
			System.out.println("열거형? " + clazz.isEnum());
			System.out.println("인터페이스? " + clazz.isInterface());
			System.out.println("익명? " + clazz.isAnonymousClass());
		
			System.out.println();
			System.out.println();
		}
	}
	
	private static class Square implements Shape {
		@Override
		public int getNumberOfVertices() {
			return 4;
		}
	}
	
	private static interface Shape {
		int getNumberOfVertices();
	}
	
	private enum Color {
		RED,
		GREEN,
		BLUE,
	}
}