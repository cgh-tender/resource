package cn.com.cgh.file.util;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({FTPNacosConfig.class,SaveFileUtil.class})
public @interface EnableCghFile {
}
