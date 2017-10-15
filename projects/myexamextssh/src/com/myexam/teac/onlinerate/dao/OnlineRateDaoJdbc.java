package com.myexam.teac.onlinerate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
 * 在线阅卷功能
 * @author wangjianme
 * @version 1.0,2010-11-18
 */
public class OnlineRateDaoJdbc extends HibernateDaoSupport implements IOnlineRateDao {
	
	/**
	 * 查询所有尚没有阅卷或且规定的阅卷人为自己的
	 */
	public Map<String, Object> queryStuds(Map<String, Object> param) {
		String rate 		= (String)param.get("rate");			//阅卷人
		String clsName  	= (String)param.get("clsName");			//班级
		String examName     = (String)param.get("examName");		//试卷名称
		String infoRate	    = (String)param.get("infoRate");		//默认查询所有没有阅卷的试卷，但用户可以选
		StringBuffer sql = new StringBuffer("select info_id as infoId,"+
											"info_oe as infoOe,oe_exam as oeExam,exam_name as examName,"+
											"info_stud as infoStud,stud_name as studName,"+
											"oe_state as oeState,oe_teac as oeTeac,"+
											"info_btime as infoBtime,info_score as infoScore,"+
											"info_rate as infoRate,info_timein as infoTimein,"+
											"info_type as infoType,info_etime as infoEtime,"+
											"stud_cls as studCls,cls_name as clsName"+
											" from info inner join openexam on info_oe=oe_id " +
											" and info_stud=oe_stud"+
											" inner join exam on oe_exam=exam_id"+
											" inner join stud on info_stud=stud_id"+
											" inner join cls on stud_cls=cls_id"+
											" where info_state='1' and info_resit='0' and oe_state='2'");
											//此条件的意思是已经交卷且已经考试完成,且还没有安排补考的记录
		Map<String,Object> prop = new HashMap<String, Object>();
		if(rate!=null && !rate.trim().equals("")){
			sql.append(" and oe_rate=:rate");
			prop.put("rate",rate.trim());
		}
		if(clsName!=null && !clsName.trim().equals("")){
			sql.append(" and cls_name like :clsName");
			
			prop.put("clsName", "%"+clsName.trim()+"%");
		}
		if(examName!=null && !examName.trim().equals("")){
			sql.append(" and exam_name like :examName");
			prop.put("examName", "%"+examName.trim()+"%");
		}
		if(infoRate.equals("1")){
			sql.append(" and info_rate!=''");						//已经阅卷，0：全部，1：已经阅卷，2：没有阅卷
		}else if(infoRate.equals("2")){
			sql.append(" and info_rate=''");						
		}
		
		Query query = getSession().createSQLQuery(sql.toString());
		if(!prop.isEmpty()){
			query = query.setProperties(prop);
		}
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> infos = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										 .list();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("infos",infos);
		return result;
	}
	/**
	 * 根据用户提供的info_id查询学生做答的试卷情况
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> queryAnsws(Map<String,Object> param){
		String infoId = (String)param.get("infoId");
		String sql = "select info_id as infoId,info_oe as infoOe,"+
					 "info_stud as infoStud,stud_name as studName,"+
					 "info_score as infoScore,oe_exam as oeExam,exam_name as examName,"+
					 "exam_score as examScore,info_rate as infoRate,"+
					 "exam_cour as examCour,cour_name as courName"+
					 " from info inner join openexam on info_oe=oe_id and info_stud=oe_stud"+
					 " inner join stud on info_stud=stud_id"+
					 " inner join exam on oe_exam=exam_id"+
					 " inner join cour on exam_cour=cour_id"+
					 " where info_id='"+infoId.trim()+"'";
		System.err.println("查询出此试卷的信息及考分："+sql);
		Map<String,Object> infoExam = (Map<String,Object>)getSession().createSQLQuery(sql)
									   .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
									   .uniqueResult();
		
		String examId =(String)infoExam.get("oeExam");						//读取试卷id
		
		sql = "select answ_id as answId,answ_eq as answEq,"+
			  		 "answ_answer as answAnswer,answ_score as answScore"+
			         " from answ where answ_info='"+infoId.trim()+"'";				//根据infoId查询出学生做过的所有答案
		System.err.println("查询出所有答案：\n"+sql);
		List<Map<String,Object>> answs = getSession().createSQLQuery(sql)
										 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										 .list();
		//--第一步：根据试卷id查询所属的题型
		sql = "select ec_id as ecId,ec_exam as ecExam,ec_cate as ecCate,"+
					 "ec_catename as ecCatename,ec_seq as ecSeq,ec_score as ecScore,"+
					 "ec_qty as ecQty"+
					 " from examcate "+
					 " where ec_exam='"+examId+"'"+
					 " order by ec_seq asc";
		List<Map<String,Object>> examcates = getSession().createSQLQuery(sql)
										 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										 .list();
		//--第二步：根据型id查询出所有的试题
		for(Map<String,Object> examcate:examcates){
			sql = "select eq_id as eqId,eq_ecid as eqEcid,eq_cate as eqCate,eq_seq as eqSeq,"+
				  "eq_body as eqBody,eq_solu as eqSolu,eq_choose as eqChoose,"+
				  "eq_score as eqScore"+
				  " from examques"+
				  " where eq_ecid='"+examcate.get("ecId")+"'"+
				  " order by eq_seq asc";
			List<Map<String,Object>> quess = getSession().createSQLQuery(sql)
											.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											.list();
			for(Map<String,Object> ques:quess){							//对比是否有答案
				if(answs==null || answs.size()==0){									//如果已经没有答案了，直接退出
					break;
				}
				for(Map<String,Object> answ:answs){
					if(answ.containsValue(ques.get("eqId"))){			//如果包含此试题id则说明学生已经做了此试题
						ques.putAll(answ);								//数据复制
						answs.remove(answ);								//从答案链中删除答案
						break;
					}
				}
			}
			examcate.put("quess",quess);
		}
		
		infoExam.put("examcates",examcates);							//将题型信息放到exam中
		return infoExam;
	}
	/**
	 * 修改分数,同时还要修改info表中的总分,同时还要记录阅卷人信息，
	 */
	public Map<String, Object> updateScore(Map<String, Object> param) {
		String infoId 		= (String)param.get("infoId");				//infoid
		String teacName 	= (String)param.get("teacName");			//阅卷老师
		String answ_Ids 	= (String)param.get("answIds");
		String answ_Scores  = (String)param.get("answScores");
		String[] answIds 	= answ_Ids.split(",");
		String[] answScores = answ_Scores.split(",");
		
		for(int i=0;i<answIds.length;i++){
			String sql  = "update answ set answ_score="+answScores[i].trim()+" where answ_id='"+answIds[i].trim()+"'";
			System.err.println(i+">>"+sql);
			getSession().createSQLQuery(sql).executeUpdate();
			if(i%20==0){
				getSession().flush();
			}
		}
		getSession().flush();
		String sql = "select sum(answ_score) from answ where answ_info='"+infoId.trim()+"'";		//计算总分 
		System.err.println("计算总分的："+sql);
		String sum = ""+getSession().createSQLQuery(sql).uniqueResult();
		sql = "update info set info_score="+sum+",info_rate='"+teacName+"' where info_id='"+infoId.trim()+"'";
		getSession().createSQLQuery(sql).executeUpdate();
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("sum",sum);
		result.put("teacName",teacName);
		result.put("success",true);
		return result;
	}
	
}
