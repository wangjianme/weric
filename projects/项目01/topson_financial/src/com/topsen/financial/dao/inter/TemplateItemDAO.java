package com.topsen.financial.dao.inter;

import java.util.List;

import com.topsen.financial.dao.BaseDAO;
import com.topsen.financial.po.TemplateItem;
import com.topsen.financial.util.support.dao.DAOModel;

public abstract class TemplateItemDAO extends BaseDAO implements DAOModel<TemplateItem>{
	
	abstract public List<TemplateItem> queryByTempId(int tempId);
	
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(TemplateItem t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int update(TemplateItem t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
