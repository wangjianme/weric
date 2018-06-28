package com.topsen.financial.util.support.dao.page;

import java.util.List;

import org.apache.commons.logging.Log;

import com.topsen.financial.util.support.dao.bean.PageModel;

public class PageModelFactory{
	private PageDB pageDB;
	private PageModelFactory(String dbname){
		pageDB = PageDBFactory.getPageDB(dbname);
	}
	private PageModelFactory(){
		pageDB = PageDBFactory.getPageDB("mydb");
	}
	
	public static PageModelFactory getFactory(){
		return new PageModelFactory();
	} 
	public static PageModelFactory getFactory(String dbname){
		return new PageModelFactory(dbname);
	}
	
	
	private PageModel createBaseModel(String countSql,Object value,int curPage){
		PageModel pageModel = new PageModel();
		int count = 0;
		if (value != null){
			count  = pageDB.totalCount(countSql,value);
		}else{
			count  = pageDB.totalCount(countSql);
		}
		System.out.println("pageModel=======================>"+count);
		int beginIndex = pageDB.createBeginIndex(curPage);
		int endIndex = pageDB.createEndIndex(curPage);
		int totalPage = pageDB.totalPage(count);
		pageModel.setCount(count);
		pageModel.setBeginIndex(beginIndex);
		pageModel.setEndIndex(endIndex);
		pageModel.setTotalPage(totalPage);
		return pageModel;
	}
	public PageModel createPageModel(String sql,String countSql,int curPage) {
		PageModel pageModel = this.createBaseModel(countSql,null, curPage);
		List list = pageDB.createResultList(sql,pageModel.getBeginIndex(),pageModel.getEndIndex());
		pageModel.setList(list);
		return pageModel;
	}
	public PageModel createPageModel(String sql,Object value1,String countSql,Object value2,int curPage) {
		PageModel pageModel = this.createBaseModel(countSql,value2, curPage);
		List list = pageDB.createResultList(sql,value1,pageModel.getBeginIndex(),pageModel.getEndIndex());
		pageModel.setList(list);
		return pageModel;
	}
}
