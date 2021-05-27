package cn.com.cgh.solr;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test{

    public static void main(String[] args) {
        String a = "[{\"ownerid\":3072810100,\"objtype\":6,\"objid\":849240062431862784,\"value\":\"{\\\"text\\\":\\\"回来我看见本群99＋\\n发红包哦\\n到 七点十七 \\\"}\",\"async\":false,\"strid\":\"849240062431862784\"},{\"ownerid\":3072810100,\"objtype\":6,\"objid\":849240077116121089,\"value\":\"{\\\"text\\\":\\\"我回来\\\"}\",\"async\":false,\"strid\":\"849240077116121089\"},{\"ownerid\":3078549153,\"objtype\":6,\"objid\":849240215234564096,\"value\":\"{\\\"text\\\":\\\"？\\\"}\",\"async\":false,\"strid\":\"849240215234564096\"},{\"ownerid\":3063588011,\"objtype\":6,\"objid\":849260014018764802,\"value\":\"{\\\"text\\\":\\\"找CP(应该没有人要ð\u009F\u0098\u00ADð\u009F\u0098\u00ADð\u009F\u0098\u00AD)\\n要求:\\n11~16\\n150粉以上(最好能帮我涨粉)\\n不渣，奶狼都可\\n每天500以下100以上零花钱\\n有意者私\\\"}\",\"async\":false,\"strid\":\"849260014018764802\"},{\"ownerid\":3072810100,\"objtype\":6,\"objid\":849267869140656128,\"value\":\"{\\\"text\\\":\\\"ð\u009F\u0098\u00AD\\\"}\",\"async\":false,\"strid\":\"849267869140656128\"}]";
        a = a.replaceAll("[\\f\\n\\r\\t\\v]+","");
        System.out.println(a);
        System.out.println(JSONArray.parseArray(a,Map.class));
        String s = parserSN(a);
        System.out.println(s);
        System.out.println(JSONArray.parseArray(s,Map.class));

//        List<Map> jsonArray = ;
//        System.out.println(jsonArray);
//
    }
    public static String parserSN(String json){
        if (json != null && !"".equals(json)) {
            Pattern p = Pattern.compile("[\\s*|\t|\r|\n]");
            Matcher m = p.matcher(json);
            String strNoBlank = m.replaceAll("");
            return strNoBlank;
        } else {
            return json;
        }
    }
}


