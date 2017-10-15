package com.myexam.teac.manager.stud.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.myexam.domain.Stud;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.manager.stud.service.IStudManagerService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 学生管理
 * @author wangjianme
 * @version 1.0,2010-10-13
 */
@SuppressWarnings("serial")
public class StudManagerAction extends ActionSupport implements ModelDriven<Stud> {
	private IStudManagerService service;
	private Integer start;				//分页时使用
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
	public IStudManagerService getService() {
		return service;
	}
	public void setService(IStudManagerService service) {
		this.service = service;
	}
	public String execute() throws Exception{
		return SUCCESS;
	}
	/**
	 * 查询学生信息
	 * @return
	 * @throws Exception
	 */
	private String studMajor;
	private String studEdusys;
	private String studBirth2;
	public String getStudMajor() {
		return studMajor;
	}
	public void setStudMajor(String studMajor) {
		this.studMajor = studMajor;
	}
	public String getStudEdusys() {
		return studEdusys;
	}
	public void setStudEdusys(String studEdusys) {
		this.studEdusys = studEdusys;
	}
	public String getStudBirth2() {
		return studBirth2;
	}
	public void setStudBirth2(String studBirth2) {
		this.studBirth2 = studBirth2;
	}
	public String query() throws Exception{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("start", getStart());
		param.put("limit",getLimit());
		param.put("studMajor",getStudMajor());
		param.put("studEdusys", getStudEdusys());
		param.put("studBirth2", getStudBirth2());
		param.put("stud", stud);
		Map<String,Object> result = getService().query(param);
		String jsonsString = JSONUtil.toJsonString(result);
		System.err.println(">>:"+jsonsString);
		ActionContext.getContext().put("jsonStr",jsonsString);
		return "jsonPage";
	}
	/**
	 * 修改学生的部分信息
	 */
	public String update() throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stud", stud);
		Map<String, Object> result = getService().update(param);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 删除
	 * 首先删除此学生记录，然后再删除此学生的图片
	 */
	public String del() throws Exception{
		String studId = stud.getStudId();
		String studPic = stud.getStudPic();
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("studId", studId);
		Map<String,Object> result = getService().del(param);
		if(result.get("success").equals(true)){
			String path = ServletActionContext.getServletContext().getRealPath("/images/"+studPic);
			System.err.println("path:"+path);
			File file = new File(path);
			if(file.exists()){
				file.delete();
			}
		}
		String jsonString = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
	/**
	 * 查询班级
	 */
	public String queryCls() throws Exception{
		Map<String,Object> param  = new HashMap<String, Object>();
		param.put("start",getStart());
		param.put("limit",getLimit());
		param.put("clsName",stud.getStudCls());
		Map<String,Object> result = getService().queryCls(param);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 模式驱动
	 */
	private Stud stud =new Stud();
	public Stud getModel() {
		return stud;
	}

}
