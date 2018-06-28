package com.topsen.financial.action;

import com.opensymphony.xwork2.ModelDriven;
import com.topsen.financial.po.Role;
import com.topsen.financial.service.RoleService;
import com.topsen.financial.util.support.dao.bean.PageModel;
import com.topsen.financial.util.support.struts2.BaseAction;

public class RoleAction extends BaseAction implements ModelDriven<Role>{
	private RoleService service = new RoleService();
	private PageModel pageModel;
	private Role role = new Role();
	public Role getRole() {
		return role;
	}
	public Role getModel() {
		return role;
	}
	public PageModel getPageModel() {
		return pageModel;
	}
	public String queryAllRole(){
		String curPage = this.getRequest().getParameter("curPage");
		if (curPage == null){
			curPage = "1";
		}
		pageModel = service.queryByPage(Integer.parseInt(curPage));
		return "next";
	}
	public String queryOne(){
		String roleId = getRequest().getParameter("roleId");
		role = service.queryOne(Integer.parseInt(roleId));
		return "go";
	}
	public String insert(){
		service.insert(role);
		return this.queryAllRole();
	}
	public String update(){
		service.update(role);
		return this.queryAllRole();
	}
	public String delete(){
		String roleId = getRequest().getParameter("roleId");
		service.delete(Integer.parseInt(roleId));
		return this.queryAllRole();
	}
	
}
