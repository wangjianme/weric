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
<script type="text/javascript"
	src="<c:url value='/js/datagrid-groupview.js'/>"></script>
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


	<table id="tb" class="easyui-datagrid" style="width: 100%;"
		title="学生管理" pagination="true"
		data-options="url:'<c:url value='/studs'/>',fitColumns:true,singleSelect:true,toolbar:'#dd'">
		<thead>
			<tr>
				<th data-options="field:'id',checkbox:true">ID</th>
				<th data-options="field:'name',sortable:true">姓名</th>
				<th data-options="field:'sex',formatter:_sex">性别</th>
				<th data-options="field:'age',sortable:true">年龄</th>
				<th data-options="field:'no',sortable:true">学号</th>
				<th data-options="field:'idcard',sortable:true">身份证号</th>
				<th data-options="field:'addr',sortable:true">地址</th>

			</tr>
		</thead>
	</table>
	<div id="dd"
		style="border-bottom: 1px solid gray; padding-bottom: 10px; margin-bottom: 10px">
		<a href="javascript:void(0)" class="easyui-linkbutton "
			data-options="plain:true,iconCls:'icon-add'" id="btnAdd">增加</a> <a
			href="#" class="easyui-linkbutton"
			data-options="plain:true,iconCls:'icon-edit'" id="btnEdit">修改</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="plain:true,iconCls:'icon-remove'" id="btnDelStud">删除</a>
		<span id="sp" style="padding: 2px 5px; float: right;"> 班级: <input
			id="class" class="easyui-combobox" name="class" style="width: 100px"
			data-options="valueField:'id',textField:'name',url:'<c:url value='/studs?cmd=selectClass'/>'" />
			<a href="javascript:search()" class="easyui-linkbutton"
			iconCls="icon-search">Search</a>
		</span>
	</div>
	<!-- 增加界面 -->
	<div id="addDialog" class="easyui-dialog" title="增加学生窗口"
		style="width: 400px; height: 200px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#bb'">
		<form id="ff" method="post">
			<table>
				<tr>
					<td>学生ID：</td>
					<td><input type="text" name="id">
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input class="easyui-textbox" type="text" name="name"
						data-options="iconCls:'icon-man'" missingMessage="用户名必须输入" /></td>
				</tr>
				<tr>
					<td>学号：</td>
					<td><input type="text" name="no">
				</tr>


				<tr>
					<td>身份证号：</td>
					<td><input type="text" name="idcard">
				</tr>

				<tr>
					<td>年龄：</td>
					<td><input type="text" name="age">
				</tr>

				<tr>
					<td>初始密码：</td>
					<td><input type="text" name="pwd">
				</tr>

				<tr>
					<td>地址：</td>
					<td><input type="text" name="addr">
				</tr>

				<tr>
					<td align="left">班级:</td>
					<td ><input id="class" class="easyui-combobox" name="clasid" style="width: 100px"
			data-options="valueField:'id',textField:'name',url:'<c:url value='/studs?cmd=selectClass'/>',method:'get'" />
					</td>
				</tr>


				<tr>
					<td>性别：</td>
					<td><input type="radio" name="_sex" value="1">男 <input
						type="radio" name="_sex" value="0">女</td>
				</tr>

				<tr>
					<td><a href="#" style="width: 80px" class="easyui-linkbutton"
						id="AddBtn">确定增加</a> <a href="#" class="easyui-linkbutton"
						style="width: 80px" id="BackBtn">取消</a></td>
				</tr>
			</table>
		</form>
	</div>

	<!--修改学生窗口  -->
	<div id="editDialog" class="easyui-dialog" title="修改学生窗口"
		style="width: 400px; height: 200px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#bb'">
		<form id="ff2" method="post">
			<table>
				<tr>
					<td>姓名:</td>
					<td><input class="easyui-textbox" id="_name" type="text"
						name="name" data-options="required:true" missingMessage="请输入学生的姓名"></td>
				</tr>
				<tr>
					<td>性别:</td>
					<td><input id="_sex" type="radio" value="1" name="sex1"
						checked="checked">男 <input type="radio" value="0"
						name="sex1">女</td>
				</tr>
				<tr>
					<td>学号:</td>
					<td><input id="_no" class="easyui-textbox" name="no"
						type="text" missingMessage="请输入学生学号"></td>
				</tr>
				<tr>
					<td>身份证号:</td>
					<td><input id="_idcard" class="easyui-textbox" name="idcard"
						missingMessage="请输入学生idcard" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td>年龄:</td>
					<td><input id="_age" class="easyui-textbox" name="age"
						missingMessage="请输入学生年龄" data-options="required:true"></td>
				</tr>
				<tr>
					<td>地址:</td>
					<td><input id="_addr" class="easyui-textbox" name="addr"
						type="text" missingMessage="请输入学生地址"></td>
				</tr>
				<tr>
					<td><a href="#" style="width: 80px" class="easyui-linkbutton"
						id="EditBtn">确定修改</a> <a href="#" class="easyui-linkbutton"
						style="width: 80px" id="BackBtn2">取消</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	$("#btnAdd").click(function() {
		$("#addDialog").dialog("open");
	});

	$("#BackBtn").click(function() {
		$("#addDialog").dialog("close");
	});
	$("#BackBtn2").click(function() {
		$("#editDialog").dialog("close");
	});

	$("#AddBtn").click(function() {
		var param = $("#ff").serialize();
		$.post("<c:url value='/studs?cmd=save'/>", param, function(data) {
			$('#tb').datagrid('appendRow', data);
			$("#addDialog").dialog("close");
		}, "json");
	});

	$("#btnDelStud").click(
			function() {
				var row = $("#tb").datagrid("getSelected");
				if (!row) {
					$.messager.alert('提示', '请选择要删除的行!', "error");
					return false;
				}
				//获取要删除的记录的id
				var id = row.id;
				var param = "id=" + id;
				//alert(param);
				$.messager.confirm('提示', '确定要删除吗?', function(r) {
					if (r) {
						var id = param;
						//alert(param);
						$.post("<c:url value='/studs?cmd=delete'/>", param,
								function(rows) {
									if (rows == "1") {
										var index = $("#tb").datagrid(
												"getRowIndex", row);
										$("#tb").datagrid("deleteRow", index);
									}
								}, "text");

					}
				});

			});
	$("#btnEdit").click(function() {
		var row = $("#tb").datagrid("getSelected");
		if (!row) {
			$.messager.alert('提示', '请选择要修改的行!', "warning");
			return false;
		} else {
			$("#editDialog").dialog("open");
			$("#ff2").form("load", row);
		}
	});
	$("#EditBtn").click(
			function() {
				var row = $("#tb").datagrid("getSelected");
				var id = row.id;
				var name = $("#_name").textbox("getValue");
				//var sex = row.sex1;
				var sex = $('input:radio[name="sex1"]:checked').val();
				var no = $("#_no").textbox("getValue");
				var idcard = $("#_idcard").textbox("getValue");
				var age = $("#_age").textbox("getValue");
				var addr = $("#_addr").textbox("getValue");
				var clasid=$("#class").combo('getValue');
				var param = "id=" + id + "&" + "name=" + name + "&" + "sex="
						+ sex + "&" + "no=" + no + "&" + "idcard=" + idcard
						+ "&" + "age=" + age + "&" + "addr=" + addr+"&"+"clasid="+clasid;
				alert(param);
				//$("#editDialog").dialog("close");
				//alert(id);
				$.post("<c:url value='/studs?cmd=update'/>", param, function(
						data) {
					$("#editDialog").dialog("close");
					$("#tb").datagrid("reload");

				}, "json");
			});
	//查询
	search = function() {
		var clasid = $('#class').combo('getValue');
		$('#tb').datagrid({
			url : "<c:url value='/studs?cmd=select&clasid=" + clasid + "'/>"
		});
	}
</script>
</html>