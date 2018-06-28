package com.echarts.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.echarts.service.UserService;
import com.echarts.utils.BasicFactory;

public class AgeServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// public void sss(){
		UserService service = BasicFactory.getFactory().getInstance(UserService.class);
		Map<String, Long> ageMap = new LinkedHashMap<String, Long>();
		Map<String, Long> boyMap = new LinkedHashMap<String, Long>();
		Map<String, Long> girlMap = new LinkedHashMap<String, Long>();
		for (int j = 2; j < 10; j++) {
			List<String> ageList = new ArrayList<String>();
			for (int i = 10 * j; i < 10 * (j + 1); i++) {
				ageList.add(i + "岁");
			}
			// System.out.println(ageList);
			// 1.获取年龄的数量
			Long ageCount = service.getAgeCount(ageList);
			Long boyCount = service.getAgeCountByBoy(ageList);
			Long girlCount = ageCount - boyCount;

			ageMap.put(ageList.get(0) + "~" + ageList.get(9), ageCount);
			boyMap.put(ageList.get(0) + "~" + ageList.get(9), boyCount);
			girlMap.put(ageList.get(0) + "~" + ageList.get(9), girlCount);
			// System.out.println(ageCount);

		}

		// PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this,
		// req, resp, null, true, 80, true);
		// pageContext.setAttribute("ageMap", map);

		req.setAttribute("ageMap", ageMap);
		req.setAttribute("boyMap", boyMap);
		req.setAttribute("girlMap", girlMap);
		// req.getAttribute("ageMap");
		req.getRequestDispatcher("/age.jsp").forward(req, resp);
	}
}
