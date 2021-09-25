package cn.com.cgh.config.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.solr.SolrProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * <p> TODO
 * @author Haidar
 * @date 2020/8/14 11:54
 **/
@Configuration
public class SolrConfig {
    private static Logger logger = LoggerFactory.getLogger(SolrConfig.class);

    @Bean
    public SolrClient client(SolrResource host){
       try {
           return new HttpSolrClient.Builder(host.getHost()).build();
       } catch (Exception e) {
           logger.info(e.getMessage());
       }
       return null;
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client){
        if (client!=null){
            return new SolrTemplate(client);
        }
        return null;
    }

}
