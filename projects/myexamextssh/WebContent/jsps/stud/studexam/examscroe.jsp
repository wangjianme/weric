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
	<style type="text/css">
		.tt{
			border:0px;
			background:transparent;
		}
	</style>
  </head>
  <body>
  </body>
  <script type="text/javascript">
  	 var infoType = '<c:out value="${requestScope.examScore.infoType}"/>';
  	 if(infoType==0){
		infoType='正常考试';
  	 }else{
		infoType='第'+infoType+'次补考';
  	 }
	 var panel = new Ext.Panel({
		region:'center',
		layout:'form',
		labelAlign:'right',
		labelWdith:100,
		height:400,
		width:400,
		items:[
		       {
				  xtype:'textfield',
				  fieldLabel:'考试名称',
				  cls:'tt',
				  readOnly:true,
				  value:'<c:out value="${requestScope.examScore.examName}"/>'
			   },
			   {
				  xtype:'textfield',
				  fieldLabel:'课程名称',
				  cls:'tt',
				  readOnly:true,
				  value:'<c:out value="${requestScope.examScore.courName}"/>'
			   },
			   {
				  xtype:'textfield',
				  fieldLabel:'总分',
				  cls:'tt',
				  readOnly:true,
				  value:'<c:out value="${requestScope.examScore.examScore}"/>分'
			   },
			   {
				   xtype:'textfield',
				   fieldLabel:'分数线',
				   cls:'tt',
				   readOnly:true,
				   value:'<c:out value="${requestScope.examScore.examPass}"/>分'
			   },
			   {
				   xtype:'textfield',
				   fieldLabel:'得分',
				   cls:'tt',
				   readOnly:true,
				   value:'<c:out value="${requestScope.examScore.infoScore}"/>分'
			   },
			   {
				   xtype:'textfield',
				   fieldLabel:'用时',
				   cls:'tt',
				   readOnly:true,
				   value:'<c:out value="${requestScope.examScore.infoTimein}"/>分钟'
			   },
			   {
				   xtype:'textfield',
				   fieldLabel:'考试类型',
				   cls:'tt',
				   readOnly:true,
				   value:infoType
			   }
		]
	 });

	 var win = new Ext.Window({
		width:400,
		height:400,
		layout:'border',
		closable:false,
		items:[panel]
	 });
	 win.show();
  </script>
</html>