package cn.com.id.baseId;

import cn.com.id.FeignClientCustomBuilder;
import cn.com.common.util.R;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Id {
    private static final String NAME = "id";
    @Resource
    private DiscoveryClient discoveryClient;

    private IdServer idServer;

    public R<Long> getId() {
        if (idServer == null) {
            idServer = FeignClientCustomBuilder.getFeignClient(IdServer.class, NAME, NAME,
                    discoveryClient.getInstances(NAME).get(0).getUri().toString(),
                    null, IdFallbackFactory.class
            );
        }
        return idServer.getId();
    }

    public R<String> getIdPrefix(String prefix) {
        if (idServer == null) {
            idServer = FeignClientCustomBuilder.getFeignClient(IdServer.class, NAME, NAME,
                    discoveryClient.getInstances(NAME).get(0).getUri().toString(),
                    null, IdFallbackFactory.class
            );
        }
        return idServer.getIdPrefix(prefix);
    }

}
