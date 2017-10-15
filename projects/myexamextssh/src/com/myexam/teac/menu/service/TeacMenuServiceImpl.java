package com.myexam.teac.menu.service;

import java.util.List;
import java.util.Map;

import com.myexam.teac.menu.dao.ITeacMenuDao;

public class TeacMenuServiceImpl implements ITeacMenuService{
	private ITeacMenuDao dao;
	public ITeacMenuDao getDao() {
		return dao;
	}
	public void setDao(ITeacMenuDao dao) {
		this.dao = dao;
	}
	public List<Map<String, Object>> query(String parent,String uid) {
		return getDao().query(parent,uid);
	}
}
