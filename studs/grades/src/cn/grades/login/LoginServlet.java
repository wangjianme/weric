package cn.grades.login;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.domain.User;
import cn.grades.login.service.ILoginService;
import cn.grades.login.service.LoginService;

@WebServlet(urlPatterns="/login")
public class LoginServlet extends BaseServlet {
	private ILoginService service = new LoginService();
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		User user = 
				BeanUtils.populate(User.class,req.getParameterMap());
		user = service.login(user);
		if(user==null){
			resp.getWriter().print("0");
		}else{
			req.getSession().setAttribute("user", user);
			resp.getWriter().print("1");
		}
	}
}
