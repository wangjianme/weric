package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.topsen.financial.dao.inter.RefDAO;
import com.topsen.financial.po.RebursementReference;

public class RefDAOImpl extends RefDAO{

	public List<RebursementReference> queryAll() {
		List<RebursementReference> list = null;
		try {
			list = this.getMap().queryForList("ref_space.all");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
