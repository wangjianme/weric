package cn.sms.menu;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jcp.xml.dsig.internal.dom.DOMPGPData;

import com.alibaba.fastjson.JSONArray;

import cn.sms.domain.Menu;
import cn.sms.domain.User;
import cn.sms.menu.service.MenuService;

public class MenuServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1:从哪儿获取用户的id
		HttpSession s = req.getSession();
		User user = (User) s.getAttribute("user");
		String uid = user.getId();
		// 2:获取查询
		String id = req.getParameter("id");
		// 直接去查询
		List<Menu> list = new MenuService().query(uid, id);
		// 将List转在json串
		String json = JSONArray.toJSONString(list);
		// 直接就显示
		resp.getWriter().print(json);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	};
}
