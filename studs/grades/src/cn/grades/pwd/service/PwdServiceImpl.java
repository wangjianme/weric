package cn.grades.pwd.service;

import cn.grades.domain.User;
import cn.grades.pwd.dao.IPwdDao;
import cn.grades.pwd.dao.PwdDaoJdbc;


public class PwdServiceImpl implements IPwdService {
	
	IPwdDao dao=new PwdDaoJdbc(); 
	public int updatePwd(User user,String newPwd){
		return dao.updatePwd(user, newPwd);
	}

}
