package com.myexam.stud.login.dao;

import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Stud;
/**
 * 学生登录
 * @author wangjianme
 * @version 1.0,2010-10-18
 */
public class StudLoginDaoJdbc extends HibernateDaoSupport implements IStudLoginDao {
	/**
	 * 学生登录功能
	 */
	public Map<String, Object> login(Map<String, Object> param) {
		Stud stud = (Stud)param.get("stud");
		stud = (Stud)getSession().createCriteria(Stud.class)
						   .add(Restrictions.eq("studSid", stud.getStudSid()))
						   .add(Restrictions.eq("studPwd", stud.getStudPwd()))
						   .uniqueResult();
		param.clear();
		param.put("stud", stud);
		return param;
	}
}
