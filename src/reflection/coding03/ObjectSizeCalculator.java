package reflection.coding03;

import java.lang.reflect.Field;

public class ObjectSizeCalculator {
	private static final long HEADER_SIZE = 12;
    private static final long REFERENCE_SIZE = 4;
    
    public long sizeOfObject(Object object) throws IllegalAccessException {
    	long size = HEADER_SIZE + REFERENCE_SIZE;
    	Class<?> clazz = object.getClass();
        
    	for (Field field: clazz.getDeclaredFields()) {
			field.setAccessible(true);

    		if (field.getType().isPrimitive()) {
    			size += sizeOfPrimitive(clazz);
    		} else {
    			size += sizeOfString(((String) field.get(object)));
    		}
    	}
        
    	return size;
    }
    
    private long sizeOfPrimitive(Class<?> primitiveType) {
        if (primitiveType.equals(int.class)) {
            return 4;
        } else if (primitiveType.equals(long.class)) {
            return 8;
        } else if (primitiveType.equals(float.class)) {
            return 4;
        } else if (primitiveType.equals(double.class)) {
            return 8;
        } else if (primitiveType.equals(byte.class)) {
            return 1;
        } else if (primitiveType.equals(short.class)) {
            return 2;
        }
        
        throw new IllegalArgumentException(String.format("지원되지 않는 타입: %s", primitiveType));
    }
    
    private long sizeOfString(String string) {
        return HEADER_SIZE + REFERENCE_SIZE + string.getBytes().length;
    }    
}