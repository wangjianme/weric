package com.myexam.teac.init.edusys.service;

import java.util.List;
import java.util.Map;

import com.myexam.teac.init.edusys.dao.IEdusysDao;

public class EdusysServiceImpl implements IEdusysService {
	private IEdusysDao dao;
	public IEdusysDao getDao() {
		return dao;
	}
	public void setDao(IEdusysDao dao) {
		this.dao = dao;
	}
	public Map<String,Object> query() {
		return getDao().query();
	}
	public Map<String, Object> save(List<Object> list) {
		return getDao().save(list);
	}
	public Map<String, Object> del(Object o) {
		return getDao().del(o);
	}
}
