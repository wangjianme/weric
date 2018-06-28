package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.topsen.financial.dao.inter.RoleDAO;
import com.topsen.financial.po.Role;
import com.topsen.financial.util.support.dao.bean.PageModel;

import com.topsen.financial.util.support.dao.page.PageModelFactory;

public class RoleDAOImpl extends RoleDAO{

	public int delete(int roleId) {
		int i = 0;
		try {
			this.getMap().delete("role_space.delete",roleId);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int insert(Role t) {
		int i = 0;
		try {
			this.getMap().insert("role_space.insert",t);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public Role load(int roleId) {
		Role role = null;
		try {
			role = (Role)this.getMap().queryForObject("role_space.one",roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}

	public int update(Role t) {
		int i = 0;
		try {
			this.getMap().update("role_space.update",t);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public PageModel queryByPage(int curPage) {
		return PageModelFactory.getFactory().createPageModel("role_space.queryAll", "role_space.count", curPage);
	}
    /**
     * 数据访问层中的根据用户名去查找角色的方法
     */
	@Override
	public List<Role> queryEmpRole(String empno) {
		//创建一个集合 数据类型为Role类型的  用来接受查询的角色的相关的信息
		List<Role> list = null;
		try {
			/**
			 * 利用map对象调用 底层查询的方法  此方法是根据用户名去查找角色  需要的又是底层的查询的方法
			 * 需要的一个参数是用户名   用户名作为参数传递到查询的方法中   作为sql语句查询的条件
			 * 因为根据用户去查找角色   查询语句得存在  经过我们的分析可以知道  sql语句写在role_sqlmap.xml
			 * 的配置文件中  然后在dbconfig.xml文件中引入此sql语句所在的路径  这样在加载dbconfig.xml配置文件时  
			 * 才会把此sql语句给加载进来  然后以标签标识的形式保存到这个map中   用的时候直接根据 指定这个sql语句的标签即可
			 * 
			 * 
			 * 特此声明：
			 *    queryForList("sql语句的标识","where后面指定条件")
			 *    sql语句的标识：role_space.queryUserRole
			 *    第一个参数为:
			 *          需要查询的sql语句
			 *    第二个参数:sql语句所需要的查询的条件
			 *          
			 *          
			 *    方法执行完毕以后  将查询的角色的结果保存到Role类型的集合中
			 */
			list = this.getMap().queryForList("role_space.queryUserRole",empno);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//返回最终的角色的结果
		return list;
	}

}
