package com.myexam.teac.exammonitor.service;

import java.util.Map;

import com.myexam.teac.exammonitor.dao.IExamMonitorDao;
/**
 * 考试监控
 * @author wangjianme
 * @version 1.0,2010-11-16
 */
public class ExamMonitorServiceImpl implements IExamMonitorService {
	private IExamMonitorDao dao;

	public IExamMonitorDao getDao() {
		return dao;
	}

	public void setDao(IExamMonitorDao dao) {
		this.dao = dao;
	}

	public Map<String, Object> queryExamings() {
		return getDao().queryExamings();
	}
}
