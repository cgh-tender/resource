package cn.com.cgh.config.solr;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author haider
 * @date 2021年09月24日 11:33
 */
@Component
@ConfigurationProperties(prefix = "spring.data.solr")
public class SolrResource {
    private String host;
    private String collection;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
