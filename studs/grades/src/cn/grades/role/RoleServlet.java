package cn.grades.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.grades.core.BaseServlet;
import cn.grades.domain.Role;
import cn.grades.menu.service.MenuService;
import cn.grades.role.service.IRoleService;
import cn.grades.role.service.RoleServiceImpl;
import cn.grades.domain.Menu;

@WebServlet(urlPatterns = "/manager/role")
public class RoleServlet extends BaseServlet {
	private IRoleService service = new RoleServiceImpl();
    private MenuService menuservice = new MenuService();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String _page = req.getParameter("page");
		String _rows = req.getParameter("rows");
		System.out.println("-----------" + _page + "--------" + _rows);
		int page = Integer.parseInt(_page);
		int rows = Integer.parseInt(_rows);
		Map<String, Object> map = service.query(page, rows);
		// 返回值封装成json
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

	public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Role r = new Role();
		BeanUtils.populate(r, request.getParameterMap());

		System.err.println("rrrrrrrrrrrrrrMap" + request.getParameterMap());
		int rows = service.update(r);
		Map<String, Object> result = new HashMap<>();// 声明 集合，用于确定是否修改成功
		if (rows > 0) {
			result.put("success", true);
			result.put("role", r);
			System.err.println("修改成功。。。。。");
		} else {
			result.put("success", false);
			System.err.println("修改失败。。。。");
		}
		String json = JSONObject.toJSONString(result);
		response.getWriter().print(json);
	}
	//显示关系
	public void menu(HttpServletRequest req, HttpServletResponse resp) throws Exception  {
		String id =req.getParameter("id");
		List<Menu> list =menuservice.query(id);
		String json =JSONArray.toJSONString(list);
		resp.getWriter().print(json);
		System.out.println("输出一下"+id);
	}

	public void saveMenu(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String roleId =req.getParameter("roleId");
		String[] menuIds =req.getParameterValues("id");
		service.saveMenu(menuIds, roleId);
	}

}
