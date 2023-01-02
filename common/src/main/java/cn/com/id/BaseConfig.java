package cn.com.id;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(value = {
        "cn.com.id.baseId"
})
@Import(value = {
        FeignClientsConfiguration.class,
        FeignClientCustomBuilder.class
})
@Slf4j
public class BaseConfig {

    @PostConstruct
    public void init(){
        log.info("EnableId ...");
    }
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
//    @Bean
//    public IdServer idServer(Contract contract, Decoder decoder, Encoder encoder, DiscoveryClient discoveryClient) {
//        return FeignClientCustomBuilder.getFeignClient(IdServer.class,"id","id",
//                discoveryClient.getInstances("id").get(0).getUri().toString()
//                );
////        return Feign.builder()
////                .contract(contract)
////                .encoder(encoder)
////                .decoder(decoder)
////                .target(IdServer.class,
////                        discoveryClient.getInstances("id").get(0).getUri().toString());
//    }
}
