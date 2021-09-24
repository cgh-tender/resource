package cn.com.cgh.util.thread.countDown;

import java.util.concurrent.CountDownLatch;

/**
 * <p> 初始化
 * @author Haidar
 * @date 2020/8/10 18:12
 **/
public class UseCountDown {
    private static CountDownLatch downLatch = new CountDownLatch(4);

    public static void main(String[] args) {
        new Thread(new DownOneThread()).start();
        new Thread(new DownOneThread()).start();
        new Thread(new DownOneThread()).start();
        new Thread(new DownTwoThread()).start();
//        try {
//            System.out.println("等待");
//            downLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("结束");
    }

    static class DownTwoThread implements Runnable{
        @Override
        public void run() {
            downLatch.countDown();
            System.out.println("1111-" + Thread.currentThread().getId());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class DownOneThread implements Runnable{
        @Override
        public void run() {
            downLatch.countDown();
            System.out.println("2222-" + Thread.currentThread().getId());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            downLatch.countDown();
        }
    }
}
