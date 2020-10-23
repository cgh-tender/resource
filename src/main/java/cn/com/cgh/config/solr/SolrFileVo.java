package cn.com.cgh.config.solr;

import java.io.Serializable;

/**
 * <p> Solr 文档实体
 * @author
 * @date 2020/9/1 15:30
 **/
public class SolrFileVo implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    private String id;
    private String fname;
    private String size;
    private String fileLastModified;
    private String fileDir;
    private String url;
    private String content_type;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFileLastModified() {
        return fileLastModified;
    }

    public void setFileLastModified(String fileLastModified) {
        this.fileLastModified = fileLastModified;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
