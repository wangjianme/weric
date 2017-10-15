package com.myexam.teac.onlinerate.service;

import java.util.Map;

import com.myexam.teac.onlinerate.dao.IOnlineRateDao;

/**
 * 在线阅卷
 * @author wangjianme
 * @version 1.0,2010-11-18
 */
public class OnlineRateServiceImpl implements IOnlineRateService {
	private IOnlineRateDao dao;

	public IOnlineRateDao getDao() {
		return dao;
	}

	public void setDao(IOnlineRateDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> queryStuds(Map<String, Object> param) {
		return getDao().queryStuds(param);
	}
	public Map<String, Object> queryAnsws(Map<String, Object> param) {
		return getDao().queryAnsws(param);
	}
	public Map<String, Object> updateScore(Map<String, Object> param) {
		return getDao().updateScore(param);
	}
}
