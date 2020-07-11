package cn.com.cgh.bean;


import cn.com.cgh.invock.C;
import cn.com.cgh.invock.Peple;
import cn.com.cgh.invock.prov;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;

public class MainTest {
    private ApplicationContext context;
    @Before
    public void before(){
        context = new AnnotationConfigApplicationContext("cn.com.cgh");
    }
    @Test
    public void invoke(){
        Peple bean = context.getBean(Peple.class);
        bean.eat("不是好吃的");
        Peple o = (Peple) Proxy.newProxyInstance(MainTest.class.getClassLoader(), new Class<?>[]{Peple.class}, new prov(new C()));
        o.eat("好吃的");
    }
}
