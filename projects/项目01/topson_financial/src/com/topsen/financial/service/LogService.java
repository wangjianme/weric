package com.topsen.financial.service;

import com.topsen.financial.dao.impl.LogDAOImpl;
import com.topsen.financial.dao.inter.LogDAO;
import com.topsen.financial.util.support.dao.bean.PageModel;

public class LogService {
	private LogDAO dao = new LogDAOImpl();
	
	public PageModel queryByPage(int curPage){
		return dao.queryByPage(curPage);
	}
}
