package com.myexam.teac.exammonitor.dao;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.myexam.pub.CurrentTime;
/**
 * 考试监控
 * 
 * @author wangjianme
 * @version 1.0,2010-11-16
 */
public class ExamMonitorDaoJdbc extends HibernateDaoSupport implements	IExamMonitorDao {
	/**
	 * 查询出所有正在考试的试卷信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryExamings() {
		String sql = "select info_id as infoId,"+
					 "info_oe as infoOe,oe_exam as oeExam,"+
					 "exam_name as examName,exam_time as examTime,"+
					 "exam_score as examScore,"+
					 "info_stud as infoStud,stud_name as studName,"+
					 "info_btime as infoBtime,"+
					 //"info_score as infoScore,info_rate as infoRate,"+   //分数，阅卷人，状态和用时，都不用查询出来
					 //"info_state,info_timein,"+
					 "info_type as infoType"+
					 " from info inner join openexam on info_oe=oe_id and info_stud=oe_stud"+
					 " inner join exam on oe_exam=exam_id"+
					 " inner join stud on info_stud=stud_id"+
					 " where info_state='0'";							//0：只查询正在考试中的学生
		System.err.println("查询所有正在考试的学生："+sql);
		List<Map<String,Object>> examingStuds = getSession().createSQLQuery(sql)
										        .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										        .list();
		Date now = new Date();												//当前时间，以下判断是否已经超时
		for(Map<String,Object> map:examingStuds){
			Integer examTime 	= Integer.parseInt(""+map.get("examTime"));	//考试的时间长度
			String  infoBtime 	= (String)map.get("infoBtime");				//开始考试的时间
			Date infoBtimeDate  = CurrentTime.getDate(infoBtime);			//将字符串转成Date对像
			Integer diff = (int)((now.getTime()-infoBtimeDate.getTime())/(1000*60));//用时多少时间到现在
			diff = examTime-diff;											//是否已经超过120分钟
			if(diff<=0){
				map.put("timeout","1");
			}else{
				map.put("timeout","0");
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("examingStuds", examingStuds);
		return result;
	}
}
