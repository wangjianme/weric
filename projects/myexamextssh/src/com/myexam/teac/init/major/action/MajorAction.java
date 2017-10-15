package com.myexam.teac.init.major.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.myexam.domain.Major;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.init.major.service.IMajorService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 专业
 * @author wangjianme
 * @version 1.0,2010-10-6
 */
@SuppressWarnings("serial")
public class MajorAction extends ActionSupport implements ModelDriven<Major> {
	private IMajorService service;
	public IMajorService getService() {
		return service;
	}
	public void setService(IMajorService service) {
		this.service = service;
	}
	public String execute() throws Exception{
		return SUCCESS;
	}
	/**
	 * 查询
	 */
	public String query() throws Exception{
		List<Map<String,Object>> majors = getService().query(major);
		Map<String,Object> mm = new HashMap<String, Object>();
		mm.put("majors", majors);
		JSONObject json = JSONObject.fromObject(mm);
		String jsonStr = json.toString();
		ActionContext.getContext().put("jsonStr",jsonStr);
		return "jsonPage";
	}
	/**
	 * 保存
	 */
	public String save() throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		if(major.getMajorId()==null || major.getMajorId().equals("")){
			map = getService().save(major);;
		}else{
			map = getService().update(major);
		}
		String jsonStr = JSONUtil.toJsonString(map);
		System.err.println("Major:"+jsonStr);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 删除
	 */
	public String del() throws Exception{
		Map<String,Object> mm  = getService().del(major.getMajorId());
		String jsonStr = JSONUtil.toJsonString(mm);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	
	/**
	 * ModelDriven
	 */
	private Major major = new Major();
	public Major getModel() {
		return major;
	}
}
