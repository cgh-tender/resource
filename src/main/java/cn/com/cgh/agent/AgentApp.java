package cn.com.cgh.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author root
 */
public class AgentApp {
    public static void premain(String agentOps, Instrumentation inst){
        System.out.println("enter premain");
        inst.addTransformer(new Agent());
    }
    public static void agentmain(String agentOps, Instrumentation inst){
        System.out.println("enter agentmain");
    }
}
