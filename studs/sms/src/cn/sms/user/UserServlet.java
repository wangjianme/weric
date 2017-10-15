package cn.sms.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONObject;

import cn.sms.domain.User;
import cn.sms.user.service.UserService;
import cn.sms.utils.BaseServlet;
import cn.sms.utils.MD5Utils;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends BaseServlet {
	// http://localhost:8080/sms/user
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String rows = req.getParameter("rows");
		String page = req.getParameter("page");
		int _rows = Integer.parseInt(rows);
		int _page = Integer.parseInt(page);
		Map<String, Object> map = new UserService().query(_rows, _page);
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}

	// http://localhost:8080/sms/user?method=pwd
	public void pwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 从Session中获取user用户
		User user = (User) req.getSession().getAttribute("user");
		// 获取参数
		String oldPwd = req.getParameter("pwd");
		String newPwd = req.getParameter("newPwd");
		// 比较旧密码
		String oldPwdMd5 = MD5Utils.password(oldPwd, user.getSalt());
		// 声明写出数据的c对象
		PrintWriter out = resp.getWriter();
		if (oldPwdMd5.equals(user.getPwd())) {
			// 就去修改密码
			int effects = new UserService().updatePwd(user, newPwd);
			// 直接将修改的行数返回就可以了
			out.print("" + effects);
		} else {
			out.print("0");// 修改不成功
		}
	}
	
	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		//1:获取所有参数，封装成User对象
		//String name = req.getParameter("name");
		//User user = new User();
		//user.setName(name);//使用内技术直接实现封装
		//直接封装完成
		User user = new User();
		BeanUtils.populate(user, req.getParameterMap());
		user = new UserService().save(user);
		//将user转成json
		String json = JSONObject.toJSONString(user);
		System.err.println("保存成功了："+json);
		resp.getWriter().print(json);
	}
}
