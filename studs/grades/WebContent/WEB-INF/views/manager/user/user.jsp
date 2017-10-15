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
<script type="text/javascript">
	function _role(data) {
		var str = "";
		$.each(data, function(indx, ele) {
			str += " " + ele.name;
		});
		return str;
	}
</script>
</head>
<body style="margin: 15px">
	<table id="tb" class="easyui-datagrid" style="width: 100%;"
		title="用户管理" toolbar="#td" pagination="true"
		data-options="url:'<c:url value='/manager/user?cmd=execute'/>',method:'get',fitColumns:true,singleSelect:false">
		<thead>
			<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'roles',formatter:_role">角色</th>
			</tr>
		</thead>
	</table>
	<div id="td">
		<a id="addUser" class="easyui-linkbutton" plain="true"
			data-options="iconCls:'icon-add'">增加用户</a> <a id="addTeacher"
			class="easyui-linkbutton" plain="true"
			data-options="iconCls:'icon-add'">教师增加用户</a> <a id="delUser"
			class="easyui-linkbutton" plain="true"
			data-options="iconCls:'icon-remove'">删除用户</a> <a id="assignRole"
			class="easyui-linkbutton" plain="true"
			data-options="iconCls:'icon-edit'">分配角色</a> <a id="editUser"
			class="easyui-linkbutton" plain="true"
			data-options="iconCls:'icon-edit'">管理用户</a>
	</div>

	<div id="addDialog" class="easyui-dialog" title="添加用户"
		style="width: 300px; height: 150px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#add'">
			<form id="addForm">
			<table>
				<thead>
					<tr>
						<td>姓名：</td>
						<td><input class="easyui-textbox" type="text" name="name"
							data-options="iconCls:'icon-man'" /></td>
					</tr>
					<tr>
						<td>初始密码：</td>
						<td><input class="easyui-textbox" type="password" name="pwd" /></td>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			</form>
	</div>
	<div id="add">
		<a href="#" style="width: 80px" class="easyui-linkbutton" id="addBtn">确定</a>
		<a href="javascript:window.history.go(-1);" class="easyui-linkbutton"
			style="width: 80px">取消</a>
	</div>
	<!--用教师增加用户-->
	<div id="addTeacherDialog" class="easyui-dialog" title="用老师增加用户"
		style="width: 350px; height: 150px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#teacherBtn'">
			<form id="teacherForm">
			<table id="teaTable">
				<thead>
					<tr>
						<td>教师姓名：</td>
						<td><input id="selTeacher" class="easyui-textbox" type="text" name="name"></td>
						<td><a href="#" style="width: 60px" class="easyui-linkbutton" id="selTeacherBtn">查询</a></td>
					</tr>
					<tr>
						<td>初始密码：</td>
						<td><input class="easyui-textbox" type="password" name="pwd"></td>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			</form>
	</div>
	<div id="teacherDialog" class="easyui-dialog" title="教师"
		style="padding: 10px; width: 500px; height: 400px;"
		data-options="resizable:true,modal:true,closed:true,buttons:'#teacherBtn2'">
		<table id="teacherTable" class="easyui-datagrid" style="width:100%;"
		title="教师列表" toolbar="#ted" pagination="true"
		data-options="url:'<c:url value='/manager/user?cmd=queryTeacher'/>',method:'get',fitColumns:true,singleSelect:true">
		<thead>
			<tr>
				<th data-options="field:'id'"></th>
				<th data-options="field:'nm'">教师名</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
		</table>
	</div>
	<div id="teacherBtn2">
		<a href="#" style="width: 80px" class="easyui-linkbutton" id="teacherBtn2True">确定</a>
		<a href="#" class="easyui-linkbutton" id="teacherBtn2False" style="width: 80px">取消</a>
	</div>
	<div id="teacherBtn">
		<a href="#" style="width: 80px" class="easyui-linkbutton" id="teacherBtnTrue">确  定</a>
		<a href="#" class="easyui-linkbutton" style="width: 80px" id="teacherBtnFalse">取  消</a>
	</div>
	<div id="delDialog" class="easyui-dialog" title="删除用户"
		style="width: 300px; height: 150px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#del'">
	</div>
	<div id="del">
		<a href="#" style="width: 80px" class="easyui-linkbutton" id="delBtn">确定</a>
		<a href="javascript:window.history.go(-1);" class="easyui-linkbutton"
			style="width: 80px">取消</a>
	</div>
	
	<!-- 管理用户 -->
<div id="userDialog" class="easyui-dialog" title="管理用户"
		style="padding: 10px; width: 500px; height: 400px;"
		data-options="resizable:true,modal:true,closed:true,buttons:'#eb'">
		<table id="userTable" class="easyui-datagrid" style="padding: 20px;width:400px;"
		title="分配角色" toolbar="#usd" pagination="true"
		data-options="url:'<c:url value='/manager/user?cmd=queryUser'/>',method:'get',fitColumns:true,singleSelect:true">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true"></th>
				<th data-options="field:'name'">角色</th>
				<th data-options="field:'state'">状态</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
		</table>
	</div>

