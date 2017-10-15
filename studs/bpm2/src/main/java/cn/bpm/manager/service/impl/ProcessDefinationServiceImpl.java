package cn.bpm.manager.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bpm.manager.dao.IProcessDefinationDao;
import cn.bpm.manager.service.IProcessDefinationService;

@Service(value = "processDefinationService")
public class ProcessDefinationServiceImpl implements IProcessDefinationService {
	@Autowired
	private RepositoryService repositoryService;
	@Resource(name = "processDefinationDao")
	private IProcessDefinationDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.bpm.manager.service.impl.IProcessDefinationService#
	 * queryAllProcessDefination()
	 */
	public List<Map<String, Object>> queryAllProcessDefination() {
		return dao.queryAllProcessDefination();
	}

	@Override
	public void deleteDeploymentById(String id) {
		repositoryService.deleteDeployment(id, true);
	}
	/**
	 * 查询显示当前流程图
	 */
	@Override
	public InputStream resource(String procdefId) {
		InputStream in = repositoryService.getProcessDiagram(procdefId);
		return in;
	}

}
