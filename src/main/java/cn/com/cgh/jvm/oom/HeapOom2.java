package cn.com.cgh.jvm.oom;

import java.util.LinkedList;
import java.util.List;

/**
 * -Xms30m -Xmx30m -XX:+PrintGCDetails // -XX:+HeapDumpOnOutOfMemoryError
 *  java.lang.OutOfMemoryError: GC overhead limit exceeded
 *  GC 占据98%的资源 回收不足2%
 */
public class HeapOom2 {
    public static void main(String[] args) throws InterruptedException {
        List<Object> list = new LinkedList<>();
        int i = 0;
        while (true){
            i++;
            if ((i%1000 == 0)) Thread.sleep(10);
            list.add(new Object());
        }
    }
}
