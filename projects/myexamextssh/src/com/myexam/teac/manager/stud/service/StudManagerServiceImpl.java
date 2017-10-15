package com.myexam.teac.manager.stud.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Stud;
import com.myexam.teac.manager.stud.dao.IStudManagerDao;

/**
 * 学生管理
 * @author wangjianme
 * @version 1.0,2010-10-13
 */
public class StudManagerServiceImpl implements IStudManagerService {
	private IStudManagerDao dao;

	public IStudManagerDao getDao() {
		return dao;
	}

	public void setDao(IStudManagerDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> query(Map<String,Object> param) {
		return getDao().query(param);
	}
	public Map<String, Object> update(Map<String, Object> param){
		return getDao().update(param);
	}
	public Map<String, Object> del(Map<String, Object> param) {
		return getDao().del(param);
	}
	public Map<String, Object> queryCls(Map<String, Object> param) {
		return getDao().queryCls(param);
	}
}
