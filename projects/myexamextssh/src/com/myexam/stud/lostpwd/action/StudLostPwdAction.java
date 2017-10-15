package com.myexam.stud.lostpwd.action;

import java.util.HashMap;
import java.util.Map;

import com.myexam.pub.JSONUtil;
import com.myexam.pub.Md5Password;
import com.myexam.stud.lostpwd.service.IStudLostPwdService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 学生忘记密码
 * @author wangjianme
 * @version 1.0,2010-12-8
 */
@SuppressWarnings("serial")
public class StudLostPwdAction extends ActionSupport{
	private IStudLostPwdService service;

	public IStudLostPwdService getService() {
		return service;
	}

	public void setService(IStudLostPwdService service) {
		this.service = service;
	}
	/**
	 *查询出提示问题
	 */
	private String studSid;
	private String studId;
	private String studPwd;
	private String studAnswer;
	public String getStudId() {
		return studId;
	}

	public void setStudId(String studId) {
		this.studId = studId;
	}

	public String getStudPwd() {
		return studPwd;
	}

	public void setStudPwd(String studPwd) {
		this.studPwd = studPwd;
	}

	public String getStudAnswer() {
		return studAnswer;
	}

	public void setStudAnswer(String studAnswer) {
		this.studAnswer = studAnswer;
	}

	public String getStudSid() {
		return studSid;
	}
	public void setStudSid(String studSid) {
		this.studSid = studSid;
	}
	public String queryQues() throws Exception{
		Map<String,Object> result = getService().queryQues(getStudSid());
		if(result==null){
			result = new HashMap<String, Object>();
			result.put("exsits",false);
		}else{
			result.put("exsits",true);
			ActionContext.getContext().getSession().put("answer",result.get("studAnswer"));
		}
		result.put("success",true);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 验证答案是否正确
	 * @return
	 * @throws Exception
	 */
	public String toUpdate() throws Exception{
		studAnswer 	= Md5Password.encodePwd(getStudAnswer().trim());
		String answ = (String)ActionContext.getContext().getSession().get("answer");
		Map<String,Object> result = new HashMap<String, Object>();
		if(studAnswer.equals(answ)){
			result.put("equals",true);
		}else{
			result.put("equals",false);
		}
		result.put("success",true);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 确定更新
	 */
	public String update() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("studId",getStudId());
		param.put("studPwd",Md5Password.encodePwd(getStudPwd().trim()));
		System.err.println(">>>:"+param);
		getService().update(param);
		param.clear();
		param.put("success",true);
		String json = JSONUtil.toJsonString(param);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
}
