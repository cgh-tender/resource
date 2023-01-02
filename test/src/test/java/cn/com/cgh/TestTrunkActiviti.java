//package cn.com.cgh;
//
//import org.activiti.engine.*;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.junit.Test;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//public class TestTrunkActiviti {
//    public static final String deployKey = "myLeave-1";
//    @Test
//    public void proper(){
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Deployment deploy = repositoryService.createDeployment()
//                .addClasspathResource("activiti/testActivi.bpmn20.xml")
//                .deploy();
//        System.out.println("deploy.getId() = " + deploy.getId());
//        System.out.println(deploy.getKey());
//        System.out.println("deploy.getName() = " + deploy.getName());
//    }
//
//    @Test
//    public void start(){
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(deployKey, "1111");
//        System.out.println("processInstance.getId() = " + processInstance.getId());
//        System.out.println("processInstance.getName() = " + processInstance.getName());
//        System.out.println("processInstance.getProcessInstanceId() = " + processInstance.getProcessInstanceId());
//        System.out.println("processInstance.getProcessDefinitionName() = " + processInstance.getProcessDefinitionName());
//    }
//    public static ProcessEngine processEngine ;
//    @Test
//    public void trunk() throws Exception {
//        processEngine = ProcessEngines.getDefaultProcessEngine();
//        TaskService taskService = processEngine.getTaskService();
//        Task task = taskService.createTaskQuery()
//                .processInstanceBusinessKey("1111")
//                .singleResult();
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("a", 3);
////        ActivitiUtil activitiUtil = new ActivitiUtil();
////        String rollBaseTaskAss = activitiUtil.getRollBaseTaskAss(task, variables);
////        taskService.setAssignee(task.getId(),rollBaseTaskAss);
////        taskService.setAssignee(task.getId(),"test01");
//        taskService.complete(task.getId(),variables);
//        List<Task> list = taskService.createTaskQuery()
//                .processInstanceBusinessKey("1111").list();
//        for (Task task1 : list) {
////            taskService.setAssignee(task1.getId(),"test04");
////            taskService.setAssignee(task1.getId(),rollBaseTaskAss);
//        }
////        System.out.println("rollBaseTaskAss = " + rollBaseTaskAss);
//    }
//
//
//}
