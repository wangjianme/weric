package cn.grades.major;

import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.domain.Major;
import cn.grades.major.service.MajorService;

@WebServlet("/manager/major")
public class MajorServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> map = new MajorService().query();
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}
	public void select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Major major = BeanUtils.populate(Major.class, req.getParameterMap());
		Map<String, Object> map = new MajorService().select(major);
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}
	public void selectDept(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Major> list=new MajorService().selectDept();
		String json = JSONObject.toJSONString(list);
		System.out.println(json);
		resp.getWriter().print(json);
	}
	public void selectMajor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Major> list=new MajorService().selectMajor();
		String json = JSONObject.toJSONString(list);
		System.out.println(json);
		resp.getWriter().print(json);
	}
	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Major major = BeanUtils.populate(Major.class, req.getParameterMap());
		int rows=new MajorService().save(major);
		resp.getWriter().print(rows);
	}
	public void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		int rows = new MajorService().delete(id);
		resp.getWriter().print(rows);
	}
       
}
