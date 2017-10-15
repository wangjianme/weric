package com.myexam.teac.security.users.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Teac;
import com.myexam.teac.security.users.dao.IUsersDao;
/**
 * 用户管理
 * @author wangjianme
 * @version 1.0,2010-10-3
 */
public class UsersServiceImpl implements IUsersService{
	private IUsersDao dao;
	public IUsersDao getDao() {
		return dao;
	}
	public void setDao(IUsersDao dao) {
		this.dao = dao;
	}
	/**
	 * 查询全部用户,分页
	 */
	public Map<String,Object> query(String start,String limit) {
		return getDao().query(start,limit);
	}
	public Map<String, Object> agree(Teac teac) {
		return getDao().agree(teac);
	}
	public List<Map<String, Object>> queryRoles(String teacId) {
		return getDao().queryRoles(teacId);
	}
	public Map<String, Object> saveRole(String teacId, String roles) {
		return getDao().saveRole(teacId, roles);
	}
	public Map<String, Object> delete(String teacId) {
		return getDao().delete(teacId);
	}
	public Map<String, Object> changePwd(String sid, String newpwd) {
		return getDao().changePwd(sid, newpwd);
	}
}
