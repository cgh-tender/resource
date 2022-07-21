package cn.com.common.util;

import com.sun.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.apache.commons.lang3.StringUtils;

import javax.el.ExpressionFactory;

/**
 * <dependency>
 * <groupId>de.odysseus.juel</groupId>
 * <artifactId>juel-impl</artifactId>
 * <version>2.2.7</version>
 * </dependency>
 * <dependency>
 * <groupId>javax.el</groupId>
 * <artifactId>javax.el-api</artifactId>
 * <version>3.0.1-b06</version>
 * </dependency>
 * <dependency>
 * <groupId>org.glassfish.web</groupId>
 * <artifactId>javax.el</artifactId>
 * <version>2.2.6</version>
 * </dependency>
 * <dependency>
 * <groupId>org.apache.commons</groupId>
 * <artifactId>commons-lang3</artifactId>
 * </dependency>
 */
public class El {
    private static final ExpressionFactory factory = new ExpressionFactoryImpl();
    private static final ThreadLocal<SimpleContext> THREAD_CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<String> THREAD_EL = new ThreadLocal<>();

    public El create(String el, String... arg) {
        if (arg.length % 2 != 0) {
            throw new RuntimeException("【参数只能是双数】");
        }
        SimpleContext simpleContext = new SimpleContext();
        parser(arg, simpleContext);
        THREAD_CONTEXT.set(simpleContext);
        THREAD_EL.set(el);
        return this;
    }

    private void parser(String[] arg, SimpleContext simpleContext) {
        if (arg.length > 0) {
            String key = "";
            for (int i = 0; i < arg.length; i++) {
                if (i % 2 == 1 && StringUtils.isNotBlank(key)) {
                    simpleContext.setVariable(key, factory.createValueExpression(arg[i], String.class));
                    key = "";
                } else {
                    key = arg[i];
                }
            }
        }
    }

    public boolean get() {
        Boolean resource = (Boolean) factory.createValueExpression(THREAD_CONTEXT.get(), THREAD_EL.get(), Boolean.class).getValue(THREAD_CONTEXT.get());
        THREAD_CONTEXT.remove();
        THREAD_EL.remove();
        return resource;
    }

    public static void main(String[] args) {
        El el = new El();
        System.out.println(el.create("${name=='a'&&age>=100}", "name", "a", "age", "100").get());
    }

}
