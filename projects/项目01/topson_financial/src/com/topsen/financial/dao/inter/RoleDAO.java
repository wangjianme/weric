package com.topsen.financial.dao.inter;

import java.util.List;

import com.topsen.financial.dao.BaseDAO;
import com.topsen.financial.po.Emploee;
import com.topsen.financial.po.Role;
import com.topsen.financial.util.support.dao.page.PageableDAO;

public abstract class RoleDAO extends BaseDAO implements PageableDAO<Role>{
	public abstract List<Role> queryEmpRole(String empno);
	public List<Role> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
