package cn.com.cgh.gateway.logs;

import cn.com.cgh.gateway.logs.pojo.LogCgh;
import cn.com.cgh.gateway.logs.service.LogCghService;
import cn.com.common.util.NetUtils;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @author haider
 * @date 2022年06月18日 12:58
 */
@Component
@Slf4j(topic = "log")
public class LogFilter implements GlobalFilter {
    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();
    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();

    @Autowired
    private LogCghService logCghService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("{} {}",NetUtils.getLocalAddress(),exchange.getRequest().getPath().value());
        LogCgh logCgh = parseGateway(exchange);
        ServerHttpRequest request = exchange.getRequest();
        MediaType mediaType = request.getHeaders().getContentType();
        if (Objects.isNull(mediaType)) {
            return writeNormalLog(exchange, chain, logCgh);
        }
        logCgh.setRequestContentType(mediaType.getType() + "/" + mediaType.getSubtype());
        // 对不同的请求类型做相应的处理
        if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
            return writeBodyLog(exchange, chain, logCgh);
        } else if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(mediaType) || MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)) {
            return readFormData(exchange, chain, logCgh);
        } else {
            return writeBasicLog(exchange, chain, logCgh);
        }
//        return chain.filter(exchange);
    }

    private Mono<Void> writeBasicLog(ServerWebExchange exchange, GatewayFilterChain chain, LogCgh logCgh) {
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            DataBufferUtils.retain(dataBuffer);
            final Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
            final ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }

                @Override
                public MultiValueMap<String, String> getQueryParams() {
                    return UriComponentsBuilder.fromUri(exchange.getRequest().getURI()).build().getQueryParams();
                }
            };
            StringBuilder builder = new StringBuilder();
            MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
            if (CollectionUtil.isNotEmpty(queryParams)) {
                for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
                    builder.append(entry.getKey()).append("=").append(entry.getValue()).append(StrPool.COMMA);
                }
            }
            logCgh.setRequestBody(builder.toString());            //获取响应体
            ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, logCgh);
            return chain.filter(exchange.mutate().request(mutatedRequest).response(decoratedResponse).build()).then(Mono.fromRunnable(() -> {                        // 打印日志
                // 推送相应的报告
                report(logCgh);
            }));
        });
    }

    private void report(LogCgh logCgh) {
//        logCghService.save(logCgh);
    }

    private Mono<Void> readFormData(ServerWebExchange exchange, GatewayFilterChain chain, LogCgh logCgh) {
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            DataBufferUtils.retain(dataBuffer);
            final Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
            final ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }

                @Override
                public MultiValueMap<String, String> getQueryParams() {
                    return UriComponentsBuilder.fromUri(exchange.getRequest().getURI()).build().getQueryParams();
                }
            };
            final HttpHeaders headers = exchange.getRequest().getHeaders();
            if (headers.getContentLength() == 0) {
                return chain.filter(exchange);
            }
            ResolvableType resolvableType;
            if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(headers.getContentType())) {
                resolvableType = ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, Part.class);
            } else {
                //解析 application/x-www-form-urlencoded
                resolvableType = ResolvableType.forClass(String.class);
            }

            return MESSAGE_READERS.stream().filter(reader -> reader.canRead(resolvableType, mutatedRequest.getHeaders().getContentType())).findFirst().orElseThrow(() -> new IllegalStateException("no suitable HttpMessageReader.")).readMono(resolvableType, mutatedRequest, Collections.emptyMap()).flatMap(resolvedBody -> {
                if (resolvedBody instanceof MultiValueMap) {
                    LinkedMultiValueMap map = (LinkedMultiValueMap) resolvedBody;
                    if (CollectionUtil.isNotEmpty(map)) {
                        StringBuilder builder = new StringBuilder();
                        final Part bodyPartInfo = (Part) ((MultiValueMap) resolvedBody).getFirst("body");
                        if (bodyPartInfo instanceof FormFieldPart) {
                            String body = ((FormFieldPart) bodyPartInfo).value();
                            builder.append("body=").append(body);
                        }
                        logCgh.setRequestBody(builder.toString());
                    }
                } else {
                    logCgh.setRequestBody((String) resolvedBody);
                }

                //获取响应体
                ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, logCgh);
                return chain.filter(exchange.mutate().request(mutatedRequest).response(decoratedResponse).build()).then(Mono.fromRunnable(() -> {                                    // 打印日志
                    // 推送相应的报告
                    report(logCgh);
                }));
            });
        });
    }

    private Mono<Void> writeBodyLog(ServerWebExchange exchange, GatewayFilterChain chain, LogCgh logCgh) {
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(body -> {
            logCgh.setRequestBody(body);
            return Mono.just(body);
        });
        // 通过 BodyInserter 插入 body(支持修改body), 避免 request body 只能获取一次
        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        // the new content type will be computed by bodyInserter
        // and then set in the request decorator
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {                    // 重新封装请求
            ServerHttpRequest decoratedRequest = requestDecorate(exchange, headers, outputMessage);                    // 记录响应日志
            ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, logCgh);                    // 记录普通的
            return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse).build()).then(Mono.fromRunnable(() -> {                                // 打印日志
                // 推送相应的报告
                report(logCgh);
            }));
        }));
    }

    private Mono<Void> writeNormalLog(ServerWebExchange exchange, GatewayFilterChain chain, LogCgh logCgh) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            int value = response.getStatusCode().value();
            logCgh.setCode(value);
            long executeTime = DateUtil.between(logCgh.getRequestTime(), new Date(), DateUnit.MS);
            logCgh.setExecuteTime(executeTime);
            ServerHttpRequest request = exchange.getRequest();
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            Map<String, String> paramsMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(queryParams)) {
                for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
                    paramsMap.put(entry.getKey(), StrUtil.join(StrPool.COMMA, entry.getValue()));
                }
            }
            logCgh.setQueryParams(JSONUtil.toJsonStr(paramsMap));
            report(logCgh);
        }));
    }

    private Route getGatewayRoute(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    }

    private LogCgh parseGateway(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        Route route = getGatewayRoute(exchange);
        return LogCgh.builder()
                .ip(NetUtils.getLocalAddress())
                .mode(request.getURI().getScheme())
                .method(request.getMethodValue())
                .path(request.getPath().pathWithinApplication().value())
                .targetServer(route.getId())
                .requestTime(new Date()).build();
    }

    private ServerHttpRequestDecorator requestDecorate(ServerWebExchange exchange, HttpHeaders headers, CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

    private ServerHttpResponseDecorator recordResponseLog(ServerWebExchange exchange, LogCgh logCgh) {
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    // 计算执行时间
                    long executeTime = DateUtil.between(logCgh.getRequestTime(), new Date(), DateUnit.MS);
                    logCgh.setExecuteTime(executeTime);
                    // 获取响应类型，如果是 json 就打印
                    String originalResponseContentType = exchange.getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);//
                    logCgh.setCode(this.getStatusCode().value());
                    //
                    if (Objects.equals(this.getStatusCode(), HttpStatus.OK)
                            && !StringUtil.isNullOrEmpty(originalResponseContentType)
                            && originalResponseContentType.contains("application/json")) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            // 合并多个流集合，解决返回体分段传输
                            DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                            DataBuffer join = dataBufferFactory.join(dataBuffers);
                            byte[] content = new byte[join.readableByteCount()];
                            // 释放掉内存
                            join.read(content);
                            DataBufferUtils.release(join);
                            return bufferFactory.wrap(content);
                        }));
                    } else {

                    }
                }
                return super.writeWith(body);
            }
        };
    }
}
