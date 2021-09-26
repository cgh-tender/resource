package cn.com.security.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haider
 * @date 2021年09月26日 13:01
 */
@RestController
public class LoginController {

    @RequestMapping("dologin")
    public void dologin(User user){

    }

}
