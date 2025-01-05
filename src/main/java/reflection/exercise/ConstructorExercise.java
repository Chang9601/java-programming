package reflection.exercise;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorExercise {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		printConstructorData(Person.class);
		printConstructorData(Address.class);
		
		Address address = createInstanceWithArguments(Address.class, "서울 종로구 효자로", 12);
		Person person = createInstanceWithArguments(Person.class, address, "란다우", 20);
		System.out.println(person);
	}
	
	public static <T> T createInstanceWithArguments(Class<T> clazz, Object ...args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Constructor<?> constructor: clazz.getDeclaredConstructors()) {
			if (constructor.getParameterTypes().length == args.length) {
				return (T) constructor.newInstance(args);
			}
		}
		
		System.out.println("생성자가 존재하지 않음");
		return null;
	}
	
	public static void printConstructorData(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		
		System.out.println(String.format("클래스 %s는 %d개의 선언 생성자를 소유", clazz.getSimpleName(), constructors.length));
		
		for (int i = 0; i < constructors.length; i++) {
			Class<?>[] parameterTypes = constructors[i].getParameterTypes();
			
			List<String> parameterTypeNames = Arrays.stream(parameterTypes).map(parameterType -> parameterType.getSimpleName()).collect(Collectors.toList());
			
			System.out.println(parameterTypeNames);
		}
		
		System.out.println();
	}
	
	public static class Person {
		private final Address address;
		private final String name;
		private final int age;
		
		public Person() {
			this.name = "홍길동";
			this.age = 0;
			this.address = null;
		}
		
		public Person(String name) {
			this.name = name;
			this.age = 0;
			this.address = null;
		}
		
		public Person(String name, int age) {
			this.name = name;
			this.age = age;
			this.address = null;
		}
		
		public Person(Address address, String name, int age) {
			this.name = name;
			this.age = age;
			this.address = address;
		}

		@Override
		public String toString() {
			return "Person [address=" + address + ", name=" + name + ", age=" + age + "]";
		}
	}
	
	public static class Address {
		private String street;
		private int number;
		
		public Address(String street, int number) {
			this.street = street;
			this.number = number;
		}

		@Override
		public String toString() {
			return "Address [street=" + street + ", number=" + number + "]";
		}
	}
}
