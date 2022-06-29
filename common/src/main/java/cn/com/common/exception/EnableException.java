package cn.com.common.exception;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({AuthGlobalExceptionHandler.class,AuthGlobalBandingExceptionHandler.class})
public @interface EnableException {
}
