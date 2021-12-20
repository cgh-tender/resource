package cn.com.cgh.openfeign.Interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestTemplate string = requestTemplate.query("string");
        log.info(" cn.com.cgh.feign 拦截嚣 {} ",string);
    }
}
