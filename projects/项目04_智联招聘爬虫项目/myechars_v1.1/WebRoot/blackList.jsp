<%@ page language="java" import="java.util.*,spider.*,spider.node.*,java.net.*,spider.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'blackList.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<% 
	request.setCharacterEncoding("UTF-8");
	String city = request.getParameter("city");
	String job = request.getParameter("job");
	String name = request.getParameter("name");
	if (city == null){
		city = "北京";
	}
	if (job == null){
		job = "java";
	}
	String fileName = "c://zhilian//black//black_"+city+"_"+job+".txt";
	BlackList bl = new BlackList(fileName);
	Map<String,String[]> list = bl.getBlackList();
	Set<String> keySet = list.keySet();
	%>
  </head>
  
  <body>
    <% 
    for (String key : keySet){
    	String[] values = list.get(key);
		String url = values[1];
		String compy = values[0];
		out.println("<a href='"+url+"' target='_blank'><font color='red'>"+compy+"</font></a>");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;<a href='https://www.baidu.com/s?wd="+compy+"&rsv_spt=1&rsv_iqid=0xc51aeba700023fa6&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=23&rsv_sug1=18&rsv_sug7=100' target='_blank'>百度一下</a></br>");
	}
    %>
  </body>
</html>
