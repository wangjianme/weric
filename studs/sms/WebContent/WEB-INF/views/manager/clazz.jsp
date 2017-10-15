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
	
</script>
</head>
<body style="margin: 15px">
	<table class="easyui-datagrid" id="userGrid" title="班级"
		style="width: 100%; height: 260px;" pagination="true"
		data-options="singleSelect:true,collapsible:true,url:'${pageContext.request.contextPath}/manager/cls',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">用户ID</th>
				<th data-options="field:'name',width:200">班级名称</th>
				<th data-options="field:'grade',width:100">年级</th>
				<th data-options="field:'major',width:100">专业</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="height: auto">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-man'">分配角色</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
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
					<td>班级名称：</td>
					<td><input type="text" name="name" class="easyui-textbox">
					</td>
				</tr>
				<tr>
					<td>年级：</td>
					<td><input class="easyui-combobox" id="_grade" name="gradeid"
						editable="false"
						data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/manager/grade?method=all'">
					</td>
				</tr>
				<tr>
					<td>专业：</td>
					<td><input id="cc" class="easyui-combobox" name="majorid"
						data-options="valueField:'id',textField:'text',url:'get_data.php'">
					</td>
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
		$.post("<c:url value='/manager/cls?method=save'/>", param, function(
				data) {
			$("#addDialog").dialog("close");

			var nm = $("#_grade").combobox("getText");
			data.grade = nm;
			//将返回的json添加到
			//userGrid
			$('#userGrid').datagrid('appendRow', data);
		}, "json");
	}
</script>

</html>