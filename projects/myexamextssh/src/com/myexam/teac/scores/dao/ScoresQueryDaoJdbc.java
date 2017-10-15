package com.myexam.teac.scores.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 查询学生的考试分数
 * @author wangjianme
 * @version 1.0,2010-12-2
 */
public class ScoresQueryDaoJdbc extends HibernateDaoSupport implements IScoresQueryDao{
	/**
	 * 根据参加查询学生的考试分数,
	 * 必须 要分页
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryScores(Map<String, Object> param) {
		String examName = (String)param.get("examName");
		String clsName  = (String)param.get("clsName");
		String courName = (String)param.get("courName");
		String studName = (String)param.get("studName");
		Integer start   = (Integer)param.get("start");
		Integer limit   = (Integer)param.get("limit");
		
		String sql = "select info_id as infoId,info_oe as infoOe,"+
					 "oe_exam as oeExam,exam_name as examName,"+
					 "info_stud as infoStud,stud_name as studName,"+
					 "stud_cls as studCls,cls_name as clsName,"+
					 "exam_cour as examCour,cour_name as courName,"+
					 "info_score as infoScore,info_timein as infoTimein,"+
					 "info_type as infoType,info_rate as infoRate,"+
					 "oe_teac as oeTeac,info_resit as infoResit,"+
					 "exam_pass as examPass"+
					 " from info inner join openexam on info_oe=oe_id " +
					 "and info_stud=oe_stud"+
					 " inner join exam on oe_exam=exam_id"+
					 " inner join cour on exam_cour=cour_id"+
					 " inner join stud on info_stud=stud_id"+
					 " inner join cls on stud_cls=cls_id"+
					 " where info_state='1'";
		Map<String,Object> prop = new HashMap<String, Object>();
		if(examName!=null && !examName.trim().equals("")){
			sql = sql + " and exam_name like :examName";
			prop.put("examName","%"+examName.trim()+"%");
		}
		if(clsName!=null && !clsName.trim().equals("")){
			sql = sql + " and cls_name like :clsName";
			prop.put("clsName","%"+clsName.trim()+"%");
		}
		if(studName!=null && !studName.trim().equals("")){
			sql = sql +" and stud_name like :studName";
			prop.put("studName", "%"+studName.trim()+"%");
		}
		if(courName!=null && !courName.trim().equals("")){
			sql = sql +" and cour_name like :courName";
			prop.put("courName","%"+courName.trim()+"%");
		}
		String sql2 = sql.substring(sql.indexOf("from"));						//从第一个sql中截取第二个查询数量的sql
		sql2 = "select count(*) "+sql2;
		
		System.err.println("sql1>>"+sql);
		System.err.println("sql2>>"+sql2);
		Query query1 = getSession().createSQLQuery(sql);
		Query query2 = getSession().createSQLQuery(sql2);
		if(!prop.isEmpty()){
			query1 = query1.setProperties(prop);
			query2 = query2.setProperties(prop);
		}
		List<Map<String,Object>> scores = query1.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
												.setFirstResult(start)
												.setMaxResults(limit)
											    .list();
		Integer count = Integer.parseInt(""+query2.uniqueResult());
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("count",count);
		result.put("scores",scores);
		return result;
	}
}
