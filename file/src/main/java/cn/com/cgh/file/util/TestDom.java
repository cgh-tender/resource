package cn.com.cgh.file.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class TestDom {
    private SaveFileUtil saveFileUtil;
    public void main(String[] args) {
        File file = new File("F:\\My_Work_Base\\spring_base\\resource\\solr\\src\\main\\java\\cn\\com\\cgh\\solr\\SolrApplication.java");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            MockMultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
            CFromDocumentExtend save = saveFileUtil.save(multipartFile, true);
            log.info(save.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
