package cn.com.cgh.Id;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {
        "com.baidu.fsg.uid.worker.dao"
})
public class IdApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdApplication.class, args);
    }
}
