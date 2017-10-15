package com.myexam.stud.lostpwd.service;

import java.util.Map;

import com.myexam.stud.lostpwd.dao.IStudLostPwdDao;

public class StudLostPwdServiceImpl implements IStudLostPwdService{
	private IStudLostPwdDao dao;

	public IStudLostPwdDao getDao() {
		return dao;
	}

	public void setDao(IStudLostPwdDao dao) {
		this.dao = dao;
	}
	public Map<String,Object> queryQues(String studSid){
		return getDao().queryQues(studSid);
	}
	public void update(Map<String, Object> param) {
		getDao().update(param);
	}
}
