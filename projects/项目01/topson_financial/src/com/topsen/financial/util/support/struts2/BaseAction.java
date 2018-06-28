package com.topsen.financial.util.support.struts2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class BaseAction {
	
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest(); 
	}
	public HttpServletResponse getResponse(){
		return ServletActionContext.getResponse(); 
	}
	public HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	
	public void setRequestAttribute(String name,Object o){
		getRequest().setAttribute(name, o);
	}
	
	public void setSessionAttribute(String name,Object o){
		getSession().setAttribute(name, o);
	}
}
