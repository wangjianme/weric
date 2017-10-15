package com.myexam.teac.init.cour.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Cour;
import com.myexam.domain.Major;
import com.myexam.teac.init.cour.dao.ICourDao;
/**
 * 课程
 * @author wangjianme
 * @version 1.0,2010-10-7
 */
public class CourServiceImpl implements ICourService{
	private ICourDao dao;

	public ICourDao getDao() {
		return dao;
	}

	public void setDao(ICourDao dao) {
		this.dao = dao;
	}
	public Map<String,Object> query(Map<String,Object> param) {
		return getDao().query(param);
	}
	public Map<String, Object> save(Map<String,Object> param) {
		return getDao().save(param);
	}
	public List<Major> queryMajor() {
		return getDao().queryMajor();
	}
	public Map<String, Object> del(Cour cour) {
		return getDao().del(cour);
	}
}
