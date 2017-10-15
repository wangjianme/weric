package com.myexam.teac.exam.exam.action;

import java.util.HashMap;
import java.util.Map;

import com.myexam.domain.Exam;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.exam.exam.service.IExamService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 试卷设置
 * @author wangjianme
 * @version 1.0,2010-10-29
 */
@SuppressWarnings("serial")
public class ExamSetAction extends ActionSupport implements ModelDriven<Exam>{
	private IExamService service;
	public IExamService getService() {
		return service;
	}
	public void setService(IExamService service) {
		this.service = service;
	}
	public String execute() throws Exception{
		return SUCCESS;
	}
	/**
	 * 查询试卷信息
	 */
	private Integer start;
	private Integer limit;
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
	public String queryExam() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("exam",exam);
		param.put("start", getStart());
		param.put("limit",getLimit());
		param = getService().queryExam(param);
		String json = JSONUtil.toJsonString(param);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}
	/**
	 * 转到增加试卷页面
	 */
	public String toAdd() throws Exception{
		return "add";
	}
	/**
	 * 课程帮助
	 */
	private String courName;
	public String getCourName() {
		return courName;
	}
	public void setCourName(String courName) {
		this.courName = courName;
	}
	public String queryCour() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("start",getStart());
		param.put("limit",getLimit());
		param.put("courName", getCourName());
		param = getService().queryCour(param);
		String json = JSONUtil.toJsonString(param);
		System.err.println(json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 修改状态
	 */
	public String updateState() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("examId",exam.getExamId());
		param.put("examState",exam.getExamState());
		Map<String,Object> result = getService().updateState(param);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 删除
	 */
	public String delExam() throws Exception{
		Map<String,Object> result = getService().delExam(exam.getExamId());
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 模式驱动
	 */
	private Exam exam = new Exam();
	public Exam getModel() {
		return exam;
	}
}
