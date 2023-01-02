package cn.com.common.exception;

import cn.com.common.util.HttpServlet;
import cn.com.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(1)
@Slf4j(topic = "AuthGlobalExceptionHandler")
public class AuthGlobalExceptionHandler {
    @ExceptionHandler(value = DaoException.class)
    @ResponseBody
    public void defaultErrorHandler(HttpServletRequest req, DaoException e) throws Exception {
        HttpServlet.print(R.failed().msg(e.getMessage()),500);
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public void defaultErrorHandler(HttpServletRequest req, ServiceException e) throws Exception {
        HttpServlet.print(R.failed().msg(e.getMessage()),500);
    }

}