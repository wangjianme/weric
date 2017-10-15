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
</head>
<body>
	<table class="easyui-datagrid" id="gradeGrid" title="年级管理"
		style="width: 100%; height: 300px;" pagination="true"
		data-options="toolbar:'#tb',singleSelect:true,collapsible:true,url:'${pageContext.request.contextPath}/manager/grade'">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">年级ID</th>
				<th data-options="field:'name',width:200">年级名称</th>
				<th data-options="field:'dt',width:100">录入时间</th>
				<th data-options="field:'dsc',width:300">说明</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="height: auto">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add'" id="btnAdd">增加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit'" onclick="reject()">修改</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove'" onclick="_del();">删除</a>
	</div>
</body>
<script type="text/javascript">
	function _del() {
		var row = $("#gradeGrid").datagrid("getSelected");//row = null - false
		if (!row) {
			$.messager.alert('提示', '请选择你要删除的行', 'info');
			return false;
		}
		//获取你要删除的记录的id
		var id = row.id;
		var param = "id=" + id;
		$.messager.confirm("提示", "确定要删除吗?", function(ok) {
			if (ok) {
				$.post("<c:url value='/manager/grade?method=del'/>", param,
						function(rows) {
							if (rows == "1") {
								var index = $("#gradeGrid").datagrid(
										"getRowIndex", row);
								$("#gradeGrid").datagrid("deleteRow", index);
							}
						}, "text");
			}
		});
	}
</script>
</html>