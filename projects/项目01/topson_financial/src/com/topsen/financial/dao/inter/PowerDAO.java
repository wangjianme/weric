package com.topsen.financial.dao.inter;

import java.util.List;

import com.topsen.financial.dao.BaseDAO;
import com.topsen.financial.po.Power;
import com.topsen.financial.util.support.dao.DAOModel;

public abstract class PowerDAO extends BaseDAO implements DAOModel<Power>{
	
	abstract public List<Power> queryPowerByRoleId(int roleId);
	
	abstract public List<Power> queryAllPower();
	
	abstract public int updatePowerAndRole(int[] powerId,int roleId);
	
	abstract public List<Power> queryEmpPowerByRoleId(int roleId);
	
	public int delete(int id) {
		return 0;
	}

	public int insert(Power t) {
		return 0;
	}

	public Power load(int id) {
		return null;
	}

	public int update(Power t) {
		return 0;
	}
	public List<Power> queryAll(){
		return null;
	}

}
