package com.myexam.teac.examadd.service;

import java.util.Map;

import com.myexam.teac.examadd.dao.IExamAddDao;
/**
 * 试卷增加
 * @author wangjianme
 * @version 1.0,2010-10-31
 */
public class ExamAddServiceImpl implements IExamAddService{
	private IExamAddDao dao;

	public IExamAddDao getDao() {
		return dao;
	}

	public void setDao(IExamAddDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> save(Map<String, Object> param) {
		return getDao().save(param);
	}
}
