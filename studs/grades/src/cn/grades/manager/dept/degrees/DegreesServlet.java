package cn.grades.manager.dept.degrees;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.domain.Degrees;
import cn.grades.manager.dept.degrees.service.DegreesService;
import cn.grades.manager.dept.degrees.service.IDegreesService;

@WebServlet(urlPatterns = "/manager/dept/degrees")
public class DegreesServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private IDegreesService service = new DegreesService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Degrees> list = new DegreesService().query();
		String json = JSONObject.toJSONString(list);

		response.getWriter().print(json);
	}

	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Degrees degrees = BeanUtils.populate(Degrees.class, req.getParameterMap());

		service.save(degrees);
		String json = JSONObject.toJSONString(degrees);
		resp.getWriter().print(json);
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("degrees_id");
		int rows = new DegreesService().delete(id);
		resp.getWriter().print(rows);
	}
	public void update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("degrees_id");
		if (id == null || id.equals("")) {
			save(req, resp);
		} else {
			System.out.println("1");
			Degrees degrees = BeanUtils.populate(Degrees.class, req.getParameterMap());
			service.update(degrees);
			String json = JSONObject.toJSONString(degrees);
			resp.getWriter().print(json);
		}
	}

}
