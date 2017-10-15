package com.myexam.teac.reg.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Teac;

public class TeacRegDaoJdbc extends HibernateDaoSupport implements ITeacRegDao{
	public boolean reg(Teac teac) {
		System.err.println(">>>::"+teac);
		String teacId= (String)getSession().save(teac);
		if(teacId!=null){
			return true;
		}
		return false;
	}
	/**
	 * 验证某个用户名是否存在
	 */
	public boolean validate(Teac teac) {
		List list = getHibernateTemplate().findByExample(teac);
		System.err.println(">>>"+list.size());
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
}
