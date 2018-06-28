package com.echarts.controller;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.echarts.service.UserService;
import com.echarts.utils.BasicFactory;

public class CityServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	//@Test
	//public void sss(){
		UserService service=BasicFactory.getFactory().getInstance(UserService.class);
		String[] cities=new String[]{"北京","上海","重庆","天津","广东","江苏","浙江","四川",
				"福建","山东","湖北","河北","江西","内蒙古","辽宁","吉林","黑龙江","安徽","山西","河南"
				,"湖南","广西","海南","贵州","云南","西藏","陕西","甘肃","青海","宁夏","新疆","香港","澳门","台湾"};
		Map<String,Long> cityMap=new LinkedHashMap<String,Long>();
		for(int i=0;i<cities.length;i++){
			Long count=service.getCountByCity('%' +cities[i] + '%');
			cityMap.put(cities[i], count);
		}
		System.out.println(cityMap);
		req.setAttribute("cityMap", cityMap);
		req.getRequestDispatcher("/city.jsp").forward(req, resp);

	}

}
