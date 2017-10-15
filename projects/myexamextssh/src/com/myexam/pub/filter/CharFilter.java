package com.myexam.pub.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
public class CharFilter implements Filter{
	private Logger log = Logger.getLogger(CharFilter.class);
	public void destroy() {
	}
	public void doFilter(ServletRequest req, ServletResponse rep,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		chain.doFilter(req, rep);
	}
	public void init(FilterConfig arg0) throws ServletException {
	}
}
