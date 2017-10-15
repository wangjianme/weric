package com.myexam.teac.lostexam.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Openexam;
import com.myexam.pub.CurrentTime;
/**
 * 补考设置
 * 
 * @author wangjianme
 * @version 1.0,2010-11-22
 */
public class LostExamDaoJdbc extends HibernateDaoSupport implements ILostExamDao {
	/**
	 * 显示所有已经设置的补考，但还没有考试完成的学生
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryResitStuds() {
		String sql = "select oe_id as oeId,oe_exam as oeExam,exam_name as examName,"+
					 "oe_stud as oeStud,stud_name as studName,oe_teac as oeTeac,"+
					 "oe_rate as oeRate,oe_type as oeType,"+
					 "stud_cls as studCls,cls_name as clsName,"+
					 "oe_info as oeInfo"+
					 " from openexam inner join exam on oe_exam=exam_id"+
					 " inner join stud on oe_stud=stud_id"+
					 " inner join cls on stud_cls=cls_id"+
					 " where oe_state='1' and oe_type>'0'";
		System.err.println("查询补考的学生SQL:"+sql);
		List<Map<String,Object>> resitStuds = getSession().createSQLQuery(sql)
											  .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											  .list();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("resitStuds",resitStuds);
		return result;
	}
	/**
	 * 查询应该参加的补考的学生
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> queryLostStud(){
		String sql = "select info_id as infoId,info_rate as infoRate,"+
					 "info_stud as infoStud,stud_name as studName,"+
					 "info_score as infoScore,info_type as infoType,"+
					 "info_oe as infoOe,oe_exam as oeExam,exam_name as examName,"+
					 "exam_score as examScore,exam_pass as examPass,"+
					 "oe_teac as oeTeac,oe_rate as oeRate"+
					 " from info inner join openexam on info_oe=oe_id"+
				     " and info_stud=oe_stud"+
				     " inner join exam on oe_exam=exam_id"+
				     " inner join stud on info_stud=stud_id"+
				     " where info_score<exam_pass and info_resit='0' and info_state='1'";		//考试完成且没有参加补考的学生
		System.err.println("查询的应该参加补考的学生是："+sql);
		List<Map<String,Object>> lostStuds = getSession().createSQLQuery(sql)
											 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											 .list();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("lostStuds",lostStuds);
		return result;
	}
	/**
	 * 保存补考学生信息
	 */
	public Map<String,Object> saveLostStuds(List<Map<String,Object>> lostStuds){
		String oeTime = CurrentTime.getDate();
		for(int i=0;i<lostStuds.size();i++){
			Map<String,Object> map = lostStuds.get(i);
			String infoId = ""+map.get("infoId");
			Openexam oe = new Openexam();
			oe.setOeExam((""+map.get("oeExam")).trim());
			oe.setOeStud((""+map.get("infoStud")).trim());
			oe.setOeState("1");
			oe.setOeTeac((""+map.get("oeTeac")).trim());
			oe.setOeRate((""+map.get("oeRate")).trim());
			oe.setOeTime(oeTime);
			Integer oeType = Integer.parseInt(""+map.get("infoType"));
			oe.setOeType(oeType+1);									//必须加1
			oe.setOeInfo(infoId);									//如果是补考则必须说明补的是哪一次考试，通过infoId引用
			
			getSession().save(oe);
			String sql = "update info set info_resit='1' where info_id='"+infoId.trim()+"'";
			getSession().createSQLQuery(sql).executeUpdate();
			if(i%20==0){
				getSession().flush();
			}
		}
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("success",true);
		return result;
	}
	/**
	 * 删除已经安排的补考，首先要检查是否已经参加考试，如果没有才可以删除
	 */
	public Map<String,Object> deleteLostStud(Map<String,Object> param){
		String oeId 	= (String)param.get("oeId");
		String infoId 	= (String)param.get("infoId");
		param.clear();
		String sql = "select count(*) from info where info_oe='"+oeId.trim()+"'";
		Integer count = Integer.parseInt(""+getSession().createSQLQuery(sql).uniqueResult());
		if(count>0){							//已经参加考试不能删除
			param.put("inuse",true);
		}else{
			param.put("inuse",false);
			sql = "delete from openexam where oe_id='"+oeId.trim()+"'";
			getSession().createSQLQuery(sql).executeUpdate();
			sql = "update info set info_resit='0' where info_id='"+infoId.trim()+"'";
			getSession().createSQLQuery(sql).executeUpdate();
		}
		param.put("success",true);
		return param;
	}
}
