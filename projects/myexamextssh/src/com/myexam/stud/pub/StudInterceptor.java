package com.myexam.stud.pub;

import java.util.Map;

import com.myexam.stud.login.action.LoginAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 验证学生是否登录的拦截器
 * @author wangjianme
 * @version 1.0,2010-12-6
 */
@SuppressWarnings("serial")
public class StudInterceptor extends AbstractInterceptor{
	public String intercept(ActionInvocation inc) throws Exception {
		if(inc.getAction() instanceof LoginAction){
			String method = inc.getProxy().getMethod();
			if(method.equals("loginSuccess")){
				Map<String,Object> session = ActionContext.getContext().getSession();
				if(session.get("stud")==null){					//没有登录，返回到登录页面
					return "studLogin";
				}else{
					return inc.invoke();								//已经登录了，执行Action
				}
			}else{
				return inc.invoke();
			}
		}else{
			Map<String,Object> session = ActionContext.getContext().getSession();
			if(session.get("stud")==null){					//没有登录，返回到登录页面
				return "studLogin";
			}else{
				return inc.invoke();								//已经登录了，执行Action
			}
		}
	}
}
