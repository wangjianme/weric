package cn.sms.test;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import cn.sms.core.DataSourceUtils;
import cn.sms.domain.User;
import cn.sms.login.dao.LoginDao;
import cn.sms.utils.MD5Utils;
import cn.sms.utils.QueryRunner;

public class Demo01 {
	@Test
	public void test1() {
		String salt = UUID.randomUUID().toString().replace("-", "");
		String pwd = "1234";
		String pwd2 = MD5Utils.password(pwd, salt);
		System.err.println(pwd2 + "," + salt);
	}

	@Test
	public void testDBUtils() {
		QueryRunner run = new QueryRunner(DataSourceUtils.getDataSource());
		run.update("insert into users(user_id,user_name) values(?,?)", "U003", "张三2");
	}

	@Test
	public void testDBUtils2() throws Exception {
		QueryRunner run = new QueryRunner(DataSourceUtils.getDataSource());
		List<User> list = run.query("select user_id as id,user_name as name from users",
				new BeanListHandler<User>(User.class));
		System.err.println(list);
	}

	@Test
	public void testLogin() {
		User user = new User();
		user.setName("Jack");
		user.setPwd("1234");
		user = new LoginDao().login(user);
		System.err.println("user is:" + user);
	}

	@Test
	public void teest2() {
		String pattern = "/([a-z0-9/]+)\\.html";
		String uri = "/manager/main.html";
		String toUri ="/WEB-INF/views/$1.jsp";
		

		Pattern p = Pattern.compile(pattern);// 声明正则的对象
		Matcher matcher = p.matcher(uri);// 获取匹配的对象
		boolean boo = matcher.matches();
		System.err.println(boo);
		if (boo) {
			matcher.reset();//重新回到下标0
			if (matcher.find()) {
				String str = matcher.group(1);
				System.err.println(str);
				String to = toUri.replace("$1", str);
				System.err.println(to);
			}
		}
	}
}
