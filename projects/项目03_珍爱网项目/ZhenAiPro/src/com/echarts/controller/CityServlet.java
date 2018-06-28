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
		String[] cities=new String[]{"����","�Ϻ�","����","���","�㶫","����","�㽭","�Ĵ�",
				"����","ɽ��","����","�ӱ�","����","���ɹ�","����","����","������","����","ɽ��","����"
				,"����","����","����","����","����","����","����","����","�ຣ","����","�½�","���","����","̨��"};
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
