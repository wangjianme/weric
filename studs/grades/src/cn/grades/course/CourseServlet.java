package cn.grades.course;

import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.course.service.CourseService;
import cn.grades.course.service.ICourseService;
import cn.grades.domain.Course;
import cn.grades.domain.Grade;
import cn.grades.domain.Major;

@WebServlet(urlPatterns = "/manager/course")
public class CourseServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private ICourseService service = new CourseService();

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String _page = req.getParameter("page");
		String _rows = req.getParameter("rows");
		int page = Integer.parseInt(_page);
		int rows = Integer.parseInt(_rows);
		Map<String, Object> map = service.query(page, rows);
		String json = JSONObject.toJSONString(map);
		System.out.println("===" + json);
		resp.getWriter().print(json);
	}
	public void selectMajor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Major> list=new CourseService().selectMajor(req.getParameter("deptid"));
		String json = JSONObject.toJSONString(list);
		System.out.println(json);
		resp.getWriter().print(json);
	}
	public void selectGrade(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Major> list=new CourseService().selectGrade();
		String json = JSONObject.toJSONString(list);
		System.out.println(json);
		resp.getWriter().print(json);
	}
	public void addCourse(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Course course = BeanUtils.populate(Course.class, req.getParameterMap());
		int row=new CourseService().addCourse(course);
		resp.getWriter().print(row);
	}
	public void search(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Course course = BeanUtils.populate(Course.class, req.getParameterMap());
		Map<String, Object> map=new CourseService().search(course);
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}
	public void selectmg_id(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Course course = BeanUtils.populate(Course.class, req.getParameterMap());
		String  row=new CourseService().selectmg_id(course);
		resp.getWriter().print(row);
	}
	public void update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Course course = BeanUtils.populate(Course.class, req.getParameterMap());
		if (course.getSign()==null) {
			//修改
			System.out.println(1);
			int  row=new CourseService().update(course);
			resp.getWriter().print(row);
			
		}else {
			System.out.println(2);
			//System.out.println("<>"+course.getSign());
			//增加
			int  row=new CourseService().add(course);
			resp.getWriter().print(row);
		}
		
	}
	public void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id=req.getParameter("id");
		System.out.println("id000:"+id);
		int row=new CourseService().delete(id);
		resp.getWriter().print(row);
	}
	public void selectparent_id(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id=req.getParameter("id");
		System.out.println("id111:"+id);
		Map<String ,String> map=new CourseService().selectparent_id(id);
		String  json=JSONObject.toJSONString(map);
		System.out.println("json:"+json);
		resp.getWriter().print(json);
	}

}