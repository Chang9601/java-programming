package reflection.coding04;

import java.lang.reflect.Array;

public class ArrayReader {
	public Object getArrayElement(Object object, int index) {
		int length = Array.getLength(object);
		Object component;
		
		if (index >= 0) {
			component = Array.get(object, index);
		} else {
			component = Array.get(object, index + length);
		}
		
		return component;
	}
}