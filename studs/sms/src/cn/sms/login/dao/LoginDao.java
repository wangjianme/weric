package cn.sms.login.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.sms.core.BaseDao;
import cn.sms.domain.User;
import cn.sms.utils.MD5Utils;

/**
 * 实现登录的功能，就接收User对象，查询数据库
 */
public class LoginDao extends BaseDao {
	public User login(User user) {
		// 1:根据name查询这个user
		String sql = "select user_id as id,user_name as name," + "user_pwd as pwd,user_salt as salt,"
				+ "user_sex as sex from users where user_name=?";
		// 直接查询
		User user2 = run.query(sql, new BeanHandler<User>(User.class), user.getName());
		if(user2==null){//用户名不存在
			return null;
		}else{
			String salt = user2.getSalt();
			//获取用户真实的密码
			String pwd = user.getPwd();
			//做md5
			String md5Pwd = MD5Utils.password(pwd, salt);
			//比较
			if(md5Pwd.equals(user2.getPwd())){
				return user2;//登录成功了
			}else{
				return null;
			}
		}
	}
}
