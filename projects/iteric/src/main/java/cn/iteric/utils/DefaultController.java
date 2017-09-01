package cn.iteric.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	@RequestMapping("/*")
	public void defaultForward() {
		System.err.println("default..");
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
