package cn.grades.validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.grades.core.BaseServlet;

/**
 * Servlet implementation class ValidateServlet
 */
public class ValidateServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// <input type="text" name="code">
		// 1:获取用户提交的验证码
		String code = req.getParameter("code");
		// 2:从Sessoin中获取code
		String sCode = (String) req.getSession().getAttribute("code");
		// 将验证的结果放到request中
		boolean boo = code.equals(sCode);
		resp.getWriter().print(boo);
	}
}
