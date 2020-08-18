package cn.com.cgh.config.solr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * <p> TODO
 * @author Haidar
 * @date 2020/8/14 11:54
 **/
@Component
@ImportResource({"classpath:spring/spring-solr.xml"})
public class SolrConfig {
    @Autowired
    private SolrTemplate solrTemplate;

    //    @Bean
//    public SolrTemplate solrTemplate(@Qualifier("solrClient") SolrClient client){
//        SolrTemplate solrTemplate = new SolrTemplate(client);
//        return solrTemplate;
//    }

}
