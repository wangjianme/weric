package com.myexam.teac.security.users.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Teac;
/**
 * 用户接口
 * @author wangjianme
 * @version 1.0,2010-10-4
 */
public interface IUsersService {
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	Map<String,Object> query(String start,String limit);
	/**
	 * 审批
	 * @param teac
	 * @return
	 */
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
	 * 删除用户
	 */
	Map<String, Object> delete(String teacId);
	/**
	 * 
	 */
	Map<String,Object> changePwd(String sid,String newpwd);
}
