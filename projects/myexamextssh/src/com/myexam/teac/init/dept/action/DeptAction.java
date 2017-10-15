package com.myexam.teac.init.dept.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.json.types.JsonObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.myexam.domain.Dept;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.init.dept.service.IDeptService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 部门管理
 * @author wangjianme
 * @version 1.0,2010-10-4
 */
@SuppressWarnings("serial")
public class DeptAction extends ActionSupport implements ModelDriven<Dept>{
	private IDeptService service;
	public IDeptService getService() {
		return service;
	}
	public void setService(IDeptService service) {
		this.service = service;
	}
	/**
	 * 查询所有部门
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception{
		List<Dept> depts = getService().query(dept.getDeptId());
		JSONArray json = JSONArray.fromObject(depts);
		String jsonStr = json.toString();
		jsonStr = jsonStr.replaceAll("deptId", "id");
		jsonStr = jsonStr.replaceAll("deptName", "text");
		jsonStr = jsonStr.replaceAll("deptIsleaf","leaf");
		System.err.println("jsonStr:"+jsonStr);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 直接转向操作页面
	 */
	public String execute(){
		return SUCCESS;
	}
	/**
	 * 保存部门
	 */
	public String save() throws Exception{
		dept.setDeptIsleaf(true);
		Map<String,Object> result = getService().save(dept);
		String jsonStr = JSONUtil.toJsonString(result);
		System.err.println(">>>>:"+result);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 删除
	 */
	public String del() throws Exception{
		Map<String,Object> result = getService().del(dept.getDeptId());
		String jsonStr = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	public static void main(String[] args) {
		Dept d = new Dept();
		d.setDeptId("dd");
		d.setDeptName("大家好");
		d.setDeptParent("劳而无功");
		d.setDeptDesc("劳而无功");
		d.setDeptIsleaf(true);
		Map<String,Object> result= new HashMap<String, Object>();
		result.put("dept",d);
		result.put("success",true);
		JSONObject j = JSONObject.fromObject(result);
		System.err.println(j.toString());
	}
	/**
	 * ModelDriven..
	 */
	private Dept dept  = new Dept();
	public Dept getModel() {
		return dept;
	}
}
