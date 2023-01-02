package cn.com.id.baseId;

import cn.com.common.util.R;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class IdFallbackFactory implements FallbackFactory<IdServer> {
    @Override
    public IdServer create(Throwable cause) {

        return new IdServer() {
            @Override
            public R<Long> getId() {
                throw new RuntimeException("id生成服务异常 " + cause.getMessage());
            }

            @Override
            public R<String> getIdPrefix(String prefix) {
                throw new RuntimeException("id生成服务异常 " + cause.getMessage());
            }
        };
    }
}
