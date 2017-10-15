package com.myexam.teac.scores.action;

import java.util.HashMap;
import java.util.Map;

import com.myexam.pub.JSONUtil;
import com.myexam.teac.scores.service.IScoresQueryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 查询学生考试成绩
 * @author wangjianme
 * @version 1.0,2010-12-2
 */
@SuppressWarnings("serial")
public class ScoresQueryAction extends ActionSupport {
	private IScoresQueryService service;
	public IScoresQueryService getService() {
		return service;
	}

	public void setService(IScoresQueryService service) {
		this.service = service;
	}
	/**
	 * 直接返回
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 根据参数查询学生的考试分数
	 * @param param
	 * @return
	 */
	private Integer start;
	private Integer limit;
	private String clsName;
	private String examName;
	private String courName;
	private String studName;
	public String getStudName() {
		return studName;
	}

	public void setStudName(String studName) {
		this.studName = studName;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
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

	public String getCourName() {
		return courName;
	}

	public void setCourName(String courName) {
		this.courName = courName;
	}

	public String queryScores() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("start",getStart());
		param.put("limit",getLimit());
		param.put("clsName",getClsName());
		param.put("examName",getExamName());
		param.put("courName",getCourName());
		param.put("studName",getStudName());
		Map<String,Object> result = getService().queryScores(param);
		String json = JSONUtil.toJsonString(result);
		System.err.println(">>:"+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
}
