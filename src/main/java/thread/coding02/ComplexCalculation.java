package thread.coding02;

import java.math.BigInteger;

public class ComplexCalculation {
    public BigInteger compute(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result;

        PowerComputation thread1 = new PowerComputation(base1, power1);
        PowerComputation thread2 = new PowerComputation(base2, power2);

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        result = thread1.getResult().add(thread2.getResult());        

        return result;
    }

    private static class PowerComputation extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;
    
        public PowerComputation(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }
    
        @Override
        public void run() {
			for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
				result = result.multiply(base);
			}
        }
    
        public BigInteger getResult() { return result; }
    }
}