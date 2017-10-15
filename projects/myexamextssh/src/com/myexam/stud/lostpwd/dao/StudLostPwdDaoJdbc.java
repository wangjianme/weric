package com.myexam.stud.lostpwd.dao;

import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 学生忘记密码以后的找回
 * @author wangjianme
 * @version 1.0,2010-12-8
 */
public class StudLostPwdDaoJdbc extends HibernateDaoSupport implements IStudLostPwdDao {
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryQues(String studSid) {
		String sql = "select stud_id as studId,stud_ques as studQues," +
					 "stud_answer as studAnswer,stud_name as studName" +
					 " from stud where stud_sid='"+studSid.trim()+"'";
		Map<String,Object> result = (Map<String,Object>)getSession().createSQLQuery(sql)
									.addScalar("studId",Hibernate.STRING)
									.addScalar("studQues",Hibernate.STRING)
									.addScalar("studAnswer",Hibernate.STRING)
									.addScalar("studName",Hibernate.STRING)
									.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
									.uniqueResult();
		return result;
	}
	public void update(Map<String, Object> param) {
		String pwd = (String)param.get("studPwd");
		String id  = (String)param.get("studId");
		String sql = "update stud set stud_pwd=:pwd where stud_id=:id";
		getSession().createSQLQuery(sql)
					.setString("pwd",pwd)
					.setString("id",id)
					.executeUpdate();
	}
}
