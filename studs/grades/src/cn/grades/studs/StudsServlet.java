package cn.grades.studs;

import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.domain.Clas;
import cn.grades.domain.Stud;
import cn.grades.studs.service.IStudsService;
import cn.grades.studs.service.StudsService;

import java.util.List;
import java.util.Map;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONObject;
@WebServlet(urlPatterns = "/studs")
public class StudsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private IStudsService service = new StudsService();
    

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
		Stud s = BeanUtils.populate(Stud.class, req.getParameterMap());
		service.save(s);
		String json = JSONObject.toJSONString(s);
		resp.getWriter().print(json);
	}
	
	public void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		int rows = service.delete(id);
		resp.getWriter().print(rows);
	}
	public void update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		 Stud s=BeanUtils.populate(Stud.class, req.getParameterMap());
		 int rows=service.update(s);
		 //System.err.println("11111"+req.getParameterMap());
		 if(rows==1){
			 String json = JSONObject.toJSONString(s);
		     resp.getWriter().print(json);
	          System.err.println("修改成功了");
		 }else{
			 resp.getWriter().print("0");
		 }
	}
	public void selectClass(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Clas> list=new StudsService().selectClass();
		String json = JSONObject.toJSONString(list);
		System.out.println(json);
		resp.getWriter().print(json);
	}
	public void select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Stud s  = BeanUtils.populate(Stud.class, req.getParameterMap());
		//String clasid=(String) req.getParameter("class");
		//System.err.println(clasid);
		Map<String, Object> map = new StudsService().select(s);
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}

}
