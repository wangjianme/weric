package com.myexam.teac.onlinerate.action;

import java.util.HashMap;
import java.util.Map;

import com.myexam.domain.Teac;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.onlinerate.service.IOnlineRateService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.spring.interceptor.ActionAutowiringInterceptor;
/**
 * 在线阅卷功能
 * @author wangjianme
 * @version 1.0,2010-11-18
 */
@SuppressWarnings("serial")
public class OnlineRateAction extends ActionSupport {
	private IOnlineRateService service;
	public IOnlineRateService getService() {
		return service;
	}
	public void setService(IOnlineRateService service) {
		this.service = service;
	}
	/**
	 * 转向显示页面
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 查询应该阅的卷子
	 */
	private String rate;
	private String clsName;
	private String examName;
	private String infoRate;					//是否已经阅卷的记录
	public String getInfoRate() {
		return infoRate;
	}
	public void setInfoRate(String infoRate) {
		this.infoRate = infoRate;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getClsName() {
		return clsName;
	}
	public void setClsName(String clsName) {
		this.clsName = clsName;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String queryInfos() throws Exception{
		Teac teac = (Teac)ActionContext.getContext().getSession().get("teac");		//根据用户的阅卷人查询
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("rate",teac.getTeacName());
		param.put("clsName",getClsName());
		param.put("examName",getExamName());
		param.put("infoRate",getInfoRate());
		System.err.println("参数："+param);
		Map<String,Object> result = getService().queryStuds(param);
		String json = JSONUtil.toJsonString(result);
		System.err.println("infos:"+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 查询学生的做答情况
	 */
	private String infoId;
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String queryAnsws() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("infoId",getInfoId());
		Map<String,Object> result = getService().queryAnsws(param);
		System.err.println(result);
		ActionContext.getContext().put("exam",result);
		return "answs";
	}
	/**
	 * 保存修改的分数
	 */
	private String answId;
	private String answScore;

	public String getAnswId() {
		return answId;
	}

	public void setAnswId(String answId) {
		this.answId = answId;
	}

	public String getAnswScore() {
		return answScore;
	}

	public void setAnswScore(String answScore) {
		this.answScore = answScore;
	}

	public String updateScore() throws Exception{
		System.err.println(">>>>>>>>>>...");
		System.err.println(getAnswId());
		System.err.println(getAnswScore());
		System.err.println("infoId:"+getInfoId());
		
		Teac teac = (Teac)ActionContext.getContext().getSession().get("teac");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("answIds",getAnswId());
		param.put("answScores",getAnswScore());
		param.put("infoId",getInfoId());
		param.put("teacName",teac.getTeacName());
		
		Map<String,Object> result= getService().updateScore(param);
		String s = JSONUtil.toJsonString(result);
		System.err.println("修改结果："+s);
		ActionContext.getContext().put("jsonStr",s);
		return "jsonPage";
	}
}
