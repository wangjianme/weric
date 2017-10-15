package com.myexam.teac.exammonitor.dao;

import java.util.Map;

/**
 * 考试监控
 * @author wangjianme
 * @version 1.0,2010-11-17
 */
public interface IExamMonitorDao {
	/**
	 * 查询出所有的
	 * @return
	 */
	public Map<String,Object> queryExamings();
}
