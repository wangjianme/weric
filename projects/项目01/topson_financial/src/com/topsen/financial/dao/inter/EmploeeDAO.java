package com.topsen.financial.dao.inter;

import java.util.List;

import com.topsen.financial.dao.BaseDAO;
import com.topsen.financial.po.Emploee;
import com.topsen.financial.util.support.dao.DAOModel;

public abstract class EmploeeDAO extends BaseDAO implements DAOModel<Emploee>{
	public Emploee load(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	public int delete(int id) {return 0;}

	public int insert(Emploee t) {return 0;}

	public abstract Emploee load(String empno);
	
	public abstract List<Emploee> queryByRoleId(int roleId);
	
	public abstract int updateRoleAndEmp(int roleId,String[] empnos);
	
}	
