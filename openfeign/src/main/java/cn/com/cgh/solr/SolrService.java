package cn.com.cgh.solr;

import cn.com.cgh.openfeign.config.MyInterceptor;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "solr")//path = "" ,configuration = MyInterceptor.class
public interface SolrService {
    @GetMapping(value = "/echo/{string}")
//    @RequestLine(value = "GET /echo/{string}") // 原生的注解
    public String echo(@PathVariable(value = "string") String string);
//    public String echo(@Param(value = "string") String string);
}
