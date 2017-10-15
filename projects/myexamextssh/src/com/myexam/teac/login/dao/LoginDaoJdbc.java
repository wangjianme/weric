package com.myexam.teac.login.dao;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.myexam.domain.Teac;
import com.myexam.teac.login.dao.ILoginDao;
/**
 * 教师登录功能
 * @author wangjianme
 * @version 1.0,2010-9-25
 */
public class LoginDaoJdbc extends HibernateDaoSupport implements ILoginDao{
	/**
	 * 登录查询
	 */
	public Map<String, Object> login(Teac teac) {
		System.err.println("teacName:"+teac.getTeacName());
		System.err.println("teacPwd:"+teac.getTeacPwd());
		Map<String,Object> result = new HashMap<String, Object>();
		Teac t = (Teac)getSession().createCriteria(Teac.class)
					   .add(Restrictions.eq("teacName",teac.getTeacName().trim()))
					   .add(Restrictions.eq("teacPwd",teac.getTeacPwd().trim()))
					   .uniqueResult();
		result.put("teac",t);
		return result;
	}
}
