package cn.com.cgh.util.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cgh
 * <p> CAS 原子操作 Compare And Swap
 * <p> CAS 比较 compare/交换 swap
 * <p>  相等 -> 交换 ; 不相等循环操作
 *
 * <p> AtomicLong/AtomicInteger 计数应用
 */
public class UseAtomicInteger {
    static AtomicInteger ai = new AtomicInteger(10);
    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.incrementAndGet());
        System.out.println();
    }
    
}
