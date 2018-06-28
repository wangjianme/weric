<%@ page language="java" import="java.util.*,spider.*,java.net.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'compyinfo.jsp' starting page</title>
    
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
		String fileName = "c://zhilian//result_"+city+"_"+job+"_ETL.txt";
		Analysis an = new Analysis(fileName,new ZhiLianProcessor());
		List<Map<Object,String[]>> list = an.getMap("compy",0,name);
		System.out.println(list.size());
	%>
  </head>
  
  <body>
      <% 
      	for (Map<Object,String[]> map : list){
      		String[] values = map.get("message");
      		String[] url = map.get("com.oracle.url");
      		String[] compy = map.get("compy");
      		String[] pay = map.get("pay");
      		String[] workExp = map.get("workExp");
      		String s = URLEncoder.encode(compy[0],"UTF-8");
      		String[] work = map.get("work");
      		System.out.println(work);
      		out.println("<a href='"+url[0]+"' target='_blank'><font color='red'>"+compy[0]+"</font></a>");
      		out.println("&nbsp;&nbsp;&nbsp;&nbsp;<b>招聘岗位:</b>"+work[0]);
      		out.println("&nbsp;&nbsp;&nbsp;&nbsp;<b>薪资范围:</b>"+pay[0]+"-"+pay[1]);
      		out.println("&nbsp;&nbsp;&nbsp;&nbsp;<b>工作经验:</b>"+workExp[0]+"-"+workExp[1]);
      		out.println("&nbsp;&nbsp;&nbsp;&nbsp;<a href='https://www.baidu.com/s?wd="+s+"&rsv_spt=1&rsv_iqid=0xc51aeba700023fa6&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=23&rsv_sug1=18&rsv_sug7=100' target='_blank'>百度一下</a></br>");
      		for (String value : values){
      			out.println(value+"<br/>");
      		}
      		
      	}
      %>
  </body>
</html>
