package com.myexam.teac.examview.service;

import java.util.Map;

import com.myexam.teac.examview.dao.IExamviewDao;

/**
 * 试卷的预览功能
 * @author wangjianme
 * @version 1.0,2010-11-1
 */
public class ExamviewServiceImpl implements IExamviewService{
	private IExamviewDao dao;
	public IExamviewDao getDao() {
		return dao;
	}
	public void setDao(IExamviewDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> view(Map<String, Object> param) {
		return getDao().view(param);
	}
	public Map<String, Object> queryOneExamQues(String examQuesId) {
		return getDao().queryOneExamQues(examQuesId);
	}
}
