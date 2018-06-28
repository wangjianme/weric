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

public class GenderServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	//@Test
	//public void sss(){
		UserService service=BasicFactory.getFactory().getInstance(UserService.class);
		String[] genders=new String[]{"ÄÐ","Å®"};
		Map<String,Long> genderMap=new LinkedHashMap<String,Long>();
		for(int i=0;i<genders.length;i++){
			Long count=service.getCountByGender(genders[i]);
			genderMap.put(genders[i], count);
		}
		//System.out.println(genderMap);
		req.setAttribute("genderMap", genderMap);
		req.getRequestDispatcher("/gender.jsp").forward(req, resp);
	}

}
