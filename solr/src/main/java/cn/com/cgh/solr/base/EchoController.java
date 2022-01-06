package cn.com.cgh.solr.base;

import cn.com.cgh.file.util.CFromDocumentExtend;
import cn.com.cgh.file.util.SaveFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author haider
 * @date 2021年12月20日 13:37
 */
@RestController
@Slf4j
public class EchoController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) throws IOException {
        log.info("info {}",string);
        Long a = redisTemplate.opsForValue().increment("a", 1);
        redisTemplate.delete("a");
        File file = new File("F:\\My_Work_Base\\spring_base\\resource\\solr\\src\\main\\java\\cn\\com\\cgh\\solr\\SolrApplication.java");
        FileInputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
        CFromDocumentExtend save = saveFileUtil.save(multipartFile, true);
        log.info(save.toString());
        return "Hello Nacos Discovery " + port + " " + string + " " + a;
    }
    @Autowired
    private SaveFileUtil saveFileUtil;
}
