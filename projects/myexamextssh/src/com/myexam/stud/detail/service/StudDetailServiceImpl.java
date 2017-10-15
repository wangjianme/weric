package com.myexam.stud.detail.service;

import java.util.Map;

import com.myexam.stud.detail.dao.IStudDetailDao;

public class StudDetailServiceImpl implements IStudDetailService {
	private IStudDetailDao dao;

	public IStudDetailDao getDao() {
		return dao;
	}

	public void setDao(IStudDetailDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> update(Map<String, Object> param) {
		return getDao().update(param);
	}
}
