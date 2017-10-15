package cn.grades.dept;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONArray;

import cn.grades.core.BaseServlet;
import cn.grades.dept.service.DeptServiceImpl;
import cn.grades.dept.service.IDeptService;
import cn.grades.domain.Department;

@WebServlet(urlPatterns = "/manager/dept/college")
public class DeptServlet extends BaseServlet {
	private IDeptService service = new DeptServiceImpl();

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String parent = req.getParameter("id");
		// System.out.println(parent);
		int page = 1;
		int rows = 1;
		String _page = req.getParameter("page");
		if (_page != null && _page.matches("\\d+")) {
		page = Integer.parseInt(_page);
		}
		String _rows = req.getParameter("rows");
		if (_rows != null && _rows.matches("\\d+")) {
		rows = Integer.parseInt(_rows);
		}
		String json = service.query(parent, page, rows);
		//String json = JSONArray.toJSONString(map);
		//System.out.println("json>>>>" + json);
		resp.getWriter().print(json);
	}
	
	public void combotree(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String parent = req.getParameter("id");
		List<Department> list =service.combotree(parent);
		String json = JSONArray.toJSONString(list);
		System.out.println(json);
		resp.getWriter().print(json);
	}
	
	public void combobox(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String boo = req.getParameter("isdept");
		System.out.println(boo);
		List<Department> list =service.combobox(boo);
		String json = JSONArray.toJSONString(list);
		System.out.println(json);
		resp.getWriter().print(json);
	}

	public void del(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		System.out.println(id);
		int row = service.delete(id);
		PrintWriter out = resp.getWriter();
		if (row >= 1) {
			out.print("1");
		} else {
			out.print("-1");
		}
	}

	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Department c = new Department();
		BeanUtils.populate(c, req.getParameterMap());
		c = service.save(c);
		String json = JSONArray.toJSONString(c);
		resp.getWriter().print(json);
	}

	public void upd(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Department c = new Department();
		BeanUtils.populate(c, req.getParameterMap());
		int row = service.update(c);
		PrintWriter out = resp.getWriter();
		if (row == 1) {
			out.print("1");
		} else {
			out.print("-1");
		}
	}
	
	public void search(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Department dept = new Department();
		String _parent = req.getParameter("parent");
		BeanUtils.populate(dept, req.getParameterMap());
		if(_parent.equals("全部")){
			dept.setParent(null);
		}
		List<Department> list =service.search(dept);
		String json = JSONArray.toJSONString(list);
		System.out.println(json);
		resp.getWriter().print(json);
	}

}
