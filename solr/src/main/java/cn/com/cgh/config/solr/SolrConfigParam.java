package cn.com.cgh.config.solr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p> TODO
 * @author Haidar
 * @date 2020/8/14 11:24
 **/
//@Configuration
public class SolrConfigParam {
    @Value("${spring.data.solr.host}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
