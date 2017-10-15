package cn.sms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 验证用户是否登录的过虑器
 */
public class LoginFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 初始化方法，只会执行一次，由容器调用,Tomcat
	}

	/***
	 * 多次执行的执行一个方法
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 1:获取用户的信息，是否在session
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession s = req.getSession();
		if (s.getAttribute("user") == null) {
			System.err.println("用户没有登录");
			// 去登录页面显示
			HttpServletResponse resp = (HttpServletResponse) response;
			// 重定向到登录页面/sms/
			s.setAttribute("error", "1");
			resp.sendRedirect(req.getContextPath() + "/index.html");
		} else {// 否则已经登录了,,放行
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// 销毁方法，只会执行一次
	}

}
