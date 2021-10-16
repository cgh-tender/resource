package cn.com.cgh.activiti.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CreateBean implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        DataSource dataSource = applicationContext.getBean(DataSource.class);
//        System.out.println(dataSource);
    }
}