package cn.com.cgh.agentApp;

import java.lang.instrument.Instrumentation;

/**
 * @author root
 */
public class AgentApp {
    /**
     * <plugin>
     *     <groupId>org.apache.maven.plugins</groupId>
     *     <artifactId>maven-jar-plugin</artifactId>
     *     <version>3.1.2</version>
     *     <configuration>
     *         <archive>
     *             <manifestEntries>
     *                 <Premain-Class>cn.com.cgh.agent.AgentApp</Premain-Class>
     *                 <agentmain-Class>AgentApp</agentmain-Class>
     *                 <Can-Redefine-Classes>true</Can-Redefine-Classes>
     *                 <Can-Retransform-Classes>true</Can-Retransform-Classes>
     *             </manifestEntries>>
     *             <manifest>
     *                 <addClasspath>true</addClasspath>
     *             </manifest>
     *         </archive>
     *     </configuration>
     * </plugin>
     * 运行前进行动态追踪 要知道代码
     * */
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("enter premain");
        inst.addTransformer(new Agent());
    }

    /**
     * 1. 获取机串运行的所有JVM进程ID
     * 2. 选择要诊断的JVM
     * 3. 将JVM使用attach函数链接上；
     * 4. 使用loadAgent函数加载agent动态修改字节码
     * 5. 卸载JVM
     * @param agentOps
     * @param inst
     */
    public static void agentmain(String agentOps, Instrumentation inst) {
        System.out.println("enter agentmain");
    }
}
