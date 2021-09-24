package cn.com.cgh;

public class MyTest {
    public static void main(String[] args) {

//        System.out.println(Integer.toBinaryString(1<<1));
//        System.out.println(Integer.toBinaryString(1<<2));
//        System.out.println(Integer.toBinaryString(1<<3));
        System.out.println(Integer.toBinaryString(15));
        System.out.println(Integer.toBinaryString(~4));
        System.out.println(Integer.toBinaryString(4));
        System.out.println(Integer.toBinaryString(15&~4));
        System.out.println(Integer.toBinaryString(15&4));
        System.out.println(Runtime.getRuntime().availableProcessors());
//        System.out.println(Integer.toBinaryString(4));
//        System.out.println(~4);
    }
}
