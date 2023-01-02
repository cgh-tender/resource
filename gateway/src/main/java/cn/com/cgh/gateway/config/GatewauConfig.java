package cn.com.cgh.gateway.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;

@Configuration
public class GatewauConfig {
    @PostConstruct
    public void init() {
//        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue("限流了！！！"));
//        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}
