package cn.com.cgh.solr;

import cn.com.cgh.config.solr.*;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.util.NamedList;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class SolrTestMain {
    private ApplicationContext context;
    private SolrTemplate solrTemplate;
    private SolrClient solrClient;
    private String collection = "/mycor";
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

        Criteria criteria = new Criteria("*").contains("DATA_TYPE");
        Criteria aa = new Criteria("*").contains("asd");
//        FilterQuery filterQuery = new SimpleFilterQuery();

        SimpleHighlightQuery query = new SimpleHighlightQuery();

//        criteria.and(new Criteria("item_title").is("e"));

        query.addCriteria(criteria.or(aa));

//        query.addFilterQuery(filterQuery);

        Sort sort = Sort.by(Sort.Direction.DESC, "fileLastModified");
        PageRequest of = PageRequest.of(0, 10, sort);
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
        List<HighlightEntry<Item>> highlighted = query1.getHighlighted();
        System.out.println(JSONObject.toJSONString(highlighted));
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
        Optional<SolrBaseVO> byId = solrTemplate.getById(collection, "2481", SolrBaseVO.class);
        System.out.println(JSONObject.toJSONString(byId));
    }

    @Test
    public void saveBean(){
        SolrCommentVO commentVO = new SolrCommentVO();
        commentVO.setId("comment_10153");
        commentVO.setComment_content("我是王者");
        commentVO.setQuestion_id("10151");
        UpdateResponse response = solrTemplate.saveBean(collection, commentVO);
        solrTemplate.commit(collection);
        System.out.println("over:" + response);
    }

    protected HighlightOptions getHighlightOptions(){
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.setSimplePrefix("<font color='red'>");
        highlightOptions.setSimplePostfix("</font>");
        highlightOptions.setNrSnipplets(10);
        return highlightOptions;
    }

    protected List<SolrBaseVO> getComment(String text, int page, int size, Boolean isColor,AtomicInteger total){
        CountDownLatch countDownLatch = new CountDownLatch(2);
        AtomicReference<HighlightPage<SolrBaseVO>> solrBaseVOS = new AtomicReference<>();
        AtomicReference<HighlightPage<SolrCommentVO>> solrCommentOS = new AtomicReference<>();
        Thread base = new Thread(() -> {
            try {
                SimpleHighlightQuery query = new SimpleHighlightQuery();
                Criteria criteria = null;
                try {
                    IKAnalyzer ikAnalyzer = new IKAnalyzer(true);
                    TokenStream stream = ikAnalyzer.tokenStream("", text);
                    stream.reset();

                    CharTermAttribute attribute = stream.getAttribute(CharTermAttribute.class);

                    while (stream.incrementToken()){
                        if (null == criteria){
                            criteria = new Criteria("id").is(attribute.toString());
                        }
                        criteria = criteria.or(new Criteria("id").is(attribute.toString()))
                                .or(new Criteria("comment_content").contains(attribute.toString()));
                    }
                } catch (Exception e) {
                    criteria = new Criteria("id").is(text)
                            .or(new Criteria("comment_content").contains(text));
                }

                /* 条件 where */
                query.addCriteria(criteria);
                query.setJoin(new Join(new SimpleField("question_id"),new SimpleField("id")));
                /* 排序 order by */
                PageRequest of = new PageRequest(page, size);
                query.setPageRequest(of);

                /* 设置颜色 */
                if (null != isColor && isColor){
                    query.setHighlightOptions(getHighlightOptions());
                }

                HighlightPage<SolrBaseVO> solrBaseVOS1 = solrTemplate.queryForHighlightPage(collection, query, SolrBaseVO.class);
                solrBaseVOS.set(solrBaseVOS1);
            } finally {
                countDownLatch.countDown();
            }
        });
        Thread comment = new Thread(() -> {
            try {
                SimpleHighlightQuery query = new SimpleHighlightQuery();
                Criteria criteria = null;
                try {
                    IKAnalyzer ikAnalyzer = new IKAnalyzer(true);
                    TokenStream stream = ikAnalyzer.tokenStream("", text);
                    stream.reset();

                    CharTermAttribute attribute = stream.getAttribute(CharTermAttribute.class);

                    while (stream.incrementToken()){
                        if (null == criteria){
                            criteria = new Criteria("id").is(attribute.toString());
                        }
                        criteria = criteria.or(new Criteria("id").is(attribute.toString()))
                                .or(new Criteria("comment_content").contains(attribute.toString()));
                    }
                } catch (Exception e) {
                    criteria = new Criteria("id").is(text)
                            .or(new Criteria("comment_content").contains(text));
                }

                /* 条件 where */
                query.addCriteria(criteria);
                /* 排序 order by */
                PageRequest of = new PageRequest(page, size);
                query.setPageRequest(of);

                /* 设置颜色 */
                if (null != isColor && isColor){
                    query.setHighlightOptions(getHighlightOptions());
                }

                HighlightPage<SolrCommentVO> solrCommentVOS1 = solrTemplate.queryForHighlightPage(collection, query, SolrCommentVO.class);
                solrCommentOS.set(solrCommentVOS1);
            } finally {
                countDownLatch.countDown();
            }
        });

        try {
            base.start();
            comment.start();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<SolrBaseVO> solrBaseVOList = solrBaseVOS.get().getContent();
        if (!solrBaseVOList.isEmpty()){
            total.set((int) solrBaseVOS.get().getTotalElements());
            HighlightPage<SolrCommentVO> solrCommentVOS = solrCommentOS.get();
            for (SolrBaseVO solrBaseVO: solrBaseVOList){
                for (SolrCommentVO solrCommentVO: solrCommentVOS.getContent()){
                    if (solrBaseVO.getId().equalsIgnoreCase(solrCommentVO.getQuestion_id())){
                        List<HighlightEntry.Highlight> highlights = solrCommentVOS.getHighlights(solrCommentVO);
                        StringBuilder builder = new StringBuilder("");
                        for (HighlightEntry.Highlight highlight : highlights) {
                            List<String> snipplets = highlight.getSnipplets();
                            for (String snipplet : snipplets) {
                                builder.append(snipplet);
                                builder.append("    ");
                            }
                        }
                        solrBaseVO.setHighlightList(builder.toString());
                    }
                }
            }
        }
        return solrBaseVOList;
    }

    protected List<SolrBaseVO> getFileContent(String text, int page, int size, Boolean isColor,AtomicInteger total){
        CountDownLatch countDownLatch = new CountDownLatch(2);
        AtomicReference<HighlightPage<SolrBaseVO>> solrBaseVOS = new AtomicReference<>();
        AtomicReference<HighlightPage<SolrFileVo>> solrFileOS = new AtomicReference<>();
        Thread base = new Thread(() -> {
            try {
                SimpleHighlightQuery query = new SimpleHighlightQuery();
                Criteria criteria = null;
                try {
                    IKAnalyzer ikAnalyzer = new IKAnalyzer(true);
                    TokenStream stream = ikAnalyzer.tokenStream("", text);
                    stream.reset();

                    CharTermAttribute attribute = stream.getAttribute(CharTermAttribute.class);

                    while (stream.incrementToken()){
                        if (null == criteria){
                            criteria = new Criteria("id").is(attribute.toString());
                        }
                        criteria = criteria.or(new Criteria("id").is(attribute.toString()))
                                .or(new Criteria("content").contains(attribute.toString()));
                    }
                } catch (Exception e) {
                    criteria = new Criteria("id").is(text)
                            .or(new Criteria("content").contains(text));
                }

                /* 条件 where */
                query.addCriteria(criteria);
                query.setJoin(new Join(new SimpleField("id"),new SimpleField("question_file_name")));
                /* 排序 order by */
                PageRequest of = new PageRequest(page, size);
//                PageRequest of = PageRequest.of(page, size);
                query.setPageRequest(of);

                /* 设置颜色 */
                if (null != isColor && isColor){
                    query.setHighlightOptions(getHighlightOptions());
                }

                HighlightPage<SolrBaseVO> solrBaseVOS1 = solrTemplate.queryForHighlightPage(collection, query, SolrBaseVO.class);
                solrBaseVOS.set(solrBaseVOS1);
            } finally {
                countDownLatch.countDown();
            }
        });
        Thread files = new Thread(() -> {
            try {
                SimpleHighlightQuery query = new SimpleHighlightQuery();
                Criteria criteria = null;

                try {
                    IKAnalyzer ikAnalyzer = new IKAnalyzer(true);
                    TokenStream stream = ikAnalyzer.tokenStream("", text);
                    stream.reset();

                    CharTermAttribute attribute = stream.getAttribute(CharTermAttribute.class);
                    while (stream.incrementToken()){
                        if (null == criteria){
                            criteria = new Criteria("id").is(attribute.toString());
                        }
                        criteria = criteria.or(new Criteria("id").is(attribute.toString()))
                                .or(new Criteria("content").contains(attribute.toString()));
                    }
                } catch (Exception e) {
                    criteria = new Criteria("id").is(text)
                            .or(new Criteria("content").contains(text));
                }

                /* 条件 where */
                query.addCriteria(criteria);
                /* 排序 order by */
                PageRequest of = new PageRequest(page, size);
                query.setPageRequest(of);

                /* 设置颜色 */
                if (null != isColor && isColor){
                    query.setHighlightOptions(getHighlightOptions());
                }

                HighlightPage<SolrFileVo> solrFileOS1 = solrTemplate.queryForHighlightPage(collection, query, SolrFileVo.class);
                solrFileOS.set(solrFileOS1);
            } finally {
                countDownLatch.countDown();
            }
        });
        try {
            base.start();
            files.start();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<SolrBaseVO> solrBaseVOList = solrBaseVOS.get().getContent();
        if (!solrBaseVOList.isEmpty()){
            total.set((int) solrBaseVOS.get().getTotalElements());
            HighlightPage<SolrFileVo> solrFileVos = solrFileOS.get();
            for (SolrBaseVO solrBaseVO: solrBaseVOList){
                for (SolrFileVo solrFileVo: solrFileVos.getContent()){
                    if (solrBaseVO.getQUESTION_FILE_NAME().indexOf(solrFileVo.getFname()) != -1){
                        List<HighlightEntry.Highlight> highlights = solrFileVos.getHighlights(solrFileVo);
                        StringBuilder highlightStr = new StringBuilder("");
                        for (HighlightEntry.Highlight highlight : highlights) {
                            List<String> snipplets = highlight.getSnipplets();
                            for (String snipplet : snipplets) {
                                highlightStr.append(snipplet);
                                highlightStr.append("    ");
                            }
                        }
                        solrBaseVO.setHighlightList(highlightStr.toString());
                    }
                }
            }
        }
        return solrBaseVOList;
    }

    protected HighlightPage<SolrBaseVO> getBase(String text,int page,int size,Boolean isColor,AtomicInteger total,String auditcode){
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        IKAnalyzer ikAnalyzer = new IKAnalyzer(true);
        Criteria criteria = null;
        try {
            TokenStream stream = ikAnalyzer.tokenStream("", text);
            stream.reset();
            CharTermAttribute attribute = stream.getAttribute(CharTermAttribute.class);
            while (stream.incrementToken()){
                if (null == criteria){
                    criteria = new Criteria("id").is(attribute.toString());
                }
                criteria = criteria.or(new Criteria("id").is(attribute.toString()))
                        .or(new Criteria("provincename").contains(attribute.toString()))
                        .or(new Criteria("question_create_user").contains(attribute.toString()))
                        .or(new Criteria("question_title").contains(attribute.toString()))
                        .or(new Criteria("question_type").contains(attribute.toString()))
                        .or(new Criteria("question_create_time").contains(attribute.toString()))
                        .or(new Criteria("question_product_type").contains(attribute.toString()))
                        .or(new Criteria("question_auditname").contains(attribute.toString()))
                        .or(new Criteria("email").is(attribute.toString()))
                        .or(new Criteria("question_content").is(attribute.toString()))
                        .or(new Criteria("phone").is(attribute.toString()))
                        .or(new Criteria("question_level").contains(attribute.toString()))
                        .or(new Criteria("query_source_type").contains(attribute.toString()));
            }
        } catch (Exception e) {
            criteria = new Criteria("id").is(text)
                    .or(new Criteria("provincename").contains(text))
                    .or(new Criteria("question_create_user").contains(text))
                    .or(new Criteria("question_title").contains(text))
                    .or(new Criteria("question_type").contains(text))
                    .or(new Criteria("question_create_time").contains(text))
                    .or(new Criteria("question_product_type").contains(text))
                    .or(new Criteria("question_auditname").contains(text))
                    .or(new Criteria("email").is(text))
                    .or(new Criteria("question_content").contains(text))
                    .or(new Criteria("phone").is(text))
                    .or(new Criteria("question_level").contains(text))
                    .or(new Criteria("query_source_type").contains(text));
        }
        Criteria type = null;
        if(auditcode.equals("RA06") || auditcode.equals("RA21") || auditcode.equals("RA31") ){
            criteria = criteria.or(new Criteria("question_type_code").in("RA06","RA21","31"));
//            type = new Criteria("question_type_code").in("RA06","RA21","31");
        }else if(auditcode.equals("RA02") || auditcode.equals("RA26") || auditcode.equals("RA36")){
            criteria = criteria.or(new Criteria("question_type_code").in("RA02","RA26","RA36"));
//            type = new Criteria("question_type_code").in("RA02","RA26","RA36");
        }else if(auditcode.equals("RA04") || auditcode.equals("RA24") || auditcode.equals("RA34") ){
            criteria = criteria.or(new Criteria("question_type_code").in("RA04","RA24","RA34"));
//            type = new Criteria("question_type_code").in("RA04","RA24","RA34");
        }else if(auditcode.equals("RA01") || auditcode.equals("RA23") || auditcode.equals("RA33") ){
//            type = new Criteria("question_type_code").in("RA01","RA23","RA33");
        }else if(auditcode.equals("RA03") || auditcode.equals("RA22") || auditcode.equals("RA32") ){
//            type = new Criteria("question_type_code").in("RA03","RA22","RA32");
        }else if(auditcode.equals("RA05") || auditcode.equals("RA25") || auditcode.equals("RA35")){
//            criteria = criteria.or(new Criteria("question_type_code").in("RA05","RA25","RA35"));
            type = new Criteria("question_type_code").in("RA05","RA25","RA35");
//            type = new Criteria("question_type_code").is("RA05")
//            .or(new Criteria("question_type_code").is("RA25"))
//            .or(new Criteria("question_type_code").is("RA35"));
        }else if(auditcode.equals("RA37")) {
//            type = new Criteria("question_type_code").in("RA37");
        }
        if (type != null){
            query.addCriteria(type);
        }
        /* 条件 where */
        query.addCriteria(criteria);
        /* 排序 order by */
        Sort sort = new Sort(Sort.Direction.DESC, "question_create_time")
                .and(new Sort(Sort.Direction.DESC, "question_click"))
                .and(new Sort(Sort.Direction.DESC, "collection_count"));
//        Sort sort = Sort.by(Sort.Direction.DESC, "question_create_time").and(Sort.by().and(Sort.by());
        PageRequest of = new PageRequest(page, size, sort);
        query.setPageRequest(of);

        /* 设置颜色 */
        if (null != isColor && isColor){
            query.setHighlightOptions(getHighlightOptions());
        }

        return solrTemplate.queryForHighlightPage(collection, query, SolrBaseVO.class);
    }


    @Test
    public void queryColle(){
        queryByColor("HTML", 0, 10, true);
    }

    public void queryByColor(String text,int page ,int size ,Boolean isColor) {
        try {
            AtomicInteger total = new AtomicInteger(0);
            HighlightPage<SolrBaseVO> base = getBase(text, page, size, isColor,total,"");
//            List<SolrBaseVO> comment = getComment(text, page, size, isColor,null);
//            List<SolrBaseVO> content = getFileContent(text, page, size, isColor,null);
            System.out.println(JSONObject.toJSONString(base));
//            System.out.println(JSONObject.toJSONString(comment));
//            System.out.println(JSONObject.toJSONString(content));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 解析主表
     * @param base
     * @param baseIdList
     */
    protected void parserBase(HighlightPage<SolrBaseVO> base,HashSet<String> baseIdList,List<SolrBaseVO> baseVOList){
        long totalElements = base.getTotalElements();
        if (totalElements != 0){
            base.getHighlighted().forEach(solrBaseVOHighlightEntry -> {
                SolrBaseVO entity = solrBaseVOHighlightEntry.getEntity();
                List<HighlightEntry.Highlight> highlights = solrBaseVOHighlightEntry.getHighlights();
                StringBuilder builder = new StringBuilder("");
                for (HighlightEntry.Highlight highlight : highlights) {
                    List<String> snipplets = highlight.getSnipplets();
                    for (String snipplet : snipplets) {
                        builder.append(snipplet);
                        builder.append("    ");
                    }
                }
                entity.setHighlightList(builder.toString());
                baseVOList.add(entity);
            });
        }
    }


    @Test
    public void dataOracleimport(){
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/mycor" + "/dataOracleimport");

        query.setParam("command","full-import");
        query.setParam("verbose",true);
        query.setParam("clean",false);
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
        query.setRequestHandler(collection+"/dataFileimport");

        query.setParam("entity","fileImportTofileName");
        query.setParam("command","full-import");
        query.setParam("verbose",true);
        query.setParam("clean",false);
        query.setParam("commit",true);
        query.setParam("fileName","11111.xlsx");
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
    class TestItem extends HashMap implements Serializable {
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
    @Data
    class MyHashMap<K,V> extends HashMap<K,V> implements Serializable {
        private static final long serialVersionUID = 5516075349620653480L;

        public MyHashMap() {
            super();
        }

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            Iterator<Entry<K,V>> i = entrySet().iterator();
            if (! i.hasNext())
                return "{}";

            StringBuilder sb = new StringBuilder();
            sb.append('{');
            if (StringUtils.isNotBlank(getId())){
                sb.append("id=");
                sb.append(getId());
            }
            for (;;) {
                Entry<K,V> e = i.next();
                K key = e.getKey();
                V value = e.getValue();
                sb.append(key   == this ? "(this Map)" : key);
                sb.append('=');
                sb.append(value == this ? "(this Map)" : value);
                if (! i.hasNext())
                    return sb.append('}').toString();
                sb.append(',').append(' ');
            }
        }
    }
    public static class MyExecutor{
        ExecutorService executor = Executors.newCachedThreadPool();
        public boolean aa(){
            try {
                executor.submit(()->{
                    try {
                        System.out.println("开始睡眠");
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    System.out.println("结束睡眠");
                });
                System.out.println("----");
                return true;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return false;
        };
    }

    public static void main(String[] args){
        MyExecutor a = new MyExecutor();
        a.aa();
        System.out.println("====");
    }



}
