package cn.com.cgh.Id.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseDbBean {

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    @RefreshScope
    public DruidDataSource dataSource() {
        return new DruidDataSourceWrapper();
    }
}
