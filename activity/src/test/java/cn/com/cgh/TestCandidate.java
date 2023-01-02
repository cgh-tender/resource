package cn.com.cgh;

import cn.com.cgh.activiti.ActivitiApplication;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ActivitiApplication.class)
//@AutoConfigureMockMvc
public class TestCandidate {
    @Test
    public void aVoid(){

    }
    @Test
    public void proper(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("activiti/test.bpmn20.xml")
                .name("test")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getName() = " + deploy.getName());
    }

    @Test
    public void startProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance myLeave = runtimeService.startProcessInstanceByKey("myLeave", businessKey);
        System.out.println("myLeave.getProcessDefinitionId() = " + myLeave.getProcessDefinitionId());
        System.out.println("myLeave.getBusinessKey() = " + myLeave.getBusinessKey());
        System.out.println("myLeave.getProcessInstanceId() = " + myLeave.getProcessInstanceId());
    }

    public static final String processInstanceId = "100001";
    public static final String businessKey = "33333";
    public static final String taskId = "102502";

    @Test
    public void complete(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .processInstanceBusinessKey(businessKey)
                .taskAssignee("find")
                .singleResult();
        System.out.println("task.getName() = " + task.getName());
        if (task!= null){
            taskService.complete(task.getId());
        }
    }

    @Test
    public void queryTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list =
                taskService.createTaskQuery().processInstanceId(processInstanceId)
                        .processInstanceBusinessKey(businessKey)
                        .list();
        for (Task task : list) {
            System.out.println("task.getAssignee() = " + task.getAssignee());
            System.out.println("task.getTaskDefinitionKey() = " + task.getTaskDefinitionKey());
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getName() = " + task.getName());
            System.out.println("task.getFormKey() = " + task.getFormKey());
            System.out.println("task.getOwner() = " + task.getOwner());
            System.out.println("task.getCategory() = " + task.getCategory());
        }
    }

    /**
     * 拾取任务
     */
    @Test
    public void claim() {
        String assigned = "m2";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskId(taskId)
                .taskCandidateOrAssigned(assigned)
                .singleResult();
        if (task!=null){
            taskService.claim(taskId,assigned);
        }
    }

    @Test
    public void queryClaim() {
        String assigned = "m2";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskId(taskId)
                .taskCandidateOrAssigned(assigned)
                .singleResult();
        System.out.println("task.getOwner() = " + task.getOwner());
    }

    @Test
    public void unClaim1() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskId(taskId)
                .singleResult();
        if (task!=null){
            taskService.claim(taskId,null);
        }
    }

    @Test
    public void unClaim() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskId(taskId)
                .singleResult();
        if (task!=null){
            taskService.unclaim(taskId);
        }
    }
}
