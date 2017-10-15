package cn.bpm.manager;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.bpm.manager.service.IProcessDefinationService;

@Controller
public class ProcessDefinationController {
	@Resource(name = "processDefinationService")
	private IProcessDefinationService service;

	@RequestMapping(value = "/manager/processdefination")
	public ModelAndView queryAllProcessDefination() {
		List<Map<String, Object>> list = service.queryAllProcessDefination();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("manager/main");
		mv.addObject("list", list);
		return mv;
	}

	/**
	 * 删除某个流程定义，根据部署id
	 * 
	 */
	@RequestMapping(value = "/manager/deleteprocess")
	public String deleteDemployment(String id) {
		service.deleteDeploymentById(id);
		return "redirect:/manager/processdefination.html";
	}

	/**
	 * 查询流程定义图片<br>
	 * 使用原生方式获取
	 */
	@RequestMapping(value = "/manager/img/png")
	public void resource(HttpServletRequest req, HttpServletResponse resp, String procdefId) throws Exception {
		procdefId = URLDecoder.decode(procdefId, "UTF-8");
		System.err.println("procdefId is:"+procdefId);
		InputStream in = service.resource(procdefId);
		resp.setContentType("image/png");
		OutputStream out = resp.getOutputStream();
		byte[] bs = new byte[1024 * 4];
		int len = 0;
		while ((len = in.read(bs)) != -1) {
			out.write(bs, 0, len);
		}
		out.close();
	}
	
	/**
	 * 查询流程定义的图片，只是去显示
	 */
	@RequestMapping(value="/manager/**")
	public void forward(){
		System.err.println("Forward");
	}
}
