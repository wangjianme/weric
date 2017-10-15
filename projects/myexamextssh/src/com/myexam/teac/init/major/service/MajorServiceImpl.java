package com.myexam.teac.init.major.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Major;
import com.myexam.teac.init.major.dao.IMajorDao;
/**
 * 专业
 * @author wangjianme
 * @version 1.0,2010-10-6
 */
public class MajorServiceImpl implements IMajorService{
	private IMajorDao dao;
	public IMajorDao getDao() {
		return dao;
	}
	public void setDao(IMajorDao dao) {
		this.dao = dao;
	}
	public List<Map<String,Object>> query(Major major) {
		return getDao().query(major);
	}
	public Map<String, Object> save(Major major) {
		return getDao().save(major);
	}
	public Map<String, Object> del(String majorId) {
		return getDao().del(majorId);
	}
	public Map<String, Object> update(Major major) {
		return getDao().update(major);
	}
}
