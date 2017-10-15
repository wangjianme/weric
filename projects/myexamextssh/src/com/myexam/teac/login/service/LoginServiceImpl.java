package com.myexam.teac.login.service;

import java.util.Map;

import com.myexam.domain.Teac;
import com.myexam.teac.login.dao.ILoginDao;
import com.myexam.teac.login.service.ILoginService;
/**
 * Login...
 * @author wangjianme
 * @version 1.0,2010-9-25
 */
public class LoginServiceImpl implements ILoginService{
	private ILoginDao dao;
	public ILoginDao getDao() {
		return dao;
	}
	public void setDao(ILoginDao dao) {
		this.dao = dao;
	}
	public Map<String, Object> login(Teac teac) {
		return getDao().login(teac);
	}
}
