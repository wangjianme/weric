package com.myexam.teac.security.users.action;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.myexam.domain.Teac;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.security.users.service.IUsersService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 用户管理功能,分页显示所有用户
 * @author wangjianme
 * @version 1.0,2010-10-3
 */
@SuppressWarnings("serial")
public class UsersAction extends ActionSupport implements ModelDriven<Teac>{
	private IUsersService service;				//注入Service
	private String start;						//开始值
	private String limit;						//限制值
	private Teac teac = new Teac();
	public IUsersService getService() {
		return service;
	}
	public void setService(IUsersService service) {
		this.service = service;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	/**
	 * 直接转向显示页面
	 */
	public String execute() throws Exception{
		return SUCCESS;
	}
	/**
	 * 查询,且分页
	 */
	public String query() throws Exception{
		Map<String,Object> teacs = getService().query(getStart(),getLimit());
	    JSONObject json = JSONObject.fromObject(teacs);
	    String str = json.toString();
	    System.err.println(str);
	    ActionContext.getContext().put("jsonStr", str);
		return "jsonPage";
	}
	/**
	 * 管理员对注册用户的审批保存功能
	 */
	public String agree() throws Exception{
		JSONObject j = JSONObject.fromObject(teac);
		System.err.println(">>>>:"+j.toString());
		Map<String,Object> result = getService().agree(teac);
		JSONObject json = JSONObject.fromObject(result);
		String jsonStr = json.toString();
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 查询所有的角色
	 */
	public String roles() throws Exception{
		List<Map<String,Object>> roles = getService().queryRoles(teac.getTeacId());
		JSONArray json = JSONArray.fromObject(roles);
		String jsonStr = json.toString();
		System.err.println(">>::"+jsonStr);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 保存用户对应的角色
	 */
	private String teac_id;
	private String role_ids;
	
	public String getTeac_id() {
		return teac_id;
	}
	public void setTeac_id(String teacId) {
		teac_id = teacId;
	}
	public String getRole_ids() {
		return role_ids;
	}
	public void setRole_ids(String roleIds) {
		role_ids = roleIds;
	}
	public String saveRole() throws Exception{
		Map<String,Object> result = getService().saveRole(getTeac_id(), getRole_ids());
		JSONObject json = JSONObject.fromObject(result);
		String jsonStr = json.toString();
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 删除此用户的信息
	 */
	public String delete() throws Exception{
		Map<String,Object> result = getService().delete(getTeac_id());
		String jsonStr = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * ModelDriven..
	 */
	public Teac getModel() {
		return teac;
	}
}
