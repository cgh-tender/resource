package cn.com.cgh.annotation;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@Configurable
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource(){
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//
//        try{
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return dataSource;
        return null;
    }
}
