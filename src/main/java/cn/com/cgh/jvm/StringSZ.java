package cn.com.cgh.jvm;

import java.util.Random;

/**
 * -XX:+PrintGC
 * -XX:+PrintGCDetails
 */
public class StringSZ {

    public static void main(String[] args) {
        String[] aa = new String[5];
        Random random = new Random(Long.MAX_VALUE);
        while (true){
            for (int i = 0; i < aa.length; i++) {
                aa[i] = random.longs(Long.MAX_VALUE) + "";
            }
        }
//        System.out.println(aa);
    }
}
