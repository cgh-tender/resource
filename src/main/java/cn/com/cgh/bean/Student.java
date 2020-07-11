package cn.com.cgh.bean;

import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
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
}
