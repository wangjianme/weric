<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
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
<body style="margin: 15px">
	<table class="easyui-datagrid" id="userGrid" title="班级"
		style="width: 100%;" pagination="true" rownumbers="ture"
		data-options="singleSelect:true,collapsible:true,url:'${pageContext.request.contextPath}/manager/clas',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">用户ID</th>
				<th data-options="field:'name',width:100">班级名称</th>
				<th data-options="field:'des',width:100">班级说明</th>
				<th data-options="field:'gradeid',width:100">年级</th>
				<th data-options="field:'majorid',width:100">专业</th>
				<th data-options="field:'deptid',width:200">学院</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="height: auto">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add'" id="btnAdd">增加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton" onclick="del()"
			data-options="iconCls:'icon-remove'">删除</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit'" id="btnUp">修改</a> <span
			style="float: right"> <input name="name"
			class="easyui-textbox" id="cc" style="width: 100px; height: 24px"
			value="" data-options="prompt: '输入班级名称'">
			<tr>
				<td>所属学院:</td>
				<td><input id="_deptid" class="easyui-combobox" name="deptid"
					missingMessage="请选择班级所属的学院"
					data-options="required:true,editable:false,valueField:'id',textField:'text',url:'<c:url value='/manager/clas?cmd=dept'/>',method:'get'">
				</td>
			</tr> <a id="search" class="easyui-linkbutton" href="javascript:search()"
			data-options="plain:true,iconCls:'icon-search'">查询</a>
		</span>

	</div>
	<!-- 增加时的对话框 -->
	<div id="addDialog" class="easyui-dialog" title="添加班级"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">

		<form id="addForm">
			<table>
				<tr>
					<td>班级名称：</td>
					<td><input id="mc" class="easyui-textbox" name="name">
					</td>
				</tr>
				<tr>
					<td>班级说明：</td>
					<td><input id="sm" class="easyui-textbox" name="des">
					</td>
				</tr>
				<tr>
					<td>年级：</td>
					<td><input class="easyui-combobox" id="_grade" name="gradeid"
						data-options="valueField:'id',editable:false,textField:'name',url:'${pageContext.request.contextPath}/manager/clas?cmd=grade',method:'get'">
					</td>
				</tr>
				<tr>
					<td>专业：</td>
					<td><input id="_major" type="text" name="majorid"
						class="easyui-combobox"
						data-options="valueField:'id',editable:false,textField:'majorVal',url:'${pageContext.request.contextPath}/manager/clas?cmd=major',method:'get'">
					</td>
				</tr>
				<tr>
					<td>学院：</td>
					<td><input id="_dept" type="text" name="deptid"
						class="easyui-combobox"
						data-options="valueField:'id',editable:false,textField:'text',url:'${pageContext.request.contextPath}/manager/clas?cmd=dept',method:'get'">
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
	<div id="editDialog" class="easyui-dialog" title="修改班级"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">

		<form id="editForm">
			<table>
				<tr>
					<td>班级名称：</td>
					<td><input id="_mc" class="easyui-textbox" name="name">
					</td>
				</tr>
				<tr>
					<td>班级说明：</td>
					<td><input id="_sm" class="easyui-textbox" name="des">
					</td>
				</tr>
				<tr>
					<td>年级：</td>
					<td><input class="easyui-combobox" id="grade" name="gradeid"
						data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/manager/clas?cmd=grade',method:'get'">
					</td>
				</tr>
				<tr>
					<td>专业：</td>
					<td><input id="major" type="text" name="majorid"
						class="easyui-combobox"
						data-options="valueField:'id',textField:'majorVal',url:'${pageContext.request.contextPath}/manager/clas?cmd=major',method:'get'">
					</td>
				</tr>
				<tr>
					<td>学院：</td>
					<td><input id="dept" type="text" name="deptid"
						class="easyui-combobox"
						data-options="valueField:'id',textField:'text',url:'${pageContext.request.contextPath}/manager/clas?cmd=dept',method:'get'">
					</td>
				</tr>
				<tr>
					<td></td>
					<td><a href="#"
						class="easyui-linkbutton easyui-linkbutton-primary"
						onclick="editSave();">确定修改</a> <a href="#"
						class="easyui-linkbutton" onclick="_close2();">关闭</a></td>
				</tr>
			</table>
		</form>

	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#btnAdd").click(function() {
			$("#addDialog").dialog("open");
			$('#addForm').form('reset');
		});

	});

	function _close() {
		$("#addDialog").dialog("close");
	}

	function _save() {
		//var param = $("#addForm").serialize();
		var name = $("#mc").val();
		var des = $("#sm").val();
		var gradeid = $("#_grade").combo('getValue');
		var name2 = $("#_grade").combo('getText');
		var majorid = $("#_major").combo('getValue');
		var majorVal = $("#_major").combo('getText');
		var deptid = $("#_dept").combo('getValue');
		var text = $("#_dept").combo('getText');
		//	alert(name+des+gradeid+majorid+deptid);

		$.post("<c:url value='/manager/clas?cmd=save'/>", {
			name : name,
			des : des,
			gradeid : gradeid,
			majorid : majorid,
			deptid : deptid
		}, function(data) {
			$('#userGrid').datagrid('appendRow', {
				name : name,
				des : des,
				gradeid : name2,
				majorid : majorVal,
				deptid : text
			});
		}, "json");
		$("#addDialog").dialog("close");
	}
	//删除
	function del() {
		var row = $("#userGrid").datagrid("getSelected");
		if (!row) {
			$.messager.alert('提示', '请选择你要删除的班级', 'info');
			return false;
		}
		//获取你要删除的记录的id
		var id = row.id;
		var param = "id=" + id;
		$.messager.confirm("提示", "确定要删除吗?", function(ok) {
			if (ok) {
				$.post("<c:url value='/manager/clas?cmd=delete'/>", param,
						function(rows) {
							if (rows == "1") {
								var index = $("#userGrid").datagrid(
										"getRowIndex", row);
								$("#userGrid").datagrid("deleteRow", index);
								$('#userGrid').datagrid('reload');

							}
						}, "text");

			}
		});
	}
	//修改
	$(function() {
		$("#btnUp").click(function() {
			var row = $("#userGrid").datagrid("getSelected");
			if (!row) {
				$.messager.alert('提示', '请选择你要修改的行', 'info');
				return false;
			} else {
				$("#editDialog").dialog("open");
				$("#editForm").form("load", row);
			}
		});
	});
	function editSave() {
		//var param = $("#addForm").serialize();
		var row = $("#userGrid").datagrid("getSelected");
		var name = $("#_mc").val();
		var des = $("#_sm").val();
		var gradeid = $("#grade").combo('getText');
		var majorid = $("#major").combo('getText');
		var deptid = $("#dept").combo('getText');
		id = row.id;
		var param = "id=" + id + "&name=" + name + "&des=" + des + "&gradeid="
				+ gradeid + "&majorid=" + majorid + "&deptid=" + deptid;
		alert(param);
		$.post("<c:url value='/manager/clas?cmd=update'/>", param, function(
				data) {
			$("#editDialog").dialog("close");
			$("#userGrid").datagrid('reload');
		});
	}

	function _close2() {
		$("#editDialog").dialog("close");
	}
	/*查询*/
	function search() {
		var name = $('#cc').val();
		var id = $('#_deptid').combo('getValue');
		$('#userGrid').datagrid(
				{
					url : "<c:url value='/manager/clas?cmd=select&name=" + name
							+ "&deptid=" + id + "'/>"
				});

	}
</script>
</html>