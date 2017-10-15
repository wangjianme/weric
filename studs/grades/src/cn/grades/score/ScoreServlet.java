package cn.grades.score;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.domain.Course;
import cn.grades.domain.Grade;
import cn.grades.domain.Score;
import cn.grades.domain.Stud;
import cn.grades.grade.service.GradeService;
import cn.grades.major.service.MajorService;
import cn.grades.score.service.IScoreService;
import cn.grades.score.service.ScoreService;

@WebServlet(urlPatterns = "/manager/score")
public class ScoreServlet extends 	BaseServlet {
	private static final long serialVersionUID = 1L;

	private IScoreService service = new ScoreService();


	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String _page = req.getParameter("page");
		String _rows = req.getParameter("rows");
		
		System.out.println("=======================>"+_page+_rows);
		
		int page = Integer.parseInt(_page);
		int rows = Integer.parseInt(_rows);
		Map<String, Object> map = service.query(page, rows);
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}

	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Score s= BeanUtils.populate(Score.class, req.getParameterMap());
		service.save(s);
		String json = JSONObject.toJSONString(s);
		resp.getWriter().print(json);
	}
	public void query1(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Stud> s=service.query1();
		String json = JSONObject.toJSONString(s);
		resp.getWriter().print(json);
	}
	public void query2(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//Score s= BeanUtils.populate(Score.class, req.getParameterMap());
		List<Course> s=service.query2();
		String json = JSONObject.toJSONString(s);
		System.out.println(json);
		resp.getWriter().print(json);
	}
	public void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		int rows = new ScoreService().delete(id);
		resp.getWriter().print(rows);
	}

	public void update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		System.out.println("====="+id);
	
			System.out.println("1");                    
			Score score = BeanUtils.populate(Score.class, req.getParameterMap());
			
			
			int rows = new ScoreService().update(score);
			resp.getWriter().print(rows);
		
	}

public void select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	//String _rows = req.getParameter("rows");
	//String _page = req.getParameter("page");
	
	
	//System.out.println(_rows+_page);
	
	//int row = Integer.parseInt(_rows);
	//int pag = Integer.parseInt(_page);
	//int  row =10;
	//int  pag=1;
	
	
	Score score= BeanUtils.populate(Score.class, req.getParameterMap());
	
	System.out.println("nnnn"+score.getCourseid());
	System.out.println("dddd"+score.getDatetime());
	System.out.println("tttt"+score.getTerm());
	System.out.println("score"+score);
	
	
	
	Map<String, Object> map = new ScoreService().select(score);
	String json = JSONObject.toJSONString(map);
	
	
	System.out.println("json===========>"+json);
	
	
	resp.getWriter().print(json);
}
}
