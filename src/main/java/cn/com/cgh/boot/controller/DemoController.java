package cn.com.cgh.boot.controller;

import lombok.Data;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author root
 */
@Controller
@RequestMapping("/test")
@Validated
public class DemoController {
    private static Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     *
     * @param name 传入名称
     * @return
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String test1(@Valid Names name) {
        return "hello " + name;
    }

    public void createActivitiTable(){
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        logger.info(processEngine.toString());
    }
    @PostConstruct
    public void p(){
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM aaa");
        System.out.println(result);
    }
}

