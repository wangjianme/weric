package com.myexam.stud.repwd.service;

import com.myexam.stud.repwd.dao.IRepwdDao;
/**
 * 
 * @author wangjianme
 * @version 1.0,2010-12-6
 */
public class RepwdServiceImpl implements IRepwdService {
	private IRepwdDao dao;

	public IRepwdDao getDao() {
		return dao;
	}

	public void setDao(IRepwdDao dao) {
		this.dao = dao;
	}
	public void updatePwd(Object object) {
		getDao().updatePwd(object);
	}
}
