package cn.com.common.exception;

import cn.com.common.util.HttpServlet;
import cn.com.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @NotBlank
 * @NotNull
 * 等
 */
@ControllerAdvice
@Order(1000)
@Slf4j
public class AuthGlobalBandingExceptionHandler {
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public void exceptionHandler(BindException e)
    {
        log.info("@"+e.getBindingResult().getFieldError().getCode() + " ===> " + e.getBindingResult().getFieldError().getDefaultMessage());
        HttpServlet.print(R.failed(e.getBindingResult().getFieldError().getDefaultMessage()),200);
    }
}
