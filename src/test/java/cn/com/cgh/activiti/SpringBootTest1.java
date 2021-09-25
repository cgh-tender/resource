package cn.com.cgh.activiti;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootTest1 {
    @Autowired
    public RuntimeService runtimeService;

    @Test
    public void setRuntimeService(){
//        ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("myLeave");
//        System.out.println("processInstance1.getId() = " + processInstance1.getId());
//        System.out.println("processInstance1.getProcessDefinitionId() = " + processInstance1.getProcessDefinitionId());
    }
}
