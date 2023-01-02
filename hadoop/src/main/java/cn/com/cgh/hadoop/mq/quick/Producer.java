//package cn.com.cgh.hadoop.mq.quick;
//
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//
//public class Producer {
//    public static void main(String[] args) throws MQClientException {
//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("localhost:9876");
//        producer.start();
//        for (int i = 0; i < 2; i++) {
//            try {
//                Message message = new Message("TopicTest", "TageA", ("Hello Rocket Mq " + i).getBytes());
//                message.setDelayTimeLevel(3);
//                SendResult send = producer.send(message);
//                System.out.printf("%s\n",send);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//}
