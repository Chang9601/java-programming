package reflection.coding02;

import java.util.HashSet;
import java.util.Set;

public class Recursion {
	public static Set<Class<?>> findAllImplementedInterfaces(Class<?> input) {
		Set<Class<?>> allImplementedInterfaces = new HashSet<>();
	        
        Class<?>[] inputInterfaces = input.getInterfaces();
        for (Class<?> currentInterface : inputInterfaces) {
            allImplementedInterfaces.add(currentInterface);
            allImplementedInterfaces.addAll(findAllImplementedInterfaces(currentInterface));
        }

        return allImplementedInterfaces;
	}
}