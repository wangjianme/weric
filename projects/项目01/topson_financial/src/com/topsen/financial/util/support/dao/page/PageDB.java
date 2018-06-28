package com.topsen.financial.util.support.dao.page;

import java.util.List;

public abstract class PageDB {
	private int perPage;
	public PageDB(){}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	abstract public List createResultList(String sql,int arg0,int arg1);
	abstract public List createResultList(String sql,Object value,int arg0,int arg1);
	abstract public int createEndIndex(int curPage);
	abstract public int createBeginIndex(int curPage);
	abstract public int totalCount(String countSql);
	abstract public int totalCount(String countSql,Object value);
	public int totalPage(int totalCount) {
		int totalPage = totalCount/perPage;
		if (totalCount % perPage!= 0){
			totalPage++;
		}
		return totalPage;
	}
}
