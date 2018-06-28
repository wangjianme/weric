package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.topsen.financial.dao.inter.TemplateDAO;
import com.topsen.financial.po.Template;

public class TemplateDAOImpl extends TemplateDAO{

	public List<Template> queryAll() {
		List<Template> list = null;
		try {
			list = this.getMap().queryForList("temp_space.all");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Template load(int id) {
		Template template = null;
		try {
			template = (Template) this.getMap().queryForObject("temp_space.one",id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return template;
	}

}
