package reflection.coding05;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayFlattening {
    public <T> T concat(Class<?> type, Object... arguments) {
        if (arguments.length == 0) {
            return null;
        }
        
        List<Object> array = new ArrayList<>();
        
        for (int i = 0; i < arguments.length; i++) {
        	Object argument = arguments[i];
        	
        	if (argument.getClass().isArray()) {        		
        		for (int j = 0; j < Array.getLength(argument); j++) {
        			array.add(Array.get(argument, j));
           		}
        		
        	} else {
        		array.add(argument);        		
        	}
        }
        
        Object flattenedArray = Array.newInstance(type, array.size());
        
        for (int i = 0; i < array.size(); i++) {
        	Array.set(flattenedArray, i, array.get(i));
        }
        
        return (T) flattenedArray;
    }
}