package cn.com.cgh.common.exception;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SentinelBlockExceptionHandler.class})
public @interface EnableSentinelException {
}
