package com.myexam.teac.security.role.service;

import java.util.List;
import java.util.Map;

import com.myexam.teac.security.role.dao.IRoleDao;
/**
 * 角色管理
 * @author wangjianme
 * @version 1.0,2010-9-29
 */
public class RoleServiceImpl implements IRoleService{
	private IRoleDao dao;
	public IRoleDao getDao() {
		return dao;
	}
	public void setDao(IRoleDao dao) {
		this.dao = dao;
	}
	public List<Map<String, Object>> query() {
		return getDao().query();
	}
	public Map<String, Object> save(Map<String, Object> params) {
		return getDao().save(params);
	}
	public boolean del(String roleId) {
		return getDao().del(roleId);
	}
}
