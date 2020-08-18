package cn.com.cgh.util.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomicInteger {
    static AtomicInteger ai = new AtomicInteger(10);
    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.incrementAndGet());
        System.out.println();
    }
    
}
