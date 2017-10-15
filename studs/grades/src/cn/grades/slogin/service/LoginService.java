package cn.grades.slogin.service;

import java.util.List;

import cn.grades.domain.Score;
import cn.grades.domain.Stud;
import cn.grades.domain.User;
import cn.grades.slogin.dao.ILoginDao;
import cn.grades.slogin.dao.LoginDaoJdbc;

public class LoginService implements ILoginService {
	private ILoginDao dao = new LoginDaoJdbc();
	public Stud login1(Stud stud){
		return dao.login1(stud);
	}
	public List<Stud> query(String id){
		return dao.query(id);
	}
	public List<Score> queryscore(String id,Score s){
		return dao.queryscore(id,s);
	}
}
