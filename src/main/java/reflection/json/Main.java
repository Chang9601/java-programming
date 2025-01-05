package reflection.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import reflection.data.Address;
import reflection.data.Cast;
import reflection.data.Company;
import reflection.data.Film;
import reflection.data.Person;

public class Main {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException  {
		Company company = new Company("란다우 연구소", "모스크바", new Address("아카데미카 세메노바 거리", (short) 1));
		Address address = new Address("서울 종로구 효자로", (short) 11241);
		Person person = new Person("란다우", true, 21, 200f, address, company);
		
		String json1 = toJson(person, 0);
		
		System.out.println(json1);
			
		Cast cast1 = new Cast("송강호", new String[] {"괴물", "설국열차", "기생충"});
		Cast cast2 = new Cast("김상경", new String[] {"몽타주", "사라진 밤"});
		Cast cast3 = new Cast("김뢰하", new String[] {"리턴", "작은 연못"});
		
		Film film = new Film("살인의 추억", 9.1f, new String[] {"봄죄", "드라마", "스릴러"}, new Cast[] {cast1, cast2, cast3});
		
		String json2 = toJson(film, 0);
		
		System.out.println(json2);
	}
	
	private static String toJson(Object object, int indentSize) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = object.getClass().getDeclaredFields();
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(indent(indentSize));
		stringBuilder.append("{");
		stringBuilder.append("\n");
		
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			
			if (field.isSynthetic()) {
				continue;
			}
			
			stringBuilder.append(indent(indentSize + 1));
			stringBuilder.append(formatString(field.getName()));
			stringBuilder.append(":");
			
			if (field.getType().isPrimitive()) {
				stringBuilder.append(formatPrimitive(field.get(object), field.getType()));
			} else if (field.getType().equals(String.class)) {
				stringBuilder.append(formatString(field.get(object).toString()));
			} else if (field.getType().isArray()) {
				stringBuilder.append(formatArray(field.get(object), indentSize + 1));
			} else {
				stringBuilder.append(toJson(field.get(object), indentSize + 1));
			}
			
			if (i != fields.length - 1) {
				stringBuilder.append(",");
			}
			
			stringBuilder.append("\n");
		}
		
		stringBuilder.append(indent(indentSize));
		stringBuilder.append("}");
		
		return stringBuilder.toString();
	}
	
	private static String formatArray(Object object, int indentSize) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder stringBuilder = new StringBuilder();
		int length = Array.getLength(object);
		
		Class<?> componentType = object.getClass().getComponentType();
		
		stringBuilder.append("[");
		stringBuilder.append("\n");
		
		for (int i = 0; i < length; i++) {
			Object component = Array.get(object, i);
			
			if (componentType.isPrimitive()) {
				stringBuilder.append(indent(indentSize + 1));
				stringBuilder.append(formatPrimitive(component, componentType));
			} else if (componentType.equals(String.class)) {
				stringBuilder.append(indent(indentSize + 1));
				stringBuilder.append(formatString(component.toString()));
			} else {
				stringBuilder.append(toJson(component, indentSize + 1));
			}
			
			if (i != length - 1) {
				stringBuilder.append(", ");
			}
			
			stringBuilder.append("\n");
		}

		stringBuilder.append(indent(indentSize));
		stringBuilder.append("]");
		
		return stringBuilder.toString();
	}
	
	private static String formatPrimitive(Object object, Class<?> type) throws IllegalArgumentException, IllegalAccessException {
		if (type.equals(boolean.class)
				|| type.equals(int.class) 
				|| type.equals(long.class)
				|| type.equals(short.class)) {
			return object.toString();
		} else if (type.equals(double.class) || type.equals(float.class)) {
			return String.format("%.02f", object);
		}
		
		throw new RuntimeException(String.format("지원되지 않음 타입: %s", type.getName()));
	}
	
	private static String formatString(String string) {
		return String.format("\"%s\"", string);
	}
	
	private static String indent(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		
		for (int i = 0; i < size; i++) {
			stringBuilder.append("\t");
		}
		
		return stringBuilder.toString();
	}
}