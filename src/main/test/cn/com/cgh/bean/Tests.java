package cn.com.cgh.bean;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

public class Tests {
    //从html中提取纯文本
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        //剔除空格行
        textStr = textStr.replaceAll("[ ]+", " ");
        textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        return textStr;// 返回文本字符串
    }


    public static void main(String[] args) throws IOException {
//        String text="<p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\"><span class=\"bjh-p\"><span class=\"bjh-strong\" style=\"font-size: 18px; font-weight: 700;\">海外网9月8日电</span>当地时间7日，印度外交部长苏杰生谈及中印关系时表示，中印的边界情况和两国的关系“无法脱钩”，认为当前双方边境间的紧张局势可能会影响中印关系。他也表示，中印双方需要就当前的边境局势在政治层面进行“非常、非常深入的对话”。</span></p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\"><span class=\"bjh-p\">综合印度报业托拉斯、《经济时报》7日报道，苏杰生当天对印媒表示，中印边境的状态与中印关系“无法脱钩”，过去30年得益于中印边境地区的和平安宁，两国关系在其他方面也取得了进展。“如果边境没有和平与安宁，那么（中印）两国关系的其余部分就不可能在同一基础上继续下去。显然，和平与安宁是两国关系的基础。”</span></p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\"><span class=\"bjh-p\">《经济时报》指出，这一观点在苏杰生的新书《印度之路：在不确定世界中的策略》里也有所体现，反映出印方意识到当前中印边境的紧张局势可能会影响两国关系。此外，苏杰生也谈到，当前中印边境局势“非常严峻”，并称这要求中印双方在政治层面进行“非常、非常深入的对话”。</span></p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\"><span class=\"bjh-p\">苏杰生近一段时间曾多次就中印关系作出表态。9月3日，他在印度智库观察家研究基金会(ORF)的一场线上活动中表示，现在不是中印双边关系中“最容易的时候”，两国达成和解是当务之急且至关重要，这不仅仅是对两国自身而言。8月31日，他在参加论坛时称，中印都是拥有超过10亿人口的国家，各自拥有自己的历史和文化，两国之间达成一定的理解或平衡是非常重要的。</span></p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\"><span class=\"bjh-p\">9月4日，外交部发言人华春莹主持例行记者会时曾表示，对于中印边界历史遗留的问题和现实存在的问题，我们历来主张通过和平友好协商，找到公平合理和双方都能接受的解决方案。对于边境地区最近出现的事态，中印双方正通过外交和军事渠道保持着密切沟通。我们希望同印方通过磋商妥善解决有关问题，共同维护边境地区的和平与安宁。这是符合中印两国人民利益。（海外网 张霓）</span></p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\"><span class=\"bjh-p\">本文系版权作品，未经授权严禁转载。海外视野，中国立场，浏览人民日报海外版官网——海外网www.haiwainet.cn或“海客”客户端，领先一步获取权威资讯。</span></p><p><br/></p>";
//        System.out.println(Html2Text(text));

        System.out.println((int)Math.ceil((double) 101/100));
        System.out.println((int)Math.floor((double) 101/100));
        System.out.println((int)Math.round((double) 101/100));
//
    }
}
