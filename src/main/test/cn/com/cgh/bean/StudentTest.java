package cn.com.cgh.bean;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class StudentTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }
    @Test
    public void test1(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("cn.com.cgh");
        Student bean = context.getBean(Student.class);
        System.out.println(bean.getName());
    }
    @Test
    public void test2(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring.xml");
//        context.setAllowBeanDefinitionOverriding(true);
//        context.setAllowCircularReferences(true);
//        context.refresh();
        Student bean = context.getBean(Student.class);
        System.out.println(bean.getName());
    }
    @Test
    public void test3(){
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("");
    }
    @Test
    public void test4(){
//        EmbeddedWebApplicationContext context = new EmbeddedWebApplicationContext();
    }
}