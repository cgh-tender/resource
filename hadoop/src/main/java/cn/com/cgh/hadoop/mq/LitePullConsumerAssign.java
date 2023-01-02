//package cn.com.cgh.hadoop.mq;
//
//import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.MessageExt;
//
//import java.util.List;
//
///**
// * pull consumer 优化代码
// */
//public class LitePullConsumerAssign {
//    public static volatile boolean running = true;
//    public static void main(String[] args) throws MQClientException {
//        DefaultLitePullConsumer consumerAssign = new DefaultLitePullConsumer("please_rename_unique_group_name");
//        consumerAssign.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumerAssign.subscribe("TopicTest","*");
//        consumerAssign.start();
//        try {
//            while (running){
//                List<MessageExt> poll = consumerAssign.poll();
//                System.out.printf("%s\n",poll);
//            }
//        }finally {
//            consumerAssign.shutdown();
//        }
//    }
//}
