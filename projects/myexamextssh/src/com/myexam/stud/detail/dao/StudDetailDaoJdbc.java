package com.myexam.stud.detail.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Stud;
/**
 * 学生明细信息
 * @author wangjianme
 * @version 1.0,2010-12-9
 */
public class StudDetailDaoJdbc extends HibernateDaoSupport implements IStudDetailDao {
	public Map<String, Object> update(Map<String, Object> param) {
		Map<String,Object> result = new HashMap<String, Object>();
		String sql = "";
		String studId = (String)param.get("studId");
		result.put("valid",true);
		if(param.get("studSid")!=null){						//检查身份证号是否存在
			sql = "select count(*) from stud where stud_sid=:sid and stud_id!=:id";
			Integer count = Integer.parseInt(""+getSession().createSQLQuery(sql)
												.setString("sid",param.get("studSid").toString().trim())
												.setString("id",studId)
												.uniqueResult()
				);
			if(count>0){
				result.put("valid",false);
			}
		}
		if(result.get("valid").equals(true)){
			Stud stud = (Stud)param.get("stud");
			if(param.get("studName")!=null){
				stud.setStudName(param.get("studName").toString().trim());
			}
			if(param.get("studNo")!=null){
				stud.setStudNo(param.get("studNo").toString().trim());
			}
			if(param.get("studSid")!=null){
				stud.setStudSid(param.get("studSid").toString().trim());
			}
			if(param.get("studSex")!=null){
				stud.setStudSex(param.get("studSex").toString());
			}
			if(param.get("studBirth")!=null){
				stud.setStudBirth(param.get("studBirth").toString().substring(0,10));
			}
			if(param.get("studTel")!=null){
				stud.setStudTel(param.get("studTel").toString().trim());
			}
			if(param.get("studQq")!=null){
				stud.setStudQq(param.get("studQq").toString().trim());
			}
			if(param.get("studEmail")!=null){
				stud.setStudEmail(param.get("studEmail").toString().trim());
			}
			if(param.get("studAddr")!=null){
				stud.setStudAddr(param.get("studAddr").toString().trim());
			}
			getSession().update(stud);
			result.put("stud",stud);
		}
		result.put("success",true);
		return result;
	}
}
