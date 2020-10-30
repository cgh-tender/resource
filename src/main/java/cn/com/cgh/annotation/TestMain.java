package cn.com.cgh.annotation;

import cn.com.cgh.CghApplication;
import cn.com.cgh.bean.Student;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMain {
    private ApplicationContext applicationContext;
    @Before
    public void before(){
        applicationContext =
                new AnnotationConfigApplicationContext(CghApplication.class);
    }

    @Before
    @Ignore
    public void bef(){
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    }
    @Test
    public void aaa(){
        applicationContext.getBean(Student.class);
    }
}
