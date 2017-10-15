package cn.sms.login.service;

import cn.sms.domain.User;
import cn.sms.login.dao.LoginDao;

public class LoginService {
	private LoginDao dao = new LoginDao();
	public User login(User user){
		return dao.login(user);
	}
}
