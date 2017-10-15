package cn.grades.pwd.service;

import cn.grades.domain.User;

public interface IPwdService {
	int updatePwd(User user,String newPwd);
}
