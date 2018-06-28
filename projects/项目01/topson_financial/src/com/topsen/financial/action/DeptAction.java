package com.topsen.financial.action;

import com.opensymphony.xwork2.ModelDriven;
import com.topsen.financial.po.Deptment;
import com.topsen.financial.service.DeptService;
import com.topsen.financial.util.support.dao.bean.PageModel;
import com.topsen.financial.util.support.struts2.BaseAction;
public class DeptAction extends BaseAction implements ModelDriven<Deptment>{
	private DeptService service = new DeptService();
	private Deptment deptment = new Deptment();
	private PageModel pageModel;
	public PageModel getPageModel() {
		return pageModel;
	}
	public Deptment getDeptment() {
		return deptment;
	}
	public void setDeptment(Deptment deptment) {
		this.deptment = deptment;
	}
	public Deptment getModel() {
		return deptment;
	}
	
	
	
	public String queryAllDept(){
		String curPage = getRequest().getParameter("curPage");
		if (curPage == null){
			curPage = "1";
		}
		pageModel = service.queryByPage(Integer.parseInt(curPage));
		return "next";
	}
	
	public String insertDept(){
		service.insertDept(deptment);
		return this.queryAllDept();
	}
	
	public String queryOne(){
		String deptno = getRequest().getParameter("deptno");
		deptment = service.queryOne(Integer.parseInt(deptno));
		return "go";
	}
	
	public String updateDept(){
		service.updateOne(deptment);
		return this.queryAllDept();
	}
	public String deleteOne(){
		String deptno = getRequest().getParameter("deptno");
		service.deleteOne(Integer.parseInt(deptno));
		return this.queryAllDept();
	}
	
	public String deleteCheck(){
		String[] deptnos = getRequest().getParameterValues("deptnos");
		service.delete(deptnos);
		return this.queryAllDept();
	}
	
}
