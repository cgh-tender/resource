//package cn.com.cgh;
//
//import com.baidu.fsg.uid.impl.CachedUidGenerator;
//import com.baidu.fsg.uid.impl.DefaultUidGenerator;
//import org.activiti.engine.*;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.*;
//import java.util.concurrent.CountDownLatch;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestApplication.class)
////@AutoConfigureMockMvc
//public class TestCandidate {
//    @Resource
//    DefaultUidGenerator defaultUidGenerator;
//    @Resource
//    CachedUidGenerator cachedUidGenerator;
//    public synchronized long getUID(){
//        return cachedUidGenerator.getUID();
//    }
//    @Test
//    public void aVoid() throws InterruptedException {
//        HashSet<Long> set = new HashSet<>();
////        CountDownLatch count = new CountDownLatch(10);
//        for (int j = 0; j < 200000; j++) {
////            new Thread(() -> {
////                for (int i = 0; i < 10000; i++) {
////                    set.add(getUID());
////                }
////                count.countDown();
////            }).start();
//            set.add(getUID());
//        }
////        count.await();
//        System.out.println(set.size());
//        System.out.println(defaultUidGenerator.getUID());
//        System.out.println(cachedUidGenerator.getUID());
//    }
//    public ProcessEngine getProcessEngine(){
//        return ProcessEngines.getDefaultProcessEngine();
//    }
//    /**
//     * 安装 test xml
//     * deploy.getId() = e3c5dfc8-748d-11ed-b4f6-00ff09f86a5e
//     * deploy.getName() = test
//     * deploy.getKey() = test
//     */
//    @Test
//    public void proper(){
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Deployment deploy = repositoryService.createDeployment()
//                .addClasspathResource("activiti/test.bpmn20.xml")
//                .name("test")
//                .key("test")
//                .deploy();
//        System.out.println("deploy.getId() = " + deploy);
//    }
//
//    /** 646.84
//     *  deploy.getId() = DeploymentEntity[id=e3c5dfc8-748d-11ed-b4f6-00ff09f86a5e, name=test]
//     * myLeave.getProcessDefinitionId() = test:1:e3f1d1ca-748d-11ed-b4f6-00ff09f86a5e
//     *
//     * select * from act_hi_taskinst; -- aa5d1041-7498-11ed-8a8b-00ff09f86a5e
//     * select * from act_ru_execution; -- aa5d1041-7498-11ed-8a8b-00ff09f86a5e
//     * select * from act_ru_task; -- aa5d1041-7498-11ed-8a8b-00ff09f86a5e
//     * select * from act_hi_procinst;-- aa5d1041-7498-11ed-8a8b-00ff09f86a5e
//     */
//    @Test
//    public void startProcess(){
////        proper();
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        ProcessInstance myLeave = runtimeService.startProcessInstanceByKey("test", businessKey);
//        System.out.println("myLeave.getProcessDefinitionId() = " + myLeave.getProcessDefinitionId());
//        System.out.println("myLeave.getBusinessKey() = " + myLeave.getBusinessKey());
//        System.out.println("myLeave.getProcessInstanceId() = " + myLeave.getProcessInstanceId());
////        complete("user1");
////        complete("a");
//    }
//
//    public static final String processInstanceId = "aa5d1041-7498-11ed-8a8b-00ff09f86a5e";
//    public static final String businessKey = "0";
//    public static final String taskId = "102502";
//
//    @Test
//    public void complete(){
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        TaskService taskService = processEngine.getTaskService();
//        Task task = taskService.createTaskQuery()
//                .processInstanceId(processInstanceId)
//                .processInstanceBusinessKey(businessKey)
//                .taskAssignee("user1")
//                .singleResult();
//        System.out.println("task.getName() = " + task);
//        Map<String, Object> var = new HashMap<>();
////        var.put("a,b,c", Arrays.asList("a","b"));
//        taskService.complete(task.getId(),var);
//    }
//
//    @Test
//    public void queryTask(){
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        TaskService taskService = processEngine.getTaskService();
//        List<Task> list =
//                taskService.createTaskQuery().processInstanceId(processInstanceId)
//                        .processInstanceBusinessKey(businessKey)
//                        .list();
//        Task task = list.get(0);
//        System.out.println(task.getProcessInstanceId());
//        System.out.println(task.getAssignee());
//    }
//
//    /**
//     * 拾取任务
//     */
//    @Test
//    public void claim() {
//        String assigned = "m2";
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        TaskService taskService = processEngine.getTaskService();
//        List<Task> list =
//                taskService.createTaskQuery().processInstanceId(processInstanceId)
//                        .processInstanceBusinessKey(businessKey)
//                        .list();
//        Task task = list.get(0);
//        taskService.claim(task.getId(),assigned);
//    }
//
//    @Test
//    public void queryClaim() {
//        String assigned = "m2";
//        List<Task> list =
//                getProcessEngine().getTaskService().createTaskQuery().processInstanceId(processInstanceId)
//                        .processInstanceBusinessKey(businessKey)
//                        .list();
//        Task task = list.get(0);
//        System.out.println("task.getOwner() = " + task.getOwner());
//    }
//
//    @Test
//    public void unClaim1() {
//        Task task = getProcessEngine().getTaskService().createTaskQuery().taskId(taskId)
//                .singleResult();
//        getProcessEngine().getTaskService().unclaim(taskId);
//    }
//
//    @Test
//    public void unClaim() {
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        TaskService taskService = processEngine.getTaskService();
//        Task task = taskService.createTaskQuery().taskId(taskId)
//                .singleResult();
//        if (task!=null){
//            taskService.unclaim(taskId);
//        }
//    }
//
//    @Test
//    public void delegateTask(){
//        List<Task> list = getProcessEngine().getTaskService().createTaskQuery().processInstanceId(processInstanceId)
//                .processInstanceBusinessKey(businessKey)
//                .list();
//        Task task = list.get(0);
//        getProcessEngine().getTaskService().delegateTask(task.getId(),"dd");
//    }
//    @Test
//    public void resolveTask(){
//        List<Task> list = getProcessEngine().getTaskService().createTaskQuery().processInstanceId(processInstanceId)
//                .processInstanceBusinessKey(businessKey)
//                .list();
//        Task task = list.get(0);
//        getProcessEngine().getTaskService().resolveTask(task.getId());
//    }
//}
