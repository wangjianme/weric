package com.myexam.teac.init.cate.service;

import java.util.Map;

import com.myexam.teac.init.cate.dao.ICateDao;

public class CateServiceServiceImpl implements ICateService{
	private ICateDao dao;

	public ICateDao getDao() {
		return dao;
	}

	public void setDao(ICateDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> query() {
		return getDao().query();
	}
}
