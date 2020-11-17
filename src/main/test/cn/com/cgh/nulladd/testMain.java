package cn.com.cgh.nulladd;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class testMain {
    public static void main(String[] args) {
//        HashMap<String, String> hashMap = new HashMap<String, String>();
//        System.out.println(hashMap.get("11")+","+hashMap.get("2222"));
//        Timer timer = new Timer();
//        AtomicInteger atomicInteger = new AtomicInteger(10);
//
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (atomicInteger.get() == 0)throw new RuntimeException();
//                atomicInteger.getAndDecrement();
//                System.out.println("111111");
//            }
//        },100,100);
        byte a = -5;
        byte c = (byte) (-5>>>1);
        System.out.println(c);
    }
}
