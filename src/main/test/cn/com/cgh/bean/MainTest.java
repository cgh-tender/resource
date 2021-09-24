package cn.com.cgh.bean;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class MainTest {
    private ApplicationContext context;
    @Before
    public void before(){
        context = new AnnotationConfigApplicationContext("cn.com.cgh");
//        context = new AnnotationConfigApplicationContext(CghApplication.class);
    }
    @Test
    public void invoke(){
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM aaa");
        System.out.println(result);
    }
}
