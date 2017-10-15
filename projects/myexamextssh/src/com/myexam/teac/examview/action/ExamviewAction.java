package com.myexam.teac.examview.action;

import java.util.HashMap;
import java.util.Map;

import com.myexam.teac.examview.service.IExamviewService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 试卷预览功能
 * @author wangjianme
 * @version 1.0,2010-11-1
 */
@SuppressWarnings("serial")
public class ExamviewAction extends ActionSupport{
	private IExamviewService service;
	public IExamviewService getService() {
		return service;
	}
	public void setService(IExamviewService service) {
		this.service = service;
	}
	/**
	 * 转到预览功能上去，同时要查询数据库所有的此席卷的题型和试题
	 */
	private String examId;
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String execute() throws Exception{
		System.err.println("ExamId is:"+getExamId());
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("examId", getExamId());
		param = getService().view(param);
		System.err.println("Action exam>>:"+param);
		//ActionContext.getContext().put("exam", param);
		ActionContext.getContext().getSession().put("exam",param);				//将信息放到Session中
		return SUCCESS;
	}
	/**
	 * 转到考试预览功能页面
	 */
	public String toView() throws Exception{
		System.err.println("转到试卷预览页面...");
		return "view";
	}
	/**
	 * 查询一个固定的试题
	 */
	private String eqId;
	public String getEqId() {
		return eqId;
	}
	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	public String queryOneEq() throws Exception{
		System.err.println(">>>:"+getEqId());
		Map<String,Object> examques = getService().queryOneExamQues(getEqId());
		System.err.println(">>>:"+examques);
		ActionContext.getContext().put("eq", examques);
		return "examques";
	}
	
}
