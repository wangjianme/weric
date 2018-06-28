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
			this.getMap().executeBatch();//批量执行sql语句的吗？
			this.getMap().commitTransaction();//将数据提交到数据库
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
		//声明一个权限类型的集合  用来接收查询的权限的结果
		List<Power> list = null;
		try {
			/*
			 * 调用底层的查询的方法   把根据角色查找权限的sql语句给拿出来  
			 * 两个参数第一个  被查询的sql语句  此sql语句经过框架处理会虚拟的保存到map对象中   
			 * 所以直接拿出来即可  方法执行完毕以后  把查询出来的权限的结果直接用权限类型的集合来接收
			 */
			list = this.getMap().queryForList("power_space.queryEmpPower", roleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//返回最终的权限的结果
		return list;
	}
	
	

	
	

}
