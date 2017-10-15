package com.myexam.teac.lostexam.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.myexam.pub.JSONUtil;
import com.myexam.teac.lostexam.service.ILostExamService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 补考管理
 * @author wangjianme
 * @version 1.0,2010-11-30
 */
@SuppressWarnings("serial")
public class LostExamAction extends ActionSupport{
	private ILostExamService service;
	public ILostExamService getService() {
		return service;
	}
	public void setService(ILostExamService service) {
		this.service = service;
	}
	/**
	 * 显示操作页面
	 */
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 显示所有已经设置的补考，但还没有考试完成的学生
	 */
	public String queryResitStuds(){
		Map<String,Object> result = getService().queryResitStuds();
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 查询应该参加的补考的学生
	 */
	public String queryLostStud(){
		Map<String,Object> result = getService().queryLostStud();
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 添加补考人员名单
	 */
	private String lostStuds;
	public String getLostStuds() {
		return lostStuds;
	}
	public void setLostStuds(String lostStuds) {
		this.lostStuds = lostStuds;
	}
	public String saveLostStuds() throws Exception{
		System.err.println("参数是："+getLostStuds());
		JSONArray json = JSONArray.fromObject(getLostStuds());
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String,Object>>)JSONArray.toCollection(json,Map.class);
		Map<String,Object> result = getService().saveLostStuds(list);
		String jsonStr = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",jsonStr);
		return "jsonPage";
	}
	/**
	 * 删除
	 */
	public String deleteLostStud() throws Exception{
		Map<String,Object> param = JSONUtil.getMap(getLostStuds());
		System.err.println(">>>:"+param);
		param = getService().deleteLostStud(param);
		String json = JSONUtil.toJsonString(param);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
}
