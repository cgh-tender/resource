package cn.com.cgh.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConditionalOnProperty(name = "dWCacheType",havingValue = "5")
public class RestBean {
    @PostConstruct
    public void in(){
        System.out.println("2222222222222222222222");
    }
}
