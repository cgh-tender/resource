package cn.com.cgh.activiti.service.solr;

import cn.com.common.util.R;
import org.springframework.stereotype.Component;

@Component
public class SolrServiceFallback implements SolrService {
    @Override
    public String echo(String string) {
        return R.failed().msg("降级了！").toString();
    }
}
