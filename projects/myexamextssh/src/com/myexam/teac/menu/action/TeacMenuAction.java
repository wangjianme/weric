package com.myexam.teac.menu.action;
import java.util.List;
import java.util.Map;

import com.myexam.domain.Teac;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.menu.service.ITeacMenuService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 管理员登录功能
 * @author wangjianme
 * @version 1.0,2010-10-14
 */
@SuppressWarnings("serial")
public class TeacMenuAction extends ActionSupport{
	private String parentId;
	private ITeacMenuService service;
	public ITeacMenuService getService() {
		return service;
	}
	public void setService(ITeacMenuService service) {
		this.service = service;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String execute(){
		Teac teac = (Teac)ActionContext.getContext().getSession().get("teac");
		List<Map<String,Object>> menus = getService().query(getParentId(), teac.getTeacId());
		String jsonString = JSONUtil.toJsonString(menus);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
}
