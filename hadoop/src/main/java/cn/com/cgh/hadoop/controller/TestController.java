package cn.com.cgh.hadoop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @GetMapping("/echo/{data}")
    public String echo(@PathVariable String data){
        logger.info(data);
        return data;
    }
}
