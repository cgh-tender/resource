package cn.com.cgh.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Service;

//@Service

/**
 * 如果 @see InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation 返回false 则所有类的ioc全部做不成
 *
 */
public class InstantiationAwareBeanPostProcessorDemo implements InstantiationAwareBeanPostProcessor {
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }
}
