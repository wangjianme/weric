package com.myexam.stud.repwd.action;

import java.util.HashMap;
import java.util.Map;

import com.myexam.domain.Stud;
import com.myexam.pub.JSONUtil;
import com.myexam.pub.Md5Password;
import com.myexam.stud.repwd.service.IRepwdService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 修改密码
 * @author wangjianme
 * @version 1.0,2010-12-6
 */
@SuppressWarnings("serial")
public class RepwdAction extends ActionSupport{
	private IRepwdService service;

	public IRepwdService getService() {
		return service;
	}

	public void setService(IRepwdService service) {
		this.service = service;
	}
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 修改密码
	 */
	private String oldPwd;
	private String newPwd;
	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String update() throws Exception{
		String oldPwd = Md5Password.encodePwd(getOldPwd().trim());				//将旧密码进行加密运算
		Stud stud     = (Stud)ActionContext.getContext().getSession().get("stud");
		Map<String,Object> map = new HashMap<String,Object>();
		if(oldPwd.equals(stud.getStudPwd())){						//如果旧密码和以前的密码相同
			map.put("valid",true);
			newPwd = Md5Password.encodePwd(getNewPwd().trim());
			stud.setStudPwd(newPwd);
			getService().updatePwd(stud);
		}else{
			map.put("valid",false);
		}
		map.put("success",true);
		String json = JSONUtil.toJsonString(map);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
}
