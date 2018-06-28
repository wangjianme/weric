<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'condition.jsp' starting page</title>
    
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
    %>
    <script type="text/javascript">
	    var ui = new UI();
	    var curd = new AjaxCURD();
	    ui.init(function (){
	    	document.getElementById("b1").onclick=function(){
	    		var name = document.getElementById("t1").value;
	      	    curd.ajaxQueryWithShadowDivClose("POST","compyinfo.jsp",{"city":"<%=city%>","job":"<%=job%>","name":name},function (html){
					var framex = new Frame("使用技术明细");
				    framex.init();
				    var containerx = new JContainer();
				    containerx.addHTML(html);
				    framex.addComp(containerx);
				    framex.showFrame();
				    framex.setForeground();
				    curFrame.onclose();
				    return framex;
				});
	    	}
		});
    </script>
  </head>
  
  <body>
    <h3 align="center">请输入要查询的公司名称:</h3>
    <div align="center">
    <form action="compyinfo.jsp" method="post">
      <input type="text" name="num" id="t1">
      <input type="button" id="b1" value="提交">
    </form>
    </div>
  </body>
</html>
