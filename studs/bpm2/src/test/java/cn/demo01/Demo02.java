package cn.demo01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo02 {
	ProcessEngine processEnging;

	@Test
	public void init() {
		System.err.println(processEnging);
	}

	@Before
	public void before() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-pubs.xml");
		processEnging = ctx.getBean("processEngine", ProcessEngine.class);
	}

	/**
	 * 张三申请请假流程
	 */
	@Test
	public void testStartProcdef() {
		RuntimeService rs = processEnging.getRuntimeService();
		Map<String, Object> map = new HashMap<>();
		map.put("user", "张三");
		map.put("days", 2);

		ProcessInstance pi = rs.startProcessInstanceByKey("leave", map);
		System.err.println(pi.getId());

	}

	@Test
	public void testSure() {
		TaskService ts = processEnging.getTaskService();
		Map<String, Object> map = new HashMap<>();
		map.put("cancel", true);
		ts.complete("80006", map);
	}

	/**
	 * 查询当前用户的所有任务
	 */
	@Test
	public void testTaskByUser() {
		TaskService rs = processEnging.getTaskService();
		RuntimeService run = processEnging.getRuntimeService();
		List<Task> list = rs.createTaskQuery()//
				.taskAssignee("张三")// 设置用户名
				.orderByTaskId().desc().list();
		for (Task task : list) {
			System.err.println(task.getId());
			System.err.println(task.getExecutionId());
			System.err.println(task.getName());
			// 获取流程变量
			Map<String, Object> vars = run.getVariables(task.getExecutionId());
			System.err.println(vars);
			System.err.println("------------");
		}
	}

	/**
	 * 查询出当前正在执行的流程定义的节点，并进行定位
	 */
	@Test
	public void queryShape() {
		String procdefid = "leave:1:77504";// 流程实例id
		String activityId = "usertask1";// 节点id,通过查看xml获知
		RepositoryService rs = processEnging.getRepositoryService();
		ProcessDefinition pd = rs.getProcessDefinition(procdefid);
		if (pd instanceof ProcessDefinitionEntity) {
			ProcessDefinitionEntity pde = (ProcessDefinitionEntity) pd;
			ActivityImpl activityImpl = pde.findActivity(activityId);
			System.err.println(activityImpl.getX());
			System.err.println(activityImpl.getY());
			System.err.println(activityImpl.getWidth());
			System.err.println(activityImpl.getHeight());
		}
	}

	/**
	 * 查询当前用户可以使用的流程
	 */
	public void test1() {
		// TODO
	}

	/**
	 * 查询某个用户正在执行的任务<br>
	 * 即没有完成的任务，然后再根据历史任务查询出流程实例的信息
	 */
	@Test
	public void queryHistory() {
		// 获取历史Service
		HistoryService hs = processEnging.getHistoryService();
		// 获取流程实例Service
		RuntimeService rs = processEnging.getRuntimeService();
		// 查询出当前任务的名称
		TaskService ts = processEnging.getTaskService();

		List<HistoricProcessInstance> list = hs.createHistoricProcessInstanceQuery()//
				.involvedUser("李四")//
				.unfinished()/* 查询没有完成的 */
				.list();
		for (HistoricProcessInstance h : list) {
			System.err.println(h.getProcessDefinitionId());// 流程定义id
			System.err.println(h.getStartTime());// 开始时间
			System.err.println(h.getId());// 流程实例id
			ProcessInstance pi = rs.createProcessInstanceQuery().processInstanceId(h.getId()).singleResult();
			System.err.println(pi.getActivityId());// 当前正在执行的任务节点
			System.err.println(pi.getId());
			System.err.println(pi.getProcessDefinitionName());// 流程定义的名称
			// 如果有，可能会有两任务节点同时执行
			Task task = ts.createTaskQuery().processInstanceId(pi.getId()).singleResult();
			System.err.println(task.getName());// 当前正在执行的节点名称
			System.err.println("-------------------------");
		}
	}

	/**
	 * 修改流程变量
	 */
	@Test
	public void queryVariables() {
		TaskService ts = processEnging.getTaskService();
		String str = ts.getVariable("18", "msg", String.class);
		System.err.println(str);

		ts.setVariable("18", "msg", "休息几天!!");
	}

	/**
	 * 查看某个用户完成的所有历史任务
	 */
	@Test
	public void queryHistoryInstance() {
		RepositoryService rs = processEnging.getRepositoryService();
		ProcessDefinitionQuery query = rs.createProcessDefinitionQuery();

		HistoryService hs = processEnging.getHistoryService();
		List<HistoricProcessInstance> list = //
				hs.createHistoricProcessInstanceQuery()// 创建查询历史任务的对象
						.involvedUser("张三")// 传递当前用户名称
						.finished()// 查询已经完成的任务
						.list();// 查询
		HistoricVariableInstanceQuery query2 = processEnging.getHistoryService().createHistoricVariableInstanceQuery();
		for (HistoricProcessInstance h : list) {
			System.err.println(h.getId());
			String name = query.processDefinitionId(h.getProcessDefinitionId()).singleResult().getName();
			System.err.println(name);
			System.err.println(h.getProcessDefinitionId());
			System.err.println(h.getDurationInMillis());
			System.err.println(h.getStartTime());
			System.err.println(h.getEndTime());
			System.err.println("------------------");
			HistoricVariableInstanceQuery q2 = query2.processInstanceId(h.getId());

			String applyUser = (String) q2.variableName("applyUser").singleResult().getValue();
			String days = (String) q2.variableName("days").singleResult().getValue();
			String msg = (String) q2.variableName("msg").singleResult().getValue();
			String from = (String) q2.variableName("from").singleResult().getValue();
			String to = (String) q2.variableName("to").singleResult().getValue();
			System.err.println(applyUser+","+days+","+msg+","+from+","+to);
		}

	}
}
