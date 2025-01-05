package reflection.exercise;

import java.lang.reflect.Array;

public class ArrayExercise {
	public static void main(String[] args) {
		int[] array1 = {1, 2};
		double[][] array2 = {{1.5, 2.5}, {3.5, 4.5}};
		
		inspectArray(array1);
		inspectArray(array2);
		
		inspectComponent(array1);
		inspectComponent(array2);
	}
	
	public static void inspectComponent(Object object) {
		int length = Array.getLength(object);
		
		System.out.print("[");
		
		for (int i = 0; i < length; i++) {
			Object component = Array.get(object, i);
			
			if (component.getClass().isArray()) {
				inspectComponent(component);
			} else {
				System.out.print(component);
			}
			
			if (i != length - 1) {
				System.out.print(", ");
			}
		}
		
		System.out.print("]");
	}
	
	public static void inspectArray(Object object) {
		Class<?> clazz = object.getClass();
		
		System.out.println(String.format("배열? %s", clazz.isArray()));
		
		Class<?> componentType = clazz.getComponentType();
		
		System.out.println(String.format("배열 타입: %s", componentType.getTypeName()));
	}
}