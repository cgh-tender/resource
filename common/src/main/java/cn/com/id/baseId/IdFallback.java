package cn.com.id.baseId;

import cn.com.common.util.R;
import org.springframework.stereotype.Component;

@Component
public class IdFallback implements IdServer{

    @Override
    public R<Long> getId() {
        return null;
    }

    @Override
    public R<String> getIdPrefix(String prefix) {
        return null;
    }
}
