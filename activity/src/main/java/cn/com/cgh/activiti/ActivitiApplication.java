package cn.com.cgh.activiti;

import cn.com.common.exception.EnableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient
//@RibbonClients(
//        value = {
//                @RibbonClient(name = "solr",configuration = RibbonRandomRuleConfig.class)
//        }
//)
@Slf4j
@EnableException
public class ActivitiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }
}
