package cn.sms.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sms.domain.User;
import cn.sms.login.service.LoginService;

public class LoginServlet extends HttpServlet {
	/**
	 * <form method="post"> 一定会请求doPost方法
	 * 
	 */
	// 表单数据，所以，必须要使用doPost
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String pwd = req.getParameter("pwd");
		// 获取验证码-用户输入的
		String sCode = req.getParameter("sCode");
		// 从Session中再获取图片验证码
		String code = (String) req.getSession().getAttribute("code");
		PrintWriter out = resp.getWriter();
		if (sCode.equals(code)) {
			// 封装
			User user = new User();
			user.setName(name);
			user.setPwd(pwd);
			// 调用Service
			user = new LoginService().login(user);
			if (user == null) {
				out.print("0");
			} else {
				// 登录成功
				req.getSession().setAttribute("user", user);
				out.print("1");
			}
		} else {
			out.print("2");// 验证码不正确
		}
	}

	/**
	 * 如果用户提交是get请求，则执行这边的doGet方法
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO:请大家在这儿开发退出功能：
		req.getSession().removeAttribute("user");
		resp.sendRedirect(req.getContextPath() + "/index.html");
	}
}
