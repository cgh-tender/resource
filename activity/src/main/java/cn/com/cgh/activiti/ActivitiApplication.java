package cn.com.cgh.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
//@EnableDiscoveryClient
//@RibbonClients(
//        value = {
//                @RibbonClient(name = "solr",configuration = RibbonRandomRuleConfig.class)
//        }
//)
public class ActivitiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class,args);
    }
}
