package cn.com.cgh.annotation;

import cn.com.cgh.CghApplication;
import cn.com.cgh.bean.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMain {
    private AnnotationConfigApplicationContext applicationContext;
    @Before
    public void before(){
        applicationContext =
                new AnnotationConfigApplicationContext(CghApplication.class);

    }
    @Test
    public void aaa(){
        applicationContext.getBean(Student.class);
    }
}
