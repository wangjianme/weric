package cn.sms.cls;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONObject;

import cn.sms.cls.service.ClsService;
import cn.sms.domain.Cls;
import cn.sms.utils.BaseServlet;

@WebServlet(urlPatterns = "/manager/cls")
public class ClsServlet extends BaseServlet {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		Map<String, Object> map = new ClsService().query();
		String json = JSONObject.toJSONString(map);
		resp.getWriter().print(json);
	}
	
	public void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Cls cls = new Cls();
		BeanUtils.populate(cls, req.getParameterMap());
		cls = new ClsService().save(cls);
		String json = JSONObject.toJSONString(cls);
		resp.getWriter().print(json);
	}
	

}
