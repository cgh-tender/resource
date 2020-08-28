package cn.com.cgh.config.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
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
//@ImportResource({"classpath:spring/spring-solr.xml"})
@PropertySource("classpath:application.properties")
public class SolrConfig {

    @Bean
    public SolrClient client(@Value("${spring.data.solr.host}")String host){
        return new HttpSolrClient.Builder(host).build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client){
        return new SolrTemplate(client);
    }

}
