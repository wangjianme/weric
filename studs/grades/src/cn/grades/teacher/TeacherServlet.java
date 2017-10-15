package cn.grades.teacher;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import com.alibaba.fastjson.JSONObject;
import cn.grades.core.BaseServlet;
import cn.grades.domain.Degrees;
import cn.grades.domain.Department;
import cn.grades.domain.Teacher;
import cn.grades.domain.Title;
import cn.grades.teacher.service.ITeacherService;
import cn.grades.teacher.service.TeacherService;

@WebServlet(urlPatterns = "/manager/dept/teacher")
public class TeacherServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private ITeacherService service = new TeacherService();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String _page = req.getParameter("page");
		String _rows = req.getParameter("rows");
		int page = Integer.parseInt(_page);
		int rows = Integer.parseInt(_rows);
		Map<String, Object> map = service.query(page, rows);
		// 遍历显示map的key和value
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
		/*
		 * //另一种方法遍历 Set<Entry<String, Object>> entries = map.entrySet(); for
		 * (Entry<String, Object> entry : entries) {
		 * System.out.println(entry.getKey());
		 * System.out.println(entry.getValue()); }
		 */
		String json = JSONObject.toJSONString(map);
		// System.err.println(json);
		resp.getWriter().print(json);
	}
	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Teacher teacher = new Teacher();
		BeanUtils.populate(teacher, req.getParameterMap());
		teacher = service.save(teacher);
		// teacher就是个list
		System.out.println("=================" + teacher);
		// 获取combobox下拉框的list，和teacher的list整合到一起之后再转成json，传到页面。
		// 将teacher转成json
		String json = JSONObject.toJSONString(teacher);
		System.out.println("保存成功了：" + json);
		resp.getWriter().print(json);
	}
	public void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("id");
		int rows = new TeacherService().delete(id);
		resp.getWriter().print(rows);
	}
	public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		System.out.println("++++++++++++++++++++++++++++++++"+id);
			Teacher teacher=new Teacher();
			 BeanUtils.populate(teacher, request.getParameterMap());
			int rows = new TeacherService().update(teacher);
			String json = JSONObject.toJSONString(teacher);
			System.out.println("更新成功了====：" + json);
			response.getWriter().print(json);
			response.getWriter().print(rows);
		}
	// 下拉框中获取所属学院的值的方法
		public void institute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			List<Department> list = service.ins();
			String json = JSONObject.toJSONString(list);
	System.out.println("666666666666666"+json);
			resp.getWriter().print(json);
		}

		// 下拉框中获取学历的值的方法
		public void education(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			List<Degrees> list = service.edu();
			String json = JSONObject.toJSONString(list);
			System.out.println("77777777777777"+json);
			resp.getWriter().print(json);
		}

		// 下拉框中获取职称的值的方法
		public void rank(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			List<Title> list = service.rank();
			String json = JSONObject.toJSONString(list);
			System.out.println("888888888888888"+json);
			resp.getWriter().print(json);
		}
		/*以下是查询*/

		public void select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
			System.out.println("laile");
			Teacher teacher = new Teacher();
					BeanUtils.populate(teacher, req.getParameterMap());
			System.out.println("要查询的姓名是："+teacher.getNm());
			System.out.println("要查询的學院是："+teacher.getInstitute());
		
		
			Map<String, Object> map = new TeacherService().select(teacher );
			System.out.println("00m:"+map);
			String json = JSONObject.toJSONString(map);
			System.out.println("json"+json);
			resp.getWriter().print(json);
			System.out.println(111);
		}

}
