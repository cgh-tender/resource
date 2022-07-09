package cn.com.cgh.hadoop.mq.sort;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public class Producer {
    public static void main(String[] args) throws MQClientException {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
            producer.start();
            for (int i = 0; i < 10; i++) {
                int orderId = i;
                for (int j = 0; j < 5; j++) {
                    Message message = new Message("OrderTopicTest", "order_" + orderId, "KEY" + orderId, ("order_" + orderId + " step " + j).getBytes());
                    SendResult sendResult = producer.send(message, new MessageQueueSelector() {

                        @Override
                        public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                            Integer id = (Integer) o;
                            int index = id % list.size();
                            return list.get(index);
                        }
                    }, orderId);
                    System.out.printf("%s\n",sendResult);
                }
            }


        } catch (MQBrokerException e) {
            throw new RuntimeException(e);
        } catch (RemotingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}