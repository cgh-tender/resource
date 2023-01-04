package cn.com.cgh.gateway.filters;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.GatewayToStringStyler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haider
 * @date 2022年06月18日 12:28
 */
@Component
@Slf4j
public class CghLogGatewayFilterFactory extends AbstractGatewayFilterFactory<CghLogGatewayFilterFactory.LogConf> {
    public List<String> shortcutFieldOrder() {
        List<String> resource = new ArrayList<>();
        resource.add("age");
        resource.add("age1");
        return resource;
    }

    public CghLogGatewayFilterFactory() {
        super(LogConf.class);
    }


    @Override
    public GatewayFilter apply(final LogConf config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                log.info(JSONUtil.toJsonStr(config));
                return chain.filter(exchange.mutate().request(request).build());
            }

            public String toString() {
                return GatewayToStringStyler.filterToStringCreator(CghLogGatewayFilterFactory.this)
                        .append("arg", config.getAge())
                        .append("arg1", config.getAge1()).toString();
            }
        };
    }

    public static class LogConf {
        private String age;
        private String age1;

        public LogConf() {
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAge1() {
            return age1;
        }

        public void setAge1(String age1) {
            this.age1 = age1;
        }
    }
}
