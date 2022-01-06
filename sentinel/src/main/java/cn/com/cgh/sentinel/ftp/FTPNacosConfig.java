package cn.com.cgh.sentinel.ftp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * nacos 配置
 * @author haider
 * @date 2021年12月22日 16:35
 */
@Configuration
@Getter
@Setter
public class FTPNacosConfig {
    public static final String SPIDER = "spider1";
    @Value("${enableFTP:false}")
    private Boolean enableFTP;
    @Value("${ftpServerPath:}")
    private String ftpServerPath;
    @Value("${ftpHost:}")
    private String ftpHost;
    @Value("${ftpPort:22}")
    private int ftpPort;
    @Value("${ftpUserName:}")
    private String ftpUserName;
    @Value("${ftpPwd:}")
    private String ftpPwd;
    @Value("${spider_file_path}")
    private String filePath;// = "/Users/cgh/Desktop/aaa/";
}
