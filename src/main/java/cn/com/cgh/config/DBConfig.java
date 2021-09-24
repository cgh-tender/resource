package cn.com.cgh.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author haider
 * @date 2021年09月24日 10:48
 */
@Configuration
public class DBConfig {
    private static Logger logger = LoggerFactory.getLogger(DBConfig.class);
    @Autowired
    private DataSourceProperties basicProperties;
    @PostConstruct
    public void p(){
        logger.info(basicProperties.getUrl());
    }
}
