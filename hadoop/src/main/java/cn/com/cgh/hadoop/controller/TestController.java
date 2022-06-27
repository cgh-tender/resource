package cn.com.cgh.hadoop.controller;

import com.alibaba.nacos.common.utils.ThreadUtils;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @GetMapping("/echo/{data}")
    public String echo(@PathVariable String data){
        logger.info(data);
        sleep();
        return data;
    }

    @Trace
    public void sleep(){
        ThreadUtils.sleep(2000);
    }

    @GetMapping("/header")
    public String header(@RequestHeader("X-Request-color") String data){
        logger.info(data);
        return data;
    }
}
