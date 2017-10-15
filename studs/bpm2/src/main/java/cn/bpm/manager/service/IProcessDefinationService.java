package cn.bpm.manager.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
public interface IProcessDefinationService {

	List<Map<String, Object>> queryAllProcessDefination();
	/**
	 * 删除流程定义
	 */
	public void deleteDeploymentById(String id);
	
	public InputStream resource(String procdefId);
}