package cn.com.cgh;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.task.Task;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.*;

public class ActivitiUtil {
    public static ProcessEngine processEngine = null;
    public String getRollBaseTaskAss(Task task,Map<String,Object> variables) throws Exception {
        String ass = null;
        processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> hisTaskList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .orderByTaskCreateTime()
                .desc()
                .list();
        UserTask element = (UserTask) getNextUserFlowElement(task);
        List<SequenceFlow> flows = element.getOutgoingFlows();
        String defId = null;
        for (SequenceFlow flow : flows) {
            String conditionExpression = flow.getConditionExpression();
            for (String s : variables.keySet()) {
                if (conditionExpression.contains(s) && isCondition(s,conditionExpression, String.valueOf(variables.get(s)))) {
                    defId = flow.getTargetRef();
                    break;
                }
            }
        }
        if (defId != null){
            for (HistoricTaskInstance instance : hisTaskList) {
                if (defId.equalsIgnoreCase(instance.getTaskDefinitionKey())){
                    ass = instance.getAssignee();
                    break;
                }
            }
        }

        return ass;
    }

    private String getNextActivityId(String executionId,
                                           String processInstanceId,
                                           List<SequenceFlow> outgoingFlows) {
        String activityId = null;
        // 遍历出线
        for (SequenceFlow outgoingFlow : outgoingFlows) {
            // 取得线上的条件
            String conditionExpression = outgoingFlow.getConditionExpression();
            // 取得所有变量
            Map<String, Object> variables = processEngine.getRuntimeService().getVariables(executionId);
            String variableName = "";
            // 判断网关条件里是否包含变量名
            for (String s : variables.keySet()) {
                if (conditionExpression.contains(s)) {
                    // 找到网关条件里的变量名
                    variableName = s;
                }
            }
            String conditionVal = getVariableValue(variableName, processInstanceId);
            // 判断el表达式是否成立
            if (isCondition(variableName, conditionExpression, conditionVal)) {
                // 取得目标节点
                FlowElement targetFlowElement = outgoingFlow.getTargetFlowElement();
                activityId = targetFlowElement.getId();
                continue;
            }
        }
        return activityId;
    }

    private FlowElement getNextUserFlowElement(Task task) throws Exception {
        // 取得已提交的任务
        HistoricTaskInstance historicTaskInstance = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
                .taskId(task.getId()).singleResult();
        // 获得流程定义
        ProcessDefinition processDefinition = processEngine.getRepositoryService().getProcessDefinition(historicTaskInstance.getProcessDefinitionId());
        //获得当前流程的活动ID
        ExecutionQuery executionQuery = processEngine.getRuntimeService().createExecutionQuery();
        Execution execution = executionQuery.executionId(historicTaskInstance.getExecutionId()).singleResult();
        String activityId = execution.getActivityId();
        UserTask userTask = null;
        while (true) {
            //根据活动节点获取当前的组件信息
            FlowNode flowNode = getFlowNode(processDefinition.getId(), activityId);
            //获取该节点之后的流向
            List<SequenceFlow> sequenceFlowListOutGoing = flowNode.getOutgoingFlows();
            // 获取的下个节点不一定是userTask的任务节点，所以要判断是否是任务节点
            if (sequenceFlowListOutGoing.size() > 1) {
                // 如果有1条以上的出线，表示有分支，需要判断分支的条件才能知道走哪个分支
                // 遍历节点的出线得到下个activityId
                activityId = getNextActivityId(execution.getId(),
                        task.getProcessInstanceId(), sequenceFlowListOutGoing);
            } else if (sequenceFlowListOutGoing.size() == 1) {
                // 只有1条出线,直接取得下个节点
                SequenceFlow sequenceFlow = sequenceFlowListOutGoing.get(0);
                // 下个节点
                FlowElement flowElement = sequenceFlow.getTargetFlowElement();
                if (flowElement instanceof UserTask) {
                    // 下个节点为UserTask时
                    userTask = (UserTask) flowElement;
                    System.out.println("下个任务为:" + userTask.getName());
                    return userTask;
                } else if (flowElement instanceof ExclusiveGateway) {
                    // 下个节点为排它网关时
                    ExclusiveGateway exclusiveGateway = (ExclusiveGateway) flowElement;
                    List<SequenceFlow> outgoingFlows = exclusiveGateway.getOutgoingFlows();
                    // 遍历网关的出线得到下个activityId
                    activityId = getNextActivityId(execution.getId(), task.getProcessInstanceId(), outgoingFlows);
                }
            } else {
                // 没有出线，则表明是结束节点
                return null;
            }
        }
    }
    private FlowNode getFlowNode(String processDefinitionId, String flowElementId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(processDefinitionId);
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(flowElementId);
        return flowNode;
    }

    private String getVariableValue(String variableName, String processInstanceId) {
        Execution execution = processEngine.getRuntimeService()
                .createExecutionQuery().processInstanceId(processInstanceId).list().get(0);
        Object object = processEngine.getRuntimeService().getVariable(execution.getId(), variableName);
        return object == null ? "" : object.toString();
    }
    private boolean isCondition(String key, String el, String value) {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        context.setVariable(key, factory.createValueExpression(value, String.class));
        ValueExpression e = factory.createValueExpression(context, el, boolean.class);
        return (Boolean) e.getValue(context);
    }

}