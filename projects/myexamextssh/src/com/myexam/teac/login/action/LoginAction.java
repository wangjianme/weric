package com.myexam.teac.login.action;

import java.util.Map;

import com.myexam.domain.Teac;
import com.myexam.pub.JSONUtil;
import com.myexam.pub.Md5Password;
import com.myexam.teac.login.service.ILoginService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 页面转向用户登录页面
 * @author wangjianme
 * @version 1.0,2010-9-25
 */
public class LoginAction extends ActionSupport implements ModelDriven<Teac>{
	private static final long serialVersionUID = 1L;
	private ILoginService service;
	public ILoginService getService() {
		return service;
	}
	public void setService(ILoginService service) {
		this.service = service;
	}
	/**
	 * 直接转到用户登录页面
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 转到登录成功以后页面
	 * @return
	 * @throws Exception
	 */
	public String loginSuccess() throws Exception{
		return "loginSuccess";
	}
	/**
	 * 用户登录功能
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception{
		teac.setTeacPwd(Md5Password.encodePwd(teac.getTeacPwd())); 						//加密运算
		Map<String,Object> result = getService().login(teac);
		if(result.get("teac")==null){
			result.put("success", false);
		}else{
			ActionContext.getContext().getSession().put("teac",result.get("teac"));		//将用户信息放到Session中
			result.put("success", true);
		}
		String jsonString = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonString);
		return "jsonPage";
	}
	/**
	 * 安全退出
	 */
	public String loginOut() throws Exception{
		Map<String,Object> session = ActionContext.getContext().getSession();
		session.remove("teac");
		System.err.println("用户信息已经清除....");
		return "teacLogin";
	}
	/**
	 * 模式驱动
	 */
	private Teac teac = new Teac();
	public Teac getModel() {
		return teac;
	}
}
