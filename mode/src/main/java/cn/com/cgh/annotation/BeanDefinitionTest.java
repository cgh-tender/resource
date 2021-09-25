package cn.com.cgh.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 所有的类者要进行 加载为 BeanDefinition 类型的类实例
 */
@Component
public class BeanDefinitionTest implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
//        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
//        beanDefinition.setBeanClass(Student.class);
//
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue("");
//
//        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
//        propertyValues.addPropertyValue("name","xxxx");
//
//        beanDefinitionRegistry.registerBeanDefinition("student1",beanDefinition);


//        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
//        scanner.addIncludeFilter(new AnnotationTypeFilter());
//        scanner.scan("cn.com.cgh");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
