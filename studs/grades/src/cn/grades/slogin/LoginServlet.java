package cn.grades.slogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.domain.Score;
import cn.grades.domain.Stud;
import cn.grades.domain.User;
import cn.grades.slogin.service.ILoginService;
import cn.grades.slogin.service.LoginService;

@WebServlet(urlPatterns="/slogin")
public class LoginServlet extends BaseServlet {
	private ILoginService service = new LoginService();
	
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Stud stud = BeanUtils.populate(Stud.class,req.getParameterMap());
		String id = req.getParameter("name");
		stud = service.login1(stud);
		if(stud==null){
			resp.getWriter().print("0");
		}else{
			req.getSession().setAttribute("stud", stud);
			resp.getWriter().print("1");
		}

	}
	public void queryself(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Stud stud=(Stud) req.getSession().getAttribute("stud");
		String id=stud.getId();
		System.out.println("<00>"+id);
		List<Stud> list =service.query(id);
		System.out.println(list);
		req.setAttribute("list", list);
		req.getRequestDispatcher("/WEB-INF/views/htmls/stud/sshow.jsp").forward(req, resp);
	}
	public void querys(HttpServletRequest req, HttpServletResponse resp) throws Exception {	
		Score s=BeanUtils.populate(Score.class,req.getParameterMap());
		Stud stud=(Stud) req.getSession().getAttribute("stud");
		String id=stud.getId();
	//	Score score=(Score) req.getSession().getAttribute("score");
	//	String s= score;
		System.out.println("<11>"+id);
		List<Score> list =service.queryscore(id , s);
		System.out.println(list);
		req.setAttribute("list", list);
		req.getRequestDispatcher("/WEB-INF/views/htmls/stud/score.jsp").forward(req, resp);
	}
	/**
	 * 如果用户提交是get请求，则执行这边的doGet方法
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO:退出功能：
		req.getSession().removeAttribute("stud");
		resp.sendRedirect(req.getContextPath() + "/login.html");
	}
}
