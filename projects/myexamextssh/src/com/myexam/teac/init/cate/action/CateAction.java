package com.myexam.teac.init.cate.action;

import java.util.Map;

import com.myexam.pub.JSONUtil;
import com.myexam.teac.init.cate.service.ICateService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 题型
 * @author wangjianme
 * @version 1.0,2010-10-21
 */
@SuppressWarnings("serial")
public class CateAction extends ActionSupport {
	private ICateService service;
	public ICateService getService() {
		return service;
	}
	public void setService(ICateService service) {
		this.service = service;
	}
	public String execute(){
		return SUCCESS;
	}
	/**
	 * 查询全部
	 */
	public String query(){
		Map<String,Object> result = getService().query();
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
}
