package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.RoleDAOImpl;
import com.topsen.financial.dao.inter.RoleDAO;
import com.topsen.financial.po.Role;
import com.topsen.financial.util.support.dao.bean.PageModel;

public class RoleService {
	//构建数据访问层的对象  用来调用数据访问层中的根据用户名去查找角色的方法  
	private RoleDAO dao = new RoleDAOImpl();
	public PageModel queryByPage(int curPage){
		return dao.queryByPage(curPage);
	}
	public Role queryOne(int roleId){
		return dao.load(roleId);
	}
	public void update(Role role){
		dao.update(role);
	}
	
	public void delete(int roleId){
		dao.delete(roleId);
	}
	public void insert(Role role){
		dao.insert(role);
	}
	/**
	 * 
	 * 根据用户名去查找角色的方法
	 * 
	 * 此方法在services中 代码是根据用户名去查找角色的   构建数据访问层的对象  然后利用此对象把 
	 * 数据访问层的底层的根据用户名去查找角色的方法调用出来  然后把我们为从servlet层的带过来的用户名传递
	 * 进去  然后根据用户名去查询该用户的角色  只有这样的处理才可以实现方法的传递接力性
	 * 
	 *
	 */
	public List<Role> queryEmpRole(String empno){
		return dao.queryEmpRole(empno);
	}
}
