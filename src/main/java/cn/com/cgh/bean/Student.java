package cn.com.cgh.bean;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;

@Service("student1")
public class Student implements Serializable {

    private String name = "cgh";
    private int age = 25;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @PostConstruct
    public void start(){

    }
    @PreDestroy
    public void preDestory(){

    }
}