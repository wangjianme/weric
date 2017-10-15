<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>MyExam</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="在线考试系统">
	<link rel="stylesheet" type="text/css" href="<c:url value='/ext/resources/css/ext-all.css'/>"/>
	<script type="text/javascript" src="<c:url value='/ext/adapter/ext/ext-base.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-all.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-lang-zh_CN.js'/>"></script>
	<script language="javascript">
		Ext.BLANK_IMAGE_URL = "<c:url value='/ext/resources/images/default/s.gif'/>"; //设置空白图片的位置
		var path = "<c:url value='/'/>";
	</script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/public.css'/>"/>
  </head>
  <body>
  	 <div id="header">
  	 		<div style="float:left;color: white;">
				&nbsp;&nbsp;<img width="30" height="30"  src="<c:url value='/css/images/exam_64.png'/>"/>
				MyExamOnLine.在线考试系统
			</div>
			<div style="float:right;vertical-align: bottom;">
				<br/>
				<p style="font-size:9pt;font:bold;vertical-align: bottom;">
			 		<a href="javascript:loginOut();" style="color:white;">安全退出</a>
				</p>
			</div>
	 </div> 
	 <div id="pic" style="text-align:center;vertical-align:bottom;">
	 	<br/>
	 	<img width="80" height="100" src="<c:url value='/images/${stud.studPic}'/>"/>
	 	<br/>
	 	<font size="2">
	 		欢迎你：${stud.studName}
	 	</font>
	 </div>
    <iframe id="mainFrame" name="mainFrame" frameborder="0" height="100%" width="100%"></iframe>
  </body>
  <script language="javascript" src='<c:url value="/jsps/stud/desktop/desktop.js"/>'></script>
</html>