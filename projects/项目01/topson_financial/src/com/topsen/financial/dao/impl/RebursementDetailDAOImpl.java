package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topsen.financial.dao.inter.RebursementDetailDAO;
import com.topsen.financial.po.RebursementDetail;

public class RebursementDetailDAOImpl extends RebursementDetailDAO{

	public int insert(List<RebursementDetail> list,String rebId) {
		int i = 0;
		try {
			this.getMap().startTransaction();
			this.getMap().startBatch();
			if (rebId != null && !rebId.equals("")){
				this.getMap().delete("reb_detail_space.delete",rebId);
			}
			for (RebursementDetail detail : list){
				detail.setRebId(rebId);
				this.getMap().insert("reb_detail_space.insert", detail);
			}
			this.getMap().executeBatch();
			this.getMap().commitTransaction();
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				this.getMap().getCurrentConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				this.getMap().endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	@Override
	public List<RebursementDetail> queryByRebId(String rebId) {
		List<RebursementDetail> list = null;
		try {
			list = this.getMap().queryForList("reb_detail_space.queryByRebId", rebId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Integer> queryDistinctTempId(String rebId) {
		List<Integer> list = null;
		try {
			list = this.getMap().queryForList("reb_detail_space.distinctTempId",rebId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int update(String rebId) {
		int i = 0;
		try {
			this.getMap().update("reb_detail_space.update",rebId);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

}
