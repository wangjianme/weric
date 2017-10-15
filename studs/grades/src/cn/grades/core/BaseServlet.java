package cn.grades.core;


import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 抽象类，不能配置到web.xml中
 */
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 252421034763498718L;
	/**
	 * 直接重写service方法，避免httpServlet分发
	 */
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收一个参数，用于执行用户指定的方法
		String methodName = req.getParameter("cmd");
		if (methodName == null || methodName.trim().equals("")) {
			methodName = "execute";
		}
		// 通过反射来调用子类的方法
		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, req, resp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 要求子类，必须要拥有一个默认的方法
	 */
	public abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
