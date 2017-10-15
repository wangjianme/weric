package cn.sms.user.service;

import java.util.Map;

import cn.sms.domain.User;
import cn.sms.user.dao.UserDao;

public class UserService {
	private UserDao dao = new UserDao();
	public Map<String, Object> query(int rows,int page){
		return dao.query(rows,page);
	}
	public int updatePwd(User user,String newPwd){
		return dao.updatePwd(user, newPwd);
	}
	
	public User save(User user){
		return dao.save(user);
	}
}
