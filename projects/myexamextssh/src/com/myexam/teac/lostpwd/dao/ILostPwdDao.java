package com.myexam.teac.lostpwd.dao;

import java.util.Map;

/**
 * 忘记密码
 * @author wangjianme
 * @version 1.0,2010-12-7
 */
public interface ILostPwdDao {
	/**
	 * 根据提供的用户名查询出密码提示问题
	 */
	Map<String,Object> queryQues(String name); 
	
	/**
	 * 更新操作
	 */
	void update(Map<String,Object> param);
}
