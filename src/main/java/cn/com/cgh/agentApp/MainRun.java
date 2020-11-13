package cn.com.cgh.agentApp;

/**
 * jvm: -javaagent:F:\My_Work_Base\spring_base\resource\target\resource.jar
 */
public class MainRun {
    public static void main(String[] args) {
        hello("world");
    }
    private static void hello(String name){
        System.out.println("hell0 " + name);
    }
}
