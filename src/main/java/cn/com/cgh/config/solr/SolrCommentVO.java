package cn.com.cgh.config.solr;

import org.apache.solr.client.solrj.beans.Field;

import javax.swing.tree.RowMapper;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>  solr 提交意见 实体
 * @author
 * @date 2020/9/1 17:38
 **/
public class SolrCommentVO implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
     @Field
    private String id;
     @Field
    private String comment_content;
     @Field
    private String question_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    @Override
    public String toString() {
        return "SolrCommentVO{" +
                "id='" + id + '\'' +
                ", comment_content='" + comment_content + '\'' +
                ", question_id='" + question_id + '\'' +
                '}';
    }
}
