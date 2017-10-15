package cn.bpm.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
public class ForwardController {
	/**
	 * 接收所有请求，执行转发
	 */
	//@RequestMapping(value = "/**")
	public void redirect(HttpServletRequest req) {
		String uri = req.getRequestURI();
		System.err.println("ForwardController:"+uri);
	}
}
