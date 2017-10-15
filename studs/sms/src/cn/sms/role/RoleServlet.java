package cn.sms.role;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.sms.domain.Menu;
import cn.sms.domain.Role;
import cn.sms.menu.service.MenuService;
import cn.sms.role.service.RoleService;
import cn.sms.utils.BaseServlet;

@WebServlet(urlPatterns = "/manager/role")
public class RoleServlet extends BaseServlet {
	private RoleService service = new RoleService();
	private MenuService menuService = new MenuService();

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> map = service.query();
		// 转成jdon
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}

	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String name = req.getParameter("name");
		Role role = new Role();
		role.setName(name);
		role = service.save(role);
		String json = JSONObject.toJSONString(role);
		resp.getWriter().print(json);
	}

	public void del(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		int a = service.delete(id);
		resp.getWriter().print(a);
	}
	
	public void menu(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");//接收的是父菜单的id
		List<Menu> list = 
				menuService.query(id);
		String json = JSONArray.toJSONString(list);
		resp.getWriter().print(json);
	}
	public void saveMenu(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String roleId = req.getParameter("roleId");
		String[] menuIds = req.getParameterValues("id");
		service.saveMenu(menuIds, roleId);
	}
	

}
