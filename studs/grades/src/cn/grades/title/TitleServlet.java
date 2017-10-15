package cn.grades.title;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONObject;

import cn.grades.core.BaseServlet;
import cn.grades.domain.Title;
import cn.grades.title.service.TitleService;
import cn.grades.domain.Title;

/**
 * Servlet implementation class TitleServlet
 */
@WebServlet("/manager/title")
public  class TitleServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// 注入service
	private TitleService service = new TitleService();
	//声明一侧参数，用于接收调用方法
	
	
	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Title title = new Title();
	    BeanUtils.populate(title, req.getParameterMap());
	    System.err.println(title.toString());
		title = service.save(title);
		//将title转成json
		String json = JSONObject.toJSONString(title);
		System.err.println("保存成功了："+json);
		resp.getWriter().print(json);
		
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取用户查询的信息
		List<Title> list = service.query();
		String json = JSONObject.toJSONString(list);
		System.err.println("=="+json);
		resp.getWriter().print(json);
	}
	
	//删除
	public void delete(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		String id = req.getParameter("id");
	    //service.delete(id);
		int rows = service.delete(id);
		resp.getWriter().print(rows);
	}
	
	//修改
	public void update(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException, IllegalAccessException, InvocationTargetException{
		Title t = new Title();
		BeanUtils.populate(t, req.getParameterMap());
		System.err.println("title is:" + t);
		int rows = service.update(t);
		Map<String, Object> result = new HashMap<>();//声明集合，用于确定修改是否成功
		if(rows > 0){
		    result.put("success",true);
			result.put("title",t);
		}else{
			result.put("success", false);
		}
		String json = JSONObject.toJSONString(result);
		resp.getWriter().print(json);
	}

    //查询
	public void select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Title title = new Title();
		BeanUtils.populate(title, req.getParameterMap());
		System.err.println("()()()()()()()"+title);
		List<Title> list = service.select(title);
		System.err.println("%%%%%%%%%%%%"+list);
		String json = JSONObject.toJSONString(list);
		System.out.println("json:"+json);
		resp.getWriter().print(json);
	}

}