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
<body style="margin: 15px">
	<table id="roleGrid" class="easyui-datagrid"
		style="width: 100%; height: 250px" title="角色管理列表" toolbar="#tb"
		pagination="true"
		data-options="url:'<c:url value='/manager/role'/>',singleSelect:true,fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">Code</th>
				<th data-options="field:'name'">角色名称</th>
			</tr>
		</thead>
	</table>

	<div id="tb">
		<a id="roleAddBtn" class="easyui-linkbutton c8"
			data-options="iconCls:'icon-add'">增加</a> <a
			class="easyui-linkbutton c8" data-options="iconCls:'icon-remove'"
			id="roleDelBtn">删除</a> <a id="roleModBtn"
			class="easyui-linkbutton c8" data-options="iconCls:'icon-edit'">修改</a>
		<a class="easyui-linkbutton c8" data-options="iconCls:'icon-man'"
			id="roleMenuBtn">分配菜单</a>
	</div>
	<div id="addRoleDialog" class="easyui-dialog" title="增加新的角色"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,iconCls:'icon-man',closed:true,
		buttons:[{text:'保存',iconCls:'icon-save',handler:function(){_save();}},{text:'关闭',iconCls:'icon-cancel',handler:function(){_cancel();}}]">
		<form id="addRoleForm">
			<label>角色名称：</label> <input type="text" class="easyui-textbox"
				name="name">
		</form>
	</div>

	<div id="modRoleDialog" class="easyui-dialog" title="修改角色"
		style="padding: 10px;"
		data-options="iconCls:'icon-edit',resizable:true,modal:true,iconCls:'icon-man',closed:true,
		buttons:[{text:'保存',iconCls:'icon-save',handler:function(){_modsave();}},{text:'关闭',iconCls:'icon-cancel',handler:function(){_modcancel();}}]">
		<form id="modRoleForm">
			<input id="id" type="hidden" name="id"> <label>姓名：</label> <input
				id="updateName" class="easyui-textbox" name="name">
		</form>
	</div>



	<div id="roleMenuDialog" class="easyui-dialog" title="分配菜单"
		style="padding: 10px; width: 300px; height: 400px;"
		data-options="iconCls:'icon-man',resizable:true,modal:true,closed:true,
		buttons:[{text:'保存',iconCls:'icon-save',handler:function(){_saveMenu();}},{text:'关闭',iconCls:'icon-cancel',handler:function(){_closeMenu();}}]">
		<ul class="easyui-tree" id="menuTree" checkbox="true"
			data-options="url:'<c:url value='/manager/menu'/>'"></ul>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#roleAddBtn").click(function() {
			$("#addRoleDialog").dialog("open");
		});

		_cancel = function() {
			$("#addRoleDialog").dialog("close");
		};

		_save = function() {
			var param = $("#addRoleForm").serialize();
			$.post("<c:url value='/manager/role?cmd=save'/>", param, function(
					data) {
				_cancel();
				$("#roleGrid").datagrid('appendRow', data);
			}, "json");
		};

		/**
		删除功能*/
		$("#roleDelBtn").click(
				function() {
					var recoard = $("#roleGrid").datagrid("getSelected");
					if (!recoard) {
						$.messager.alert('提示', '请选择要删除的行!', "error");
						return false;
					}
					//获取id
					$.messager.confirm('提示', '确定要删除吗?', function(r) {
						if (r) {
							var id = recoard.id;
							$.post("<c:url value='/manager/role?cmd=del'/>", {
								id : id
							},
									function(data) {
										if (data == "-1") {
											$.messager.alert('提示',
													'存在在菜单关联不能删除!', "error");
										} else if (data == "-2") {
											$.messager.alert('提示',
													'存在在用户的关联不能删除!', "error");
										} else if (data == "1") {
											var index = $("#roleGrid")
													.datagrid("getRowIndex",
															recoard);
											$("#roleGrid").datagrid(
													"deleteRow", index);
										}
									}, "text");
						}
					});

				});

		/**
				修改角色
		 */
		$(function() {
			$("#roleModBtn").click(function() {
				var row = $("#roleGrid").datagrid("getSelected");
				//var inde = $('#roleGrid').datagrid('getRowIndex', row)
				//alert(inde);
				if (!row) {
					$.messager.alert('提示', '请选择你要修改的行', 'info');
					return false;
				}
				var  name=row.name;
				alert(name)
				$("#updateName").textbox("setValue",name);
				$("#modRoleDialog").dialog("open");
				id = row.id

			});

			_modcancel = function() {
				$("#modRoleDialog").dialog("close");
			};

			_modsave = function() {
				var name = $("#updateName").textbox('getText');
				param = "name=" + name + "&id=" + id

				$.post("<c:url value='/manager/role?cmd=update'/>", param,
						function(data) {
							_modcancel();
							//$("#roleGrid").datagrid('appendRow', data);
							var row = $("#roleGrid").datagrid("getSelected");
							var idx = $("#roleGrid").datagrid("getRowIndex",
									row);
							$('#roleGrid').datagrid('updateRow', {
								index : idx,
								row : data.role
							});
						}, "json");
			};

		});

		/**
		分配菜单
		 */
		$("#roleMenuBtn").click(function() {
			var role = $("#roleGrid").datagrid("getSelected");
			if (!role) {
				$.messager.alert('提示', '请先选择要设置的角色!', "error");
				return false;
			}
			
			$("#roleMenuDialog").dialog("open");
			
			var id=role.id
			alert(id)
		});

		_saveMenu = function() {
			//当前选择的角色
			var role = $("#roleGrid").datagrid("getSelected");

			var nodes = $('#menuTree').tree('getChecked',
					[ 'checked', 'indeterminate' ]);
			var param = ""
			for (var i = 0; i < nodes.length; i++) {
				var node = nodes[i];
				if (param == "") {
					param = "id=" + node.id;
				} else {
					param += "&id=" + node.id;
				}
			}
			param += "&roleId=" + role.id;
			$.post("<c:url value='/manager/role?cmd=saveMenu'/>", param,
					function(data) {
						$.messager.alert('提示', '修改成功!', "info", function() {
							_closeMenu();
						});
					}, "text");
		};

		_closeMenu = function() {
			$("#roleMenuDialog").dialog("close");
		};

	});
</script>
</html>