package cn.sms.grade;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.sms.domain.Grade;
import cn.sms.grade.service.GradeService;
import cn.sms.utils.BaseServlet;

@WebServlet(urlPatterns = "/manager/grade")
public class GradeServlet extends BaseServlet {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String rows = req.getParameter("rows");
		String page = req.getParameter("page");
		int _rows = Integer.parseInt(rows);
		int _page = Integer.parseInt(page);
		Map<String, Object> map = new GradeService().query(_rows, _page);
		// 转成json
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}

	// ?method=del
	public void del(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		int rows = new GradeService().delete(id);
		// 返回这个rows
		resp.getWriter().print(rows);
	}

	public void all(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Grade> list = new GradeService().query();
		resp.getWriter().print(JSONArray.toJSONString(list));
	}
}
