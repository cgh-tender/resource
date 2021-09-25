package cn.com.cgh.util.quere.DelayQue;

import java.util.concurrent.DelayQueue;

public class PutOrder implements Runnable {
    private DelayQueue<ItemVo<Order>> queue;

    public PutOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        ItemVo<Order> v1 = new ItemVo<>(5,
                new Order("Tb12345", 366));

        ItemVo<Order> v2 = new ItemVo<>(7,
                new Order("Tb67890", 455));

        queue.offer(v1);
        queue.offer(v2);
    }
}
