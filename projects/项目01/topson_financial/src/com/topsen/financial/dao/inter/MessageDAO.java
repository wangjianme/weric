package com.topsen.financial.dao.inter;

import java.util.List;

import com.topsen.financial.dao.BaseDAO;
import com.topsen.financial.po.BackMessage;
import com.topsen.financial.util.support.dao.DAOModel;

public abstract class MessageDAO extends BaseDAO implements DAOModel<BackMessage>{

	public int delete(int id) {
		return 0;
	}
	abstract public BackMessage load(String rebId);
	public BackMessage load(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<BackMessage> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}


}
