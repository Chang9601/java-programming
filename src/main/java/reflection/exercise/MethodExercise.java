package reflection.exercise;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import reflection.data.Item;

public class MethodExercise {
	public static void main(String[] args) {
		testGetters(Item.class);
		testSetters(Item.class);
	}

	public static void testGetters(Class<?> clazz) {
		List<Field> fields = getAllFields(clazz);
		
		Map<String, Method> nameToMethod = mapNameToMethod(clazz);
		
		for (Field field: fields) {
			String name = "get" + upperCaseFirstLetter(field.getName());
			
			if (!nameToMethod.containsKey(name)) {
				throw new IllegalStateException(String.format("필드 " + "%s: 게터 존재 X", field.getName()));
			}
			
			Method getter = nameToMethod.get(name);
			
			if (!getter.getReturnType().equals(field.getType())) {
				throw new IllegalStateException(
					String.format("게터 " + "%s: 반환타입 %s vs. 예측타입 %s", 
							getter.getName(), 
							getter.getReturnType().getTypeName(), 
							field.getType().getTypeName()));
			}
			
			if (getter.getParameterCount() > 0) {
				throw new IllegalStateException(String.format("게터 %s: %d개의 인자", getter.getName(), getter.getParameterCount()));
			}
		}
	}
	
	public static void testSetters(Class<?> clazz) {
		List<Field> fields = getAllFields(clazz);
						
		for (Field field: fields) {
			String name = "set" + upperCaseFirstLetter(field.getName());
			
			Method setter = null;
			
			try {
				setter = clazz.getMethod(name, field.getType());
			} catch (NoSuchMethodException exception) {
				throw new IllegalStateException(String.format("세터 %s: 존재 X", name));
			}

			if (!setter.getReturnType().equals(void.class)) {
				throw new IllegalStateException(String.format("세터 " + "%s: 반환타입 void", name));
			}
		}
	}
	
	private static List<Field> getAllFields(Class<?> clazz) {
		if (clazz == null || clazz.equals(Object.class)) {
			return Collections.emptyList();
		}
		
		Field[] currentClassFields = clazz.getDeclaredFields();
		List<Field> inheritedClassFields = getAllFields(clazz.getSuperclass());
		
		List<Field> allFields = new ArrayList<>();
		
		allFields.addAll(Arrays.asList(currentClassFields));
		allFields.addAll(inheritedClassFields);
		
		return allFields;
	}
	
	private static String upperCaseFirstLetter(String name) {
		return name.substring(0, 1).toUpperCase().concat(name.substring(1));
	}
	
	private static Map<String, Method> mapNameToMethod(Class<?> clazz) {
		Method[] methods = clazz.getMethods();
		
		Map<String, Method> nameToMethod = new HashMap<>();
		
		for (Method method: methods) {
			nameToMethod.put(method.getName(), method);
		}
		
		return nameToMethod;
	}
}