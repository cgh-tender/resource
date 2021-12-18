//package cn.com.cgh.agent.attach;
//
//
//import com.sun.tools.attach.AttachNotSupportedException;
//import com.sun.tools.attach.VirtualMachine;
//
//import java.io.IOException;
//import java.util.*;
//
///**
// * 动态追踪
// * @author root
// */
//public class Attach1 {
//    public static void main(String[] args) {
////        16420 pid
//        VirtualMachine vm = null;
//        try {
//           vm = VirtualMachine.attach("16420");
//            Properties properties = vm.getSystemProperties();
//            String property = properties.getProperty("java.version");
//            System.out.println(property);
//            properties.keySet().forEach(k->System.out.println(k + "    " +  properties.get(k)));
//        } catch (AttachNotSupportedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (null != vm){
//                try {
//                    vm.detach();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
