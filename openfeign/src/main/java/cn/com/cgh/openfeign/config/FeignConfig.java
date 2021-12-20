package cn.com.cgh.openfeign.config;

import feign.Logger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(value = {
        "cn.com.cgh.solr.**"
})
public class FeignConfig {
    /**
     * 全局 Feign log
     */
//    @Bean
//    public Logger.Level feignLoggerLevel(){
//        return Logger.Level.FULL;
//    }
}
