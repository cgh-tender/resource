package cn.com.cgh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author cgh
 */
@SpringBootApplication(scanBasePackages = "cn.com.cgh")
@ServletComponentScan //过滤器扫描
public class CghApplication {

    public static void main(String[] args) {
        SpringApplication.run(CghApplication.class);
    }
}
