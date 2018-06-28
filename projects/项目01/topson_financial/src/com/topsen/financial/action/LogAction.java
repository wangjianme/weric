package com.topsen.financial.action;

import com.topsen.financial.service.LogService;
import com.topsen.financial.util.support.dao.bean.PageModel;
import com.topsen.financial.util.support.struts2.BaseAction;

public class LogAction extends BaseAction{
	private LogService service = new LogService();
	private PageModel pageModel;
	public PageModel getPageModel() {
		return pageModel;
	}
	public String queryAll(){
		String curPage = getRequest().getParameter("curPage");
		if (curPage == null){
			curPage = "1";
		}
		pageModel = service.queryByPage(Integer.parseInt(curPage));
		return "next";
	}
}
