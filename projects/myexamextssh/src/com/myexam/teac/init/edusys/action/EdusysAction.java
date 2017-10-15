package com.myexam.teac.init.edusys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.myexam.domain.Edusys;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.init.edusys.service.IEdusysService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @author wangjianme
 * @version 1.0,2010-10-14
 */
@SuppressWarnings("serial")
public class EdusysAction extends ActionSupport implements ModelDriven<Edusys>{
	private IEdusysService service;
	
	public IEdusysService getService() {
		return service;
	}
	public void setService(IEdusysService service) {
		this.service = service;
	}
	/**
	 * 只负责转向
	 */
	public String execute(){
		return SUCCESS;
	}
	/**
	 * 查询出所有学制
	 */
	public String query() throws Exception{
		Map<String,Object> edusys = getService().query();
		String jsonsString = JSONUtil.toJsonString(edusys);
		System.err.println("json>>:"+jsonsString);
		ActionContext.getContext().put("jsonStr", jsonsString);
		return "jsonPage";
	}
	/**
	 * 保存方法
	 */
	private String json;
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	/**
	 * 保存功能，注意下面还要将字符串转成java对像
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception{
		System.err.println(">>>>>>>>>>>>>>>>>>>>>."+getJson());
		JSONArray json = JSONArray.fromObject(getJson());			//目前还是一个字符串
		Object[] oo     = json.toArray();
		List<Object> list = new ArrayList<Object>();
		for(Object o:oo){
			JSONObject jo = JSONObject.fromObject(o);
			Edusys edusys = (Edusys)JSONObject.toBean(jo,Edusys.class);
			list.add(edusys);
		}
		Map<String,Object> result = getService().save(list);
		String jsonStr = JSONUtil.toJsonString(result);
		System.err.println(">>>:"+jsonStr);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 删除方法
	 */
	public String del() throws Exception{
		Map<String,Object> result = getService().del(edusys);
		String jsonString = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
	/**
	 * 模式驱动
	 */
	private Edusys edusys = new Edusys();
	public Edusys getModel() {
		return edusys;
	}
}
