package com.topsen.financial.util.template.bean;

import java.util.ArrayList;
import java.util.List;

public class TemplateBean {
	private Object title = null;
	private List<Object> headList = new ArrayList<Object>();
	private List<List<Object>> bodyList = new ArrayList<List<Object>>();
	private List<Object> footList = new ArrayList<Object>();
	
	public List<Object> getHeadList() {
		return headList;
	}
	public List<List<Object>> getBodyList() {
		return bodyList;
	}
	public List<Object> getFootList() {
		return footList;
	}
	public void addHeader(String head){
		headList.add(head);
	}
	public void addBody(List<Object> body){
		bodyList.add(body);
	}
	public void addFoot(String foot){
		footList.add(foot);
	}
	
	public Object getTitle() {
		return title;
	}
	public void setTitle(Object title) {
		this.title = title;
	}
	
}
