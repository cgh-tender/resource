package cn.com.cgh.jvm.sync;

import lombok.extern.log4j.Log4j2;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author root
 * 000
 * 0 是否可以偏向 0 不可偏向 1 可偏向
 * 01 偏向锁
 * 00 轻量锁
 * 10 重量锁 mutex 互斥量   pthread_create_t()创建Thread 互斥量（pthread_metex_t），自旋锁（pthread_spin_t 空转），信号量
 * 11 GC
 * -XX:BiasedLockingStartupDelay=0
 * -XX:-BiasedLocking
 * -XX:+BiasedLocking
 */
@Log4j2(topic = "SyncTest")
public class SyncTest {
    public static void main(String[] args) {
        L l = new L();
//        log.info(Integer.toHexString(l.hashCode()));
        /*打印出 对象头地址*/
        log.info(ClassLayout.parseInstance(l).toPrintable());
        synchronized (l){
            log.info(ClassLayout.parseInstance(l).toPrintable());
        }
        log.info(ClassLayout.parseInstance(l).toPrintable());
    }
    private static class L{

    }
}
