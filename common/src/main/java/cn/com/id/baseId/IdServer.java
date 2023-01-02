package cn.com.id.baseId;

import cn.com.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@Component
//@FeignClient(name = "id", url = "EMPTY", fallbackFactory = IdFallbackFactory.class, configuration = IdConfig.class)
public interface IdServer {
    @GetMapping(value = "/getId")
    R<Long> getId();

    @GetMapping(value = "/getIdPrefix/{prefix}")
    R<String> getIdPrefix(@PathVariable("prefix") String prefix);
}
