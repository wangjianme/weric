package com.topsen.financial.util.support.dao.bean;

import java.util.List;

public class PageModel {
	private List list;
	private int count;
	private int totalPage;
	private int beginIndex;
	private int endIndex;
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public String toString(){
		return "[�ܼ�¼��:"+count+",��ҳ��:"+totalPage+",����Ԫ��:"+list+"]";
	}
	
}
