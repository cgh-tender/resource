package cn.com.cgh.solr;

import cn.com.cgh.config.solr.Item;
import cn.com.cgh.config.solr.SolrController;
import com.alibaba.fastjson.JSONObject;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SolrTestMain {
    private ApplicationContext context;
    private SolrTemplate solrTemplate;
    private SolrClient solrClient;
    private String collection = "mycor";
    @Before
    public void mvcApplication(){
        context = new ClassPathXmlApplicationContext("spring/spring.xml");
        solrTemplate = context.getBean(SolrTemplate.class);
        solrClient = context.getBean(SolrClient.class);

    }

    /**
     * <p> spring mvc
     * @param
     * @return 
     * @author Haidar
     * @date 2020/8/14 10:06
     **/
    @Test
    public void addSave(){
        Item item = new Item();
        item.setId("3");
        item.setTitle("testAddByDoc!");
        item.setBrand("通过solrTemplate新增文档通过solrTemplate新增文档");
        item.setPrice(System.currentTimeMillis() / 1000);
        UpdateResponse response = solrTemplate.saveBean(collection, item);
        solrTemplate.commit(collection);
        System.out.println("over:" + response);
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


//    @Before
    public void bootApplication(){
        context = new AnnotationConfigApplicationContext("cn.com.cgh");
    }
    @Test
    public void bootTest(){
        SimpleQuery sq=new SimpleQuery("item_brand:文档");
        Page<Item> query = solrTemplate.queryForPage(collection,sq, Item.class);
        System.out.println(JSONObject.toJSONString(query));
    }


}
