package cn.grades.pwd.dao;

import cn.grades.domain.User;

public interface IPwdDao {

	/**
	 * 增加一个方法，用于修改密码
	 */
	int updatePwd(User user, String newPwd);



}