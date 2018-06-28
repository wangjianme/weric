package com.topsen.financial.dao.impl;

import java.sql.SQLException;

import com.topsen.financial.dao.inter.TemplateTypeDAO;
import com.topsen.financial.po.TemplateType;

public class TemplateTypeDAOImpl  extends TemplateTypeDAO{

	public TemplateType load(int id) {
		TemplateType type = null;
		try {
			type = (TemplateType) this.getMap().queryForObject("temp_type_space.one", id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return type;
	}


}
