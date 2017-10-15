package com.myexam.stud.login.action;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.myexam.domain.Stud;
import com.myexam.pub.JSONUtil;
import com.myexam.pub.Md5Password;
import com.myexam.stud.login.service.IStudLoginService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 登录
 * @author wangjianme
 * @version 1.0,2010-8-28
 */
@SuppressWarnings("serial")
public class LoginAction extends ActionSupport implements ModelDriven<Stud>{
	private Logger log = Logger.getLogger(LoginAction.class); 
	private IStudLoginService service;
	public IStudLoginService getService() {
		return service;
	}
	public void setService(IStudLoginService service) {
		this.service = service;
	}
	/**
	 * 转向登录页面
	 */
	public String execute(){
		return SUCCESS;
	}
	/**
	 * 学生登录
	 */
	public String login() throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		stud.setStudPwd(Md5Password.encodePwd(stud.getStudPwd()));
		param.put("stud", stud);
		param = getService().login(param);
		if(param.get("stud")!=null){				//登录成功
			param.put("success",true);
			ActionContext.getContext().getSession().put("stud",param.get("stud"));
		}else{										//登录不成功
			param.put("success", false);
		}
		String json = JSONUtil.toJsonString(param);
		System.err.println(json);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}
	/**
	 * 安全退出
	 */
	public String loginOut() throws Exception{
		Map<String,Object> session = ActionContext.getContext().getSession();
		session.remove("stud");
		return "studLogin";
	}
	/**
	 * 登录成功以后的转向
	 */
	public String loginSuccess() throws Exception{
		return "desktop";
	}
	
	private Stud stud = new Stud();
	public Stud getModel() {
		return stud;
	}
}
