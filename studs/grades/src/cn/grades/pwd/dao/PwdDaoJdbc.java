package cn.grades.pwd.dao;

import cn.grades.core.BaseDao;
import cn.grades.core.Md5Utils;
import cn.grades.domain.User;

public class PwdDaoJdbc extends BaseDao implements IPwdDao {

	@Override
	public int updatePwd(User user, String newPwd) {
		// 对新密码进行md5
		String newPwdMd5 = Md5Utils.md5(newPwd, user.getSalt());
		System.err.println("mysql-pwd:-----------------"+newPwdMd5);

		String sql = "update users set user_pwd=? where user_id=?";
		int rows // 修改了几行
		= run.update(sql, newPwdMd5, user.getId());
		System.err.println("rows:---------------------"+rows);
		return rows;
	}

}
