package com.topsen.financial.dao.impl;

import java.sql.SQLException;

import com.topsen.financial.dao.inter.SumDAO;
import com.topsen.financial.po.SumRebursement;

public class SumDAOImpl extends SumDAO{

	@Override
	public SumRebursement load(String rebId) {
		SumRebursement sum = null;
		try {
			sum = (SumRebursement) this.getMap().queryForObject("sum_space.one",rebId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;
	}


	public int insert(SumRebursement t) {
		int i = 0;
		try {
			this.getMap().insert("sum_space.insert",t);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int update(SumRebursement t) {
		int i = 0;
		try {
			this.getMap().delete("sum_space.update",t);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

}
