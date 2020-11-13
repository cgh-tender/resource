package cn.com.cgh.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author root
 */
@Controller
@RequestMapping("/test")
public class DemoController {
    @RequestMapping("/hello")
    @ResponseBody
    public String test1(String name) {
        return "hello " + name;
    }
}
