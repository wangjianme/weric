<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/default/easyui.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/icon.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.easyui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/locale/easyui-lang-zh_CN.js'/>"></script>
</head>
<body>
	<div id="win" class="easyui-dialog" title="学生信息管理系统" style="padding: 15px;"
		closable="false" minimizable="false" maximizable="false"
		collapsible="false" data-options="iconCls:'icon-man',buttons:'#bb'">
		<form id="ff" method="post">
			<table>
				<tr>
					<td>姓名：</td>
					<td><input class="easyui-textbox" type="text" name="name"
						data-options="iconCls:'icon-man'" missingMessage="用户名必须输入" /></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input class="easyui-textbox" type="password" name="pwd"
						data-options="required:true,iconCls:'icon-lock'" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="bb">
		<a href="#" style="width: 80px" class="easyui-linkbutton"
			id="loginBtn">登录</a> <a href="#" class="easyui-linkbutton"
			style="width: 80px">注册</a>
	</div>

</body>
<script type="text/javascript">
	$(function() {
		$("#loginBtn").click(function() {

			var param = $("#ff").serialize();
			//alert(param)
			$.post("<c:url value='/login'/>", param, function(data) {
				if (data == "1") {
					//$.messager.alert('提示', '登录成功!', "info");
					//用脚本，去显示工作平台
					//$.messager.alert('提示', '欢迎你，管理员！',"info");
					window.location="<c:url value='/manager/desktop.html'/>";
				}/*  else if(data == "12"){
					$.messager.alert('提示', '欢迎你，学生！',"info");
					window.location="<c:url value='/manager/desktop1.html'/>";
				} */else{
					$.messager.alert('提示', '用户名或是密码错误!', "error");
				}
			}, "text");

		});		
	});
	function chg() {
		//直接使用jquery的代码修改页面的值
		var d = new Date();
		var time = d.getTime();
		//document.getElementById("_img")
		var url = "img?" + time;
		$("#_img").attr("src", url);
	}
</script>
</html>