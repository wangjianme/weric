package com.myexam.teac.examarrange.action;
import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.json.JsonUtil;

import com.myexam.domain.Exam;
import com.myexam.domain.Openexam;
import com.myexam.pub.CurrentTime;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.examarrange.service.IExamArrangeService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 考试安排
 * @author wangjianme
 * @version 1.0,2010-11-4
 */
@SuppressWarnings("serial")
public class ExamArrangeAction extends ActionSupport{
	private IExamArrangeService service;
	public IExamArrangeService getService() {
		return service;
	}
	public void setService(IExamArrangeService service) {
		this.service = service;
	}
	/**
	 * 转到显示页面
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 查询所有试卷信息
	 */
	private Integer start;
	private Integer limit;
	private String examId;								//查询此试卷开通班级时有用
	private String examName;							//模糊查询试卷时有用
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
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

	public String queryExam() throws Exception{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("examName",getExamName());
		param.put("start",getStart());
		param.put("limit",getLimit());
		param = getService().queryExam(param);
		String json = JSONUtil.toJsonString(param);
		System.err.println(".>>:"+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 查询已经开通的学生信息
	 * @return
	 * @throws Exception
	 */
	public String queryOpenedStuds() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("examId",getExamId());
		Map<String,Object> result = getService().queryOpendStuds(param);
		String json = JSONUtil.toJsonString(result);
		System.err.println("所有开通的学生JSON\n"+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 根据班级开通考试信息
	 */
	private String clsId;				//班级id
	private String teac;				//授课教师 全名
	private String rate;				//阅卷教师 全名
	public String getClsId() {
		return clsId;
	}
	public void setClsId(String clsId) {
		this.clsId = clsId;
	}
	public String getTeac() {
		return teac;
	}
	public void setTeac(String teac) {
		this.teac = teac;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String saveFromCls() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("clsId",getClsId());
		param.put("teac",getTeac());
		param.put("rate",getRate());
		param.put("examId",getExamId());
		System.err.println("参数列表为:"+param);
		Map<String,Object> result = getService().saveFromCls(param);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 修改学生信息,主要指修改禁止考试或是允许考试
	 */
	private String oeId;
	private String oeState;
	public String getOeId() {
		return oeId;
	}
	public void setOeId(String oeId) {
		this.oeId = oeId;
	}
	public String getOeState() {
		return oeState;
	}
	public void setOeState(String oeState) {
		this.oeState = oeState;
	}
	public String updateState() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("oeId",getOeId());
		param.put("oeState",getOeState());
		param = getService().updateState(param);
		String json = JSONUtil.toJsonString(param);
		System.err.println("修改返回："+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 根据用户选择的记录一次删除多行
	 */
	public String deleteStuds() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("oeIds",getOeId());
		param = getService().deleteStuds(param);
		String json = JSONUtil.toJsonString(param);
		System.err.println("删除返回："+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	
}
