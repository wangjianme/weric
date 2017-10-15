package com.myexam.teac.init.cour.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.myexam.domain.Cour;
import com.myexam.domain.Major;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.init.cour.service.ICourService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 课程管理
 * @author wangjianme
 * @version 1.0,2010-10-7
 */
@SuppressWarnings("serial")
public class CourAction extends ActionSupport implements ModelDriven<Cour>{
	private ICourService service;
	public ICourService getService() {
		return service;
	}
	public void setService(ICourService service) {
		this.service = service;
	}
	public String execute(){
		return SUCCESS;
	}
	/**
	 * 查询时有可能带有根据专业来查询
	 */
	private Integer start;
	private Integer limit;
	private String majors;//查询的专业ID
	private Integer courHour2;//查询课时的终止时间
	public Integer getCourHour2() {
		return courHour2;
	}
	public void setCourHour2(Integer courHour2) {
		this.courHour2 = courHour2;
	}
	public String getMajors() {
		return majors;
	}
	public void setMajors(String majors) {
		this.majors = majors;
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
	public String query() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("cour", cour);
		param.put("majors", getMajors());
		param.put("start",getStart());
		param.put("limit", getLimit());
		param.put("courHour2", getCourHour2());
		Map<String,Object> result = getService().query(param);
		String jsonStr = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	public String save() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("cour", cour);
		param.put("majors", getMajors());
		Map<String,Object> mm = getService().save(param);
		String jsonStr = JSONUtil.toJsonString(mm);
		System.err.println(">>>:"+jsonStr);
		ActionContext.getContext().put("jsonStr",jsonStr);
		return "jsonPage";
	}
	/**
	 * 专业帮助
	 */
	public String queryMajor() throws Exception{
		List<Major> list = getService().queryMajor();
		Map<String,Object> mm = new HashMap<String, Object>();
		mm.put("majors", list);
		String jsonStr = JSONUtil.toJsonString(mm);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 删除
	 */
	public String del() throws Exception{
		Map<String,Object> mm = getService().del(cour);
		String jsonString = JSONUtil.toJsonString(mm);
		System.err.println("><><><:"+jsonString);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
	/**
	 * ModelDriven
	 */
	private Cour cour = new Cour();
	public Cour getModel() {
		return cour;
	}
}
