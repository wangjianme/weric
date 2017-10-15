package cn.grades.core.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.grades.core.DSUtils;
import cn.grades.core.QueryRunner;
import cn.grades.domain.User;
import cn.grades.major.service.IMajorService;
import cn.grades.menu.service.IMenuService;
import cn.grades.menu.service.MenuService;

public class AuthFilter implements Filter {
	private IMenuService menuService = new MenuService();
	private Map<String, List<String>> map;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		map = menuService.menuUser();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 1:获取userid
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession s = req.getSession();
		User user = (User) s.getAttribute("user");
		String userid = user.getId();
		// 2：获取url
		String uri = req.getRequestURI();// /grades/manager/user.html
		uri = uri.replace(req.getContextPath() + "/", "");
		if (map.containsKey(uri)) {// 如果包含这个url,则查看是否有权限
			List<String> userids = map.get(uri);
			if (userids.contains(userid)) {
				chain.doFilter(request, response);
			} else {
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.sendRedirect(req.getContextPath() + "/htmls/denied.html");
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
	}
}
