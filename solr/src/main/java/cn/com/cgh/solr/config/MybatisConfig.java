package cn.com.cgh.solr.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.com.cgh.solr.**.mapper")
public class MybatisConfig {
}
