package com.myexam.stud.studscore.action;

import java.util.HashMap;
import java.util.Map;

import com.myexam.domain.Stud;
import com.myexam.pub.JSONUtil;
import com.myexam.stud.studscore.service.IStudScoreService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 学生查询自己的成绩
 * @author wangjianme
 * @version 1.0,2010-12-2
 */
@SuppressWarnings("serial")
public class StudScoreAction extends ActionSupport {
	private IStudScoreService service;

	public IStudScoreService getService() {
		return service;
	}

	public void setService(IStudScoreService service) {
		this.service = service;
	}
	/**
	 * 直接转到登录页面
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 查询学生的成绩
	 */
	public String queryScores() throws Exception{
		Stud stud = (Stud)ActionContext.getContext().getSession().get("stud");
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("studId",stud.getStudId());
		param = getService().queryHistoryScore(param);
		String json = JSONUtil.toJsonString(param);
		System.err.println("成绩JSON："+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
}
