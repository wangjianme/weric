package com.echarts.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.echarts.service.UserService;
import com.echarts.utils.BasicFactory;

public class ChuangZhouServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		UserService service=BasicFactory.getFactory().getInstance(UserService.class);
		List<String> cities = service.getGD("%广东%");
		
		//String[] cities=new String[]{"广东东莞","广东中山","广东云浮 ","广东江门",};
		Map<String,Long> cityMap=new LinkedHashMap<String,Long>();
		for(int i=0;i<cities.size();i++){
			Long count=service.getCountByGZ(cities.get(i));
			cityMap.put(cities.get(i), count);
		}
		System.out.println(cityMap);
		req.setAttribute("cityMap", cityMap);
		req.getRequestDispatcher("/guangdong.jsp").forward(req, resp);

	}
}

