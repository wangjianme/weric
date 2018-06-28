package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.topsen.financial.dao.inter.PowerDAO;
import com.topsen.financial.po.Power;

public class PowerDAOImpl extends PowerDAO{

	public List<Power> queryAllPower() {
		
		List<Power> list = null;
		try {
			list = this.getMap().queryForList("power_space.queryAll");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Power> queryPowerByRoleId(int roleId) {
		List<Power> list = null;
		try {
			list = this.getMap().queryForList("power_space.queryByRoleId",roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
    
	@Override
	public int updatePowerAndRole(int[] powerIds, int roleId) {
		int i = 0;
		try {
			this.getMap().startTransaction();
			this.getMap().startBatch();
			this.getMap().delete("power_space.delete", roleId);
			for (int powerId : powerIds){
				Map<String,Integer> map = new HashMap<String,Integer>();
				map.put("powerId", powerId);
				map.put("roleId", roleId);
				this.getMap().insert("power_space.insert", map);
			}
			this.getMap().executeBatch();//����ִ��sql������
			this.getMap().commitTransaction();//�������ύ�����ݿ�
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
	public List<Power> queryEmpPowerByRoleId(int roleId) {
		               //queryEmpPowerByRoleId
		//����һ��Ȩ�����͵ļ���  �������ղ�ѯ��Ȩ�޵Ľ��
		List<Power> list = null;
		try {
			/*
			 * ���õײ�Ĳ�ѯ�ķ���   �Ѹ��ݽ�ɫ����Ȩ�޵�sql�����ó���  
			 * ����������һ��  ����ѯ��sql���  ��sql��侭����ܴ��������ı��浽map������   
			 * ����ֱ���ó�������  ����ִ������Ժ�  �Ѳ�ѯ������Ȩ�޵Ľ��ֱ����Ȩ�����͵ļ���������
			 */
			list = this.getMap().queryForList("power_space.queryEmpPower", roleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�������յ�Ȩ�޵Ľ��
		return list;
	}
	
	

	
	

}
