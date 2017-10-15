package com.myexam.teac.reg.service;

import com.myexam.domain.Teac;
import com.myexam.teac.reg.dao.ITeacRegDao;

public class TeacRegService implements ITeacRegService{
	private ITeacRegDao dao;
	public ITeacRegDao getDao() {
		return dao;
	}
	public void setDao(ITeacRegDao dao) {
		this.dao = dao;
	}
	public boolean reg(Teac teac) {
		return getDao().reg(teac);
	}
	public boolean validate(Teac teac) {
		return getDao().validate(teac);
	}
}
