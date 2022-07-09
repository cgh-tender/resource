package cn.com.cgh.hadoop.mq;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * pull 定制化消费
 */
public class LitePullConsumerSubscribe {
    private static final List<MessageQueue> assignList = new ArrayList<MessageQueue>();
    private static final boolean running = true;
    public static void main(String[] args) throws MQClientException {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("please_rename_unique_group_name");
        consumer.setAutoCommit(false);
        consumer.start();
        Collection<MessageQueue> topicTest = consumer.fetchMessageQueues("TopicTest");
        ArrayList<MessageQueue> queues = new ArrayList<>(topicTest);
        for (int i = 0; i < queues.size() /2; i++) {
            assignList.add(queues.get(i));
        }
        consumer.assign(assignList);
        consumer.seek(assignList.get(0),10);
        try {
            while (running){
                List<MessageExt> poll = consumer.poll();
                System.out.printf("%s\n",poll);
                consumer.commitSync();
            }
        }finally {
            consumer.shutdown();
        }

    }
}
