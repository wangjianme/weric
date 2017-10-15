package com.myexam.teac.init.dept.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Dept;
import com.myexam.teac.init.dept.dao.IDeptDao;

public class DeptServiceImpl implements IDeptService{
	private IDeptDao dao;
	public IDeptDao getDao() {
		return dao;
	}
	public void setDao(IDeptDao dao) {
		this.dao = dao;
	}
	public List<Dept> query(String id) {
		return getDao().query(id);
	}
	public Map<String, Object> save(Dept dept) {
		return getDao().save(dept);
	}
	public Map<String, Object> del(String deptId) {
		return getDao().del(deptId);
	}
}
