package com.myexam.stud.studexam.dao;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Answ;
import com.myexam.domain.Info;
import com.myexam.pub.CurrentTime;
/**
 * 学生考试Dao
 * @author wangjianme
 * @version 1.0,2010-11-28
 */
public class StudExamDaoJdbc extends HibernateDaoSupport implements IStudExamDao {
	/**
	 * 查询此学生已经开通的考试列表
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryOpenedExam(Map<String, String> param) {
		String studId= param.get("studId");										//学生id
		String sql = "select oe_id as oeId,"+
					 "oe_exam as oeExam,exam_name as examName,exam_score as examScore,"+
					 "exam_time as examTime,exam_pass as examPass,"+
					 "exam_cour as examCour,cour_name as courName,"+
					 "oe_stud as oeStud,oe_type as oeType"+
					 " from openexam inner join exam on oe_exam=exam_id"+
					 " inner join cour on exam_cour=cour_id"+
					 " where oe_stud='"+studId+"'"+
					 " and oe_state='1'";										//查询开通还没有参加，或是已经参加考试尚没有完成的考试	
		logger.info("查询开正在开通的SQL:\n"+sql);
		List<Map<String,Object>> openedExams = getSession().createSQLQuery(sql)
											   .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											   .list();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exams",openedExams);
		return map;
	}
	/**
	 * 开始考试,
	 * 具体的步骤如下
	 * 1、根据oeId查询出openexam对像
	 * 2、根据oe_exam查询出所有试卷和试题信息,主要指考试的时间长度等信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> beginExaming(Map<String,Object> param){
		Map<String,Object> result = new HashMap<String, Object>();				//用于保存返回的信息
		boolean examIsOver = false;
		String oeId 	= (String)param.get("oeId");
		String sql = "select oe_id as oeId,"+
					 "oe_exam as oeExam,exam_name as examName,exam_score as examScore,"+
					 "exam_time as examTime,exam_pass as examPass,"+
					 "exam_cour as examCour,cour_name as courName,"+
					 "oe_stud as oeStud,oe_type as oeType"+
					 " from openexam inner join exam on oe_exam=exam_id"+
					 " inner join cour on exam_cour=cour_id"+
					 " where oe_id='"+oeId.trim()+"'";
		System.err.println("查询一个试卷的SQL:"+sql);
		Map<String,Object> openexam = (Map<String,Object>)getSession().createSQLQuery(sql)
									  .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
									  .uniqueResult();
		System.err.println("查询的结果为："+openexam);
		
		Integer examTime = Integer.parseInt(""+openexam.get("examTime"));			//考试的时间长度
		
		Info info = isBegined(oeId,""+openexam.get("oeStud"));
		if(info==null){																//说明还没有进行考试
			info = new  Info();
			info.setInfoOe(oeId);													//关联的openexam.oe_id
			info.setInfoStud(""+openexam.get("oeStud"));							//学生id
			info.setInfoBtime(CurrentTime.getDate());								//开始考试的时间
			info.setInfoState("0");													//状态为考试进行中
			info.setInfoScore(0);													//分数为0
			info.setInfoTimein(0);
			info.setInfoType(Integer.parseInt(""+openexam.get("oeType")));			//0是正常考试,N是第N次补考
			info.setInfoResit("0");	
			getSession().save(info);
			result.put("remain",examTime-1);										//剩余考试时间为总时间减1			
		}else{																		//说明已经开始考试
			String infoBtime 	= info.getInfoBtime();									//获取考试的开始时间
			Date infoBtimeDate  = CurrentTime.getDate(infoBtime);
			Date nowTime 	    = new Date();										//当前时间
			int diff            = (int)(nowTime.getTime()-infoBtimeDate.getTime());		//计算从开始时间到目前的时间差
			diff 			    = diff/(1000*60);									//换算成分钟
			diff				= examTime-diff;									//再用考试总时间减去到现在的用时
			System.err.println("是否已经超时："+diff);
			if(diff<=0){															//说明考试时间超时，即离场时间已经超时,需要在这儿计算考试分数
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("infoId",info.getInfoId());
				map.put("infoTimein",examTime);										//如果已经超时则默认使用了120分钟
				map.put("infoEtime",CurrentTime.getDate());
				map.put("oeId",oeId);
				calculateScore(map);												//计算得分
				examIsOver = true;													//记录考试已经完成的标志	
			}else{																	//查询已经考过的试题
				result.put("remain",diff-1);											//计录考试的剩余时间
				sql = "select answ_eq as answEq from answ where answ_info=:info";   //查询已经做过的试题
				List<Map<String,Object>> answs = new ArrayList<Map<String,Object>>();
				answs = getSession().createSQLQuery(sql)
											     .setString("info",info.getInfoId())
											     .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											     .list();
				result.put("answs",answs);											//保存做过的试题id
			}
		}
		if(examIsOver==false){														//如果还没有考试完成则要查询此试卷的所有试题
			Map<String,Object> exam = queryExamQues(""+openexam.get("oeExam"));		//根据试卷的id查询出所有试题
			result.putAll(exam);
		}
		result.put("infoBtime",info.getInfoBtime());
		result.put("success",true);
		result.put("infoId",info.getInfoId());
		result.put("infoType",info.getInfoType());
		result.put("oeId", oeId);
		result.put("examIsOver",examIsOver);
		return result;
	}
	/**
	 * 查询此试卷是否已经开始了考试,并判断是否已经超时
	 * 参数：oeId,studId,examTime
	 */
	private Info isBegined(String oeId,String studId){
		Info info = (Info)getSession().createCriteria(Info.class)
					.add(Restrictions.eq("infoOe",oeId))
					.add(Restrictions.eq("infoStud",studId))
					.uniqueResult();
		return info;
	}
	/**
	 * 计算分数的方法,
	 * 1、首先对answ中的每个试题和答案进行对比，在对比时要通过试题类型来做
	 * 2、将计算结果相加后的总分放到info表中，并写入最后提交时间，和修改状态为已经考试完成
	 * 参数为：infoId:开始考试后的id,并与examques表进行关联
	 * [studId:学生id,examId:试卷id]
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> calculateScore(Map<String,Object> param){
		System.err.println("自动计算分值...开始...");
		String oeId        	 = (String)param.get("oeId");
		String infoId		 = (String)param.get("infoId");									//考试id
		Integer infoTimein	 = (Integer)param.get("infoTimein");							//考试用时
		String infoEtime 	 = (String)param.get("infoEtime");								//结束时间
		
		String sql = "select answ_id as answId,answ_answer as answAnswer,"+
				     "eq_cate as eqCate,eq_solu as eqSolu,eq_score as eqScore"+
				     " from answ inner join examques on answ_eq=eq_id"+
				     " where answ_info=:infoId";
		List<Map<String,Object>> answs = getSession().createSQLQuery(sql)
										 .setString("infoId",infoId)
										 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										 .list();
		if(answs!=null && answs.size()>0){
			String answId;
			String cate;						//题型,首先要判断是何种题型，然后再根据题型判断学生的答案，最后更新answ表的得分
			String answAnswer;					//学生答案
			String solu;	 					//正确答案
			Integer score;						//分值
			for(int j=0;j<answs.size();j++){
				Map<String,Object> answ = answs.get(j);
				answId 		= (String)answ.get("answId");
				cate   		= (String)answ.get("eqCate");
				answAnswer  = (String)answ.get("answAnswer");
				solu 		= (String)answ.get("eqSolu");
				score		= (Integer)answ.get("eqScore");
				if(cate.equals("5")){						//如果是编程题目的话
					String[] solus = solu.split(",");
					int sum = solus.length;
					int right=0;
					for(int i=0;i<sum;i++){
						if(answAnswer.indexOf(solus[i])!=-1){		//有此关键字则加上1
							right++;
						}
					}
					System.err.println("正确的关键字Right:"+right);
					score = score*(right/sum);
				}else{
					if(!solu.equals(answAnswer)){					//其他试题直接判断,如果不相同则直接为0分
						score = 0;
					}
				}
				sql="update answ set answ_score="+score+" where answ_id='"+answId+"'";
				getSession().createSQLQuery(sql).executeUpdate();
				if(j%20==0){										//批处理
					getSession().flush();
				}
			}
		}
		//以下计算总得分
		sql = "select sum(answ_score)"+
				     " from answ where answ_info='"+infoId+"'";
		Integer sumScore = Integer.parseInt(""+getSession().createSQLQuery(sql).uniqueResult());
		sql = "update info set info_score="+sumScore+",info_state='1'," +
			  "info_etime='"+infoEtime+"',info_rate='',info_timein="+infoTimein+" where info_id='"+infoId+"'";
		System.err.println("修改info表的sql:"+sql);
		getSession().createSQLQuery(sql).executeUpdate();
		sql = "update openexam set oe_state='2' where oe_id='"+oeId+"'";					//修改openexam状态为考试已经完成，以防止再查询出来
		System.err.println("修改openexam状态SQL:"+sql);
		getSession().createSQLQuery(sql).executeUpdate();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("success",true);
		return result;
	}
	/**
	 * 使用以下值进行随机的排序
	 * String[] orders = {"eq_quesid","eq_id","eq_title","eq_body","eq_solu"};
	 * 同时使用asc还是desc也是随机的。
	 * 此功能和ExamViewDaoJdbc中的view方法一样，只是加了随机排序
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryExamQues(String examId) {
		String sql = "select exam_id as examId,exam_name as examName,"+			//查询此试卷信息及关联的课程
					 "exam_cour as examCour,cour_name as courName,"+
					 "exam_mktime as examMktime,exam_note as examNote,exam_time as examTime,"+
					 "exam_score as examScore,exam_pass as examPass"+
					 " from exam inner join cour on exam_cour=cour_id"+
					 " where exam_id='"+examId+"'";
		System.err.println("查询试卷的sql\n"+sql);
		
		Map<String,Object> exam = (Map<String,Object>)getSession().createSQLQuery(sql)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.uniqueResult();

		sql = "select ec_id as ecId,ec_cate as ecCate,ec_catename as ecCatename,ec_seq as ecSeq,"+		//查询此试卷下的所有题型
	      	  "ec_score as ecScore,ec_qty as ecQty"+
	          " from examcate where ec_exam=:examId order by ec_seq asc";
		System.err.println("查询出题型的sql:\n"+sql);
		List<Map<String,Object>> examcates = getSession().createSQLQuery(sql)
													 .setString("examId",examId)
													 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
													 .list();
		
		//查询题型下的所有试题，注意使用随机排序
		String[] orders = {"eq_quesid","eq_id","eq_title","eq_body","eq_solu"};
		Random r = new Random();
		int col = r.nextInt(5);
		String order = orders[col];
		String[] ascs = {"asc","desc"};
		col = r.nextInt(2);
		String asc = ascs[col];
		
		sql = "select eq_id as eqId,eq_quesid as eqQuesid,eq_seq as eqSeq,eq_cate as eqCate"+
		  		" from examques where eq_ecid=:ecid order by "+order+" "+asc;
		System.err.println("查询试题的sql:\n"+sql);
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
	 * 第一步，查询出某个试题，
	 * 第二步：查询此试题是否已经做过
	 * @see ExamviewDaoJdbc.queryOneExamQues
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryOneExamQues(Map<String,Object> param) {
		String infoId 		= (String)param.get("infoId");
		String examQuesId   = (String)param.get("quesId");
		String sql = "select eq_id as eqId,eq_cate as eqCate,eq_body as eqBody,eq_choose as eqChoose,"+
					 "ec_catename as cateName,eq_score as eqScore"+
					 " from examques inner join examcate on eq_ecid=ec_id"+
				     " where eq_id=:eqId";
		Map<String,Object> eq = (Map<String,Object>)getSession().createSQLQuery(sql)
								.setString("eqId",examQuesId)
								.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
								.uniqueResult();
		System.err.println("一个试题为："+eq);
		sql = "select answ_id as answId,answ_info as answInfo,answ_answer as answAnswer"+
			  " from answ"+
			  " where "+
			  " answ_eq=:eqId and answ_info=:infoId";
		Map<String,Object> answ = (Map<String,Object>)getSession().createSQLQuery(sql)
								  .setString("infoId",infoId)
								  .setString("eqId",examQuesId)
								  .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
								  .uniqueResult();
		System.err.println("answer 为:"+answ);
		if(answ!=null && !answ.isEmpty()){
			eq.putAll(answ);
		}
		System.err.println("最后的结果为:"+eq);
		return eq;
	}
	/**
	 * 保存学生考题信息
	 */
	public Map<String, Object> saveAnsw(Map<String, Object> param) {
		System.err.println("执行保存答案..");
		Answ answ = (Answ)param.get("answ");
		Map<String, Object> result = new HashMap<String, Object>();
		if(answ.getAnswId()==null){
			System.err.println("新答案");
			getSession().save(answ);
			result.put("answId",answ.getAnswId());
		}else{
			System.err.println("修改或...");
			if(answ.getAnswAnswer()==null || answ.getAnswAnswer().equals("")){
				getSession().delete(answ);		//如果用户又将自己的输入全部删除了则删除此记录
			}else{
				getSession().update(answ);
				result.put("answId",answ.getAnswId());
			}
		}
		result.put("success",true);
		return result;
	}
	/**
	 * 查询考试分数
	 */
	public Map<String,Object> examScore(Map<String,Object> param){
		String infoId = (String)param.get("infoId");
		String sql 	  = "select info_id as infoId,info_btime as infoBtime,info_score as infoScore,"+
					    "info_rate as infoRate,info_timein as infoTimein,"+
					    "info_type as infoType,info_etime as infoTtime"+
					    " from info "+
					    " where info_id='"+infoId+"';";
		System.err.println("查询分数："+sql);
		@SuppressWarnings("unchecked")
		Map<String,Object> info = (Map<String,Object>)getSession().createSQLQuery(sql)
								  .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
								  .uniqueResult();
		return info;
	}
}
