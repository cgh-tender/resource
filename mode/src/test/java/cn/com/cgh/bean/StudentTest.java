package cn.com.cgh.bean;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class StudentTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
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