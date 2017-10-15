<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>在线考试预览功能</title>
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
	<style type="text/css">
		p{
			margin-top:10px;
		}
	</style>
  </head>
  <body style="padding:10px;">
  	<p style="font:bold;font-family:monospace;font-size:9pt;">考试提示信息</p>
  	<hr/>
  	<p>${exam.examNote}</p>
  	<p>本次考试时间为：${exam.examTime}分钟</p>
  	<p>本次考试总分为：${exam.examScore}分</p>
  	<hr/>
  </body>
  <script language="javascript">
  	Ext.onReady(function(){
		var btn = new Ext.Button({
			id:'startBtn',
			text:'开始考试',
			margins:'10 10 10 10',
			renderTo:Ext.getBody(),
			handler:function(){
				Ext.Msg.confirm('提示','开始考试后，将开始倒计时，确定开始考试吗？',function(btn){
					if(btn=='yes'){
						parent.showMenu();
					}
				});
			}
		});
  	});
  </script>
</html>