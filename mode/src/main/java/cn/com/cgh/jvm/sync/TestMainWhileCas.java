package cn.com.cgh.jvm.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author root
 */
@Slf4j(topic = "TestMainWhileCas")
public class TestMainWhileCas {
    public static void add(int i,AtomicInteger integer) {
        while (!integer.compareAndSet(integer.get(),integer.get()+i)){

        }
    }
    public static void startThread(AtomicInteger integer) throws InterruptedException {
        List<Thread> list = new ArrayList();
        for (int i = 0; i < 222; i++) {
            list.add(new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    add(1,integer);
                }
            }));
        }
        for (Thread thread : list) {
            thread.start();
        }
        Thread.sleep(20);
        log.info(String.valueOf(integer.get()));
    }

    public static void main(String[] args) throws InterruptedException {
        for (int ji = 0; ji < 100; ji++) {
            startThread(new AtomicInteger(0));
        }
    }
}
