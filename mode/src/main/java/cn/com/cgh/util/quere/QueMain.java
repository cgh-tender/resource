package cn.com.cgh.util.quere;

public class QueMain {
    /**
     * ArrayBlockingQueue 一个由数组结构组成的有界队列
     * LinkedBlockingQueue 一个链表结构组成的有界队列
     * PriorityBlockingQueue 一个支持优先级排序的无界阻塞队列
     * DelayQueue 一个使用优先级队列实现的无界阻塞队列 定时推出数据
     * SynchronousQueue 一个不存储元素的阻塞队列 每一个put 必须要有一介task() 传递性的作业
     * LinkedTransferQueue 一个由链表结构组成的无界阻塞队列 传递
     *      如果有等代的 task()/poll() 的操作则put会直接传递给消费着 truTransfer/transfer 不会有存储的动作
     * LinkedBlockingDeque 一个由链表结构组成的双向阻塞队列
     */
}
