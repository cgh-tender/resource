package cn.com.cgh.common.exception;

import cn.com.cgh.common.util.HttpServlet;
import cn.com.cgh.common.util.R;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        if (e instanceof FlowException){
            HttpServlet.print(response, new R(100,"接口限流了"),200);
        }else if(e instanceof DegradeException){
            HttpServlet.print(response, new R(101,"服务降级了"),200);
        }else if(e instanceof ParamFlowException){
            HttpServlet.print(response, new R(102,"热点参数限流了"),200);
        }else if(e instanceof AuthorityException){
            HttpServlet.print(response, new R(103,"触发系统保护规则了"),200);
        }else if(e instanceof SystemBlockException){
            HttpServlet.print(response, new R(104,"授权规则不通过"),200);
        }
    }
    @PostConstruct
    public void p(){
        System.out.println("=============");
    }
}
