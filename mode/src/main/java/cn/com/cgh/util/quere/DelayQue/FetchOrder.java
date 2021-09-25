package cn.com.cgh.util.quere.DelayQue;

import java.util.concurrent.DelayQueue;

public class FetchOrder implements Runnable {
    private DelayQueue<ItemVo<Order>> queue;

    public FetchOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                ItemVo<Order> take = queue.take();
                Order order = take.getData();
                System.out.println(order.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
