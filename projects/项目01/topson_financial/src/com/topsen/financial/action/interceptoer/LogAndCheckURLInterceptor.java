package com.topsen.financial.action.interceptoer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topsen.financial.po.Emploee;
import com.topsen.financial.po.LogCreater;
public class LogAndCheckURLInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation arg0) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String uri = request.getRequestURI();
		Emploee emp = (Emploee)request.getSession().getAttribute("emp");
		String requestURI = uri.substring(uri.indexOf("!")+1,uri.lastIndexOf("."));
		if (!requestURI.equals("code")&&!requestURI.equals("login")&&!requestURI.equals("queryEmpPowerByRoleId")){
//			List<Power> powerList = emp.getPowerList();
//			String requestPower = uri.substring(uri.lastIndexOf("/")+1,uri.lastIndexOf("."));
//			boolean goNext = false;
//			for (Power power:powerList){
//				String url = power.getPowerURL();
//				if (!url.equals("") && (url.contains(".jsp") || url.contains(".action"))){
//					url = url.substring(0,url.lastIndexOf("."));
//				}
//				if (requestPower.equals(url)){
//					goNext = true;
//					break;
//				}
//			}
			if (emp == null){
				return "index";
			}
		}
		String path = arg0.invoke();
//		LogCreater.saveLogToDB(emp);
		return path;
	}

}
