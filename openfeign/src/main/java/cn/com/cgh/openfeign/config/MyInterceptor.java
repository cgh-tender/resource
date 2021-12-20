package cn.com.cgh.openfeign.config;

import cn.com.cgh.openfeign.Interceptor.CustomFeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class MyInterceptor {
    @Bean
    public RequestInterceptor getRequestInterceptor(){
        return new CustomFeignInterceptor();
    }
}
