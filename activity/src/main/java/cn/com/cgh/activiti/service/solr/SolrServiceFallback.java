package cn.com.cgh.activiti.service.solr;

import cn.com.cgh.common.util.R;
import org.springframework.stereotype.Component;

@Component
public class SolrServiceFallback implements SolrService {
    @Override
    public String echo(String string) {
        return R.failed("降级了！").toString();
    }
}
