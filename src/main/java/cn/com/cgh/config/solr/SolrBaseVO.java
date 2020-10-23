package cn.com.cgh.config.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.query.result.HighlightEntry;

import java.io.Serializable;
import java.util.List;

/**
 * <p> 问题论坛 base实体
 * @author
 * @date 2020/9/1 17:38
 **/
public class SolrBaseVO implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    @Field("id")
    private String id;
    @Field("provincename")
    private String PROVINCENAME;
    @Field("code")
    private String CODE;
    @Field("question_create_user")
    private String QUESTION_CREATE_USER;
    @Field("question_create_userid")
    private String QUESTION_CREATE_USERID;
    @Field("question_file_name")
    private String QUESTION_FILE_NAME;
    @Field("question_content")
    private String QUESTION_CONTENT;
    @Field("question_title")
    private String QUESTION_TITLE;
    @Field("collection_count")
    private String COLLECTION_COUNT;
    @Field("commentnum")
    private String COMMENTNUM;
    @Field("question_support_flag")
    private String QUESTION_SUPPORT_FLAG;
    @Field("question_click")
    private String QUESTION_CLICK;
    @Field("orgorder")
    private String ORGORDER;
    @Field("question_type")
    private String QUESTION_TYPE;
    @Field("question_type_code")
    private String QUESTION_TYPE_CODE;
    @Field("question_create_time")
    private String QUESTION_CREATE_TIME;
    @Field("question_product_type")
    private String QUESTION_PRODUCT_TYPE;
    @Field("question_auditname")
    private String QUESTION_AUDITNAME;
    @Field("email")
    private String EMAIL;
    @Field("phone")
    private String PHONE;
    @Field("question_level")
    private String QUESTION_LEVEL;
    @Field("question_level_name")
    private String QUESTION_LEVEL_NAME;
    @Field("query_source_type")
    private String QUERY_SOURCE_TYPE;
    @Field("highlightlisttype")
    private String HIGHLIGHTLISTTYPE = "base";

    private String highlightList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPROVINCENAME() {
        return PROVINCENAME;
    }

    public void setPROVINCENAME(String PROVINCENAME) {
        this.PROVINCENAME = PROVINCENAME;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getQUESTION_CREATE_USER() {
        return QUESTION_CREATE_USER;
    }

    public void setQUESTION_CREATE_USER(String QUESTION_CREATE_USER) {
        this.QUESTION_CREATE_USER = QUESTION_CREATE_USER;
    }

    public String getQUESTION_CREATE_USERID() {
        return QUESTION_CREATE_USERID;
    }

    public void setQUESTION_CREATE_USERID(String QUESTION_CREATE_USERID) {
        this.QUESTION_CREATE_USERID = QUESTION_CREATE_USERID;
    }

    public String getQUESTION_FILE_NAME() {
        return QUESTION_FILE_NAME;
    }

    public void setQUESTION_FILE_NAME(String QUESTION_FILE_NAME) {
        this.QUESTION_FILE_NAME = QUESTION_FILE_NAME;
    }

    public String getQUESTION_CONTENT() {
        return QUESTION_CONTENT;
    }

    public void setQUESTION_CONTENT(String QUESTION_CONTENT) {
        this.QUESTION_CONTENT = QUESTION_CONTENT;
    }

    public String getQUESTION_TITLE() {
        return QUESTION_TITLE;
    }

    public void setQUESTION_TITLE(String QUESTION_TITLE) {
        this.QUESTION_TITLE = QUESTION_TITLE;
    }

    public String getCOLLECTION_COUNT() {
        return COLLECTION_COUNT;
    }

    public void setCOLLECTION_COUNT(String COLLECTION_COUNT) {
        this.COLLECTION_COUNT = COLLECTION_COUNT;
    }

    public String getCOMMENTNUM() {
        return COMMENTNUM;
    }

    public void setCOMMENTNUM(String COMMENTNUM) {
        this.COMMENTNUM = COMMENTNUM;
    }

    public String getQUESTION_SUPPORT_FLAG() {
        return QUESTION_SUPPORT_FLAG;
    }

    public void setQUESTION_SUPPORT_FLAG(String QUESTION_SUPPORT_FLAG) {
        this.QUESTION_SUPPORT_FLAG = QUESTION_SUPPORT_FLAG;
    }

    public String getQUESTION_CLICK() {
        return QUESTION_CLICK;
    }

    public void setQUESTION_CLICK(String QUESTION_CLICK) {
        this.QUESTION_CLICK = QUESTION_CLICK;
    }

    public String getORGORDER() {
        return ORGORDER;
    }

    public void setORGORDER(String ORGORDER) {
        this.ORGORDER = ORGORDER;
    }

    public String getQUESTION_TYPE() {
        return QUESTION_TYPE;
    }

    public void setQUESTION_TYPE(String QUESTION_TYPE) {
        this.QUESTION_TYPE = QUESTION_TYPE;
    }

    public String getQUESTION_TYPE_CODE() {
        return QUESTION_TYPE_CODE;
    }

    public void setQUESTION_TYPE_CODE(String QUESTION_TYPE_CODE) {
        this.QUESTION_TYPE_CODE = QUESTION_TYPE_CODE;
    }

    public String getQUESTION_CREATE_TIME() {
        return QUESTION_CREATE_TIME;
    }

    public void setQUESTION_CREATE_TIME(String QUESTION_CREATE_TIME) {
        this.QUESTION_CREATE_TIME = QUESTION_CREATE_TIME;
    }

    public String getQUESTION_PRODUCT_TYPE() {
        return QUESTION_PRODUCT_TYPE;
    }

    public void setQUESTION_PRODUCT_TYPE(String QUESTION_PRODUCT_TYPE) {
        this.QUESTION_PRODUCT_TYPE = QUESTION_PRODUCT_TYPE;
    }

    public String getQUESTION_AUDITNAME() {
        return QUESTION_AUDITNAME;
    }

    public void setQUESTION_AUDITNAME(String QUESTION_AUDITNAME) {
        this.QUESTION_AUDITNAME = QUESTION_AUDITNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getQUESTION_LEVEL() {
        return QUESTION_LEVEL;
    }

    public void setQUESTION_LEVEL(String QUESTION_LEVEL) {
        this.QUESTION_LEVEL = QUESTION_LEVEL;
    }

    public String getQUESTION_LEVEL_NAME() {
        return QUESTION_LEVEL_NAME;
    }

    public void setQUESTION_LEVEL_NAME(String QUESTION_LEVEL_NAME) {
        this.QUESTION_LEVEL_NAME = QUESTION_LEVEL_NAME;
    }

    public String getQUERY_SOURCE_TYPE() {
        return QUERY_SOURCE_TYPE;
    }

    public void setQUERY_SOURCE_TYPE(String QUERY_SOURCE_TYPE) {
        this.QUERY_SOURCE_TYPE = QUERY_SOURCE_TYPE;
    }

    public String getHIGHLIGHTLISTTYPE() {
        return HIGHLIGHTLISTTYPE;
    }

    public void setHIGHLIGHTLISTTYPE(String HIGHLIGHTLISTTYPE) {
        this.HIGHLIGHTLISTTYPE = HIGHLIGHTLISTTYPE;
    }

    public String getHighlightList() {
        return highlightList;
    }

    public void setHighlightList(String highlightList) {
        this.highlightList = highlightList;
    }

    @Override
    public String toString() {
        return "SolrBaseVO{" +
                "id='" + id + '\'' +
                ", PROVINCENAME='" + PROVINCENAME + '\'' +
                ", CODE='" + CODE + '\'' +
                ", QUESTION_CREATE_USER='" + QUESTION_CREATE_USER + '\'' +
                ", QUESTION_CREATE_USERID='" + QUESTION_CREATE_USERID + '\'' +
                ", QUESTION_FILE_NAME='" + QUESTION_FILE_NAME + '\'' +
                ", QUESTION_CONTENT='" + QUESTION_CONTENT + '\'' +
                ", QUESTION_TITLE='" + QUESTION_TITLE + '\'' +
                ", COLLECTION_COUNT='" + COLLECTION_COUNT + '\'' +
                ", COMMENTNUM='" + COMMENTNUM + '\'' +
                ", QUESTION_SUPPORT_FLAG='" + QUESTION_SUPPORT_FLAG + '\'' +
                ", QUESTION_CLICK='" + QUESTION_CLICK + '\'' +
                ", ORGORDER='" + ORGORDER + '\'' +
                ", QUESTION_TYPE='" + QUESTION_TYPE + '\'' +
                ", QUESTION_TYPE_CODE='" + QUESTION_TYPE_CODE + '\'' +
                ", QUESTION_CREATE_TIME='" + QUESTION_CREATE_TIME + '\'' +
                ", QUESTION_PRODUCT_TYPE='" + QUESTION_PRODUCT_TYPE + '\'' +
                ", QUESTION_AUDITNAME='" + QUESTION_AUDITNAME + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", PHONE='" + PHONE + '\'' +
                ", QUESTION_LEVEL='" + QUESTION_LEVEL + '\'' +
                ", QUESTION_LEVEL_NAME='" + QUESTION_LEVEL_NAME + '\'' +
                ", QUERY_SOURCE_TYPE='" + QUERY_SOURCE_TYPE + '\'' +
                ", HIGHLIGHTLISTTYPE='" + HIGHLIGHTLISTTYPE + '\'' +
                ", highlightList='" + highlightList + '\'' +
                '}';
    }
}
