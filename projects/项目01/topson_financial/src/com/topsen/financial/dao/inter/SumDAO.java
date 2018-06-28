package com.topsen.financial.dao.inter;

import java.sql.SQLException;
import java.util.List;
import com.topsen.financial.dao.BaseDAO;
import com.topsen.financial.po.SumRebursement;
import com.topsen.financial.util.support.dao.DAOModel;

public abstract class SumDAO extends BaseDAO implements DAOModel<SumRebursement>{
	abstract public SumRebursement load(String rebId);
	public int delete(int id) {
		
		return 0;
	}
	public SumRebursement load(int id) {
		return null;
	}

	public List<SumRebursement> queryAll() {
		return null;
	}

}
