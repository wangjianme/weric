package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.PowerDAOImpl;
import com.topsen.financial.dao.inter.PowerDAO;
import com.topsen.financial.po.Power;

public class PowerService {
	//�ڴ�service�й���Ȩ�޵����ݷ��ʵĲ�Ķ���  dao
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
	 * ���ݷ��ʲ��еĸ��ݽ�ɫȥ��ѯȨ�޵ķ���
	 */
	public List<Power> queryUserPower(int roleId){
		/*
		 * ���ù����õ����ݷ��ʵĲ�Ķ���daoȥ�������ݷ��ʲ��еĸ���roleIdȥ����Ȩ�޵ķ��� 
		 * queryEmpPowerByRoleId(roleId)
		 */
		return dao.queryEmpPowerByRoleId(roleId); 
	}
	
}
