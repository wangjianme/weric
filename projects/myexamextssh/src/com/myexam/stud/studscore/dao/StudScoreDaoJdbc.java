package com.myexam.stud.studscore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 学生查询自己的历史成绩
 * @author wangjianme
 * @version 1.0,2010-12-2
 */
public class StudScoreDaoJdbc extends HibernateDaoSupport implements IStudScoreDao {
	/**
	 * 查询出学生的历史成绩
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryHistoryScore(Map<String, Object> param) {
		String studId = (String)param.get("studId");
		String sql = "select info_id as infoId,info_oe as infoOe,"+
					 "oe_exam as oeExam,exam_name as examName,exam_score as examScore,exam_pass as examPass,"+
					 "exam_cour as examCour,cour_name as courName,"+
					 "info_btime as infoBtime,info_score as infoScore,"+
					 "info_rate as infoRate,info_timein as infoTimein,info_type as infoType,"+
					 "info_etime as infoEtime"+
					 " from info inner join openexam on info_oe=oe_id " +
					 "and info_stud=oe_stud"+
					 " inner join exam on oe_exam=exam_id"+
					 " inner join cour on exam_cour=cour_id"+
					 " where info_state='1' " +
					 "and info_stud='"+studId.trim()+"'"+
					 " order by info_etime desc";
		System.err.println("查询学生成绩的SQL:"+sql);
		List<Map<String,Object>> scores = getSession().createSQLQuery(sql)
		 								  .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		 								  .list();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("scores",scores);
		return result;
	}
}
