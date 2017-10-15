package cn.grades.teacherCensus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import cn.grades.core.BaseServlet;
import cn.grades.domain.TeacherCensus;
import cn.grades.teacherCensusService.TeacherCensusService;
 
@WebServlet("/manager/teacherCensusServlet")
public class TeacherCensusServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<TeacherCensus> lists=new TeacherCensusService().selectDept();
		List<String> list=new ArrayList<>();
		for(TeacherCensus t:lists){
			list.add(t.getName());
		}
		String json = JSONObject.toJSONString(list);
		System.out.println("=学院="+json);
		resp.getWriter().print(json);
	}
	public void titleSelect(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Map<String,Object>> list=new TeacherCensusService().titleSelect();
		String json = JSONObject.toJSONString(list);
		System.out.println("--title--"+json);
		resp.getWriter().print(json);
	}
	public void degreeSelect(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Map<String,Object>> list=new TeacherCensusService().degreeSelect();
		String json = JSONObject.toJSONString(list);
		System.out.println("--degrees--"+json);
		resp.getWriter().print(json);
	}

}
