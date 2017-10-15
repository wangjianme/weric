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
	<script type="text/javascript">
	function search(value) {
		//alert(value)
		$('#titleGrid').datagrid(
				{
					url : "<c:url value='/manager/title?cmd=select&name=" + value + "'/>"
				});
	}
</script>
<title>Insert title here</title>
</head>
<body style="margin: 15px">
	<table class="easyui-datagrid" id="titleGrid" title="职称管理"
		style="width: 100%; height: 300px;" pagination="false"
		rownumbers="true"
		data-options="toolbar:'#titleOper',
		              checkbox:true, 
		              singleSelect:false,
		              collapsible:true,
		              url:'${pageContext.request.contextPath}/manager/title',
		              method:'get',
		              selectOnCheck: true,
		              checkOnSelect: true,
		              toolbar:'#titleOper'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',hidden:true">职称ID</th>
				<th data-options="field:'name',width:200">职称</th>
				<th data-options="field:'des',width:200">说明</th>
			</tr>
		</thead>
	</table>
	<div id="titleOper" style="height: auto">
		<a href="#" id="btnAdd" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true">增加</a> <a href="#"
			id="btnDel" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true">删除</a> <a href="#"
			id="btnUpd" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit',plain:true">修改</a>
			<span style="float: right;"><input class="easyui-searchbox"
			data-options="searcher:search,prompt:'请输入职称'" style="width: 150px"></input></span>
	</div>

	<!-- 增加时的对话框 -->
	<div id="addTitleDialog" class="easyui-dialog" title="增加职称"
		style="padding: 10px;"
		data-options="resizable:true,modal:true,iconCls:'icon-man',closed:true,
		buttons:[{text:'保存',iconCls:'icon-save',handler:function(){_save();}},{text:'关闭',iconCls:'icon-cancel',handler:function(){_cancel();}}]">
		<form id="addTitleForm" method="post">
			<table>
				<tr>
					<td>职称:</td>
					<td><input type="text" class="easyui-textbox" name="name"></td>
				</tr>
				<tr>
					<td>职称说明:</td>
					<td><input type="text" class="easyui-textbox" name="des" data-options="multiline:true" style="height:60px"></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 修改时的对话框 -->
	<div id="updTitleDialog" class="easyui-dialog" title="修改职称"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,iconCls:'icon-man',closed:true,
		buttons:[{text:'确认修改',iconCls:'icon-save',handler:function(){_update();}},{text:'关闭',iconCls:'icon-cancel',handler:function(){_cancelUpd();}}]">
		<form id="updTitleForm">
			<table>
				<tr>
					<td >职称:</td>
					<td><input type="text" class="easyui-textbox" name="name" id="titleName"></td>
				</tr>
				<tr>
					<td >职称说明:</td>
					<td><input type="text" class="easyui-textbox" name="des" id="desc" data-options="multiline:true" style="height:60px">
					</td>

				</tr>
			</table>
		</form>
	</div>

</body>
<script type="text/javascript">
	$(function() {
		//增加职称
		$("#btnAdd").click(function() {
			$("#addTitleDialog").dialog("open");
		});

		_cancel = function() {
			$("#addTitleDialog").dialog("close");
		};

		_save = function() {
			var param = $("#addTitleForm").serialize();
			$.post("<c:url value='/manager/title?cmd=save'/>", param, function(
					data) {
				_cancel();
				$("#titleGrid").datagrid('appendRow', data);
			}, "json");
		};

		//修改职称
		$("#btnUpd").click(function() {
			var row = $("#titleGrid").datagrid("getSelected");
			if (!row) {
				$.messager.alert('提示', '请选择你要修改的行！', 'info');
				return false;
			}else{
			$("#updTitleDialog").dialog("open");
			var name ="" + row.name;
			//alert(name);
			var des = "" + row.des;
			$("#titleName").textbox('setText',name);
			$("#desc").textbox('setText',des);
			}

		});
		
        //关闭修改框
		_cancelUpd = function() {
			$("#updTitleDialog").dialog("close");
		};

		//确认修改
		_update = function() {
			var param = $("#updTitleForm").serialize();
			alert(param);
			var row = $("#titleGrid").datagrid("getSelected");
			var name = row.name
			var des = row.des
			param += "&id=" + row.id;
			$.post("<c:url value='/manager/title?cmd=update'/>",param, function(data) {
				_cancelUpd();
				$("#titleGrid").datagrid('reload');
			}, "json");
		};

		//删除职称
		$("#btnDel").click(
						function() {
							var rows = $("#titleGrid").datagrid("getChecked");
							var id = "";
							for (var i = 0; i < rows.length; i++) {
								id = id + rows[i].id + ";";
							}
							//var row = $("titleGrid").datagrid("getSelected");

							if (!rows) {
								$.messager.alert('提示', "请选择你要删除的行", 'info');
								return false;
							}
							$.messager.confirm("提示","确定要删除吗？",
								function(ok) {
									if (ok) {
										$.post("<c:url value='/manager/title?cmd=delete'/>",
											{"id" : id},function(data) {
																		if (data != 0) {
																			for (var i = 0; i < rows.length; i++) {
																				var inde = $('#titleGrid').datagrid('getRowIndex',rows[i]);
																				$('#titleGrid').datagrid('deleteRow',inde);
																			}
																		} else {
																			$.messager.alert('提示',"失败","error");
																		}
																	}, "text");
												}
											});
			
		});
	});
</script>
</html>