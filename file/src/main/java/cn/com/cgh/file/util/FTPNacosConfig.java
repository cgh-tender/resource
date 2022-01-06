package cn.com.cgh.file.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * nacos 配置
 * @author haider
 * @date 2021年12月22日 16:35
 */
@Component
@Getter
@Setter
@Slf4j
public class FTPNacosConfig {
    public static final String SPIDER = "file";
    @Value("${enableFTP:false}")
    private Boolean enableFTP;
    @Value("${ftpServerPath:/file/}")
    private String ftpServerPath;
    @Value("${ftpHost:192.168.111.90}")
    private String ftpHost;
    @Value("${ftpPort:24}")
    private int ftpPort;
    @Value("${ftpUserName:root}")
    private String ftpUserName;
    @Value("${ftpPwd:123456}")
    private String ftpPwd;
    @Value("${filePath:}")
    private String filePath;
    @PostConstruct
    public void p(){
        log.info("init FTPNacosConfig");
    }
}
