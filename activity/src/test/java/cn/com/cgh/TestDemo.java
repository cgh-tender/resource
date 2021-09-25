package cn.com.cgh;


import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDemo {
    public static final String myLeave = "myLeave";
    public static final String assignee = "worker";
    public static final String manager = "manager";
    public static final String find = "find";
    /**
     * 部署
     */
    @Test
    public void activitiTestDeployment(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("activiti/myLeave.bpmn20.xml")
                .addClasspathResource("activiti/myLeave.png")
                .name("请假申请流程")
                .deploy();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }

    /**
     * 启动
     */
    @Test
    public void testStartProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey("myLeave","2");
        System.out.println(processInstance.getProcessDefinitionId());
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getActivityId());

    }

    /**
     * 查看
     */
    @Test
    public void testFindPersonalTaskList(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list =
                taskService.createTaskQuery()
                .processDefinitionKey(myLeave)
                .taskAssignee(assignee)
                .list();
        for (Task task : list) {
            System.out.println("任务id: "+task.getId());
            System.out.println("流程实例id: "+task.getProcessDefinitionId());
            System.out.println("任务负责人: "+task.getAssignee());
            System.out.println("任务名称: "+task.getName());
            System.out.println("当前任务id: "+task.getProcessInstanceId());
        }
    }
    /**
     * 提交申请
     */
    @Test
    public void testworkiComplete(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery()
                .processDefinitionKey(myLeave)
                .taskAssignee(assignee)
                .processInstanceId("37501")
                .singleResult();
        Map<String, Object> resource = new HashMap<>();
        resource.put("aaa","aaa");

        taskService.complete(task.getId(),resource);
    }
    /**
     * manager 查看
     */
    @Test
    public void testFindManagerPersonalTaskList(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list =
                taskService.createTaskQuery()
                        .processDefinitionKey(myLeave)
                        .taskAssignee(manager)
                        .processInstanceId("37501")
                        .list();
        for (Task task : list) {
            System.out.println("任务id: "+task.getId());
            System.out.println("流程实例id: "+task.getProcessDefinitionId());
            System.out.println("任务负责人: "+task.getAssignee());
            System.out.println("任务名称: "+task.getName());
            System.out.println("任务id: "+task.getProcessInstanceId());
        }
        Task task = taskService.createTaskQuery().processDefinitionKey(myLeave)
                .taskAssignee(manager)
                .processInstanceId("37501")//22502
                .singleResult();
        Map<String, Object> resource = new HashMap<>();
        resource.put("bbb","bbb");

        taskService.complete(task.getId(),resource);
    }

    @Test
    public void testFindFindPersonalTaskList(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list =
                taskService.createTaskQuery()
                        .processDefinitionKey(myLeave)
                        .taskAssignee(find)
                        .list();
        for (Task task : list) {
            System.out.println("任务id: "+task.getId());
            System.out.println("流程实例id: "+task.getProcessDefinitionId());
            System.out.println("任务负责人: "+task.getAssignee());
            System.out.println("任务名称: "+task.getName());
            System.out.println("任务id: "+task.getProcessInstanceId());
        }
        Task task = taskService.createTaskQuery().processDefinitionKey(myLeave)
//                .processInstanceBusinessKey("1111")
                .processInstanceId("37501")//22502
                .taskAssignee(find)
                .singleResult();
        Map<String, Object> resource = new HashMap<>();
        resource.put("ccc","ccc");

        taskService.complete(task.getId(),resource);
    }

    @Test
    public void queryProcessDefinition(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey(myLeave)
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println("流程定义id "+processDefinition.getId());
            System.out.println("流程定义name "+processDefinition.getName());
            System.out.println("流程定义key "+processDefinition.getKey());
            System.out.println("流程定义version "+processDefinition.getVersion());
            System.out.println("流程部署ID "+processDefinition.getDeploymentId());
        }
    }

    @Test
    public void queryProcessInstances(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        HistoryService historyService = processEngine.getHistoryService();
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processDefinitionKey(myLeave)
                .list();
        for (ProcessInstance processDefinition : list) {
            System.out.println("流程实例id  "+processDefinition.getProcessInstanceId());
            System.out.println("所属流程定义id  "+processDefinition.getProcessDefinitionId());
            System.out.println("是否执行完成  "+processDefinition.isEnded());
            System.out.println("是否暂停  "+processDefinition.isSuspended());
            System.out.println("当前活动标识  "+processDefinition.getActivityId());
            System.out.println("业务关键字  "+processDefinition.getBusinessKey());
            System.out.println("================");
        }
        System.out.println("================");
        List<HistoricProcessInstance> list1 = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(myLeave)
                .list();
        for (HistoricProcessInstance processDefinition : list1) {
            System.out.println("流程实例id  "+processDefinition.getSuperProcessInstanceId());
            System.out.println("所属流程定义id  "+processDefinition.getProcessDefinitionId());
            System.out.println("当前活动标识  "+processDefinition.getStartActivityId());
            System.out.println("当前活动标识  "+processDefinition.getEndActivityId());
            System.out.println("业务关键字  "+processDefinition.getBusinessKey());
            System.out.println("================");
        }
    }

    @Test
    public void deleteDeployment(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment("7501",true); // 强制删除
//        repositoryService.deleteDeployment("7501");
    }

    @Test
    public void queryHistoryInfo(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        instanceQuery.processInstanceId("22501");
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        List<HistoricActivityInstance> list = instanceQuery.list();
        for (HistoricActivityInstance hi : list) {
            System.out.println(hi.getActivityId());
            System.out.println(hi.getActivityName());
            System.out.println(hi.getProcessDefinitionId());
            System.out.println(hi.getProcessInstanceId());
            System.out.println(hi.getAssignee());
            System.out.println("===================");
        }
    }

    /**
     * 挂起定义
     */
    @Test
    public void allProcessSuspended(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        System.out.println(list.size());
        for (ProcessDefinition processDefinition : list) {
            System.out.println(processDefinition.isSuspended());
            System.out.println(processDefinition.getId());
            if (processDefinition.isSuspended()){
                repositoryService.activateProcessDefinitionById(processDefinition.getId(),true,null);
                System.out.println("流程定义id: " + processDefinition.getId() + " 激活!");
                System.out.println("流程定义id: " + processDefinition.getName() + " 激活!");
            }else {
                repositoryService.suspendProcessDefinitionById(processDefinition.getId(),true,null);
                System.out.println("流程定义id: " + processDefinition.getId() + " 挂起!");
                System.out.println("流程定义id: " + processDefinition.getName() + " 挂起!");
            }
        }
    }
    /**
     * 挂起 单个 流程实例
     */
    @Test
    public void allRunProcessSuspended(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
                .processInstanceId("37501")
                .list();
        System.out.println(list.size());
        for (ProcessInstance processDefinition : list) {
            System.out.println(processDefinition.isSuspended());
            System.out.println(processDefinition.getId());
            if (processDefinition.isSuspended()){
                runtimeService.activateProcessInstanceById(processDefinition.getId());
                System.out.println("流程定义id: " + processDefinition.getId() + " 激活!");
                System.out.println("流程定义id: " + processDefinition.getName() + " 激活!");
            }else {
                runtimeService.suspendProcessInstanceById(processDefinition.getId());
                System.out.println("流程定义id: " + processDefinition.getId() + " 挂起!");
                System.out.println("流程定义id: " + processDefinition.getName() + " 挂起!");
            }
        }
    }




}
