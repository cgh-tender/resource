package cn.com.cgh.openfeign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class ReginFeignConfig {
    /**
     * 全局 Feign log
     */
//    @Bean
//    public Logger.Level feignLoggerLevel(){
//        return Logger.Level.FULL;
//    }
}
