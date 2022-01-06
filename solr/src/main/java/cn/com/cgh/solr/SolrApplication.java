package cn.com.cgh.solr;

import cn.com.cgh.file.util.EnableCghFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCghFile
public class SolrApplication {
    public static void main(String[] args) {
        SpringApplication.run(SolrApplication.class,args);
    }
}
