package reflection.configurationloader;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import reflection.data.GameConfiguration;
import reflection.data.UserInterfaceConfiguration;

public class Main {
	private static final Path GAME_CONFIGURATION_PATH = Path.of("resources/game-properties.cfg");
	private static final Path UI_CONFIGURATION_PATH = Path.of("resources/ui-properties.cfg");
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		GameConfiguration gameConfiguration = createConfigurationObject(GameConfiguration.class, GAME_CONFIGURATION_PATH);
		UserInterfaceConfiguration userInterfaceConfiguration = createConfigurationObject(UserInterfaceConfiguration.class, UI_CONFIGURATION_PATH);
		
		System.out.println(gameConfiguration);
		System.out.println(userInterfaceConfiguration);
	}
	
	
	public static <T> T createConfigurationObject(Class<T> clazz, Path path) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Scanner scanner = new Scanner(path);
		
		Constructor<?> constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true);
		
		T configurationInstance = (T) constructor.newInstance();
		
		while (scanner.hasNext()) {
			String configurationLine = scanner.nextLine();
			
			String[] keyValue = configurationLine.split("=");
			String key = keyValue[0];
			String value = keyValue[1];
			
			Field field;
			
			try {
				field = clazz.getDeclaredField(key);
			} catch (NoSuchFieldException exception) {
				System.out.println(String.format("지원되지 않는 키: %s", key));
				continue;
			}
			
			field.setAccessible(true);
			
			Object parsedValue = parseValue(field.getType(), value);
			
			field.set(configurationInstance, parsedValue);
		}
		
		return configurationInstance;
	}
	
	private static Object parseValue(Class<?> type, String value) {
		if (type.equals(int.class)) {
			return Integer.parseInt(value);
		} else if (type.equals(short.class)) {
			return Short.parseShort(value);
		} else if (type.equals(long.class)) {
			return Long.parseLong(value);
		} else if (type.equals(double.class)) {
			return Double.parseDouble(value);
		} else if (type.equals(float.class)) {
			return Float.parseFloat(value);
		} else if (type.equals(String.class)) {
			return value;
		}
		
		throw new RuntimeException(String.format("지원되지 않는 타입: %s", type.getTypeName()));
	}
}
