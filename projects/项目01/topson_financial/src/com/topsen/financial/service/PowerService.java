package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.PowerDAOImpl;
import com.topsen.financial.dao.inter.PowerDAO;
import com.topsen.financial.po.Power;

public class PowerService {
	//在此service中构建权限的数据访问的层的对象  dao
	private PowerDAO dao = new PowerDAOImpl();
	
	public List<Power> queryAll(){
		return dao.queryAllPower();
	}
	
	public List<Power> queryByRoleId(int roleId){
		return dao.queryPowerByRoleId(roleId);
	}
	
	public void updatePowerAndRow(int[] powerId,int roleId){
		dao.updatePowerAndRole(powerId, roleId);
	}
	/*
	 * 数据访问层中的根据角色去查询权限的方法
	 */
	public List<Power> queryUserPower(int roleId){
		/*
		 * 利用构建好的数据访问的层的对象dao去调用数据访问层中的根据roleId去查找权限的方法 
		 * queryEmpPowerByRoleId(roleId)
		 */
		return dao.queryEmpPowerByRoleId(roleId); 
	}
	
}
