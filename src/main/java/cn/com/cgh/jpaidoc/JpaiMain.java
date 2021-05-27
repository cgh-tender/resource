package cn.com.cgh.jpaidoc;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 生成接口文档
 * @author haider
 * @date 2021年04月23日 11:36
 */
@Slf4j
public class JpaiMain {
    public static void main(String[] args) {
        DocsConfig docsConfig = new DocsConfig();
        docsConfig.setProjectPath("/Users/cgh/IdeaProjects/main_frontend/dw-order");
        docsConfig.setDocsPath("/Users/cgh/MyIdeaWork/resource/resource");
        docsConfig.setApiVersion("V1.0");
        docsConfig.setProjectName("aaa");
        docsConfig.setAutoGenerate(Boolean.TRUE);
        docsConfig.setMvcFramework("springboot");
        Docs.buildHtmlDocs(docsConfig);
    }
}
