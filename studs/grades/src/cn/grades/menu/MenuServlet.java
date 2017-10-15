package cn.grades.menu;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;

import cn.grades.core.BaseServlet;
import cn.grades.domain.Menu;
import cn.grades.domain.User;
import cn.grades.menu.service.IMenuService;
import cn.grades.menu.service.MenuService;
import sun.print.resources.serviceui;
@WebServlet(urlPatterns="/manager/menu")
public class MenuServlet extends BaseServlet {
	private IMenuService service = new MenuService();

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1：获取用户的id
		User user = (User) req.getSession().getAttribute("user");
		String uid = user.getId();
		//2:获取菜单 的id
		String parent = req.getParameter("id");
		List<Menu> list =service.query(uid, parent);
		//查询以后转成json
		String json =  JSONArray.toJSONString(list);
		resp.getWriter().print(json);
	}

}
