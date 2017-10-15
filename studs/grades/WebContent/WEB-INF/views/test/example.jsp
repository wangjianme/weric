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
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/color.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.easyui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/locale/easyui-lang-zh_CN.js'/>"></script>
<script type="text/javascript">
	function _sex(value) {
		if (value == "1") {
			return "<font color='red'>男</font>";
		} else {
			return "<font color='green'>女</font>";
		}
	}
</script>
</head>
<body>
	<div
		style="border-bottom: 1px solid gray; padding-bottom: 10px; margin-bottom: 10px">
		<a href="#" id="btnAdd" class="easyui-linkbutton c1"
			data-options="iconCls:'icon-add'">增加</a> <a href="#"
			class="easyui-linkbutton c2">修改</a> <a href="#"
			class="easyui-linkbutton c3">删除</a>
	</div>
	<table id="tb" class="easyui-datagrid" style="width: 100%;"
		pagination="true"
		data-options="url:'<c:url value='/example'/>',fitColumns:true,singleSelect:true">
		<thead>
			<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th data-options="field:'name',sortable:true">姓名</th>
				<th data-options="field:'addr'">地址</th>
				<th data-options="field:'sex',formatter:_sex">性别</th>
			</tr>
		</thead>
	</table>

	<div id="addDialog" class="easyui-dialog" title="My Dialog"
		style="width: 400px; height: 200px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#bb'">
		<form id="ff" method="post">
			<table>
				<tr>
					<td>姓名：</td>
					<td><input class="easyui-textbox" type="text" name="name"
						data-options="iconCls:'icon-man'" missingMessage="用户名必须输入" /></td>
				</tr>
				<tr>
					<td>地址：</td>
					<td><input class="easyui-textbox" type="text" name="addr"
						data-options="required:true,iconCls:'icon-lock'" /></td>
				</tr>
				<tr>
					<td>性别：</td>
					<td><input type="radio" name="sex" value="1">男 <input
						type="radio" name="sex" value="0">女</td>
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
	$("#btnAdd").click(function() {
		$("#addDialog").dialog("open");
	});

	$("#loginBtn").click(function() {
		var param = $("#ff").serialize();
		$.post("<c:url value='/example?cmd=save'/>", param, function(data) {
			$('#tb').datagrid('appendRow',data);
			$("#addDialog").dialog("close");
		}, "json");
	});
</script>
</html>