package com.myexam.teac.lostpwd.action;

import java.util.HashMap;
import java.util.Map;

import com.myexam.pub.JSONUtil;
import com.myexam.pub.Md5Password;
import com.myexam.teac.lostpwd.service.ILostPwdService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 忘记密码
 * @author wangjianme
 * @version 1.0,2010-12-7
 */
@SuppressWarnings("serial")
public class LostPwdAction extends ActionSupport {
	private ILostPwdService service;

	public ILostPwdService getService() {
		return service;
	}

	public void setService(ILostPwdService service) {
		this.service = service;
	}
	/**
	 * 查询出问题
	 */
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQues() throws Exception{
		Map<String,Object> result = getService().queryQues(getName());
		if(result==null){
			result = new HashMap<String, Object>();
			result.put("exsits", false);
		}else{
			result.put("exsits",true);
			ActionContext.getContext().getSession().put("answer",result.get("teacAnswer"));
		}
		result.put("success",true);
		String json = JSONUtil.toJsonString(result);
		System.err.println(">>:"+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 回答问题
	 */
	private String answer;
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String toUpdate() throws Exception{
		Map<String,Object> result = new HashMap<String, Object>();
		String teacAnswer = (String)ActionContext.getContext().getSession().get("answer");
		answer = Md5Password.encodePwd(getAnswer().trim());
		if(!answer.equals(teacAnswer)){
			result.put("answer",false);
		}else{
			result.put("answer",true);
		}
		result.put("success",true);
		String json = JSONUtil.toJsonString(result);
		System.err.println(">>KLK:"+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 
	 */
	private String teacId;
	private String teacPwd;
	public String getTeacId() {
		return teacId;
	}

	public void setTeacId(String teacId) {
		this.teacId = teacId;
	}

	public String getTeacPwd() {
		return teacPwd;
	}

	public void setTeacPwd(String teacPwd) {
		this.teacPwd = teacPwd;
	}

	public String update() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("teacId",getTeacId().trim());
		param.put("teacPwd",Md5Password.encodePwd(getTeacPwd().trim()));
		getService().update(param);
		param.clear();
		param.put("success",true);
		String json = JSONUtil.toJsonString(param);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
}
