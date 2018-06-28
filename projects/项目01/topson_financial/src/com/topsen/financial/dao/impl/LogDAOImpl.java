package com.topsen.financial.dao.impl;

import java.sql.SQLException;

import com.topsen.financial.dao.inter.LogDAO;
import com.topsen.financial.po.OperationLog;
import com.topsen.financial.util.support.dao.bean.PageModel;
import com.topsen.financial.util.support.dao.page.PageModelFactory;

public class LogDAOImpl extends LogDAO{

	public PageModel queryByPage(int curPage) {
		return PageModelFactory.getFactory().createPageModel("log_space.all", "log_space.count", curPage);
	}

	public int insert(OperationLog log) {
		int i = 0;
		try {
			this.getMap().insert("log_space.insert", log);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return i;
	}

	public OperationLog load(int id) {
		return null;
	}

}
