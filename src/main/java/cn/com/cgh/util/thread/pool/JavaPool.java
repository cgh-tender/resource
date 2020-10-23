package cn.com.cgh.util.thread.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class JavaPool {
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("pool-%d").build();

    public static ThreadPoolExecutor getPool(){
        return new ThreadPoolExecutor(2, 4,
                1000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }
    public static void main(String[] args) {
       base1(1);
       base2();

//        getPool().shutdown();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        System.out.println("=========");
        base1(2);
        System.out.println("=========");
    }

    public static void base1(int j){
        System.out.println(j);
        try {
            ThreadPoolExecutor pool = getPool();
            for (int i = 0; i < 2; i++) {
                pool.submit(()->{
                    System.out.print(Thread.currentThread().getName() + " base1 start ");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    System.out.print(Thread.currentThread().getName() + " base1 end ");
                    System.out.println();
                });
            }
            System.out.println("1111111");
            pool.shutdown();
//            pool.shutdownNow();

            System.out.println("1111111====");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {

        }
    }
    public static void base2(){
        try {
            ThreadPoolExecutor pool = getPool();
            for (int i = 0; i < 3; i++) {
                pool.submit(()->{
                    System.out.print(Thread.currentThread().getName() + " base2 start ");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    System.out.print(Thread.currentThread().getName() + " base2 end ");
                    System.out.println();
                });
            }
            System.out.println("123123123");
            pool.shutdown();
//            pool.shutdownNow();
            System.out.println("123123123====");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
        }
    }
    
}
