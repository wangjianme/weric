<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'topN.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="ui_css/main.css" rel="stylesheet"/>
    <link href="ui_css/dialog.css" rel="stylesheet"/>
    <link href="ui_css/table.css" rel="stylesheet">
    <link href="ui_css/form.css" rel="stylesheet"/>
    <link href="ui_css/calendar.css" rel="stylesheet"/>
    <link href="ui_css/loading_style.css" rel="stylesheet"/>
    <script type="text/javascript" src="ui_js/jquery.js"></script>
    <script type="text/javascript" src="ui_js/layer.js"></script>
    <script type="text/javascript" src="ui_js/ui.js"></script>
    <script type="text/javascript" src="ui_js/z.src.js"></script>
    <script type="text/javascript" src="ui_js/dateui.js"></script>
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
	    		var num = document.getElementById("t1").value;
	      	    curd.ajaxQueryWithShadowDivClose("POST","keyword.jsp",{"city":"<%=city%>","job":"<%=job%>","num":num},function (html){
					var framex = new Frame("使用技术统计");
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
    <h3 align="center">请输入要查询的技术量:</h3>
    <div align="center">
    <form action="keyword.jsp" method="post">
      <input type="text" name="num" id="t1">
      <input type="button" id="b1" value="提交">
    </form>
    </div>
  </body>
</html>
