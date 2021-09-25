package cn.com.cgh.util.thread.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 手动进行 原子操作
 */
public class HalfAtomicInt {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    public void increment(){
        for (;;){
            int i = getCount();
            boolean suc = compareAndSet(i,++i);
            if (suc){
                break;
            }
        }
    }
    public int getCount(){
        return atomicInteger.get();
    }
    public boolean compareAndSet(int oldValue,int newValue){
        return atomicInteger.compareAndSet(oldValue,newValue);
    }

    public static void main(String[] args) {
        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        HalfAtomicInt halfAtomicInt = new HalfAtomicInt();
        for (int i = 0;i < threadNum; i++){
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    halfAtomicInt.increment();
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            System.out.println("等待");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(halfAtomicInt.getCount());
    }
}
