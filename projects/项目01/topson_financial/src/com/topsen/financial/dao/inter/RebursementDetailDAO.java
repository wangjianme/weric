package com.topsen.financial.dao.inter;

import java.util.List;

import com.topsen.financial.dao.BaseDAO;
import com.topsen.financial.po.RebursementDetail;
import com.topsen.financial.util.support.dao.DAOModel;

public abstract class RebursementDetailDAO extends BaseDAO implements DAOModel<RebursementDetail>{
	public int delete(int id) {
		return 0;
	}
	public RebursementDetail load(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<RebursementDetail> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}
	public int update(RebursementDetail t) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int insert(RebursementDetail t) {
		// TODO Auto-generated method stub
		return 0;
	}
	abstract public List<RebursementDetail> queryByRebId(String rebId);
	abstract public List<Integer> queryDistinctTempId(String rebId);
	abstract public int update(String rebId);
	abstract public int insert(List<RebursementDetail> t,String rebId);

}
