package cn.grades.grade;

import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.domain.Grade;
import cn.grades.grade.service.GradeService;

@WebServlet("/manager/grade")
public class GradeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String rows = req.getParameter("rows");
		String page = req.getParameter("page");
		int row = Integer.parseInt(rows);
		int pag = Integer.parseInt(page);
		Map<String, Object> map = new GradeService().query(row, pag);
		String json = JSONObject.toJSONString(map);
		System.out.println("已有专业:" + json);
		resp.getWriter().print(json);
	}
	public void selectGrade(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Grade> list=new GradeService().selectGrade();
		String json = JSONObject.toJSONString(list);
		System.out.println("已有班级:" + json);
		resp.getWriter().print(json);
	}
	public void select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String rows = req.getParameter("rows");
		String page = req.getParameter("page");
		int row = Integer.parseInt(rows);
		int pag = Integer.parseInt(page);
		Grade grade = BeanUtils.populate(Grade.class, req.getParameterMap());
		System.out.println("nnnn"+grade.getName());
		System.out.println("dddd"+grade.getDsc());
		System.out.println("tttt"+grade.getDt());
		System.out.println("grade"+grade);
		Map<String, Object> map = new GradeService().select(grade,row,pag );
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}

	public void del(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		int rows = new GradeService().delete(id);
		resp.getWriter().print(rows);
	}

	public void update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		System.out.println("====="+id);
		if (id == null||id.equals("")) {
			add(req, resp);
		} else {
			System.out.println("1");
			Grade grade = BeanUtils.populate(Grade.class, req.getParameterMap());
			int rows = new GradeService().update(grade);
			resp.getWriter().print(rows);
		}
	}

	public void add(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Grade grade = BeanUtils.populate(Grade.class, req.getParameterMap());
		System.out.println("0000" + grade.getName() + grade.getDsc() + grade.getDt());
		int rows = new GradeService().add(grade);
		resp.getWriter().print(rows);
	}

}
