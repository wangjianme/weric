package cn.demo01;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo01 {
	ProcessEngine processEnging;

	@Before
	public void before() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-pubs.xml");
		processEnging = ctx.getBean("processEngine", ProcessEngine.class);
	}

	/**
	 * 部署流程定义
	 */
	@Test
	public void deployment() throws Exception {
		RepositoryService repositoryService = processEnging.getRepositoryService();
		Deployment deployment = repositoryService.createDeployment()//
				.name("请假流程")//
				.addInputStream("leave.bpmn", new FileInputStream("./bpmn/leave.bpmn"))//
				.addInputStream("leave.png", new FileInputStream("./bpmn/leave.png"))//
				.deploy();
		System.err.println("流程定义id:" + deployment.getId());
		// 查询流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()//
				.deploymentId(deployment.getId())//
				.singleResult();
		System.err.println("流程定义id:" + processDefinition.getId());
		System.err.println("流程定义版本：" + processDefinition.getVersion());
		System.err.println("流程定义的key:" + processDefinition.getKey());
	}

	/**
	 * 启动一个流程定义bykey<br>
	 * 业务规则： 张三启动的流程，只能张三一个查询到<br>
	 * 这样的话，可以通过流程变量的方式来控制，而不是写死的配置文件中
	 */
	@Test
	public void startProcessInstance() throws Exception {
		RuntimeService runtimeService = processEnging.getRuntimeService();
		// 设置用户名称
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", "张三");// 传递用户名称，以后可以是用户id
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("leave", map);
		System.err.println("流程实例id:" + pi.getId());
		System.err.println("流程部署id:" + pi.getDeploymentId());
		System.err.println("名称" + pi.getName());// 流程定义的名称
	}

	/**
	 * 查询任务<br>
	 * 张三
	 */
	@Test
	public void queryTaskByUser() {
		TaskService taskService = processEnging.getTaskService();
		List<Task> list = taskService.createTaskQuery()//
				.taskAssignee("张三")//
				.list();
		for (Task task : list) {
			System.err.println(task.getId());// 50005
			System.err.println(task.getName());
			System.err.println(task.getProcessDefinitionId());
			System.err.println("---------------------------------");
		}
	}

	/**
	 * 张三<br>
	 * 完成任务<br 完成任务，将任务提交给经理<br>
	 */
	@Test
	public void completeTaskById() {
		TaskService taskService = processEnging.getTaskService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancel", false);// 不取消
		map.put("boss", "李四");// 设置上级人员名称
		map.put("days", 3);// 请假天数据为三天
		map.put("note", "回家休息所以请假");// 请假说明为xxx
		taskService.complete("50005", map);
	}

	/**
	 * 李四查询任务
	 */
	@Test
	public void queryTaskByUser2() {
		TaskService taskService = processEnging.getTaskService();
		List<Task> list = taskService.createTaskQuery()//
				.taskAssignee("李四")//
				.list();
		for (Task task : list) {
			System.err.println(task.getId());// 57506
			System.err.println(task.getName());// 经理审批
			System.err.println(task.getProcessDefinitionId());
			System.err.println("---------------------------------");
		}
	}

	/**
	 * 同意
	 */
	@Test
	public void endTaskById1() {
		TaskService taskService = processEnging.getTaskService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agree", true);// 同意
		taskService.complete("57506", map);
	}

	/**
	 * 结束某个流程<br>
	 * 以下是取消请假，则直接传递cancel=true即可
	 */
	@Test
	public void endTaskById() {
		TaskService taskService = processEnging.getTaskService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancel", true);
		taskService.complete("57506", map);
	}

	/**
	 * 查询所有的流程定义
	 */
	@Test
	public void queryAllProcessDefination() {
		RepositoryService rs = processEnging.getRepositoryService();
		/**
		 * 直接使用查询完成 SELECT d.id_,d.name_,p.key_,p.version_,d.DEPLOY_TIME_ FROM
		 * act_re_deployment d INNER JOIN act_re_procdef p ON
		 * d.ID_=p.DEPLOYMENT_ID_ ORDER BY d.DEPLOY_TIME_ ASC,p.VERSION_ ASC;
		 */

	}

	/**
	 * 查询png文件，根据流程定义id查询
	 */
	@Test
	public void queryPngById() throws Exception {
		RepositoryService rs = processEnging.getRepositoryService();
		InputStream in = rs.getProcessDiagram("leave:13:67504");
		FileUtils.copyInputStreamToFile(in, new File("d:/a/a.png"));
	}

	/**
	 * 只查询最key值中的最后一个版本
	 * 
	 * @throws Exception
	 */
	@Test
	public void queryProcdefs() throws Exception {
		RepositoryService rs = processEnging.getRepositoryService();
		List<ProcessDefinition> list = rs.createProcessDefinitionQuery().latestVersion().list();
		for (ProcessDefinition pd : list) {
			String name = rs.createDeploymentQuery().deploymentId(pd.getDeploymentId()).singleResult().getName();
			System.err.println(pd.getId() + "" + pd.getName() + "," + name);
		}
	}
}
