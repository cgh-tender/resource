package cn.com.cgh.util.thread.threadLock.aqs;

import org.apache.tools.ant.taskdefs.Sleep;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可重入锁/ 所谓锁的公平和非公平
 */
public class lockCase {
    private Lock lock = new ReentrantLock();

    int anInt = 0;

    public static class test1 extends Thread{
        private lockCase aCase;
        public test1(lockCase aCase){
            this.aCase = aCase;
        }
        @Override
        public void run() {
            for (int i = 0; i<10000;i++){
                aCase.add();
            }
        }
    }
    public static class test2 extends Thread{
        private lockCase aCase;
        public test2(lockCase aCase){
            this.aCase = aCase;
        }
        @Override
        public void run() {
            for (int i = 0; i<10000;i++){
                aCase.del();
            }
        }
    }

    public void add(){
        lock.lock();
        try{
            ++anInt;
        }finally {
            lock.unlock();
        }
    }
    public void del(){
        lock.lock();
        try{
            --anInt;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        lockCase lockCase = new lockCase();
        new test1(lockCase).start();
        new test2(lockCase).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(lockCase.anInt);
//        Executors
    }

}
