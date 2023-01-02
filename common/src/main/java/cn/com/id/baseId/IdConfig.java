package cn.com.id.baseId;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import feign.Feign;
import feign.hystrix.HystrixFeign;
import org.springframework.context.annotation.*;

@Configuration
public class IdConfig {
    @Bean
    @Scope("prototype")
    public Feign.Builder feignHystrixBuilder() {
        return HystrixFeign.builder().setterFactory((target, method) -> HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(IdServer.class.getSimpleName()))// 控制 RemoteProductService 下,所有方法的Hystrix Configuration
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(10000) // 超时配置
                ));
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
