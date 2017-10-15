package cn.grades.spwd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;

import cn.grades.core.BaseServlet;
import cn.grades.core.BeanUtils;
import cn.grades.core.Md5Utils;

import cn.grades.domain.Stud;
import cn.grades.spwd.service.PwdService;

@WebServlet(urlPatterns ="/spwd")
public class PwdServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public void spwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获取user对象
		Stud stud =(Stud) request.getSession().getAttribute("stud");
		  //获取参数
			String oldPwd =request.getParameter("code1");
			String newPwd1 =request.getParameter("code2");
			String newPwd2 = request.getParameter("code3");
			//System.out.println("0000=="+oldPwd+newPwd);
			//String oldPwdMd5 = Md5Utils.md5(oldPwd, stud.getSalt());
			System.out.println(oldPwd);
			String old = stud.getPwd();
			System.out.println(old);
			PrintWriter out = response.getWriter();
		
		if (oldPwd.equals(old)){
			if(newPwd1.equals(newPwd2)){
			int effects = new PwdService().updatePwd(stud,newPwd1);
			//System.out.println(effects);
			out.print(effects);
			}else{
			out.print("0"); //修改失败
			}
		}else{
			out.print("0"); //修改失败
		}
	}
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		
	}
}
