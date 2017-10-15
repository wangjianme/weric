package cn.grades.login.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.grades.core.BaseDao;
import cn.grades.core.Md5Utils;
import cn.grades.domain.Stud;
import cn.grades.domain.User;

public class LoginDaoJdbc extends BaseDao implements ILoginDao {
	public User login(User user){
		String sql = "select user_id as id,user_name as name,user_salt as salt,user_pwd as pwd"
				+ " from users where user_name=?";
		User loginUser = run.query(sql, new BeanHandler<User>(User.class),user.getName());
		if(loginUser!=null){
			//比较密码
			user.setPwd(Md5Utils.md5(user.getPwd(),loginUser.getSalt()));
			if(user.getPwd().equals(loginUser.getPwd())){
				return loginUser;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
/*	public Stud login1(Stud stud){
		String sql = "select stud_id as id,stud_name as name,stud_salt as salt,stud_pwd as pwd"
				+ " from studs where stud_name=?";
		Stud loginStud= run.query(sql, new BeanHandler<Stud>(Stud.class),stud.getName());
		if(loginStud!=null){
			//比较密码
			stud.setPwd(Md5Utils.md5(stud.getPwd(),loginStud.getSalt()));
			if(stud.getPwd().equals(loginStud.getPwd())){
				return loginStud;
			}
		}
		return loginStud;
	}*/
}
