package com.echarts.controller;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.echarts.service.UserService;
import com.echarts.utils.BasicFactory;

public class SchoolServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	//@Test
	//public void sss(){
		UserService service=BasicFactory.getFactory().getInstance(UserService.class);
		String[] schools=new String[]{"--","高中及以下","中专","大专","大学本科","硕士","博士"};
		Map<String,Long> schoolMap=new LinkedHashMap<String,Long>();
		for(int i=0;i<schools.length;i++){
			Long count=service.getCountBySchool(schools[i]);
			schoolMap.put(schools[i], count);
		}
		//System.out.println(schoolMap);
		req.setAttribute("schoolMap", schoolMap);
		req.getRequestDispatcher("/school2.jsp").forward(req, resp);
	}
}
