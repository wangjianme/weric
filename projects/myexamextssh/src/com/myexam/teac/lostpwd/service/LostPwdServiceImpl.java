package com.myexam.teac.lostpwd.service;

import java.util.Map;

import com.myexam.teac.lostpwd.dao.ILostPwdDao;

/**
 * 忘记密码
 * @author wangjianme
 * @version 1.0,2010-12-7
 */
public class LostPwdServiceImpl implements ILostPwdService{
	private ILostPwdDao dao;

	public ILostPwdDao getDao() {
		return dao;
	}

	public void setDao(ILostPwdDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> queryQues(String name){
		return getDao().queryQues(name);
	}
	public void update(Map<String, Object> param){
		getDao().update(param);
	}
}
