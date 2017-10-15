package cn.sms.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.sms.core.BaseDao;
import cn.sms.domain.User;
import cn.sms.utils.MD5Utils;

public class UserDao extends BaseDao {
	/**
	 * 查询所有用户封装返回
	 */
	public Map<String, Object> query(int rows, int page) {
		// 计算start
		int start = (page - 1) * rows + 1;
		int end = start + rows;
		// 1:先声明一个
		Map<String, Object> map = new HashMap<String, Object>();
		// 2:查询里面有多少行
		String sql = "select count(1) from  users";
		Object obj = run.query(sql, new ScalarHandler<Number>());// ScalarHandler返回一行一列的结果
		// 放到map
		map.put("total", obj);
		// 3:查询所有用户
		sql = "select * from (select rownum as n,user_id as id,user_name as name,user_pwd as pwd,user_salt as salt,"
				+ "user_sex as sex from users) where n>=" + start + " and n<" + end;
		List<User> list = run.query(sql, new BeanListHandler<User>(User.class));
		map.put("rows", list);
		return map;
	}
	
	/**
	 * 增加一个方法，用于修改密码
	 */
	public int updatePwd(User user, String newPwd) {
		// 对新密码进行md5
		String newPwdMd5 = MD5Utils.password(newPwd, user.getSalt());
		String sql = "update users set user_pwd=? where user_id=?";
		int rows // 修改了几行
		= run.update(sql, newPwdMd5, user.getId());
		return rows;
	}
	//保存
	public User save(User user){
		//1:设置id - 32  kdkdkdkd-kdkdkd-kdkdk-8383
		user.setId(UUID.randomUUID().toString().replace("-",""));
		user.setSalt(UUID.randomUUID().toString().replace("-",""));
		user.setPwd(MD5Utils.password(user.getPwd(), user.getSalt()));
		String sql = "insert into users(user_id,user_name,user_pwd,user_salt,user_sex)"
				+ " values(?,?,?,?,?)";
		run.update(sql,user.getId(),user.getName(),user.getPwd(),user.getSalt(),user.getSex());
		return user;
	}
}
