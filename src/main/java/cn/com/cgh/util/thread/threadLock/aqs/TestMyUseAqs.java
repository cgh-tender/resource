package cn.com.cgh.util.thread.threadLock.aqs;

import java.util.concurrent.locks.Lock;

public class TestMyUseAqs {
    public static void main(String[] args) {

    }
    public static void main1(String[] args) {
        final Lock lock = new UseAqs();
        for (int i = 0; i < 4; i++){
            Thread thread = new Thread(() -> {
                lock.lock();
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
            thread.start();
        }
        for (int i = 0; i< 10; i++){
            try {
                Thread.sleep(1);
//                System.out.println();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
