package cn.grades.spwd.service;

import cn.grades.domain.Stud;
import cn.grades.domain.User;
import cn.grades.slogin.dao.ILoginDao;
import cn.grades.slogin.dao.LoginDaoJdbc;
import cn.grades.spwd.dao.PwdDaoJdbc;

public class PwdService implements IPwdService {
	private PwdDaoJdbc dao = new PwdDaoJdbc();
	@Override
	public int updatePwd(Stud stud,String newPwd){
   	 
	return dao.updatePwd(stud, newPwd);
  }
	
	
}
