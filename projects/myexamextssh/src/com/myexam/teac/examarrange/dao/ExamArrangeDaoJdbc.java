package com.myexam.teac.examarrange.dao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.myexam.domain.Openexam;
import com.myexam.pub.CurrentTime;
/**
 * 考试安排
 * @author wangjianme
 * @version 1.0,2010-11-4
 */
public class ExamArrangeDaoJdbc extends HibernateDaoSupport implements IExamArrangeDao{
	private Logger logger = Logger.getLogger(ExamArrangeDaoJdbc.class);
	/**
	 * 查询打开的考试
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryExam(Map<String, Object> param) {
		String examName  = (String)param.get("examName");
		Integer start 	 = (Integer)param.get("start");
		Integer limit    = (Integer)param.get("limit");
		StringBuffer sb1 = new StringBuffer("select exam_id as examId,exam_name as examName,"+
											"exam_cour as examCour,cour_name as courName,"+
											"exam_time as examTime,exam_maker as examMaker," +
											"exam_score as examScore,exam_pass as examPass"+
											" from exam inner join cour on exam_cour=cour_id" +
											" where exam_state='1'");
		StringBuffer sb2 = new StringBuffer("select count(*) "+
											" from exam inner join cour on exam_cour=cour_id" +
											" where exam_state='1'");
		System.err.println(">查询考试信息的SQL:"+sb1.toString()+"\n"+sb2.toString());
		Map<String,Object> prop = new HashMap<String,Object>();
		String tmp = "";
		if(examName!=null && !examName.trim().equals("")){
			tmp = " and exam_name like :examName";
			sb1.append(tmp);
			sb2.append(tmp);
			prop.put("examName","%"+examName.trim()+"%");
		}
		Query query1 = getSession().createSQLQuery(sb1.toString());
		Query query2 = getSession().createSQLQuery(sb2.toString());
		if(!prop.isEmpty()){
			query1 = query1.setProperties(prop);
			query2 = query2.setProperties(prop);
		}
		List<Map<String,Object>> exams = query1.setFirstResult(start)
												.setMaxResults(limit)
												.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
												.list();
		Integer count = Integer.parseInt(""+query2.uniqueResult());
		param.clear();
		param.put("exams",exams);
		param.put("count", count);
		return param;
	}
	/**
	 * 根据examid查询出已经安排了考试的学员信息和所属于的班级
	 */
	public Map<String, Object> queryOpendStuds(Map<String, Object> param) {
		String examId = (String)param.get("examId");
		String sql = "select oe_id as oeId,"+
					 "oe_stud as oeStud,stud_name as studName,"+
					 "stud_cls as studCls,cls_name as clsName,"+
					 "oe_state as oeState,oe_teac as oeTeac,oe_rate as oeRate,"+
					 "oe_time as oeTime,oe_type as oeType"+
					 " from openexam inner join stud on oe_stud=stud_id"+
					 " inner join cls on stud_cls=cls_id"+
					 " where oe_exam='"+examId+"'";
		System.err.println("查询开通的学生SQL:\n"+sql);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> oeStuds = getSession().createSQLQuery(sql)
										   .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										   .list();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("oeStuds",oeStuds);
		return result;
	}
	/**
	 * 根据班级开通
	 * 具体的操作步骤为，先根据班级查询也所有的学生id
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> saveFromCls(Map<String,Object> param){
		String examId = (String)param.get("examId");
		String clsId  = (String)param.get("clsId");
		String teac   = (String)param.get("teac");
		String rate   = (String)param.get("rate");
		String oeTime = CurrentTime.getDate();								//获取一个统一的当前时间
		String sql = "select stud_id as studId from stud" +
					 " where stud_cls='"+clsId.trim()+"'";
		logger.info("查询学生id的sql:\n"+sql);
		List<Map<String,String>> studs = getSession().createSQLQuery(sql)
										.addScalar("studId",Hibernate.STRING)
										.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										.list();
		if(studs!=null && studs.size()>0){
			for(int i=0;i<studs.size();i++){
				Map<String,String> stud = studs.get(i);
				Openexam oe = new Openexam();
				oe.setOeExam(examId);
				oe.setOeStud(stud.get("studId"));
				oe.setOeTeac(teac);
				oe.setOeRate(rate);
				oe.setOeTime(oeTime);
				oe.setOeState("1");							//默认为允许考试1，0为禁止考试
				oe.setOeType(0);							//默认为正常考试，即初次考试
				getSession().save(oe);
				if(i%20==0){								//每次20行以后执行一次批处理
					getSession().flush();
				}
			}
			getSession().flush();
		}
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("success",true);
		return result;
	}
	/**
	 * 修改学生信息,主要指修改禁止考试或是允许考试
	 */
	public Map<String,Object> updateState(Map<String,Object> param){
		String oeId 	= (String)param.get("oeId");
		String oeState  = (String)param.get("oeState"); 
		String sql = "update openexam set oe_state='"+oeState+"' where oe_id='"+oeId+"'";
		getSession().createSQLQuery(sql).executeUpdate();
		param.clear();
		param.put("success",true);
		return param;
	}
	/**
	 * 根据用户选择的记录一次删除多行
	 */
	public Map<String,Object> deleteStuds(Map<String,Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		
		List<String> list = new ArrayList<String>();							            //用于记录删除的那些id
		String[] oeids = ((String)param.get("oeIds")).replaceAll(" ","").split(",");
		String sql = "";
		for(String str : oeids){
			sql = "select count(*) from info where info_oe='"+str.trim()+"'";				//检查是否已经参加了考试，如果已经参考了则不能删除
			Integer count = Integer.parseInt(""+getSession().createSQLQuery(sql).uniqueResult());
			if(count<=0){																    //还没有参加考试
				sql = "delete from openexam where oe_id='"+str.trim()+"'";
				getSession().createSQLQuery(sql).executeUpdate();
				list.add(str.trim());
			}
		}
		result.put("oeids",list);															//记录已经了的id
		result.put("success",true);
		return result;
	}
}
