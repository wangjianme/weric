package com.myexam.stud.login.service;

import java.util.Map;

import com.myexam.stud.login.dao.IStudLoginDao;
/**
 * 学生登录
 * @author wangjianme
 * @version 1.0,2010-10-18
 */
public class StudLoginServiceImpl implements IStudLoginService{
	private IStudLoginDao dao;

	public IStudLoginDao getDao() {
		return dao;
	}
	public void setDao(IStudLoginDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> login(Map<String, Object> param) {
		return getDao().login(param);
	}
}
