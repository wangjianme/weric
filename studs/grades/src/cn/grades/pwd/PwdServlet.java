package cn.grades.pwd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.grades.core.BaseServlet;
import cn.grades.core.Md5Utils;
import cn.grades.domain.User;
import cn.grades.pwd.service.IPwdService;
import cn.grades.pwd.service.PwdServiceImpl;
import cn.grades.users.service.IUserService;
import cn.grades.users.service.UserServiceImpl;

@WebServlet(urlPatterns ="/manager/pwd")
public class PwdServlet extends BaseServlet {


	private IPwdService service=new PwdServiceImpl();
	public void pwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 从Session中获取user用户
		User user = (User) req.getSession().getAttribute("user");
		// 获取参数
		String oldPwd = req.getParameter("pwd");
		System.err.println("oldpwd:-----------------"+oldPwd);
		String newPwd = req.getParameter("newPwd");
		System.err.println("newpwd:-----------------"+newPwd);
		// 比较旧密码
		String oldPwdMd5 = Md5Utils.md5(oldPwd, user.getSalt());
		System.err.println("Salt:-----------------"+user.getSalt());
		System.err.println("oldmd5pwd:-----------------"+oldPwdMd5);
		// 声明写出数据的c对象
		PrintWriter out = resp.getWriter();
		if (oldPwdMd5.equals(user.getPwd())) {
			
			// 就去修改密码
			int effects = new PwdServiceImpl().updatePwd(user, newPwd);
			// 直接将修改的行数返回就可以了
			out.print("" + effects);
                System.err.println("---------------修改成功"+effects);
		} else {

			out.print("0");// 修改不成功
            System.err.println(" ---------------修改不成功");

		}
	}


	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
