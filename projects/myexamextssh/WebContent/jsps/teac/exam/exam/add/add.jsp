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
	<link rel="stylesheet" type="text/css" href="<c:url value='/ext/ux/css/Spinner.css'/>"/>
	<script type="text/javascript" src="<c:url value='/ext/adapter/ext/ext-base.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-all.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-lang-zh_CN.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ux/Spinner.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ux/SpinnerField.js'/>"></script>
	<script language="javascript">
		Ext.BLANK_IMAGE_URL = "<c:url value='/ext/resources/images/default/s.gif'/>"; //设置空白图片的位置
		var path = "<c:url value='/'/>";
	</script>
	<style>
		.txt{
			border-top:0px;
			border-left:0px;
			border-right:0px;
			border-bottom:1px solid blue;
			background:transparent;
			width:100px;
		}
		.tb{
			font-size:9pt;
		}
		.btn{
			font-size:9pt;
			border:0px;
			cursor:hand;
		}
	</style>
	<script type="text/javascript" src="<c:url value='/jsps/teac/exam/exam/add/courHelp.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/teac/exam/exam/add/add.js'/>"></script>
  </head>
  <body>
  </body>
</html>