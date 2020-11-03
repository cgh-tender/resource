package cn.com.cgh.util.quere.DelayQue;

import java.io.Serializable;

public class Order implements Serializable {
    private final String orderNo;
    private final double orderMoney;

    public Order(String orderNo, double orderMoney) {
        this.orderNo = orderNo;
        this.orderMoney = orderMoney;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                ", orderMoney=" + orderMoney +
                '}';
    }
}

