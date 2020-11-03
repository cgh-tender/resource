package cn.com.cgh.util.thread.reentrantLock;

import com.jcraft.jsch.jce.HMACSHA256;
import com.nimbusds.jose.util.Base64URL;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p> TODO
 * @author Haidar
 * @date 2020/8/28 14:32
 **/
public class UseRwLock implements Goods {
    private static final ReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private static final Lock getlock = reentrantLock.readLock();
    private static final Lock setlock = reentrantLock.writeLock();

    private GoodInfo goodInfo;

    public UseRwLock(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public void setNum(int num) {
        setlock.lock();
        try {
            Thread.sleep(5);
            goodInfo.chenageNum(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
          setlock.unlock();
        }

    }

    @Override
    public GoodInfo getNum() {
        getlock.lock();
        try {
            Thread.sleep(5);
            return goodInfo;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            getlock.unlock();
        }
        return goodInfo;
    }

    public static void main(String[] args) {
        Base64URL encode = Base64URL.encode("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJUT0tFTiIsInBhc3N3b3JkIjoiMSIsInJvbGUiOiIiLCJpcCI6IjE5Mi4xNjguMi4xOTkiLCJuYW1lIjoiYWRtaW4iLCJleHAiOjE1OTk3MDkxODQsImlhdCI6MTU5OTcwOTEyNCwidXVpZCI6NDYzODU5ODY3NTE3NDI3NzEyLCJqdGkiOiI0NjM4NTk4Njc1MTc0Mjc3MTIifQ");
        System.out.println(encode);
//        ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
//        for (int i = 0; i < 10; i++) {
//            int finalI = i;
//            new Thread(()->{
//                System.out.println(finalI);
//                setlock.lock();
//                try {
//                    Thread.sleep(3000);
//                } catch (Exception e) {
//                    // TODO: handle exception
//                    e.printStackTrace();
//                }finally {
//                    setlock.unlock();
//                }
//                System.out.println(finalI);
//            }).start();
//
//        }
    }
}
