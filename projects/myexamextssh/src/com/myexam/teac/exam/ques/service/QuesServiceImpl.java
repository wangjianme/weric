package com.myexam.teac.exam.ques.service;

import java.util.Map;

import com.myexam.teac.exam.ques.dao.IQuesDao;

public class QuesServiceImpl implements IQuesService {
	private IQuesDao dao;
	public IQuesDao getDao() {
		return dao;
	}
	public void setDao(IQuesDao dao) {
		this.dao = dao;
	}
	public Map<String,Object> query(Map<String,Object> param){
		return getDao().query(param);
	}
	public Map<String, Object> queryCour(Map<String, Object> param) {
		return getDao().queryCour(param);
	}
	public Map<String, Object> save(Map<String, Object> param) {
		return getDao().save(param);
	}
	public Map<String, Object> queryOneQues(String quesId) {
		return getDao().queryOneQues(quesId);
	}
	public Map<String, Object> del(String quesId) {
		return getDao().del(quesId);
	}
}
