package com.topsen.financial.util.support.web;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import com.topsen.financial.util.support.dao.page.PageDB;
import com.topsen.financial.util.support.dao.page.PageDBFactory;

public class PageDBInitServlet extends HttpServlet{
	
	public void init(ServletConfig config){
		PageDBFactory.className = config.getInitParameter("className");
		String dbname = config.getInitParameter("dbname");
		int perPage = Integer.parseInt(config.getInitParameter("perPage"));
		try {
			PageDB pageDB = PageDBFactory.createNewDB();
			pageDB.setPerPage(perPage);
			PageDBFactory.putDB(dbname,pageDB);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
