package cn.com.cgh.boot.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author root
 */
@Controller
@RequestMapping("/test")
@Validated
public class DemoController {
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
@Data
class Names{
    /**
     * name
     */
    @NotNull
    private String name;
    /**
     * age
     */
    private int age;
    /**
     * Save
     */
    @Valid
    private List<D> names;
}
@Data
class D{
    /**
     * name
     */
    @NotNull
    private String nameD;
    /**
     * age
     */
    private int ageD;
}
