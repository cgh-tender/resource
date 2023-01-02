//package cn.com.cgh.hadoop.mq;
//
//import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
//import org.apache.rocketmq.client.consumer.PullResult;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.message.MessageQueue;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
///**
// * 拉模式
// */
//public class PullConsumer {
//    private static final Map<MessageQueue,Long> OFFSET_TABLE = new HashMap<>();
//
//    public static void main(String[] args) throws MQClientException {
//        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name");
//        consumer.setNamesrvAddr("127.0.0.1:");
//        consumer.start();
//
//        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest");
//        for (MessageQueue mq : mqs) {
//            System.out.printf("Consume from the queue %s \n",mq);
//            SINGLE_MQ:
//            while (true){
//                try {
//                    PullResult pull = consumer.pull(mq, "", getMessageQueueOffset(mq), 32);
//                    System.out.printf("%s \n",pull);
//                    putMessageQueueOffset(mq,pull.getNextBeginOffset());
//                    switch (pull.getPullStatus()){
//                        case NO_NEW_MSG:
//                            break SINGLE_MQ;
//                        default:
//                            break;
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//        consumer.shutdown();
//    }
//
//    private static void putMessageQueueOffset(MessageQueue mq, long nextBeginOffset) {
//        OFFSET_TABLE.put(mq,nextBeginOffset);
//    }
//
//    private static long getMessageQueueOffset(MessageQueue mq) {
//        if (OFFSET_TABLE.containsKey(mq)){
//            return OFFSET_TABLE.get(mq);
//        }
//        return 0;
//    }
//}
