package cn.com.cgh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseBeanCon {
//    @Value("${dWCacheType}")
    private String dWCacheType;

    public String getdWCacheType() {
        return dWCacheType;
    }

    public void setdWCacheType(String dWCacheType) {
        this.dWCacheType = dWCacheType;
    }
}
