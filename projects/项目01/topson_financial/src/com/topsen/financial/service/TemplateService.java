package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.TemplateDAOImpl;
import com.topsen.financial.dao.inter.TemplateDAO;
import com.topsen.financial.po.Template;

public class TemplateService {
	private TemplateDAO dao = new TemplateDAOImpl();
	
	public List<Template> queryAll(){
		return dao.queryAll();
	}
}
