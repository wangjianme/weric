package com.myexam.teac.exam.exam.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Cour;
import com.myexam.domain.Exam;
/**
 * 试卷管理
 * @author wangjianme
 * @version 1.0,2010-10-29
 */
public class ExamDaoJdbc extends HibernateDaoSupport implements IExamDao {
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryExam(Map<String, Object> param) {
		Exam exam = (Exam)param.get("exam");
		Integer start = (Integer)param.get("start");
		Integer limit = (Integer)param.get("limit");
		
		StringBuffer sb1 = new StringBuffer();
		sb1.append("select exam_id as examId,exam_name as examName,exam_time as examTime,exam_score as examScore,");
		sb1.append("exam_cour as examCour,cour_name as courName,exam_state as examState,exam_pass as examPass");
		sb1.append(" from exam inner join cour on exam_cour=cour_id");
		sb1.append(" where 1=1");
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("select count(*)");
		sb2.append(" from exam inner join cour on exam_cour=cour_id");
		sb2.append(" where 1=1");
		
		Map<String,Object> prop = new HashMap<String, Object>();
		String tmp="";
		if(exam.getExamName()!=null && !exam.getExamName().trim().equals("")){
			tmp = " and exam_name like :name";
			sb1.append(tmp);
			sb2.append(tmp);
			prop.put("name", "%"+exam.getExamName().trim()+"%");
		}
		if(exam.getExamCour()!=null && !exam.getExamCour().trim().equals("")){
			tmp = " and cour_name like :cour";
			sb1.append(tmp);
			sb2.append(tmp);
			prop.put("cour","%"+exam.getExamCour().trim()+"%");
		}
		Query query1 = getSession().createSQLQuery(sb1.toString());
		Query query2 = getSession().createSQLQuery(sb2.toString());
		if(!prop.isEmpty()){
			query1 = query1.setProperties(prop);
			query2 = query2.setProperties(prop);
		}
		List<Map<String,Object>> exams = query1.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
												.setFirstResult(start)
												.setMaxResults(limit)
												.list();
		Integer count  				  = Integer.parseInt(""+query2.uniqueResult());
		param.clear();
		param.put("exams",exams);
		param.put("count", count);
		return param;
	}
	/**
	 * 查询课程
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryCour(Map<String, Object> param) {
		String courName = (String)param.get("courName");
		Integer start =   (Integer)param.get("start");
		Integer limit =   (Integer)param.get("limit");
		Criteria c = getSession().createCriteria(Cour.class); 
		c.add(Restrictions.eq("courInuse", "1"));	   //只查询在用的课程
		String sql = "select count(*) from cour where cour_inuse='1'";
		if(courName!=null && !courName.trim().equals("")){
			c.add(Restrictions.like("courName","%"+courName.trim()+"%"));
			sql = sql+" and cour_name like '%"+courName.trim()+"%'";
		}
		c.setFirstResult(start).setMaxResults(limit);
		List<Cour> cours = c.list();
		
		Integer count = Integer.parseInt(""+getSession().createSQLQuery(sql).uniqueResult());
		param.clear();
		param.put("cours",cours);
		param.put("count", count);
		return param;
	}
	/**
	 * 修改试卷状态
	 */
	public Map<String,Object> updateState(Map<String,Object> param){
		String examId 		= (String)param.get("examId");
		String examState 	= (String)param.get("examState");
		String sql = "update exam set exam_state=:state where exam_id=:id";
		getSession().createSQLQuery(sql).setString("state",examState)
					.setString("id",examId)
					.executeUpdate();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("success",true);
		return result;
	}
	/**
	 * 删除
	 * 先查询一下是否已经安排了考试，如果已经安排了考试则不则删除
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> delExam(String examId) {
		Map<String,Object> result = new HashMap<String, Object>();
		String sql ="select count(*) from openexam where oe_exam=:examId";
		Integer count = Integer.parseInt(""+getSession().createSQLQuery(sql).setString("examId",examId).uniqueResult());
		if(count>0){
			result.put("candel",false);										//不能删除
		}else{
			result.put("candel",true);										//以下开始删除，相关表：examques,examcate,exam
			sql = "select ec_id as ecid from examcate where ec_exam=:examId";
			List<Map<String,String>> listEcids = getSession().createSQLQuery(sql)
												 .setString("examId",examId)
												 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
												 .list();
			List<String> ecids = new ArrayList<String>();
			for(Map<String,String> tmp : listEcids){
				ecids.add(tmp.get("ecid"));
			}
			if(ecids.size()>0){
				sql = "delete from examques where eq_ecid in(:ecids)";			//删除试题信息
				getSession().createSQLQuery(sql)
					.setParameterList("ecids",ecids,Hibernate.STRING)
					.executeUpdate();
			}
			sql = "delete from examcate where ec_exam=:examId";					//删除题型
			getSession().createSQLQuery(sql).setString("examId",examId).executeUpdate();
			sql = "delete from exam where exam_id=:examId";						//删除试卷
			getSession().createSQLQuery(sql).setString("examId",examId).executeUpdate();
		}
		result.put("success",true);
		return result;
	}
}
