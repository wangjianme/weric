package cn.bpm.employee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.bpm.employee.service.IEmployeeService;

@Service(value = "employeeService")
public class EmployeeServiceImpl implements IEmployeeService {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;// 查询历史任务

	/**
	 * 查询最新版本的流程定义
	 */
	public List<Map<String, Object>> queryProcdefs() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 查询所有流程定义，获取最后一个版本
		List<ProcessDefinition> procDefs = repositoryService//
				.createProcessDefinitionQuery()// 创建查询对象
				.latestVersion()// 最后一个版本
				.list();// 查询
		// 查询将最新的版本封装到List<Map>集合中去
		for (ProcessDefinition pd : procDefs) {
			Map<String, Object> map = new HashMap<String, Object>();
			String procdefId = pd.getId();
			String deploymentName = repositoryService.createDeploymentQuery()// 查询部署名称
					.deploymentId(pd.getDeploymentId())// 传递部署id
					.singleResult().getName();// 获取名称
			String key = pd.getKey();// 获取key值
			map.put("procdefId", procdefId);
			map.put("name", deploymentName);
			map.put("key", key);
			list.add(map);
		}
		return list;
	}

	/**
	 * 启动一个流程
	 */
	@Override
	public void apply(String key, Integer days, String usrName) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("user", usrName);
		vars.put("days", days);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(key, vars);
		System.err.println("启动成功:" + pi.getId());
	}

	public void apply(Map<String, Object> map) {
		String key = (String) map.get("key");
		map.remove("key");// 由于这不是流程变量，所以，删除这个值在map中
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(key, map);
		System.err.println("启动成功:--:" + pi.getId());
	}

	/**
	 * 查询当前用户所有的待办任务同时查询出它的所有流程变量
	 */
	@Override
	public List<Map<String, Object>> queryTask(String userName) {
		List<Map<String, Object>> list = new ArrayList<>();
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userName)//
				.orderByTaskId().desc().list();
		for (Task task : tasks) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("taskId", task.getId());
			map.put("procdefid", task.getProcessDefinitionId());// 流程定义id
			map.put("taskDefKey", task.getTaskDefinitionKey());// 获取当前正在执行的节点名称
			map.put("taskName", task.getName());
			map.put("taskDate", task.getCreateTime().toLocaleString());// 创建时间
			map.put("vars", runtimeService.getVariables(task.getExecutionId()));
			list.add(map);
		}
		return list;
	}

	/**
	 * 查看节点定位
	 */
	public Map<String, Object> shape(String procdefId, String activityId) {
		// 获取流程定义对象
		ProcessDefinition pd = repositoryService.getProcessDefinition(procdefId);
		if (pd instanceof ProcessDefinitionEntity) {
			ProcessDefinitionEntity pde = (ProcessDefinitionEntity) pd;
			// 查询节点对象
			ActivityImpl activityImpl = pde.findActivity(activityId);
			Map<String, Object> map = new HashMap<String, Object>();
			// 获取节点坐标
			map.put("x", activityImpl.getX());
			map.put("y", activityImpl.getY());
			map.put("width", activityImpl.getWidth());
			map.put("height", activityImpl.getHeight());
			return map;
		}
		return null;
	}

	/**
	 * 取消某个任务
	 */
	@Override
	public void cancelTask(String taskId) {
		// 声明流程变量通过设置cancel=true即可以取消
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancel", true);
		taskService.complete(taskId, map);
	}

	/**
	 * 提交即执行任务
	 */
	@Override
	public void submitTask(String taskId, String leader) {
		Map<String, Object> map = new HashMap<>();
		map.put("cancel", false);// 设置流程变量，即不是取消
		map.put("boss", leader);// 设置上级领导的名称 --
								// 目前为硬编码，在正常的程序中，应该查询数据库，并查询出当前用户的上级领导
		taskService.complete(taskId, map);
	}

	/**
	 * 查询当前用户没有完成的任务
	 */
	@Override
	public List<Map<String, Object>> unfinishTasks(String userName) {
		List<Map<String, Object>> tasks = new ArrayList<>();// 声明一个集合类，用于封装数据

		List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()//
				.involvedUser(userName)// 传递当前用户名
				.unfinished()/* 查询没有完成的 */
				.list();
		TaskQuery taskQuery = taskService.createTaskQuery();// 创建任务查询，用于查询当前正在执行的任务节点，如果同时两个节点并行执行，则可能会有多个结果

		for (HistoricProcessInstance h : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("proceDefId", h.getProcessDefinitionId());// 流程定义id
			map.put("startTime", h.getStartTime());// 开始时间
			map.put("proceInsId", h.getId());// 流程实例id
			ProcessInstance pi = runtimeService// 查询流程实例
					.createProcessInstanceQuery()//
					.processInstanceId(h.getId())//
					.singleResult();
			map.put("activityId", pi.getActivityId());// 当前正在执行的任务节点
			map.put("procDefName", pi.getProcessDefinitionName());// 流程定义的名称
			Task task = taskQuery.processInstanceId(pi.getId()).singleResult();// 有可能会有多个任务并行执行，目前先只查询第一个
			map.put("taskName", task.getName());// 当前正在执行的节点的名称
			tasks.add(map);
		}
		return tasks;
	}

	/**
	 * Agree同意或是不同意请假申请
	 */
	public void agree(String taskId, Map<String, Object> map) {
		String str = taskService.getVariable(taskId, "msg", String.class);
		str += map.get("msg");
		taskService.setVariable(taskId, "msg", str);// 设置新的值，覆盖之前的值
		taskService.setVariable(taskId, "agree", map.get("agree"));// 是否同意
		taskService.complete(taskId);// 完成这个任务
	}

	/**
	 * 查询历史信息
	 */
	@Override
	public List<Map<String, Object>> history(String userName) {
		List<Map<String, Object>> list = new ArrayList<>();
		HistoricProcessInstanceQuery query = //
				historyService.createHistoricProcessInstanceQuery();
		HistoricVariableInstanceQuery query2 = //
				historyService.createHistoricVariableInstanceQuery();
		ProcessDefinitionQuery query3 = //
				repositoryService.createProcessDefinitionQuery();
		List<HistoricProcessInstance> hs = //
				query.involvedUser(userName)//
						.list();
		for (HistoricProcessInstance hpi : hs) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", hpi.getId());// 历史流程id
			map.put("procDefId", hpi.getProcessDefinitionId());// 流利实例id
			map.put("name", query3.processDefinitionId(hpi.getProcessDefinitionId()).singleResult().getName());// 流程名称
			map.put("endTime", hpi.getEndTime());
			System.err.println("--------");
			// 以下封装流程变量
			HistoricVariableInstanceQuery q2 = query2.processInstanceId(map.get("id").toString());

			String applyUser = (String) q2.variableName("applyUser").singleResult().getValue();
			String days = (String) q2.variableName("days").singleResult().getValue();
			String msg = (String) q2.variableName("msg").singleResult().getValue();
			String from = (String) q2.variableName("from").singleResult().getValue();
			String to = (String) q2.variableName("to").singleResult().getValue();
			map.put("applyUser", applyUser);
			map.put("days", days);
			map.put("msg", msg);
			map.put("from", from);
			map.put("to", to);

			list.add(map);

		}
		//
		return list;
	}
}
