package cn.com.cgh.solr;

import cn.com.cgh.config.solr.Item;
import cn.com.cgh.config.solr.UmsDomain;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.util.NamedList;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class SolrTestMain {
    private ApplicationContext context;
    private SolrTemplate solrTemplate;
    private SolrClient solrClient;
    private String collection = "";
//    @Before
    public void mvcApplication(){
        context = new ClassPathXmlApplicationContext("spring/spring.xml");
        solrTemplate = context.getBean(SolrTemplate.class);
        solrClient = context.getBean(SolrClient.class);
    }
    @Before
    public void application(){
        context = new AnnotationConfigApplicationContext("cn.com.cgh");
        solrTemplate = context.getBean(SolrTemplate.class);
        solrClient = context.getBean(SolrClient.class);
    }



    @Test
    public void query(){
        Optional<Item> byId = solrTemplate.getById(collection, "3", Item.class);
        System.out.println(JSONObject.toJSONString(byId));

        Criteria criteria = new Criteria("item_brand").contains("te");
//        FilterQuery filterQuery = new SimpleFilterQuery();

        SimpleHighlightQuery query = new SimpleHighlightQuery();

//        criteria.and(new Criteria("item_title").is("e"));

        query.addCriteria(criteria);

//        query.addFilterQuery(filterQuery);

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest of = PageRequest.of(0, 1, sort);
        query.setPageRequest(of);

        HighlightOptions highlightOptions = new HighlightOptions();
//        highlightOptions.addField("item_brand");
//        highlightOptions.addField("item_title");
        highlightOptions.setSimplePrefix("<font color='red'>");
        highlightOptions.setSimplePostfix("</font>");
        query.setHighlightOptions(highlightOptions);

//        solrTemplate.count(collection,query);

        HighlightPage<Item> query1 = solrTemplate.queryForHighlightPage(collection, query, Item.class);
        System.out.println(String.format("总记录数: %d",query1.getTotalElements()));

        List<Item> content = query1.getContent();
        for (Item item: content){
            List<HighlightEntry.Highlight> highlights = query1.getHighlights(item);
            highlights.forEach((highLight) -> {
                System.out.println("高亮字段 Field :"+highLight.getField());
                System.out.println("高亮字段 World :"+highLight.getSnipplets());
            });
        }
        System.out.println(JSONObject.toJSONString(query1));
    }

    /**
     * <p> 进行清空
     * <p> <delete><query>*:*</query></delete><commit/>
     * @see https://blog.csdn.net/m0_37595732/article/details/72830122
     * @author Haidar
     * @date 2020/8/24 11:44
     **/
    @Test
    public void deleteAll(){
        SimpleQuery query = new SimpleQuery("*:*");
        solrTemplate.delete(collection, query);
        solrTemplate.commit(collection);
    }

    @Test
    public void queryDomain(){
        Criteria code = new Criteria("code").is("500");
        SimpleFacetAndHighlightQuery query = new SimpleFacetAndHighlightQuery(code);
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.setSimplePrefix("<font color='red'>");
        highlightOptions.setSimplePostfix("</font>");
        query.setHighlightOptions(highlightOptions);
        HighlightPage<UmsDomain> maps = solrTemplate.queryForHighlightPage(collection, query, UmsDomain.class);
        System.out.println(String.format("总记录数: %d",maps.getTotalElements()));
        System.out.println(JSONObject.toJSONString(maps));
    }
    
    @Test
    public void testQuery(){
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.setSimplePrefix("<font color='red'>");
        highlightOptions.setSimplePostfix("</font>");
        HighlightPage<TestItem> query = solrTemplate.queryForHighlightPage(collection,
                new SimpleFacetAndHighlightQuery(new Criteria("id").is("10"))
                        .setHighlightOptions(highlightOptions), TestItem.class);
        System.out.println(JSONObject.toJSONString(query));
    }


//    @Before
    public void bootApplication(){
        context = new AnnotationConfigApplicationContext("cn.com.cgh");
    }

    @Test
    public void queryById(){
        Optional<TestItem> byId = solrTemplate.getById(collection, "10", TestItem.class);
        System.out.println(JSONObject.toJSONString(byId));
    }

    @Test
    public void saveBean(){
        TestItem item = new TestItem();
        item.setCccc("ccccccccc");
        item.setId("10");
        item.setDdd("asdfadsf");
        item.setXxxx("asdfasdfasdfasddddd");
        UpdateResponse response = solrTemplate.saveBean(collection, item);
        solrTemplate.commit(collection);
        System.out.println("over:" + response);
    }

    @Test
    public void queryColle(){
        SimpleQuery simpleQuery = new SimpleQuery("*:*");
        Page<Item> query = solrTemplate.query(collection, simpleQuery, Item.class);
        System.out.println(JSONObject.toJSONString(query));
    }

    @Test
    public void dataOracleimport(){
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/dataOracleimport");
        query.setParam("entity","ums_domain");

        query.setParam("command","full-import");
        query.setParam("verbose",true);
        query.setParam("commit",true);
        try {
            QueryResponse query1 = solrClient.query(query);
            NamedList<Object> header = query1.getHeader();
            System.out.println(header.toSolrParams());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void dataOracleDey(){

    }
    @Test
    public void dataFileimport(){
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/dataFileimport");
        query.setParam("entity","fileImportTofileName");
        query.setParam("command","full-import");
        query.setParam("verbose",true);
        query.setParam("commit",true);

        query.setParam("fileName","solr.txt");
        try {
            solrClient.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteById(){
        solrTemplate.deleteByIds(collection,"solr.txt");
//        solrTemplate.deleteByIds(collection,"10");
        solrTemplate.commit(collection);
    }

    @Data
    class TestItem implements Serializable {
        private static final long serialVersionUID = 5516075349620653480L;

        @Field("xxx_txt_cjk")
        private String xxxx;
        @Field("cccc_txt_cjk")
        private String cccc;
        @Field
        private String id;
        @Field
        private String ddd;

        public String getXxxx() {
            return xxxx;
        }

        public void setXxxx(String xxxx) {
            this.xxxx = xxxx;
        }

        public String getCccc() {
            return cccc;
        }

        public void setCccc(String cccc) {
            this.cccc = cccc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDdd() {
            return ddd;
        }

        public void setDdd(String ddd) {
            this.ddd = ddd;
        }
    }



}
