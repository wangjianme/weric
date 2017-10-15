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
<link rel="stylesheet" type="text/css"
	href="<c:url value='/ext/resources/css/ext-all.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/ext/ux/fileuploadfield/css/fileuploadfield.css'/>" />
<script type="text/javascript"
	src="<c:url value='/ext/adapter/ext/ext-base.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ext/ext-all.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/ext/ext-lang-zh_CN.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/ext/ux/fileuploadfield/FileUploadField.js'/>"></script>
<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = "<c:url value='/ext/resources/images/default/s.gif'/>"; //设置空白图片的位置
	var path = "<c:url value='/'/>";
</script>
<style type="text/css"></style>
</head>
<body>
	<div>
		 <font color="blue" style="font: bold;" size="5">学生注册..MyExam迈易在线..</font>
	</div>
	<div style="float: right; margin: 0px; padding: 0px;">
		<img src="<c:url value='/css/images/reg.png'/>"
			style="background: transparent;" />
	</div>
	<br />
	<div id="mainForm"></div>
</body>
<script type="text/javascript"
	src='<c:url value="/jsps/stud/reg/cls.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/jsps/stud/reg/reg.js"/>'></script>
</html>