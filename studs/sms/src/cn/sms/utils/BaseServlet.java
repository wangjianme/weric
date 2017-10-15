package cn.sms.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//1:继承这个类
public abstract class BaseServlet extends HttpServlet {
	// 2:重写service请方法
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1:获取请求的method参数
		String method = req.getParameter("method");
		if (method == null || method.trim().equals("")) {
			method = "execute";
		}
		// 2:通过反射来调用这个方法
		try {
			Method _method = this.getClass().getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
			//调用
			_method.invoke(this, req,resp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 3:添加一个方法，让用户必须要重写这个方法
	public abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
