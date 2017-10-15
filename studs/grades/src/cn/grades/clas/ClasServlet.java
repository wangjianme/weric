package cn.grades.clas;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONObject;

import cn.grades.clas.service.ClasService;
import cn.grades.clas.service.IClasService;
import cn.grades.core.BaseServlet;
import cn.grades.domain.Clas;
import cn.grades.domain.Department;
import cn.grades.domain.Grade;
import cn.grades.domain.Major;

@WebServlet(urlPatterns = "/manager/clas")
public class ClasServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	IClasService service = new ClasService();

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String _page = req.getParameter("page");
		String _rows = req.getParameter("rows");
		int page = Integer.parseInt(_page);
		int rows = Integer.parseInt(_rows);
		Map<String, Object> map = service.query(page, rows);
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}

	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Clas clas = new Clas();
		BeanUtils.populate(clas, req.getParameterMap());
		clas = new ClasService().save(clas);
		String json = JSONObject.toJSONString(clas);
		System.out.println(json);
		resp.getWriter().print(json);
	}

	// 下拉框中获取所属学院的值的方法
	public void dept(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		IClasService service = new ClasService();
		List<Department> list = service.text();

		String json = JSONObject.toJSONString(list);
		// System.out.println(json);
		resp.getWriter().print(json);
	}

	// 下拉框中获取专业的值的方法
	public void major(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Major> list = service.majorVal();
		// System.out.println(list);
		String json = JSONObject.toJSONString(list);
		resp.getWriter().print(json);
	}

	// 下拉框中获取班级的值的方法
	public void grade(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Grade> list = service.name();
		// System.out.println(list);
		String json = JSONObject.toJSONString(list);
		resp.getWriter().print(json);
	}

	/**
	 * 以下是删除
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		int rows = service.delete(id);
		response.getWriter().print(rows);// 1,0
	}

	/* 以下是修改 */
	public void update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		if (id == null || id.equals("")) {
			System.err.println("----");
		} else {
			Clas clas = new Clas();
			BeanUtils.populate(clas, req.getParameterMap());
			int rows = new ClasService().update(clas);
			resp.getWriter().print(rows);
		}
	}
	/*以下是查询*/
	public void select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String rows = req.getParameter("rows");
		String page = req.getParameter("page");
		int pag=0;
		int row=0;
		if (page!= null && page.matches("\\d+")) {
			 pag = Integer.parseInt(page);
		}
		if (rows!= null && rows.matches("\\d+")) {
			 row = Integer.parseInt(rows);
		}
		//BeanUtils.populate(Teacher.class, req.getParameterMap());
		Clas clas = new Clas();
				BeanUtils.populate(clas, req.getParameterMap());
		System.out.println("要查询的姓名是："+clas.getName());
		System.out.println("要查询的學院是："+clas.getDeptid());
	
	
		Map<String, Object> map = new ClasService().select(clas,row,pag );
		System.out.println("00m:"+map);
		String json = JSONObject.toJSONString(map);
		System.out.println("json"+json);
		resp.getWriter().print(json);
		System.out.println(111);
	}
}
