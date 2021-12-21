package cn.com.cgh.activiti.base.controller;

import cn.com.cgh.solr.SolrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RefreshScope // @Value 从接口调用自动变更
public class TestController {

//    private final RestTemplate restTemplate;

//    @Autowired
//    public TestController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    @Value("${user.name}")
    private String userName;

    @Autowired
    private SolrService solrService;

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) {
        return solrService.echo(str);
    }

    @GetMapping(value = "/show")
    public String show() {
        return userName;
    }
}