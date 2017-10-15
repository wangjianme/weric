<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/default/easyui.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/icon.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.easyui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/locale/easyui-lang-zh_CN.js'/>"></script>
<title>教师管理</title>
<script type="text/javascript">
	function _sex(value) {
		if (value == "1") {
			return "男";
		} else {
			return "女";
		}
	}
</script>
</head>
<body>
	<div closed="true">
		<table class="easyui-datagrid" id="userGrid" title="教师信息"
			style="width: 100%"
			data-options="pagination:true,rownumbers : true,singleSelect:true,toolbar:'#tb'">
			<thead>
				<tr>
					<th data-options="field:'id',hidden: true">编号</th>
					<th data-options="field:'nm',width:100,align:'center'">姓名</th>
					<th
						data-options="field:'sex',width:55,align:'center',formatter:_sex">性别</th>
					<th data-options="field:'tel',width:100,align:'center'">联系方式</th>
					<th data-options="field:'institute',width:120,align:'center'">所属学院</th>
					<th data-options="field:'edu',width:100,align:'center'">学历</th>
					<th data-options="field:'rank',width:100,align:'center'">职称</th>
					<th data-options="field:'sub',width:133,align:'center'">专业方向</th>
					<th data-options="field:'date',width:100,align:'center'">入职时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="tb" style="width: 100%; height: 33px">
		<div style="margin-bottom: 20px">
			<!-- 增加教师按钮 -->
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true,iconCls:'icon-add'" id="btnAdd">增加</a>
			<!-- 修改按钮 -->
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true,iconCls:'icon-edit'" id="btnEdit">修改</a>
			<!-- 删除按钮 -->
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="_delete()" data-options="plain:true,iconCls:'icon-remove'"
				id="btnDel">删除</a> <span style="float: right"> <!-- 查询功能 -->
				<tr>
					<td><font color=red>请输入教师姓名:</font></td>
					<td><input name="name" class="easyui-textbox"
						id="_inputTeacher" style="width: 100px; height: 21px"></td>
				</tr>
				<tr>
					<td><font color=red>所属学院:</font></td>
					<td><input id="_deptid" class="easyui-combobox" name="deptid"
						missingMessage="请选择教师所属的学院"
						data-options="editable:false,valueField:'id',textField:'text',url:'<c:url value='/manager/dept/teacher?cmd=institute'/>',method:'get'">
					</td>
				</tr> <a id="search" class="easyui-linkbutton" href="javascript:search()"
				data-options="plain:true,iconCls:'icon-search'"><font color=blue>查询</font>
					<a class="easyui-linkbutton" href="javascript:clear()"
					data-options="plain:true,iconCls:'icon-clear'">重新查询</a> </a>
			</span>

		</div>
	</div>
	<!-- 增加对话框 -->
	<div id="addDialog" class="easyui-dialog" title="增加教师"
		style="padding: 10px; left: 344px; top: 70px"
		data-options="iconCls:'icon-save',resizable:false,modal:true,closed:true">
		<form id="addForm">
			<table>
				<tr>
					<td align="left">姓名:</td>
					<td align="left"><input class="easyui-textbox" id="_name"
						type="text" name="nm" data-options="required:true"
						missingMessage="请输入教师的姓名"></td>
				</tr>
				<tr>
					<td align="left">性别:</td>
					<td align="left"><input type="radio" value="1" name="sex"
						checked="checked">男 <input type="radio" value="0"
						name="sex">女</td>
				</tr>
				<tr>
					<td align="left">联系方式:</td>
					<td align="left"><input id="_tel" class="easyui-numberbox"
						name="tel" type="text" data-options="required:true"
						missingMessage="联系方式不能为空，且必须是11位以内数字"></td>
				</tr>
				<tr>
					<td align="left">所属学院:</td>
					<td align="left"><input id="_ins" class="easyui-combobox"
						name="institute" missingMessage="请选择教师所属的学院"
						data-options="required:true,editable:false,valueField:'id',textField:'text',url:'<c:url value='/manager/dept/teacher?cmd=institute'/>',method:'get'">
					</td>
				</tr>
				<tr>
					<td align="left">学历:</td>
					<td align="left"><input id="_edu" class="easyui-combobox"
						name="edu" missingMessage="请选择教师的学历"
						data-options="required:true,editable:false,valueField:'degrees_id',textField:'degrees_name',url:'<c:url value='/manager/dept/teacher?cmd=education'/>',method:'get'">
					</td>
				</tr>
				<tr>
					<td align="left">职称:</td>
					<td align="left"><input id="_rank" class="easyui-combobox"
						name="rank" missingMessage="请选择教师的职称"
						data-options="required:true,editable:false,valueField:'id',textField:'name',url:'<c:url value='/manager/dept/teacher?cmd=rank'/>',method:'get'">
					</td>
				</tr>
				<tr>
					<td align="left">专业方向:</td>
					<td align="left"><input id="_sub" class="easyui-textbox"
						name="sub" missingMessage="请输入教师的专业方向"
						data-options="required:true"></td>
				</tr>
				<tr>
					<td align="left">入职时间:</td>
					<td align="left"><input id="_dt" class="easyui-datebox"
						name="date" missingMessage="请选择教师的入职时间"
						data-options="required:true,editable:false"></td>
				</tr>
				<tr>
					<td></td>
					<td><a href="#" class="easyui-linkbutton" iconcls="icon-save"
						onclick="_addsave()">保存</a> <a href="#" class="easyui-linkbutton"
						iconcls="icon-cancel" onclick="_close()">关闭</a></td>
				</tr>
			</table>
		</form>
	</div>
	<!--修改对话框 -->
	<div id="editDialog" class="easyui-dialog" title="修改教师"
		style="padding: 10px; left: 344px; top: 70px"
		data-options="iconCls:'icon-save',resizable:false,modal:true,closed:true">
		<form id="editForm">
			<table>
				<tr>
					<td align="left">姓名:</td>
					<td align="left"><input class="easyui-textbox" id="_nm"
						type="text" name="nm" data-options="required:true"
						missingMessage="请输入教师的姓名"></td>
				</tr>
				<tr>
					<td align="left">性别:</td>
					<td align="left"><input id="_sexx" type="radio" value="1"
						name="sex1" checked="checked">男 <input type="radio"
						value="0" name="sex1">女</td>
				</tr>
				<tr>
					<td align="left">联系方式:</td>
					<td align="left"><input id="_tell" class="easyui-numberbox"
						type="text" name="tel" data-options="required:true"
						missingMessage="联系方式不能为空，且必须是11位以内数字"></td>
				</tr>
				<tr>
					<td align="left">所属学院:</td>
					<td align="left"><input id="_deptid1" class="easyui-combobox"
						name="institute" missingMessage="请选择教师所属的学院"
						data-options="required:true,editable:false,valueField:'id',textField:'text',url:'<c:url value='/manager/dept/teacher?cmd=institute'/>',method:'get'">
					</td>
				</tr>
				<tr>
					<td align="left">学历:</td>
					<td align="left"><input id="_degreesid"
						class="easyui-combobox" name="edu" missingMessage="请选择教师的学历"
						data-options="required:true,editable:false,valueField:'degrees_id',textField:'degrees_name',url:'<c:url value='/manager/dept/teacher?cmd=education'/>',method:'get'">
					</td>
				</tr>
				<tr>
					<td align="left">职称:</td>
					<td align="left"><input id="_titleid" class="easyui-combobox"
						name="rank" missingMessage="请选择教师的职称"
						data-options="required:true,editable:false,valueField:'id',textField:'name',url:'<c:url value='/manager/dept/teacher?cmd=rank'/>',method:'get'">
					</td>
				</tr>
				<tr>
					<td align="left">专业方向:</td>
					<td align="left"><input id="_major" class="easyui-textbox"
						name="sub" missingMessage="请输入教师的专业方向"
						data-options="required:true"></td>
				</tr>
				<tr>
					<td align="left">入职时间:</td>
					<td align="left"><input id="_date" class="easyui-datebox"
						name="date" missingMessage="请选择教师的入职时间"
						data-options="required:true,editable:false"></td>
				</tr>
				<tr>
					<td></td>
					<td><a href="#" class="easyui-linkbutton" iconcls="icon-save"
						onclick="_editsave()">保存</a> <a href="#" class="easyui-linkbutton"
						iconcls="icon-cancel" onclick="_close()">关闭</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#btnAdd").click(function() {
			$("#addForm").form("reset");
			$("#addDialog").dialog("open");
		});
	});
	function _addsave() {
		// $("#form :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");

		var params = $("#addForm").serialize();

		// 		alert(params);
		$.post("<c:url value='/manager/dept/teacher?cmd=save'/>", params,
				function(data) {
					$("#addDialog").dialog("close");
					//获取combobox中的数据，添加到data中 
					var ins = $("#_ins").combo("getText");
					//alert(ins);
					data.institute = ins;
					var edu = $("#_edu").combo("getText");
					data.edu = edu;
					var rank = $("#_rank").combo("getText");
					data.rank = rank;
					//将返回的json添加到userGrid
					//	$('#userGrid').datagrid('reload');
					$('#userGrid').datagrid('appendRow', data);
				}, "json");
	}
	function _close() {
		$("#addDialog").dialog("close");
		$("#editDialog").dialog("close");
	}
	function _delete() {
		var row = $("#userGrid").datagrid("getSelected");
		if (!row) {
			$.messager.alert('提示', '请选择你要删除的教师', 'info');
			return false;
		}
		//获取要删除的记录的id
		var id = row.id;
		var param = "id=" + id;
		//alert(param);
		$.messager.confirm("提示", "确定要删除吗?", function(ok) {
			if (ok) {
				$.post("<c:url value='/manager/dept/teacher?cmd=delete'/>",
						param, function(rows) {
							if (rows == "1") {
								var index = $("#userGrid").datagrid(
										"getRowIndex", row);
								$("#userGrid").datagrid("deleteRow", index);
							}
						}, "text");
				$('#userGrid').datagrid('reload');
			}
		});
	}
	$(function() {
		$("#btnEdit").click(function() {
			var row = $("#userGrid").datagrid("getSelected");
			if (row) {
				$("#editDialog").dialog("open");
				//将这一行数据添加到修改页面上、
				$("#editForm").form("load", row);
			} else {
				$.messager.alert('提示', '请选择你要修改的教师', 'info');
				return false;
			}
		});

	});

	//以下是查询的方法
	function search() {
		var nm = $('#_inputTeacher').val();
		var id = $('#_deptid').combo('getValue');
		$('#userGrid').datagrid(
				{
					url : "<c:url value='/manager/dept/teacher?cmd=select&nm="
							+ nm + "&institute=" + id + "'/>"
				});
	}
	function clear() {
		$("#_deptid").combobox("clear");
		$("#_inputTeacher").combobox("clear");
	}
	function _editsave() {
		var row = $("#userGrid").datagrid("getSelected");
		var id = row.id;
		var nm = $("#_nm").textbox("getValue");
		var sex = $('input:radio[name="sex1"]:checked').val();
		var tel = $("#_tell").numberbox("getValue");
		var college = $("#_deptid1").combo("getText");
		var degree = $("#_degreesid").combo("getText");
		var rank = $("#_titleid").combo("getText");
		var major = $("#_major").textbox("getValue");
		var time = $("#_date").datebox("getValue");
		var params = "id=" + id + "&" + "nm=" + nm + "&" + "sex=" + sex + "&"
				+ "tel=" + tel + "&" + "institute=" + college + "&" + "edu="
				+ degree + "&" + "rank=" + rank + "&" + "sub=" + major + "&"
				+ "date=" + time;
		//	alert(params);
		$.post("<c:url value='/manager/dept/teacher?cmd=update'/>", params,
				function(data) {
					$('#userGrid').datagrid('reload');
				});

		$("#editDialog").dialog("close");
	}
</script>
</html>