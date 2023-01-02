package cn.com.id;

import feign.hystrix.FallbackFactory;
import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class FeignClientCustomBuilder implements ApplicationContextAware {
    private static FeignClientBuilder builder;

    /**
     * 手动生成FeignClient,准备一个FeignClient基类，该类不用打{@link org.springframework.cloud.openfeign.FeignClient}注解
     *
     * @param clazz     feignClient基类
     * @param name
     * @param contextId
     * @param url
     * @param <T>
     * @return
     */
    public static <T> T getFeignClient(Class<T> clazz, String name, String contextId, String url
            , Class<? extends T> fallback, Class<? extends FallbackFactory<? extends T>> fallbackFactory
    ) {
        FeignClientBuilder.Builder<T> feignClientBuilder = builder.forType(clazz, name);
        if (fallbackFactory != null) {
            feignClientBuilder.fallbackFactory(fallbackFactory);
        } else if (fallback != null) {
            feignClientBuilder.fallback(fallback);
        }
        return feignClientBuilder
                .contextId(contextId)
                .url(url).build();
    }


    /**
     * 通过spring applicationContext生成builder
     *
     * @param applicationContext 实现ApplicationContextAware接口回调注入app
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        builder = new FeignClientBuilder(applicationContext);
    }
}
