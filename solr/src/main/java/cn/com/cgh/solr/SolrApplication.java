package cn.com.cgh.solr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class SolrApplication {
    public static void main(String[] args) {
        SpringApplication.run(SolrApplication.class,args);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${server.port}")
    private String port;

    @RestController
    public class EchoController {
        @GetMapping(value = "/echo/{string}")
        public String echo(@PathVariable String string) {
            log.info("info {}",string);
            Long a = redisTemplate.opsForValue().increment("a", 1);
            redisTemplate.delete("a");
            return "Hello Nacos Discovery " + port + " " + string + " " + a;
        }
    }
}
