package cn.com.cgh.jvm.sync;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j(topic = "enjoy")
public class SyncTest {
    public static Object object = new Object();
    public static void main(String[] args) {
        /*打印出 对象头地址*/
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
        synchronized (object){
            System.out.println();
            System.out.println(ClassLayout.parseInstance(object).toPrintable());
        }
    }
}
