package cn.bpm.login;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 开发控制器，接收用户的姓名直接保存到Sesson中去<br>
 * 并根据不同的用户名，重定向到不同的页面
 */
@Controller
public class LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, String name) {
		System.err.println("登录用户:" + name);
		session.setAttribute("user", name);
		// 由于是示例项目，所以直接硬编码到程序中
		if (name.equals("管理员")) {
			return "redirect:/manager/processdefination.html";
		} else {
			return "redirect:/emp/main.html";
		}
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpSession session, String name) {
		return "index";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		System.err.println("用户退出..");
		session.removeAttribute("user");
		return "redirect:/index.html";
	}

}
