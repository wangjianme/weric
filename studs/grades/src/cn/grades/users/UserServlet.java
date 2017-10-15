package cn.grades.users;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.grades.core.BaseServlet;
import cn.grades.domain.Role;
import cn.grades.domain.Teacher;
import cn.grades.domain.User;
import cn.grades.role.service.IRoleService;
import cn.grades.role.service.RoleServiceImpl;
import cn.grades.teacher.service.ITeacherService;
import cn.grades.teacher.service.TeacherService;
import cn.grades.users.service.IUserService;
import cn.grades.users.service.UserServiceImpl;


/**
 * Servlet implementation class addRole
 */
@WebServlet(urlPatterns="/manager/user")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private IUserService service=new UserServiceImpl();
	private IRoleService roleservice=new RoleServiceImpl();
	//用户角色表联合查询
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String _page=req.getParameter("page");
		String _rows=req.getParameter("rows");
		int page=Integer.parseInt(_page);
		int rows=Integer.parseInt(_rows);
		Map<String ,Object> map=service.query(page, rows);
		String json=JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}//实现角色表的查询
	public void queryRoles(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		Map<String,Object> roles=roleservice.query(1, 10);
		String json=JSONObject.toJSONString(roles);
		resp.getWriter().print(json);
	}//实现用户的查询
	public void queryUser(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		Map<String,Object> user=service.queryUser(1, 10);
		String json=JSONObject.toJSONString(user);
		resp.getWriter().print(json);
	}//实现教师表的查询
	public void queryTeacher(HttpServletRequest req,HttpServletResponse resp)throws Exception{
		Map<String,Object> teacher=service.queryTeacher(1, 10);
		String json=JSONObject.toJSONString(teacher);
		resp.getWriter().print(json);
	}
	public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u=new User();
		//Role role=new Role();
		try {
			BeanUtils.populate(u, req.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		u=service.save(u);
		String json=JSONObject.toJSONString(u);
		resp.getWriter().print(json);
		//resp.sendRedirect(req.getContextPath() + "/user");
		
	}
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		int rows = service.delete(id);
		response.getWriter().print(rows);// 1,0
	}
	//保存老师，用老师保存用户
	public void saveTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String names[]=req.getParameterValues("name");
		String pwd=req.getParameter("pwd");
		String nm=null;
		User u= new User();
		u.setId(UUID.randomUUID().toString().replace("-", ""));
		for(String name:names){
			nm=name;
		}
		System.out.println(nm);
		u.setName(nm);
		u.setPwd(pwd);
		System.out.println(u.getId());
		System.out.println(u.getName());
		System.out.println(u.getPwd());
		u=service.saveTeacher(u);
		String json=JSONObject.toJSONString(u);
		resp.getWriter().print(json);
		//resp.sendRedirect(req.getContextPath() + "/user");
		
	}

	//启用账户
	public void statusOn(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException{
		String id=req.getParameter("id");
			service.statusOn(id);
	}
	//停用账户
	public void statusOff(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException{
		String id = req.getParameter("id");
		service.statusOff(id);
	}
	//初始化
	public void initialize(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException{
		String id=req.getParameter("id");
		System.out.println(id);
		String nid=service.initialize(id);
		System.out.println(nid);
		resp.getWriter().print(nid);
	}
	
	public void assignRole(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
		String uid=req.getParameter("userid");
		String[] ids=req.getParameterValues("id");
		service.assignRole(uid,ids);
	}
	
//	public void userList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		
//		List<ComboBox> list = service.userList();
//		String json = JSONObject.toJSONString(list);
//		resp.getWriter().print(json);
//	}
}


