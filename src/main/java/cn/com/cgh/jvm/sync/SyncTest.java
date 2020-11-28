package cn.com.cgh.jvm.sync;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author root
 * 000
 * 0 是否可以偏向 0 不可偏向 1 可偏向
 * 00 轻量锁
 * 01 偏向锁
 * 10 重量锁
 * 11 GC
 */
@Slf4j(topic = "SyncTest")
public class SyncTest {
    public static Object object = new Object();
    public static void main(String[] args) {
        log.info("1111111111");
        /*打印出 对象头地址*/
//        System.out.println(ClassLayout.parseInstance(object).toPrintable());
        synchronized (object){
            System.out.println();
            System.out.println(ClassLayout.parseInstance(object).toPrintable());

        }
    }
}
