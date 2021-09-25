package cn.com.cgh.util.quere.DelayQue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ItemVo<T> implements Delayed {
    private long activeTime;
    private T data;

    /**
     * 秒/ 数据
     * @param expirationTime
     * @param data
     */
    public ItemVo(long expirationTime, T data) {
        this.activeTime = expirationTime * 1000 + System.currentTimeMillis();
        this.data = data;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public T getData() {
        return data;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.activeTime
                        - System.currentTimeMillis()
                ,unit);
    }

    @Override
    public int compareTo(Delayed o) {
        long d = (getDelay(TimeUnit.MICROSECONDS)
        - o.getDelay(TimeUnit.MICROSECONDS));
        if(d == 0){
            return 0;
        }else if (d<0){
            return -1;
        }else {
            return 1;
        }
    }
}
