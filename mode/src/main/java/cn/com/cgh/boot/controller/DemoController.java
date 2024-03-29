package cn.com.cgh.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author root
 */
@Controller
@RequestMapping("/test")
@Validated
public class DemoController {
    private static Logger logger = LoggerFactory.getLogger(DemoController.class);
    /**
     *
     * @param name 传入名称
     * @return
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String test1(@Valid Names name) {
        return "hello " + name;
    }
}

