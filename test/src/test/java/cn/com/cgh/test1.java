//package cn.com.cgh;
//
//import cn.hutool.core.collection.ListUtil;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @author haider
// * @date 2021年12月06日 18:39
// */
//public class test1 {
//    public static void main(String[] args) {
//        Set<Integer> resource = new HashSet<>();
//        for (int i = 0; i < 1000; i++) {
//            resource.add(i);
//        }
//        for (Integer integer : resource) {
//
//        }
//        List<List<Integer>> lists = ListUtil.split(new ArrayList<>(resource), 100);
//        System.out.println(lists.size());
//    }
//    @Test
//    public void aa(){
//        System.out.println(1);
//    }
////    public static ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
////    public static Dispatch sapo=sap.getObject();
//    @Test
//    public void aVoid(){
//
//    }
//}
