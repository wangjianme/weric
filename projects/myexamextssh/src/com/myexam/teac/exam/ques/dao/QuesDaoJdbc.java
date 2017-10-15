package com.myexam.teac.exam.ques.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Cour;
import com.myexam.domain.Ques;
/**
 * 题库管理功能
 * @author wangjianme
 * @version 1.0,2010-10-25
 */
public class QuesDaoJdbc extends HibernateDaoSupport implements IQuesDao{
	/**
	 * 分页查询试题的信息然后返回
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> query(Map<String,Object> param){
		Ques ques 			= (Ques)param.get("ques");
		Integer start 		= (Integer)param.get("start");
		Integer limit  		= (Integer)param.get("limit");
		
		StringBuffer sb1 = new StringBuffer("select ques_id as quesId,ques_title as quesTitle,");
	    sb1.append("ques_level as quesLevel,ques_state as quesState,");
	    sb1.append("ques_cate as quesCate,cate_name as cateName,");
	    sb1.append("ques_cour as quesCour,cour_name as courName,ques_time as quesTime"); 
	    sb1.append(" from ques inner join cate on ques_cate=cate_id");
	    sb1.append(" inner join cour on ques_cour=cour_id where 1=1");
        
	    StringBuffer sb2 = new StringBuffer("select count(*)");
	    sb2.append(" from ques inner join cate on ques_cate=cate_id");
	    sb2.append(" inner join cour on ques_cour=cour_id where 1=1");
	    
	    String tmp = "";
	    Map<String,Object> prop = new HashMap<String, Object>();
	    if(ques.getQuesTitle()!=null && !ques.getQuesTitle().trim().equals("")){
	    	tmp = " and ques_title like :title";
	    	sb1.append(tmp);
	    	sb2.append(tmp);
	    	prop.put("title", "%"+ques.getQuesTitle()+"%");
	    }
	    if(ques.getQuesCour()!=null && !ques.getQuesCour().trim().equals("")){
	    	tmp = " and cour_name like :cour";
	    	sb1.append(tmp);
	    	sb2.append(tmp);
	    	prop.put("cour", "%"+ques.getQuesCour()+"%");
	    }
	    if(ques.getQuesCate()!=null && !ques.getQuesCate().trim().equals("")){
	    	tmp = " and ques_cate=:cate";
	    	sb1.append(tmp);
	    	sb2.append(tmp);
	    	prop.put("cate", ques.getQuesCate());
	    }
	    if(ques.getQuesLevel()!=null && !ques.getQuesLevel().equals("")){
	    	tmp = " and ques_level=:level";
	    	sb1.append(tmp);
	    	sb2.append(tmp);
	    	prop.put("level", ques.getQuesLevel());
	    }
	    tmp = " order by ques_time desc";					//按时间倒序排列
	    sb1.append(tmp);
	    
	    Query query1 = getSession().createSQLQuery(sb1.toString());
	    Query query2 = getSession().createSQLQuery(sb2.toString());
	    if(!prop.isEmpty()){
	    	query1 = query1.setProperties(prop);
	    	query2 = query2.setProperties(prop);
	    }
	    List<Map<String,Object>> queses = query1.setFirstResult(start)
	    									    .setMaxResults(limit)
	    									    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
	    									    .list();
	    Integer count = Integer.parseInt(""+query2.uniqueResult());
	    param.clear();
	    param.put("queses", queses);
	    param.put("count", count);
		return param;
	}
	/**
	 * 查询课程,可以根据课程名称来检索
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryCour(Map<String, Object> param) {
		String courName = (String)param.get("courName");
		Integer start   = (Integer)param.get("start");
		Integer limit   = (Integer)param.get("limit");
		Criteria c = getSession().createCriteria(Cour.class);
		String sql = "select count(*) from cour";
		if(courName!=null && !courName.trim().equals("")){
			c.add(Restrictions.like("courName", "%"+courName.trim()+"%"));
			sql = sql+" where cour_name like '%"+courName.trim()+"%'";
		}
		c.setFirstResult(start).setMaxResults(limit);
		List<Cour> cours = c.list();
		Query query = getSession().createSQLQuery(sql);
		Integer count = Integer.parseInt(""+query.uniqueResult());
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("cours",cours);
		result.put("count",count);
		return result;
	}
	/**
	 * 保存试题
	 */
	public Map<String, Object> save(Map<String, Object> param) {
		Ques ques = (Ques)param.get("ques");
		getSession().save(ques);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
	/**
	 * 查询确定的一条记录
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryOneQues(String quesId) {
		Map<String,Object> result = new HashMap<String, Object>();
		String sql = "select ques_id as quesId,ques_title as quesTitle,ques_body as quesBody,ques_solu as quesSolu,"+
			         "ques_cate as quesCate,cate_name as cateName,"+
			         "ques_cour as quesCour,cour_name as courName,"+
			         "ques_score as quesScore,ques_level as quesLevel,ques_choose as quesChoose"+
			         " from ques inner join cate on ques_cate=cate_id"+
			         " inner join cour on ques_cour=cour_id"+
			         " where ques_id=:id";
		Map<String,Object> ques=(Map<String,Object>)getSession().createSQLQuery(sql)
											.setString("id", quesId)
											.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											.uniqueResult();
		result.put("ques",ques);
		return result;
	}
	/**
	 * 删除试题
	 */
	public Map<String,Object> del(String quesId){
		Map<String,Object> result = new HashMap<String, Object>();
		Ques ques = (Ques)getSession().load(Ques.class, quesId);
		String body="";
		if(ques!=null){
			body = ques.getQuesBody();
			getSession().delete(ques);
		}
		result.put("success",true);
		result.put("body", body);
		//int a = 9/0;//这儿出错是一样的回滚事务
		return result;
	}
}
