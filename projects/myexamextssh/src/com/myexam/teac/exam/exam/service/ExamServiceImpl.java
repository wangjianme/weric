package com.myexam.teac.exam.exam.service;

import java.util.Map;

import com.myexam.teac.exam.exam.dao.IExamDao;

public class ExamServiceImpl implements IExamService {
	private IExamDao dao;

	public IExamDao getDao() {
		return dao;
	}

	public void setDao(IExamDao dao) {
		this.dao = dao;
	}
	public Map<String,Object> queryExam(Map<String,Object> param){
		return getDao().queryExam(param);
	}
	public Map<String, Object> queryCour(Map<String, Object> param) {
		return getDao().queryCour(param);
	}
	public Map<String, Object> updateState(Map<String, Object> param) {
		return getDao().updateState(param);
	}
	public Map<String, Object> delExam(String examId) {
		return getDao().delExam(examId);
	}
}
