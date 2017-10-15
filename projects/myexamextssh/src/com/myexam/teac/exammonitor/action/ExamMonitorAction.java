package com.myexam.teac.exammonitor.action;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.myexam.pub.CurrentTime;
import com.myexam.pub.JSONUtil;
import com.myexam.stud.studexam.service.IStudExamService;
import com.myexam.teac.exammonitor.service.IExamMonitorService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 考试监控
 * @author wangjianme
 * @version 1.0,2010-11-16
 */
@SuppressWarnings("serial")
public class ExamMonitorAction extends ActionSupport{
	private IExamMonitorService service;
	private IStudExamService studExamService;						//注入一个学生的考试，可以直接调用并计算得分
	public IStudExamService getStudExamService() {
		return studExamService;
	}
	public void setStudExamService(IStudExamService studExamService) {
		this.studExamService = studExamService;
	}
	public IExamMonitorService getService() {
		return service;
	}
	public void setService(IExamMonitorService service) {
		this.service = service;
	}
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 查询正在考试的试卷
	 */
	public String queryExamings(){
		Map<String,Object> result = getService().queryExamings();
		String json = JSONUtil.toJsonString(result);
		System.err.println(">>"+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 选中某个试卷提交
	 */
	private String infoId;						//开始考试的id
	private String oeId;						//用于确定试卷
	private String infoBtime;					//开始时间
	private Integer examTime;					//考试允许时间
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getOeId() {
		return oeId;
	}
	public void setOeId(String oeId) {
		this.oeId = oeId;
	}
	public String getInfoBtime() {
		return infoBtime;
	}
	public void setInfoBtime(String infoBtime) {
		this.infoBtime = infoBtime;
	}
	public Integer getExamTime() {
		return examTime;
	}
	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
	}
	/**
	 * 提交某个选中的试卷
	 * 参数：infoId,oeId,infoTimein(用时),infoEtime(结束时间)
	 */
	public String submitExam() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		Date now 				= new Date();							//当前时间
		Date infoBtimeDate		= CurrentTime.getDate(getInfoBtime());	//开始时间
		Integer diff = (int)((now.getTime()-infoBtimeDate.getTime())/(1000*60));
		diff = getExamTime()-diff;
		if(diff<=0){
			param.put("infoTimein",getExamTime());						//用时
		}else{
			param.put("infoTimein",diff);
		}
		param.put("infoId",getInfoId());
		param.put("oeId",getOeId());
		param.put("infoEtime",CurrentTime.getDate(now));
		System.err.println("提交参数为:"+param);
		Map<String,Object> result = getStudExamService().calculateScore(param);				//直接调用学生提交试卷的方法
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
}
