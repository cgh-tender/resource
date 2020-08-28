package cn.com.cgh.bean;

public class Tests {
    public static void main(String[] args) {
        String k = "aaaa?sssss";
        k = k.indexOf("?") == -1 ? k : k.substring(0,k.indexOf("?"));
        System.out.println(k);
        
    }
}
