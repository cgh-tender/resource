package cn.com.cgh.jvm.oom;

/**
 * -Xms30m -Xmx30m -XX:+PrintGCDetails
 *
 * 堆 内存溢出 直接溢出  java.lang.OutOfMemoryError: Java heap space
 */
public class HeapOom {
    public static void main(String[] args) {
        String[] str = new String[35*1000*1000];

    }
}