<!--  	<div id="UserDialog" class="easyui-dialog" title="管理用户"
		style="padding: 10px; left: 344px; top: 70px"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#eb'">
			<table>
				<tr>
					<td>用户：</td>
					<td><input id="userList" name="userList" class="easyui-combobox"
					missingMessage="请选择用户" 
					data-options="required:true,editable:false,valueField:'id',textField:'name',url:'<c:url value='/manager/user?cmd=userList'/>'"></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><input class="easyui-textbox" type="text" name="status"/></td>
				</tr>
			</table>
	</div>
	-->
	<div id="eb">
		<a  style="width: 80px" class="easyui-linkbutton" id="sonBtn">启用</a> 
		<a  id="soffBtn" class="easyui-linkbutton" style="width: 80px">停用</a> 
		<a  id="initialize" class="easyui-linkbutton"  style="width: 80px">初始化密码</a>
	</div>
	<div id="assignDialog" class="easyui-dialog" title="分配角色"
		style="padding: 10px; width: 500px; height: 400px;"
		data-options="resizable:true,modal:true,closed:true,buttons:'#ab'">
		<table id="roleTable" class="easyui-datagrid" style="width:100%;"
		title="分配角色" toolbar="#asd" pagination="true"
		data-options="url:'<c:url value='/manager/user?cmd=queryRoles'/>',method:'get',fitColumns:true,singleSelect:false">
		<thead>
			<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th data-options="field:'name'">角色</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
		</table>
	</div>
	<div id="ab">
		<a href="#" style="width: 80px" class="easyui-linkbutton" id="asBtntrue">确定</a>
		<a href="#" class="easyui-linkbutton" id="asBtnfalse"
			style="width: 80px">取消</a>
	</div>
</body>
<script type="text/javascript">
var teachername=null;
var tn=null;
	$(function() {
		//增加用户
		$("#addUser").click(function() {
			$("#addDialog").dialog("open");
		});
		$("#addBtn").click(
				function() {
					var param = $("#addForm").serialize();
					$.post("<c:url value='/manager/user?cmd=save'/>", param,
							function(data) {
								$('#tb').datagrid('appendRow', data);
								$("#addDialog").dialog("close");
							}, "json");
				});
		//用教师增加用户
		$("#addTeacher").click(function() {
			$("#addTeacherDialog").dialog("open");
		});
		$("#selTeacherBtn").click(function(){
			$("#teacherDialog").dialog("open");
		});
		$("#teacherBtn2True").click(function(){
		 teachername =$("#teacherTable").datagrid("getSelected");
		 $("#teacherDialog").dialog("close");
		});
		$("#teacherBtnTrue").click(function(){
			var teacher=$("#teacherForm").serialize();
			teacher+="&name="+teachername.nm;
			alert(teacher);
			$.post("<c:url value='/manager/user?cmd=saveTeacher'/>", teacher,
					function() {
						$("#addTeacherDialog").dialog("close");
						$("#tb").datagrid("reload");
					});
		});
		$("#teacherBtn2False").click(function(){
		$("#teacherDialog").dialog("close");
		});
		$("#teacherBtnFalse").click(function(){
		$("#addTeacherDialog").dialog("close");
		})
		//删除
		$("#delUser").click(
				function() {
					var recoard = $("#tb").datagrid("getSelected");
					if (!recoard) {
						$.messager.alert('提示', '请选择要删除的行!', "error");
						return false;
					}
					//获取id
					$.messager.confirm('提示', '确定要删除吗?', function(r) {
						if (r) {
							var id = recoard.id;
							$.post("<c:url value='/manager/user?cmd=delete'/>",
									{
										id : id
									}, function(data) {
										if (data == "-1") {
											$.messager.alert('提示',
													'存在在菜单关联不能删除!', "error");
										} else if (data == "-2") {
											$.messager.alert('提示',
													'存在在用户的关联不能删除!', "error");
										} else if (data == "1") {
											var index = $("#tb").datagrid(
													"getRowIndex", recoard);
											$("#tb").datagrid("deleteRow",
													index);
										}
									}, "text");
						}
					});

				});
		//分配角色
		$("#assignRole").click(function() {
			var user = $("#tb").datagrid("getSelected");
			if (!user) {
				$.messager.alert('提示', '请选择要分配的用户!', "error");
				return false;
			}
			$("#assignDialog").dialog("open");
		});
		$("#asBtntrue").click(function(){
			var user = $("#tb").datagrid("getSelected");
			var roles = $("#roleTable").datagrid("getSelections");
			if (!roles) {
				$.messager.alert('提示', '请选择要分配的角色!', "error");
				return false;
			}
				var param="";
				for(var i=0;i<roles.length;i++){
					param+="&id="+roles[i].id;
				}
				param="userid="+user.id+param;
			$.post("<c:url value='/manager/user?cmd=assignRole'/>",param,function(){
					$("#assignDialog").dialog("close");
					$("#tb").datagrid("reload");
			});
		});
		$("#asBtnfalse").click(function(){
			$("#assignDialog").dialog("close");
		});
		
		//用户管理
		$("#editUser").click(function() {
			$("#userDialog").dialog("open");
		});
		$("#sonBtn").click(function() {
			var user=$("#userTable").datagrid("getSelected");
			if (!user) {
				$.messager.alert('提示', '请选择修改的用户!', "error");
				return false;
			}
			var para="id="+user.id;
			$.post("<c:url value='/manager/user?cmd=statusOn'/>",para,function(){
				alert("启用");
				$("#userTable").datagrid("reload");
				});
		});
		$("#soffBtn").click(function() {
			var user=$("#userTable").datagrid("getSelected");
			if (!user) {
				$.messager.alert('提示', '请选择修改的用户!', "error");
				return false;
			}
			var para="id="+user.id;
			$.post("<c:url value='/manager/user?cmd=statusOff'/>",para,function(){
				alert("停用");
				$("#userTable").datagrid("reload");
				});
		});
		$("#initialize").click(function(){
			var user=$("#userTable").datagrid("getSelected");
			if (!user) {
				$.messager.alert('提示', '请选择修改的用户!', "error");
				return false;
			}
			var para="id="+user.id;
				$.post("<c:url value='/manager/user?cmd=initialize'/>",para,function(data){
					alert("初始化密码为"+data);
					},"json");
			});
				
	});
</script>
</html>