package com.myexam.teac.manager.cls.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Cls;
import com.myexam.teac.manager.cls.dao.IClsDao;
/**
 * 班级管理
 * @author wangjianme
 * @version 1.0,2010-10-9
 */
public class ClsServiceImpl implements IClsService {
	private IClsDao dao;
	public IClsDao getDao() {
		return dao;
	}

	public void setDao(IClsDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> query(Map<String, Object> params) {
		return getDao().query(params);
	}
	public Map<String,Object> queryTeac(String name,int start,int limit){
		return getDao().queryTeac(name, start, limit);
	}
	public Map<String, Object> queryMajor() {
		return getDao().queryMajor();
	}
	public Map<String, Object> save(Cls cls) {
		return getDao().save(cls);
	}
	public Map<String, Object> del(Cls cls) {
		return getDao().del(cls);
	}
}
