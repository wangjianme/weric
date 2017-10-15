package com.myexam.teac.examview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
 * 某个试卷的预览功能
 * @author wangjianme
 * @version 1.0,2010-11-1
 */
public class ExamviewDaoJdbc extends HibernateDaoSupport implements IExamviewDao {
	/**
	 * 1、根据examId查询出此席卷的信息，包括 ：
	 * 	所以题型及别名list，所以题型下面的试题
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> view(Map<String, Object> param) {
		String examId = (String)param.get("examId");
		String sql = "select exam_id as examId,exam_name as examName,"+			//查询此试卷信息
					 "exam_cour as examCour,cour_name as courName,"+
					 "exam_mktime as examMktime,exam_note as examNote,exam_time as examTime,"+
					 "exam_score as examScore"+
					 " from exam inner join cour on exam_cour=cour_id"+
					 " where exam_id=:examId";
		Map<String,Object> exam = (Map<String,Object>)getSession().createSQLQuery(sql)
					.setString("examId", examId)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.uniqueResult();
		System.err.println(">>exam:"+exam);
		
		sql = "select ec_id as ecId,ec_cate as ecCate,ec_catename as ecCatename,ec_seq as ecSeq,"+		//查询此试卷下的所有题型
		      "ec_score as ecScore,ec_qty as ecQty"+
		      " from examcate where ec_exam=:examId order by ec_seq asc";
		List<Map<String,Object>> examcates = getSession().createSQLQuery(sql)
														 .setString("examId",examId)
														 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
														 .list();
		
		//再查询某个题型下的所有试题
		sql = "select eq_id as eqId,eq_quesid as eqQuesid,eq_seq as eqSeq,eq_cate as eqCate"+
			  " from examques where eq_ecid=:ecid order by eq_seq asc";
		for(Map<String,Object> ec : examcates){
			List<Map<String,Object>> quess = getSession().createSQLQuery(sql)
												.setString("ecid",""+ec.get("ecId"))
												.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
												.list();
			ec.put("quess",quess);
		}
		exam.put("cates",examcates);
		return exam;
	}
	/**
	 * 查询一个固定的试题
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryOneExamQues(String examQuesId) {
		String sql = "select eq_id as eqId,eq_cate as eqCate,eq_seq as eqSeq,"+
					 "eq_title as eqTitle,eq_body as eqBody,eq_solu as eqSolu,"+
					 "eq_choose as eqChoose,eq_score as eqScore,"+
					 "ec_catename as cateName"+
					 " from examques inner join examcate on eq_ecid=ec_id"+
					 " where eq_id=:eqId";
		System.err.println(sql);
		Map<String,Object> examques 
					= (Map<String,Object>)getSession().createSQLQuery(sql)
					  .setString("eqId",examQuesId)
					  .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					  .uniqueResult();
		return examques;
	}
}
