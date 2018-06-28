<%@ page language="java" import="java.util.*,java.io.*,po.*,spider.*,spider.node.*,spider.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
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
	 String fileName = "c://zhilian//result_"+city+"_"+job+"_ETL.txt";
	 String blackFileName = "C://zhilian//black//black_"+city+"_"+job+".txt";
	 BlackList bl = new BlackList(blackFileName);
	 Analysis an = new Analysis(fileName,new ZhiLianProcessor());
	 List<Map<Object,String[]>> list = an.findListByExpression("fuli",-1,name);
	 Map<String,String[]> blackList = bl.getBlackList();
	 System.out.println(blackList.containsKey("中青才智教育投资(北京)有限公司"));
	%>
  </head>
  
  <body>
    <table align="center" border="1" width="100%">
      <tr>
        <td width="5%">&nbsp;</td>
        <td width="35%">公司</td>
        <td width="30%">工作经验</td>
        <td width="30%">工资</td>
      </tr>
    <% 
    	for (Map<Object,String[]> map : list){
    		String compy = map.get("compy")[0];
    		String[] pay = map.get("pay");
    		String[] workExp = map.get("workExp");
    		String[] url = map.get(UrlNode.URL);
    		String flag = "";
    		System.out.println(compy);
    		if (blackList.get(compy) != null){
    			flag = "●";
    		}
    		out.println("<tr>");
    		out.println("<td>"+flag+"</td>");
    		out.println("<td><a href='"+url[0]+"' target='_blank'>"+compy+"</a></td>");
    		out.println("<td>"+workExp[0]+"-"+workExp[1]+"年</td>");
    		out.println("<td>"+pay[0]+"-"+pay[1]+"</td>");
    		out.println("</tr>");
    	}
    %>
    </table>
  </body>
</html>
