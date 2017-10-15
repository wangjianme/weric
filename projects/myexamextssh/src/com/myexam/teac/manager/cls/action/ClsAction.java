package com.myexam.teac.manager.cls.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.myexam.domain.Cls;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.manager.cls.service.IClsService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 班级管理
 * @author wangjianme
 * @version 1.0,2010-10-9
 */
@SuppressWarnings("serial")
public class ClsAction extends ActionSupport implements ModelDriven<Cls>{
	private IClsService service;
	private int start;
	private int limit;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public IClsService getService() {
		return service;
	}
	public void setService(IClsService service) {
		this.service = service;
	}
	/**
	 * 转向操作页
	 */
	public String execute(){
		return SUCCESS;
	}
	/**
	 * 查询班级
	 */
	private String clsBtime2;			//查询止的开学时间
	private String clsEtime2;			//查询止的毕业时间
	private String edusysName;			//学制
	public String getEdusysName() {
		return edusysName;
	}
	public void setEdusysName(String edusysName) {
		this.edusysName = edusysName;
	}
	public String getClsBtime2() {
		return clsBtime2;
	}
	public void setClsBtime2(String clsBtime2) {
		this.clsBtime2 = clsBtime2;
	}
	public String getClsEtime2() {
		return clsEtime2;
	}
	public void setClsEtime2(String clsEtime2) {
		this.clsEtime2 = clsEtime2;
	}
	public String query() throws Exception{
		System.err.println("*********");
		System.err.println(JSONObject.fromObject(cls).toString());
		System.err.println(getStart());
		System.err.println(getLimit());
		System.err.println(getClsBtime2());
		System.err.println(getClsEtime2());
		System.err.println("*********");
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("cls", cls);
		param.put("start", getStart());
		param.put("limit",getLimit());
		param.put("clsBtime2",getClsBtime2());
		param.put("clsEtime2", getClsEtime2());
		param.put("edusysName", getEdusysName());
		Map<String,Object> result = getService().query(param);
		String jsonString = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
	/**
	 * 查询所有班主任
	 */
	private String teacName;
	public String getTeacName() {
		return teacName;
	}
	public void setTeacName(String teacName) {
		this.teacName = teacName;
	}
	public String queryTeac() throws Exception{
		Map<String,Object> result = getService().queryTeac(getTeacName(), getStart(),getLimit());
		String jsonString = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
	/**
	 * 查询专业
	 */
	public String queryMajor() throws Exception{
		Map<String,Object> result = getService().queryMajor();
		String jsonString = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
	/**
	 * 保存班级
	 */
	public String save() throws Exception{
		Map<String,Object> result = getService().save(cls);
		String jsonString = JSONUtil.toJsonString(result);
		System.err.println(">>>:"+jsonString);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
	/**
	 * 删除班级
	 */
	public String del() throws Exception{
		Map<String,Object> result = getService().del(cls);
		String jsonString = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
	/**
	 * 模式驱动
	 */
	private Cls cls = new Cls();
	public Cls getModel() {
		return cls;
	}
}
