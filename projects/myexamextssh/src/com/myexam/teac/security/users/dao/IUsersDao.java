package com.myexam.teac.security.users.dao;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Teac;
/**
 * 用户管理
 * @author wangjianme
 * @version 1.0,2010-10-3
 */
public interface IUsersDao {
	Map<String,Object> query(String start,String limit);
	Map<String,Object> agree(Teac teac);
	/**
	 * 查询所有角色,同时确认是否为选中状态
	 */
	List<Map<String,Object>> queryRoles(String teacId);
	/**
	 * 保存用户对应的角色
	 */
	Map<String,Object> saveRole(String teacId,String roles);
	/**
	 * 彻底删除某个用户的所有信息
	 */
	Map<String,Object> delete(String teacId);
	/**
	 * 修改密码功能
	 */
	Map<String,Object> changePwd(String sid,String newpwd);
}
