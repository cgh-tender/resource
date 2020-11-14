package cn.com.cgh.agent.brace;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;



/**
 * @author root
 */
@BTrace(unsafe=true)
public class Btrace {

    @OnMethod(
            clazz = "cn.com.cgh.boot.controller.DemoController",
            method = "test1",
            location = @Location(Kind.ENTRY)
    )
    public static void checkEntery(
            @ProbeClassName String pcn,
            @ProbeMethodName String pmn,
            AnyType[] args
    ){
        BTraceUtils.println("Class: " + pcn);
        BTraceUtils.println("Method: " + pmn);
        BTraceUtils.printArray(args);
        BTraceUtils.println("===================");
    }

}
