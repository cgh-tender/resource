package cn.com.cgh.activiti;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDemo1 {
    public static final String deployId = "45001";
    public static final String processKey = "myLeave-variables";
    @Test
    public void createDep(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("activiti/myLeave-variables.bpmn20.xml")
                .addClasspathResource("activiti/myLeave_variables.png")
                .name("请假申请流程-variables")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(deploy.getKey());
    }

    @Test
    public void startProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee0","work");
        variables.put("assignee1","wb");
        variables.put("assignee2","yz");
        variables.put("assignee3","zc");
        variables.put("num",2);
        runtimeService.startProcessInstanceByKey(processKey,variables);
    }
    @Test
    public void startProcess1(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee0","work1");
        variables.put("assignee1","wb1");
        variables.put("assignee2","yz1");
        variables.put("assignee3","zc1");
        variables.put("num",3);
        runtimeService.startProcessInstanceByKey(processKey,variables);
    }
    @Test
    public void listTasks(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(processKey)
                .list();
        for (Task task : tasks) {
            if (task!=null){
                System.out.println(task.getId());
                System.out.println(task.getName());
                System.out.println(task.getAssignee());
            }
        }
    }

    @Test
    public void complateTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processDefinitionKey(processKey)
                .taskAssignee("work1")
                .singleResult();
        if (task!= null){
            Map<String, Object> variables = new HashMap<>();
            variables.put("assignee1","wb2");
            variables.put("assignee2","yz3");
            variables.put("assignee3","zc3");
            variables.put("num",10);
            taskService.complete(task.getId(),variables);
        }
    }

    @Test
    public void complateTask1(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processDefinitionKey(processKey)
                .taskAssignee("yz4")
                .singleResult();
        String executionId = task.getExecutionId();
        System.out.println("executionId = " + executionId);
        System.out.println("task.getTaskLocalVariables() = " + task.getTaskLocalVariables());
        RuntimeService runtimeService = processEngine.getRuntimeService();
        System.out.println(11);
//        if (task!= null){
//            taskService.complete(task.getId());
//        }
    }

    @Test
    public void listHisTasks(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricProcessInstance> list = historyService
                .createHistoricProcessInstanceQuery()
                .list();
        for (HistoricProcessInstance historicDetail : list) {
            System.out.println(historicDetail.getId());
            System.out.println(historicDetail.getName());
            System.out.println(historicDetail.getProcessVariables());
            System.out.println(historicDetail.getSuperProcessInstanceId());
            System.out.println(historicDetail.getStartTime());
            System.out.println("====================");
        }
    }

    /**
     * 动态调整受理人
     */
    @Test
    public void complateTask2(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processDefinitionKey(processKey)
                .taskAssignee("wb")
                .singleResult();
        System.out.println("task.getExecutionId() = " + task.getExecutionId());
        System.out.println(task.getTaskLocalVariables());
//        runtimeService.setVariableLocal(task.getExecutionId(),"assignee2","yz4");
//        runtimeService.setVariableLocal(task.getExecutionId(),"assignee3","zc4");
//        runtimeService.setVariableLocal(task.getExecutionId(),"num",3);
    }
}
