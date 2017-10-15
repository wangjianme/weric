package com.myexam.teac.security.func.service;

import java.util.List;
import java.util.Map;

import com.myexam.teac.security.func.dao.IFuncDao;

public class FuncServiceImpl implements IFuncService{
	private IFuncDao dao;

	public IFuncDao getDao() {
		return dao;
	}

	public void setDao(IFuncDao dao) {
		this.dao = dao;
	}
	public List<Map<String, Object>> query(String parentId, String roleId) {
		return getDao().query(parentId, roleId);
	}
	public Map<String, Object> save(Map<String, Object> param) {
		return getDao().save(param);
	}
}
