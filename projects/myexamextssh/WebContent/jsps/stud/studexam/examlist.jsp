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
	<script language="javascript" src='<c:url value="/jsps/stud/studexam/examlist.js"/>'></script>
  </head>
  <body>
  	<br/><br/><br/><br/><br/>
  	<table border="0" width="100%">
  		<tr>
			<td valign="bottom" align="left" style="font:bold;font-size:9pt;">
				应该参加的考试列表
			</td>  		
  		</tr>
  		<tr>
  			<td>
  				<div id="dd" style="width:100%;height:300px;">
  				</div>
  			</td>
  		</tr>
  	</table>
  </body>
</html>