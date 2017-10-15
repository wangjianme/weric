package com.myexam.stud.reg.service;

import java.util.Map;

import com.myexam.domain.Stud;
import com.myexam.stud.reg.dao.IStudRegDao;

/**
 * 学生注册功能
 * @author wangjianme
 * @version 1.0,2010-10-18
 */
public class StudRegService implements IStudRegService{
	private IStudRegDao dao;

	public IStudRegDao getDao() {
		return dao;
	}

	public void setDao(IStudRegDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> queryCls(Map<String, Object> param) {
		return getDao().queryCls(param);
	}

	public Map<String, Object> queryEdusys() {
		return getDao().queryEdusys();
	}

	public Map<String, Object> queryMajor(String edusysId) {
		return getDao().queryMajor(edusysId);
	}
	public Map<String, Object> save(Stud stud) {
		return getDao().save(stud);
	}
}
