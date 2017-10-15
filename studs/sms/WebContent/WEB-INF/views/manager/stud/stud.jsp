<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	function _func(sex) {
		if (sex == "1") {
			return "<img src='/sms/static/images/man.png'/>";
		} else {
			return "<img src='/sms/static/images/woman.png'/>";
		}
	}
</script>
</head>
<body style="margin: 15px">
	<table class="easyui-datagrid" id="userGrid" title="学生花名册"
		style="width: 100%;" pagination="true"
		data-options="singleSelect:true,collapsible:true,url:'${pageContext.request.contextPath}/user',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">用户ID</th>
				<th data-options="field:'name',width:200">用户名称</th>
				<th data-options="field:'sex',width:100,formatter:_func">性别</th>
			</tr>
		</thead>
	</table>
	<!-- 菜单栏 -->
	<div id="tb" style="height: auto">
		<div style="margin: 5px;">
			<label>姓名：</label> <input type="text" name="name"
				class="easyui-textbox" id="name"><a
				class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>

		</div>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add'" id="btnAdd">增加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit'" onclick="reject()">修改</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove'" onclick="getChanges()">删除</a>
	</div>
	<!-- 增加时的对话框 -->
	<div id="addDialog" class="easyui-dialog" title="添加用户"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">

		<form id="addForm">
			<table>
				<tr>
					<td>用户登录名：</td>
					<td><input type="text" name="name" class="easyui-textbox">
					</td>
				</tr>
				<tr>
					<td>用户登录密码：</td>
					<td><input type="text" name="pwd" class="easyui-textbox">
					</td>
				</tr>
				<tr>
					<td>性别：</td>
					<td><input type="radio" value="1" name="sex" checked="checked">男
						<input type="radio" value="0" name="sex">女</td>
				</tr>
				<tr>
					<td></td>
					<td><a href="#" class="easyui-linkbutton" onclick="_save();">保存</a>
						<a href="#" class="easyui-linkbutton" onclick="_close();">关闭</a></td>

				</tr>
			</table>
		</form>

	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#btnAdd").click(function() {
			$("#addDialog").dialog("open");
		});

	});

	function _close() {
		$("#addDialog").dialog("close");
	}

	function _save() {
		var param = $("#addForm").serialize();
		$.post("<c:url value='/user?method=save'/>", param, function(data) {
			$("#addDialog").dialog("close");
			//将返回的json添加到
			//userGrid
			$('#userGrid').datagrid('appendRow', data);
		}, "json");
	}
</script>

</html>