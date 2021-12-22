package cn.com.cgh;

/**
 * @author haider
 * @date 2021年12月06日 18:39
 */
public class test1 {
    public static void main(String[] args) {
        String aa = "User1{id=1, name='fanlan', password='null'}";


        System.out.println(aa.replaceAll("^[\\d\\w]+\\{","{"));
    }
}
