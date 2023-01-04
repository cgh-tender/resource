package cn.com.cgh.gateway.filters;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class IgnoreAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<IgnoreAuthGatewayFilterFactory.Config> {
    public IgnoreAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(IgnoreAuthGatewayFilterFactory.Config config) {
        return new InnerFilter(config);
    }
    private class InnerFilter implements GatewayFilter, Ordered {

        private IgnoreAuthGatewayFilterFactory.Config config;

        InnerFilter(IgnoreAuthGatewayFilterFactory.Config config) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.info("进入innerFilter=====" + config.getValue());
            if (config.getValue()) {
                exchange.getAttributes().put("AttrbuteConstant.ATTRIBUTE_IGNORE_TEST_GLOBAL_FILTER", true);
            }
            return chain.filter(exchange);
        }

        @Override
        public int getOrder() {
            return -1000;
        }
    }

    @Getter
    @Setter
    public static class Config{
        private Boolean value;
    }
}
