package cn.com.cgh.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConditionalOnProperty(name = "dWCacheType",havingValue = "2")
public class CtgBean {
    @PostConstruct
    public void in(){
        System.out.println("1111111111111111111111111111111111111111111111111111111111");
    }
}
