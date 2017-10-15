package cn.bpm.employee.service;

import java.util.List;
import java.util.Map;

public interface IEmployeeService {

	List<Map<String, Object>> queryProcdefs();

	/**
	 * 启动一个流程
	 * 
	 * @param key
	 * @param days
	 * @param usrName
	 */
	public void apply(String key, Integer days, String usrName);

	void apply(Map<String, Object> map);

	/**
	 * 查询当前用户的所有待办任务
	 */
	public List<Map<String, Object>> queryTask(String userName);
	
	public Map<String, Object> shape(String procdefId,String activityId);

	/**
	 * 根据任务id取消某个任务 
	 */
	public void cancelTask(String taskId);
	/**
	 * 提交任务，即执行任务
	 * @param taskId
	 */
	void submitTask(String taskId,String leader);
	/**
	 * 查询当前用户没有完成的任务
	 */
	List<Map<String, Object>> unfinishTasks(String userName);
	
	/**
	 * 同意或是不同意员工的请假
	 * @param map
	 * @return
	 */
	public void agree(String taskId,Map<String, Object> map);

	List<Map<String, Object>> history(String userName);
}