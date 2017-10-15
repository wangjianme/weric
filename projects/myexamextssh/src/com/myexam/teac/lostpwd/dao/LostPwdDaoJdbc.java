package com.myexam.teac.lostpwd.dao;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
 * 忘记密码
 * @author wangjianme
 * @version 1.0,2010-12-7
 */
public class LostPwdDaoJdbc extends HibernateDaoSupport implements ILostPwdDao{
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryQues(String name) {
		String sql = "select teac_id as teacId, teac_answer as teacAnswer,teac_ques as teacQues from teac where teac_name='"+name.trim()+"'";
		Map<String,Object> result = (Map<String,Object>)getSession().createSQLQuery(sql)
							.addScalar("teacId",Hibernate.STRING)
						    .addScalar("teacQues",Hibernate.STRING)
							.addScalar("teacAnswer",Hibernate.STRING)
							.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		                   .uniqueResult();
		System.err.println(">>>:"+result);
		return result;
	}
	
	public void update(Map<String, Object> param) {
		String teacId 	= (String)param.get("teacId");
		String teacPwd	= (String)param.get("teacPwd");
		String sql = "update teac set teac_pwd=:pwd where teac_id=:id";
		getSession().createSQLQuery(sql)
					.setString("pwd",teacPwd)
					.setString("id",teacId)
					.executeUpdate();
	}
}
