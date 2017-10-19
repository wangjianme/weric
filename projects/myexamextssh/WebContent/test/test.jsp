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
  </head>
  <body>
    	<a href="<c:url value='login.action'/>">学生登录页面</a>
    	<br/>
    	<a href="<c:url value='admin.action'/>">教师登录页面</a>
    	<hr/>
    	
  </body>
  <script type="text/javascript">
  	var form = new Ext.FormPanel({
  	  		renderTo:Ext.getBody(),
  	  		url:path+'test/back.jsp',
  	  		method:'post',
  	  		standardSubmit:true,
			items:[
			       {
				       xtype:'checkbox',
				       name:'nn',
				       inputValue:'A',
				       boxLabel:'AAA'
				   },
				   {
				       xtype:'checkbox',
				       name:'nn',
				       inputValue:'B',
				       boxLabel:'BBB',
				       listeners:{
							check:function(t,b){
								alert(t.inputValue);
			   				}
				       }
				   }
			],
			buttons:[
			         {
				         text:'save',
				         handler:function(){
							form.getForm().submit();
				         }
				     }
			]
  	 });
  </script>
</html>