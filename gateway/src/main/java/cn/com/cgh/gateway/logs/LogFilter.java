package cn.com.cgh.gateway.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author haider
 * @date 2022年06月18日 12:58
 */
@Component
public class LogFilter implements GlobalFilter {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(exchange.getRequest().getPath().value());
        log.info(exchange.getRequest().getLocalAddress().getAddress().getHostAddress());
        return chain.filter(exchange);
    }
}
