package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.TemplateItemDAOImpl;
import com.topsen.financial.dao.inter.TemplateItemDAO;
import com.topsen.financial.po.TemplateItem;

public class TemplateItemService {
	private TemplateItemDAO dao = new TemplateItemDAOImpl();
	
	public List<TemplateItem> queryByTempId(int tempId){
		return dao.queryByTempId(tempId);
	}
	
	public List<TemplateItem> queryAll(){
		return dao.queryAll();
	}
}	
