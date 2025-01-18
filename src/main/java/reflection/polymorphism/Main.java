package reflection.polymorphism;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import reflection.database.DatabaseClient;
import reflection.http.HttpClient;
import reflection.logging.FileLogger;
import reflection.udp.UdpClient;

public class Main {
	public static void main(String[] args) throws Throwable {
		DatabaseClient databaseClient = new DatabaseClient();
		HttpClient httpClient1 = new HttpClient("123.456.789.0");
		HttpClient httpClient2 = new HttpClient("12.23.56.0");
		FileLogger fileLogger = new FileLogger();
		UdpClient udpClient = new UdpClient();
		
		String body = "데이터";
		
		List<Class<?>> methodParameterTypes = Arrays.asList(new Class<?>[] { String.class });
		
		Map<Object, Method> executors = groupExecutors(Arrays.asList(databaseClient, httpClient1, httpClient2, fileLogger, udpClient), methodParameterTypes);
		
		execute(executors, body);
	}
	
	public static Map<Object, Method> groupExecutors(List<Object> executors, List<Class<?>> methodParameterTypes) {
		Map<Object, Method> instanceToMethod = new HashMap<>();
		
		for (Object executor: executors) {
			Method[] methods = executors.getClass().getDeclaredMethods();
			
			for (Method method: methods) {
				if (Arrays.asList(method.getParameterTypes()).equals(methodParameterTypes)) {
					instanceToMethod.put(executor, method);
				}
			}
		}
		
		return instanceToMethod;
	}
	
	public static void execute(Map<Object, Method> executors, String body) throws Throwable {
		try {
			for (Map.Entry<Object, Method> entry: executors.entrySet()) {
				Object executor = entry.getKey();
				Method method = entry.getValue();
				
				Boolean result = (Boolean) method.invoke(executor, body);
				
				if (result != null && result.equals(Boolean.FALSE)) {
					System.out.println("오류 발생! 종료 중...");
					return;
				}
			}
		} catch (InvocationTargetException exceptoin) {
			throw exceptoin.getTargetException();
		}
	}
}