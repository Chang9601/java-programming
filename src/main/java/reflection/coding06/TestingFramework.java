package reflection.coding06;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestingFramework {
    public void runTestSuite(Class<?> testClass) throws Throwable {
		Method[] methods = testClass.getMethods();
		
		Method beforeClassMethod = findMethodByName(methods, "beforeClass");
		Method setupTestMethod = findMethodByName(methods, "setupTest");
		List<Method> testMethods = findMethodsByPrefix(methods, "test");
		Method afterClassMethod = findMethodByName(methods, "afterClass");
		
		if (beforeClassMethod != null) {
			beforeClassMethod.invoke(null);
		}
	
		for (Method testMethod: testMethods) {
			Object testObject = testClass.getDeclaredConstructor().newInstance();
			
			if (setupTestMethod != null) {
				setupTestMethod.invoke(testObject);
			}
			
			testMethod.invoke(testObject);
		}
		
		if (afterClassMethod != null) {
			afterClassMethod.invoke(null);
		}

      }

      private Method findMethodByName(Method[] methods, String name) {
    	for (Method method: methods) {
    		if (method.getName().equals(name) 
    				&& method.getParameterCount() == 0 
    				&& method.getReturnType() == void.class) {
    			
    			return method;
    		}
    	}
    	
    	return null;
      }

      private List<Method> findMethodsByPrefix(Method[] methods, String prefix) {
    	  List<Method> foundMethods = new ArrayList<>();
    	  
    	  for (Method method: methods) {
    		  if (method.getName().startsWith(prefix) 
    				  && method.getParameterCount() == 0 
    				  && method.getReturnType() == void.class) {
    			  
    			  foundMethods.add(method);
    		  }
    	  }
    	  
    	  return foundMethods;
      }
}