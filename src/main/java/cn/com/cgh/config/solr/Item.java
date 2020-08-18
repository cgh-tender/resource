package cn.com.cgh.config.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;

import java.io.Serializable;
import java.util.Map;

/**
 * <p> TODO
 * @author Haidar
 * @date 2020/8/14 14:30
 **/
public class Item implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    /**
     * 商品id，同时也是商品编号 如果属性名和域name一致，那么可以省略@Filed(value="")里面的value信息
     */
    @Field
    private String id;

    /**
     * 商品标题
     */
    @Field("item_title")
    private String title;

    /**
     * 商品价格，单位为：元
     */
    @Field("item_price")
    private double price;

    /**
     * 商品分类
     */
    @Field("item_category")
    private String category;

    /**
     *商品品牌
     */
    @Field("item_brand")
    private String brand;

    /**
     * 商品所属商家
     */
    @Field("item_seller")
    private String seller;

    /***
     * @Dynamic:表示该属性对应的是一个动态域
     * @Field(value = "item_spec_*"):表示动态域的前缀是item_spec_
     *
     * 创建动态域的时候，会将Map的可以作为动态域后面通配符匹配的部分，值作为域的值
     *                  specMap:key=网络          value=联通3G
     *                  specMap:key=机身内存      value=128G
     *
     *                  往Solr索引库中添加对应的动态域：
     *                          域的名字：item_spec_网络
     *                          域的值：联通3G
     *
     *                          域的名字：item_spec_机身内存
     *                          域的值：128G
     */
    @Dynamic
    @Field(value = "item_spec_*")
    private Map<String,String> specMap;

    /**
     *用来存储高亮数据
     */
    private String tempHighLightData ;

    public void setTempHighLightData(String tempHighLightData) {
        this.tempHighLightData = tempHighLightData;
    }

    public String getTempHighLightData() {
        return tempHighLightData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Map<String, String> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(Map<String, String> specMap) {
        this.specMap = specMap;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", seller='" + seller + '\'' +
                ", specMap=" + specMap +
                '}';
    }
}
