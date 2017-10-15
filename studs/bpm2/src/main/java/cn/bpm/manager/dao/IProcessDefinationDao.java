package cn.bpm.manager.dao;

import java.util.List;
import java.util.Map;

public interface IProcessDefinationDao {
	/**
	 * 查询所有的流程定义
	 * @return
	 */
	List<Map<String, Object>> queryAllProcessDefination();

}