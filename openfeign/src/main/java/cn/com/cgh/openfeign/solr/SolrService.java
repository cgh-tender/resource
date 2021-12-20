package cn.com.cgh.openfeign.solr;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "solr")//path = ""
public interface SolrService {
    @GetMapping(value = "/echo/{string}")
    String echo(@PathVariable String string);
}
