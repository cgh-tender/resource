package cn.com.cgh.activiti;

import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class CreateBean {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    private RuntimeService runtimeService;

    @PostConstruct
    public void p(){
        System.out.println("=============");
        System.out.println(runtimeService);
        System.out.println(processEngineConfiguration);
    }
}
