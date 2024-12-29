package reflection;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		initializeServerConfiguration();
		
		Server server = new Server();
		server.startServer();
	}
	
	public static void initializeServerConfiguration() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<ServerConfiguration> constructor = ServerConfiguration.class.getDeclaredConstructor(int.class, String.class);
		
		constructor.setAccessible(true);
		constructor.newInstance(8080, "안녕!");
 	}
}