package cn.com.cgh.gateway.predicates;

import org.springframework.cloud.gateway.handler.AsyncPredicate;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author haider
 * @date 2022年06月17日 22:53
 */
public class CheckRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckRoutePredicateFactory.Config> {
    public CheckRoutePredicateFactory() {
        super(Config.class);
    }

    public static final String PARAM = "param";
    public static final String KEY = "regex";

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return null;
    }

    @Override
    public Predicate<ServerWebExchange> apply(Consumer<Config> consumer) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {// true 匹配成功 False 匹配失败
                return false;
            }
        };
    }

    @Override
    public AsyncPredicate<ServerWebExchange> applyAsync(Consumer<Config> consumer) {
        return super.applyAsync(consumer);
    }

    @Override
    public void beforeApply(Config config) {
        super.beforeApply(config);
    }

    @Override
    public AsyncPredicate<ServerWebExchange> applyAsync(Config config) {
        return super.applyAsync(config);
    }

    @Override
    public String name() {
        return super.name();
    }

    @Override
    public ShortcutType shortcutType() {
        return super.shortcutType();
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARAM,KEY);
    }

    @Override
    public String shortcutFieldPrefix() {
        return super.shortcutFieldPrefix();
    }

    @Validated
    public static class Config {
        private @NotEmpty String param;
        private String regexp;

        public Config() {
        }

        public String getParam() {
            return this.param;
        }

        public CheckRoutePredicateFactory.Config setParam(String param) {
            this.param = param;
            return this;
        }

        public String getRegexp() {
            return this.regexp;
        }

        public CheckRoutePredicateFactory.Config setRegexp(String regexp) {
            this.regexp = regexp;
            return this;
        }
    }
}
