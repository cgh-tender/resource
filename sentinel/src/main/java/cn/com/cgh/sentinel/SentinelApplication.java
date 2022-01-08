package cn.com.cgh.sentinel;

import cn.com.cgh.common.exception.EnableException;
import cn.com.cgh.common.exception.EnableSentinelException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableException
@EnableSentinelException
public class SentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
    }

}
