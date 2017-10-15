package cn.bpm.employee;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.bpm.employee.service.IEmployeeService;
import cn.bpm.manager.service.IProcessDefinationService;

/**
 * 所有员工登录以后将显示这个界面
 */
@Controller
public class EmployeeController {
	@Resource(name = "employeeService")
	private IEmployeeService service;

	/**
	 * 用于显示员工主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/emp/main")
	public ModelAndView main() {
		List<Map<String, Object>> procdefs = service.queryProcdefs();
		return new ModelAndView("emp/main", "procdefs", procdefs);
	}

	/**
	 * 启动一个流程
	 * 
	 * @param session
	 * @param key
	 *            流程定义的key
	 * @param days
	 *            : 请假天数
	 * @param from
	 *            : 请假开始时间
	 * @param to：请假结束时间
	 *            String key, Integer days,String from,String to
	 * @return
	 */
	@RequestMapping(value = "/emp/apply", method = RequestMethod.POST)
	@ResponseBody
	public String apply(HttpSession session, @RequestParam() Map<String, Object> map) {
		String usrName = (String) session.getAttribute("user");
		// 直接将参数，都放到map中完成
		map.put("user", usrName);// 必须要传递的用户名
		map.put("applyUser", usrName);// 保存申请人的名称
		System.err.println("Map is:" + map);
		service.apply(map);
		return "1";
	}

	/**
	 * 查询某个用户的所有待办任务
	 */
	@RequestMapping(value = "/emp/tasks")
	public ModelAndView queryTask(HttpSession session) {
		String userName = (String) session.getAttribute("user");
		List<Map<String, Object>> list = service.queryTask(userName);// 查询出所有流程变量
		return new ModelAndView("emp/tasks", "tasks", list);
	}

	/**
	 * 查询显示当前流程图<br>
	 * 根据流程实例id<br>
	 * 直接引用管理员那边的流程定义Service即可
	 */
	@Resource(name = "processDefinationService")
	private IProcessDefinationService processDefinationService;

	@RequestMapping(value = "/emp/img/png")
	public void queryDiagram(HttpServletResponse resp, String procdefid) throws Exception {
		InputStream in = processDefinationService.resource(procdefid);
		resp.setContentType("image/jpeg");
		OutputStream out = resp.getOutputStream();
		byte[] bs = new byte[1024 * 4];
		int len = 0;
		while ((len = in.read(bs)) != -1) {
			out.write(bs, 0, len);
		}
		out.close();
		in.close();
	}

	/**
	 * 根据流程实例id+节点id查询 shape定位
	 */
	@RequestMapping(value = "/emp/shape")
	@ResponseBody
	public String shape(String procdefId, String activityId) {
		Map<String, Object> shape = service.shape(procdefId, activityId);
		String json = new JSONObject(shape).toString();
		return json;
	}

	/**
	 * 取消某个任务
	 */
	@RequestMapping(value = "/emp/cancel")
	@ResponseBody
	public String cancelTask(String taskId) {
		service.cancelTask(taskId);
		return "success";// 取消成功
	}

	/**
	 * 执行任务
	 * 
	 */
	@RequestMapping(value = "/emp/submit")
	@ResponseBody
	public String submitTask(HttpSession session, String taskId) {
		String userName = (String) session.getAttribute("user");// 获取当前用户的名称，以便于查询他的上级领导
		String leader = "王五";
		if (userName.equals("张三")) {
			leader = "李四";
		}
		service.submitTask(taskId, leader);
		return "1";
	}

	/**
	 * 查询正在执行的任务 <br>
	 * 即查询没有完成的任务
	 */
	@RequestMapping(value = "/emp/unfinishtasks")
	public String unfinishTasks(HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("user");// 获取当前用户名
		List<Map<String, Object>> list = service.unfinishTasks(userName);
		request.setAttribute("list", list);// 将查询的结果放到request中
		return "/emp/unfinishtasks";
	}

	/**
	 * 同意请假或是不同意请假 参数3：是否同意 参数4：同意或是不同意的附加信息 <br>
	 * 思想：直接在请假原因后面添加审批人的意见即可。 <br>
	 * 同时必须要传递agree为true|false来完成当前任务，如果agree=false则直接完成这个流程
	 */
	@RequestMapping(value = "/emp/agree")
	@ResponseBody
	public String agree(HttpServletRequest request, String taskId, boolean agree, String msg) {
		String userName = (String) request.getSession().getAttribute("user");// 获取当前用户名

		//获取当前时间
		String dataTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		//添加审批变量
		Map<String, Object> map = new HashMap<>();
		String str = "<hr>" + (agree ? "<b>同意!</b>" : "<b>不同意!</b>");
		str += "<br>" + (msg.replace("<", "&lt;").replace(">", "&gt;"));// 应该将msg内容进行实体转换，否则会有JS注入攻击
		str += "<br>审批人：" + userName+"【"+dataTime+"】";
		map.put("msg", str);
		map.put("agree", agree);// 传递是否同意的参数
		service.agree(taskId, map);
		return "1";// 返回ajax请求即可
	}

	/**
	 * 查询历史任务
	 */
	@RequestMapping(value="/emp/history")
	public String history(HttpServletRequest req){
		//获取当前用户
		String userName = (String) req.getSession().getAttribute("user");
		List<Map<String,Object>> list = 
				service.history(userName);
		req.setAttribute("list",list);
		return "/emp/history";
	}
	
	/**
	 * 添加一个公共的方法，可以执行转发
	 */
	@RequestMapping(value = "/emp/**")
	public void forward(HttpServletRequest req) {
		System.err.println("forward -- Uri:" + req.getRequestURI());
	}

}
