package cn.com.cgh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author haider
 * @date 2021年09月24日 10:48
 */
@Configuration
public class DBConfig {
//    @Bean
//    public DataSource dataSource(DBProperties dbProperties){
//        ComboPooledDataSource dataSource = new C omboPooledDataSource();
//
//        try{
//            dataSource.setJdbcUrl(dbProperties.getUrl());
//            dataSource.setDriverClass(dbProperties.getDriverClassName());
//            dataSource.setUser(dbProperties.getUsername());
//            dataSource.setPassword(dbProperties.getPassword());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return dataSource;
//    }
}
