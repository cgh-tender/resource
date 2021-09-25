package cn.com.cgh.util.thread.reentrantLock;

public class GoodsImpl implements Goods{
    private GoodInfo goodInfo;
    public GoodsImpl(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public synchronized void setNum(int num) {
        try { 
            Thread.sleep(5);
            goodInfo.chenageNum(num);
        } catch (Exception e) {
        }

    }

    @Override
    public synchronized GoodInfo getNum() {
        try {
            Thread.sleep(5);
            return goodInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodInfo;
    }
}
