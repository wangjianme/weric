package com.myexam.teac.security.password.action;

import java.util.HashMap;
import java.util.Map;

import com.myexam.domain.Teac;
import com.myexam.pub.JSONUtil;
import com.myexam.pub.Md5Password;
import com.myexam.teac.security.users.service.IUsersService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class PasswordAction extends ActionSupport {
	private IUsersService service;
	public IUsersService getService() {
		return service;
	}
	public void setService(IUsersService service) {
		this.service = service;
	}
	public String execute(){
		return SUCCESS;
	}
	/**
	 * 修改密码功能,,判断teacId和teacpwd必须完全一样才可以修改密码
	 */
	private String newpwd;
	private String oldpwd;
	public String getOldpwd() {
		return oldpwd;
	}
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public String changepwd() throws Exception{
		Teac tmpTeac = (Teac)ActionContext.getContext().getSession().get("teac");
		oldpwd = Md5Password.encodePwd(getOldpwd());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		if(!oldpwd.equals(tmpTeac.getTeacPwd())){
			result.put("changed",false);
		}else{
			newpwd = Md5Password.encodePwd(newpwd);
			result = getService().changePwd(tmpTeac.getTeacId(), newpwd);
		}
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}
}
