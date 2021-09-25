package cn.com.cgh.util.thread.cyclicBarrier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * <p> barrier 有 4 个线程 
 * <p> 统计/汇总
 * @author Haidar
 * @date 2020/8/10 17:31
 **/
public class UseCyclicBarrier {
    private static CyclicBarrier barrier = new CyclicBarrier(4,new ConcurrentCount());
    private static ConcurrentHashMap<String,Long> hashMap = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++){
            Thread thread = new Thread(new ShutThread());
            thread.start();
        }
    }

    static class ConcurrentCount implements Runnable{
        @Override
        public void run() {
            StringBuilder builder = new StringBuilder("开始");
            hashMap.forEach((k,v) -> {
                builder.append(String.format("[%s]",v));
            });
            System.out.println(builder.toString());
        }
    }

    static class ShutThread implements Runnable{
        @Override
        public void run() {
            try {
                hashMap.put(Thread.currentThread().getId()+"",Thread.currentThread().getId());
                System.out.println(String.format("前 %s",Thread.currentThread().getId()));
                barrier.await();
                Thread.sleep(3000);
                hashMap.put(Thread.currentThread().getId()+"222",Thread.currentThread().getId());
                System.out.println(String.format("后 %s",Thread.currentThread()));
                barrier.await();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
