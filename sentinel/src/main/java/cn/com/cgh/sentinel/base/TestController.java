package cn.com.cgh.sentinel.base;


import cn.com.cgh.common.exception.ServiceException;
import cn.com.cgh.sentinel.entity.User;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/sentinel")
@RestController
@Slf4j
public class TestController {

    private static final String RESOURCE_NAME = "hello";
    private static final String USER_RESOURCE_NAME = "user";
    private static final String DEGRADE_RESOURCE_NAME = "degrade";

    /**
     * 资源 接口
     * @return
     */
    @GetMapping("/hello")
    public String hello(){
        Entry entry = null;
        try {
            entry = SphU.entry(RESOURCE_NAME);
            String str = "hello world";
            // TODO
            log.info(str);
            return str;
        }catch (BlockException e){
            log.info("流控！");
            return "流控！" ;
        }catch (Exception e1){
            Tracer.traceEntry(e1,entry);
        }finally {
            if (entry!=null){
                entry.exit();
            }
        }
        return null;
    }

    @PostConstruct
    public void p(){
        /**
         * 流控
         */
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(RESOURCE_NAME);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);

        rules.add(rule);

        FlowRule rule1 = new FlowRule();
        rule1.setResource(USER_RESOURCE_NAME);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setCount(1);

        rules.add(rule1);
        FlowRuleManager.loadRules(rules);

        /**
         * 熔断降级
         */
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(DEGRADE_RESOURCE_NAME);
        //RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT 异常数
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 触发熔断异常数
        degradeRule.setCount(2);
        // 触发熔断最小请求数
        degradeRule.setMinRequestAmount(2);
        // 统计时常
        degradeRule.setStatIntervalMs(60*1000);
//      熔断时长
        degradeRule.setTimeWindow(10);
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);
    }

    /**
     * blockHandler 默认声明在当前类中
     * 如果不在同一个类 需要设置 一下 blockHandlerClass blockHandlerForGetUser 需要 static
     * fallback 接口出现异常的方法
     * blockHandler 、fallback 同时指定了；则 blockHandler优先级高
     * @param id
     * @return
     */
    @GetMapping("/user")
    @SentinelResource(value = USER_RESOURCE_NAME
            , fallback = "fallbackHandlerForGetUser"
//            , exceptionsToIgnore = {ArithmeticException.class}
            , blockHandler = "blockHandlerForGetUser")
    public User getUser(String id){
        int i = 1/0;
        return new User(id);
    }

    @GetMapping("/degrade")
//    @SentinelResource(value = DEGRADE_RESOURCE_NAME
//            , entryType = EntryType.IN
//            , blockHandler = "blockHandlerForDegrade"
//    )
    public User degrade(String id){
        throw new ServiceException("异常");
    }

    /**
     * public
     * 返回值 与 原方法一样
     * @param id
     * @param ex
     * @return
     */
    public User blockHandlerForGetUser(String id,BlockException ex){
        ex.printStackTrace();
        return new User("流控！"+ex.getMessage());
    }

    public User blockHandlerForDegrade(String id,BlockException ex){
        ex.printStackTrace();
        return new User("降级熔断！"+ex.getMessage());
    }


    public User fallbackHandlerForGetUser(String id,Throwable ex){
        ex.printStackTrace();
        return new User("Exception！"+ex.getMessage());
    }
}
