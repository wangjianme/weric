package com.echarts.controller;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.echarts.service.UserService;
import com.echarts.utils.BasicFactory;

public class SalaryServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserService service=BasicFactory.getFactory().getInstance(UserService.class);
		String[] salarys=new String[]{"1000Ԫ����","1001-2000Ԫ","2001-3000Ԫ","3001-5000Ԫ",
				"5001-8000Ԫ","8001-10000Ԫ","10001-20000Ԫ","20001-50000Ԫ","50000Ԫ����"};
		Map<String,Long> salaryMap=new LinkedHashMap<String,Long>();
		Map<String,Long> salaryBoyMap=new LinkedHashMap<String,Long>();
		Map<String,Long> salaryGirlMap=new LinkedHashMap<String,Long>();
		for(int i=0;i<salarys.length;i++){
			Long count=service.getCountBySalary(salarys[i]);
			Long boyCount=service.getCountByBoySalary(salarys[i]);
			Long girlCount=count-boyCount;
			salaryMap.put(salarys[i], count);
			salaryBoyMap.put(salarys[i], boyCount);
			salaryGirlMap.put(salarys[i], girlCount);
		}
		req.setAttribute("salaryMap", salaryMap);
		req.setAttribute("salaryBoyMap", salaryBoyMap);
		req.setAttribute("salaryGirlMap", salaryGirlMap);
		req.getRequestDispatcher("/salary.jsp").forward(req, resp);
	
	}

}
