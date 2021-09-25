package cn.com.cgh.util.quere.DelayQue;

import java.util.concurrent.DelayQueue;

/**
 * 延时队列
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<ItemVo<Order>> queue = new DelayQueue<>();

        new Thread(new PutOrder(queue)).start();
        new Thread(new FetchOrder(queue)).start();

        for (int i = 0; i < 15; i++) {
            Thread.sleep(500);
            System.out.println(i*500);
        }
        ItemVo<Order> v2 = new ItemVo<>(8,
                new Order("Ta67890", 455));

        queue.offer(v2);
    }
}
