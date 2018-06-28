package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.topsen.financial.dao.inter.TemplateItemDAO;
import com.topsen.financial.po.TemplateItem;

public class TemplateItemDAOImpl extends TemplateItemDAO{
  
	public List<TemplateItem> queryAll() {
		List<TemplateItem> list = null;
		try {
			list= this.getMap().queryForList("temp_item_space.all");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TemplateItem> queryByTempId(int tempId) {
		List<TemplateItem> list = null;
		try {
			list= this.getMap().queryForList("temp_item_space.queryByTempId",tempId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public TemplateItem load(int id) {
		TemplateItem item=null;
		try {
			item = (TemplateItem) this.getMap().queryForObject("temp_item_space.queryItemById",id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

}
