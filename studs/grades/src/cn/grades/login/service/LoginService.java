package cn.grades.login.service;

import cn.grades.domain.User;
import cn.grades.login.dao.ILoginDao;
import cn.grades.login.dao.LoginDaoJdbc;

public class LoginService implements ILoginService {
	private ILoginDao dao = new LoginDaoJdbc();
	public User login(User user){
		return dao.login(user);
	}
}
