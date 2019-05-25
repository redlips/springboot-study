package com.redlips;

/**
 * @author qiaotong
 * @create 2019-01-16 16:41
 * @description
 */
public class PrimeGenerator extends Thread {
    @Override
    public void run() {
        long number = 1L;
        while (true) {
            if (isPrime(number)) {
                System.out.printf("Number %d is prime", number);
                System.out.println();
            }
            if (isInterrupted()) {
                System.out.printf("The Prime Generator has been Interrupted");
                return;
            }
            number++;
        }
    }

    /**
     * 判断是否是质数
     */
    private boolean isPrime(long number) {
        if (number <= 2)
            return true;
        for (long i = 2; i < number; i++) {
            if ((number % i == 0))
                return false;
        }
        return true;
    }
}
