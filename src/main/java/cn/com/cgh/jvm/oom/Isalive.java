package cn.com.cgh.jvm.oom;

/**
 * 要可达认证
 * -XX:+PrintGC //-XX:+PrintGCDetails
 */
public class Isalive {
    public Object instance = null;
    private byte[] bi = new byte[10*1024*1024];

    public static void main(String[] args) {
        Isalive isalive1 = new Isalive();
        Isalive isalive2 = new Isalive();
        isalive1.instance = isalive2;
        isalive2.instance = isalive1;

        isalive1 = null;
        isalive2 = null;
        System.gc();
    }
}
