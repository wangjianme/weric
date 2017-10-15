package com.myexam.teac.security.role.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.myexam.teac.security.role.service.IRoleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 角色管理功能
 * @author wangjianme
 * @version 1.0,2010-9-29
 */
public class RoleAction extends ActionSupport{
	private Logger log = Logger.getLogger(RoleAction.class);
	private static final long serialVersionUID = 1L;
	private IRoleService service;
	public IRoleService getService() {
		return service;
	}
	public void setService(IRoleService service) {
		this.service = service;
	}
	/**
	 * 初始显示操作页面
	 */
	public String init(){
		log.info("直接转向操作页面");
		return "init";
	}
	/**
	 * 查询所有角色
	 */
	public String query() throws Exception{
		List<Map<String,Object>> roles = getService().query();
		Map<String,Object> mm = new HashMap<String, Object>();
		mm.put("roles", roles);
		JSONObject json = JSONObject.fromObject(mm);
		String jsonStr = json.toString();
		log.info(">>:"+jsonStr);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 保存功能或是修改功能
	 */
	private String roleId;
	private String roleName;
	private String roleDesc;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String save() throws Exception{
		System.err.println("roleName:"+getRoleName()+",desc:"+getRoleDesc());
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("roleId",getRoleId());
		params.put("roleName", getRoleName());
		params.put("roleDesc", getRoleDesc());
		params = getService().save(params);
		JSONObject json = JSONObject.fromObject(params);
		String jsonStr = json.toString();
		System.err.println("jsonStr>"+jsonStr);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 删除功能
	 */
	public String del() throws Exception{
		boolean boo = getService().del(getRoleName());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("success", boo);
		JSONObject json = JSONObject.fromObject(map);
		String jsonStr = json.toString();
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
}
