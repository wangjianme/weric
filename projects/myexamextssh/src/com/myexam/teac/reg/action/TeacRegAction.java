package com.myexam.teac.reg.action;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.myexam.domain.Teac;
import com.myexam.pub.CurrentTime;
import com.myexam.pub.Md5Password;
import com.myexam.teac.reg.service.ITeacRegService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 教师注册功能
 * @author wangjianme
 * @version 1.0,2010-9-29
 */
@SuppressWarnings("serial")
public class TeacRegAction extends ActionSupport implements ModelDriven<Teac>{
	private ITeacRegService service;
	public ITeacRegService getService() {
		return service;
	}

	public void setService(ITeacRegService service) {
		this.service = service;
	}
	//以下是处理部分
	private Teac teac = new Teac();
	public String execute(){
		System.err.println("Forward...");
		return "init";
	}
	/**
	 * 注册功能
	 */
	public String reg() throws Exception{
		teac.setTeacState("0");							//等待审批状态
		teac.setTeacTime(CurrentTime.getDate());
		teac.setTeacPwd(Md5Password.encodePwd(teac.getTeacPwd()));//对密码进行加密
		teac.setTeacAnswer(Md5Password.encodePwd(teac.getTeacAnswer().trim())); //对密码提示问题加密
		boolean boo = getService().reg(teac);
		System.err.println(">>>>>>>>>>:"+teac);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("success", boo);
		JSONObject json = JSONObject.fromObject(map);
		String jsonStr = json.toString();
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 验证相同的名称是否存在
	 */
	public String valid() throws Exception{
		boolean boo = getService().validate(teac);
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("success", boo);
		JSONObject json = JSONObject.fromObject(m);
		String jsonStr = json.toString();
		ActionContext.getContext().put("jsonStr",jsonStr);
		return "jsonPage";
	}
	
	public Teac getModel() {
		return teac;
	}
}
