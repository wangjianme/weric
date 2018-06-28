package com.topsen.financial.dao.inter;

import java.util.List;

import com.topsen.financial.dao.BaseDAO;
import com.topsen.financial.po.OperationLog;
import com.topsen.financial.util.support.dao.page.PageableDAO;

public abstract class LogDAO extends BaseDAO implements PageableDAO<OperationLog>{


	public int delete(int id) {
		return 0;
	}
	public List<OperationLog> queryAll() {
		return null;
	}

	public int update(OperationLog t) {
		return 0;
	}

}
