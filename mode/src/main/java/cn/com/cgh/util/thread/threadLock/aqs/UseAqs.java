package cn.com.cgh.util.thread.threadLock.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <p> 手写 aqs 锁
 * @author Haidar
 * @date 2020/8/28 15:40
 **/
public class UseAqs implements Lock {
    private final static int lockNum = 2;//不可为0

    class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean isHeldExclusively() {
            return getState() == lockNum;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0,arg)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == arg){
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(arg);
            return true;
        }

        protected Condition newCondition(){
            return new ConditionObject();
        }
    }

    private final Sync sync = new Sync();


    @Override
    public void lock() {
        System.out.println(String.format("%s ready get lock ",Thread.currentThread().getName()));
        sync.acquire(lockNum);
        System.out.println(String.format("%s already got lock ",Thread.currentThread().getName()));
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(lockNum);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(lockNum);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(lockNum,unit.toNanos(time));
    }

    public boolean isLock(){
        return sync.isHeldExclusively();
    }

    @Override
    public void unlock() {
        System.out.println(String.format("%s ready release lock ",Thread.currentThread().getName()));
        sync.release(0);
        System.out.println(String.format("%s already released lock ",Thread.currentThread().getName()));
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
