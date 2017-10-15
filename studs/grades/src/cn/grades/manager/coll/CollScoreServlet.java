package cn.grades.manager.coll;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;

import cn.grades.core.BaseServlet;
import cn.grades.course.service.CourseService;
import cn.grades.domain.CollScore;
import cn.grades.domain.Degrees;
import cn.grades.domain.Department;
import cn.grades.domain.Major;
import cn.grades.manager.coll.service.CollScoreService;
import cn.grades.manager.coll.service.ICollScoreService;

/**
 * Servlet implementation class ScoreServlet
 */
@WebServlet("/manager/coll/score")
public class CollScoreServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	ICollScoreService service = new CollScoreService();

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//List<CollScore> lists = new CollScoreService().selectDept();
		//List<String> list = new ArrayList<>();
		//for (CollScore t : lists) {
	//		list.add(t.getName());
		//}
		//String json = JSONObject.toJSONString(list);
		//System.out.println("=学院=" + json);
		//resp.getWriter().print(json);
		List<Major> list=new CourseService().selectMajor(req.getParameter("deptid"));
		String json = JSONObject.toJSONString(list);
		//System.out.println(json);
		resp.getWriter().print(json);
	}

	

	
	//联动
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
	
	//以上联动
	// 下面是报表方法

	public void titleSelect(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String id = req.getParameter("id");
		List<Map<String, Object>> list = new CollScoreService().titleSelect(id);
		String json = JSONObject.toJSONString(list);
		System.out.println("--title-1-" + json);
		resp.getWriter().print(json);
	}

	

}
