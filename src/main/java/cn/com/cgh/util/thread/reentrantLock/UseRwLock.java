package cn.com.cgh.util.thread.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p> TODO
 * @author Haidar
 * @date 2020/8/28 14:32
 **/
public class UseRwLock implements Goods {
    private final ReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private final Lock getlock = reentrantLock.readLock();
    private final Lock setlock = reentrantLock.writeLock();

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
}
