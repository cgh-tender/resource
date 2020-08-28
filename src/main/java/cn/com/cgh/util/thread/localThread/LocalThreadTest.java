package cn.com.cgh.util.thread.localThread;

import java.util.Random;

public class LocalThreadTest {

    public static void main(String[] args) {

            for (int i = 0; i < 10; i++) {
                Random r = new Random();
                new Thread(()-> {
                    int i1 = r.nextInt(100);
                    Local.set(i1+ "");
                    System.out.println(String.format("%s - num : %d", Thread.currentThread().getName(), i1));
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(String.format("%s - num : %d", Thread.currentThread().getName(), Integer.parseInt(Local.get())));
                }).start();
            }
        }
    static class Local {
        public static ThreadLocal<String> data = new ThreadLocal<>();

        public static String get() {
            return data.get();
        }

        public static void set(String data) {
            Local.data.set(data);
        }
    }
}
