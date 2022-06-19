package cn.com.cgh.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author haider
 * @date 2022年06月18日 12:28
 */
@Component
public class CheckGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckGatewayFilterFactory> {
    @Override
    public GatewayFilter apply(CheckGatewayFilterFactory config) {
        return null;
    }
}
