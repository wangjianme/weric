<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

	<div id="tt"
		style="border-bottom: 1px solid gray; padding-bottom: 10px">
		<a id="btnAdd" class="easyui-linkbutton" iconCls="icon-add"
			plain="true">增加</a> <a id="btnDel" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true">刪除</a> <a href="#" id="btnUpd"
			class="easyui-linkbutton" iconCls="icon-cut" plain="true">修改</a>
	</div>

	<table id="tb" class="easyui-datagrid" style="width: 100%;"
		data-options="url:'<c:url value='/manager/dept/degrees'/>',fitColumns:true,singleSelect:true,toolbar:'#tt'"
		title="学历管理">
		<thead>
			<tr>
				<th data-options="field:'degrees_id',hidden:true">ID</th>
				<th data-options="field:'degrees_name',width:40">学位</th>
				<th data-options="field:'degrees_desc',width:100">描述</th>
			</tr>
		</thead>
	</table>



	<!-- 以下是添加页面 -->
	<div id="AddDialog" class="easyui-dialog" title="增加学历"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#bb'">
		<form id="Add" method="post">
			<table>
				<tr>
					<td>学历：</td>
					<td><input class="easyui-textbox" type="text"
						name="degrees_name" /></td>
				</tr>
				<tr>
					<td>描述：</td>
					<td><input class="easyui-textbox" type="text"
						name="degrees_desc" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="bb">
		<a href="#" style="width: 80px" class="easyui-linkbutton"
			id="EnterBtn">确定</a> <a href="#" class="easyui-linkbutton"
			style="width: 80px" id="ClsBtn1">取消</a>
	</div>
	<!-- 以下是修改的页面 -->
	<div id="UpdDialog" class="easyui-dialog" title="修改学历"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#Bb'">
		<form id="Upd" method="post">
			<table>
				<tr>
					<td>学历：</td>
					<td><input class="easyui-textbox" type="text"
						name="degrees_name" /></td>
				</tr>
				<tr>
					<td>描述：</td>
					<td><input class="easyui-textbox" type="text"
						name="degrees_desc" data-options="required:true" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="Bb">
		<a href="#" style="width: 80px" class="easyui-linkbutton" id="SureBtn">确定</a>
		<a href="#" class="easyui-linkbutton" style="width: 80px" id="ClsBtn">取消</a>

	</div>

</body>
<script type="text/javascript">
	//增加
	$("#btnAdd").click(function() {
		$("#AddDialog").dialog("open");
	});

	$("#EnterBtn").click(function() {
		var param = $("#Add").serialize();
		$.post("<c:url value='/manager/dept/degrees?cmd=save'/>", param, function(data) {
			$('#tb').datagrid('appendRow', data);
			$("#AddDialog").dialog("close");
		}, "json");
	});
	$("#ClsBtn1").click(function() {
		$("#AddDialog").dialog("close");
	});

	//修改
	$("#btnUpd").click(function() {
		var row = $("#tb").datagrid("getSelected");
		if (!row) {
			$.messager.alert('提示', '请选择你修改的行', 'info');
			return false;
		}
		$("#UpdDialog").dialog("open");
		$("#Upd").form('load', row);
	});

	$("#SureBtn").click(function() {
		var param = $("#Upd").serialize();
		//alert(param);
		var row = $("#tb").datagrid("getSelected");
		param += "&degrees_id=" + row.degrees_id;
		$.post("<c:url value='/manager/dept/degrees?cmd=update'/>", param, function(data) {
			$("#UpdDialog").dialog("close");
			$("#tb").datagrid("reload");
		}, "json");
	});
	$("#ClsBtn").click(function() {
		$("#UpdDialog").dialog("close");
	});
	//删除
	$("#btnDel").click(function() {
		var row = $("#tb").datagrid("getSelected");
		var inde = $("#tb").datagrid("getRowIndex", row);
		if (!row) {
			$.messager.alert('提示', '请选择你要删除的行', 'info');
			return false;
		}
		$.messager.confirm("提示", "确定要删除吗?", function(ok) {
			if (ok) {
				$.post("<c:url value='/manager/dept/degrees?cmd=delete'/>", {
					"degrees_id" : row.degrees_id
				}, function(data) {
					if (data !=0) {
						$("#tb").datagrid("deleteRow", inde);
					} else {
						$.messager.alert('提示', '失败!', "error");
					}
				}, "text");
			}
		});
	});
</script>
</html>