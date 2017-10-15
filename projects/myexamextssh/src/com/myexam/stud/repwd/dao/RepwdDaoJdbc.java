package com.myexam.stud.repwd.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 修改密码
 * @author wangjianme
 * @version 1.0,2010-12-6
 */
public class RepwdDaoJdbc extends HibernateDaoSupport implements IRepwdDao {
	public void updatePwd(Object object) {
		getSession().update(object);
	}
}
