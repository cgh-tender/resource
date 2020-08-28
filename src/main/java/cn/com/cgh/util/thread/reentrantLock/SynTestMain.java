package cn.com.cgh.util.thread.reentrantLock;

import java.util.Random;

public class SynTestMain {
    private final static int readWriteThreadNum = 10;
    private final static int minThreadCount = 3;

    private static Goods goods;

    static class getThread implements Runnable{
        private Goods goods;

        public getThread(Goods goods) {
           this.goods = goods;
        }

        @Override
        public void run() {
            long l = System.currentTimeMillis();
            for (int m1 = 0; m1 < 100; m1++) {
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.goods.getNum();
            }
            System.out.println(String.format("%s get time : %dms", Thread.currentThread().getName(), System.currentTimeMillis() - l));
        }
    }
    static class setThread implements Runnable{
        private Goods goods;

        public setThread(Goods goods) {
            this.goods = goods;
        }

        @Override
        public void run() {
            long l = System.currentTimeMillis();
            Random r = new Random();
            for (int m = 0; m < 100; m++) {
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                goods.setNum(r.nextInt(10));
            }
            System.out.println(String.format("%s set time : %dms", Thread.currentThread().getName(), System.currentTimeMillis() - l));
        }
    }
    public static void main(String[] args) {
        goods = new UseRwLock(new GoodInfo());
//        goods = new GoodsImpl(new GoodInfo());

        for (int i = 0 ; i< minThreadCount; i++){
            Thread TS = new Thread(new setThread(goods));
            for (int j = 0; j < readWriteThreadNum; j ++){
                Thread TR = new Thread(new getThread(goods));
                TR.start();
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            TS.start();
        }


    }
}
