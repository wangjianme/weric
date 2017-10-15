package com.myexam.teac.security.func.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.myexam.teac.security.func.service.IFuncService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FuncAction extends ActionSupport{
	private IFuncService service;
	public IFuncService getService() {
		return service;
	}
	public void setService(IFuncService service) {
		this.service = service;
	}
	/**
	 * 只负责转向功能
	 * @return
	 * @throws Exception
	 */
	public String init() throws Exception{
		return "init";
	}
	/**
	 * 查询功能菜单
	 */
	private String parentId;
	private String roleId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String query() throws Exception{
		List<Map<String,Object>> mms = getService().query(getParentId(),getRoleId());
		JSONArray json = JSONArray.fromObject(mms);
		String jsonStr = json.toString();
		ActionContext.getContext().put("jsonStr",jsonStr);
		return "jsonPage";
	}
	/**
	 * 保存功能
	 */
	private String menuIds;
	public String getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public String save() throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("roleId", getRoleId());
		map.put("menuIds", getMenuIds());
		map = getService().save(map);
		JSONObject json = JSONObject.fromObject(map);
		String jsonStr = json.toString();
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
}
