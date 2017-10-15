package cn.grades.test.example;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.domain.Example;
import cn.grades.test.example.service.ExampleServiceImpl;
import cn.grades.test.example.service.IExampleService;

@WebServlet(urlPatterns = "/example")
public class ExampleServlet extends BaseServlet {
	private IExampleService service = new ExampleServiceImpl();

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
		Example e = BeanUtils.populate(Example.class, req.getParameterMap());
		service.save(e);
		String json = JSONObject.toJSONString(e);
		resp.getWriter().print(json);
	}
}
