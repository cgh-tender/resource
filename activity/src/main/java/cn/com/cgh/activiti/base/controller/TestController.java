package cn.com.cgh.activiti.base.controller;

import cn.com.cgh.solr.SolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

//    private final RestTemplate restTemplate;

//    @Autowired
//    public TestController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    @Autowired
    private SolrService solrService;

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) {
        return solrService.echo(str);
    }
}