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
<body>
	<table id="dg" class="easyui-datagrid" style="width: 100%;"
		pagination="true" rownumbers="true" title="年级设置"
		data-options="
				singleSelect: true,
				fitColumns:true,
				nowrap:false,
				toolbar: '#tb',
				url: '<c:url value='/manager/grade'/>',
				method: 'get',
				onDblClickCell: onDblClickCell,
				onEndEdit: onEndEdit
			">
		<thead>
			<tr>
				<th data-options="field:'id',width:30,hidden : true">ID</th>
				<th data-options="field:'name',width:30,editor:'textbox'">年级名称</th>
				<th data-options="field:'dt',width:30">录入时间</th>
				<th data-options="field:'dsc',width:80,editor:'textbox'">说明</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="height: auto">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="append()">增加</a>
		<a id="update" href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit'" onclick="update()" plain="true">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
		  <span id="search2" style=" float: right;">
			年级名称:<input id="g" class="easyui-combobox" name="name"
			style="width: 100px"
			data-options="valueField:'id',textField:'name',url:'<c:url value='/manager/grade?cmd=selectGrade'/>'" />
			 录入时间: <input id="d" type="text"
			class="easyui-datebox" style="width: 100px;"> 说明: <input
			id="e" class="easyui-textbox" name="name" style="width: 100px;">
			<a id="search3" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		</span>
	</div>
	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div data-options="name:'add',iconCls:'icon-add'" onclick="append()">增加</div>
		<div data-options="name:'update',iconCls:'icon-edit'" onclick="update()">修改</div>
		<div data-options="name:'reject',iconCls:'icon-undo'" onclick="reject()">撤销</div>
		<div data-options="name:'save',iconCls:'icon-save'" onclick="accept()">保存</div>
		<div class="menu-sep"></div>
		<div data-options="name:'exit'">Exit</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$(document).bind('contextmenu', function(e) {
			e.preventDefault();
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		});
	});

	$(function() {
		$("#search").click(function() {
			if ($("#search2").css("display") == "none") {
				$("#search2").css("display", "block");
			} else {
				$("#search2").css("display", "none");
			}
		});
		update = function() {
			var row = $("#dg").datagrid("getSelected");
			var inde = $('#dg').datagrid('getRowIndex', row)
			//alert(inde);
			if (!row) {
				$.messager.alert('提示', '请选择你要修改的行', 'info');
				return false;
			}
			onDblClickCell(inde);

		}
	});

	var editIndex = undefined;
	function endEditing() {
		if (editIndex == undefined) {
			return true
		}
		if ($('#dg').datagrid('validateRow', editIndex)) {
			$('#dg').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onDblClickCell(index, field) {
		if (editIndex != index) {
			if (endEditing()) {
				$('#dg').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				var ed = $('#dg').datagrid('getEditor', {
					index : index,
					field : field
				});
				if (ed) {
					($(ed.target).data('textbox') ? $(ed.target).textbox(
							'textbox') : $(ed.target)).focus();
				}
				editIndex = index;
			} else {
				setTimeout(function() {
					$('#dg').datagrid('selectRow', editIndex);
				}, 0);
			}
		}
	}

	$("#search3") .click(
					function() {
						var name = $("#g").combo('getText');;
						var dt = $("#d").datebox('getValue');
						var dsc = $("#e").val();
						$('#dg')
								.datagrid(
										{
											url : "<c:url value='/manager/grade?cmd=select&name="
													+ name
													+ "&dt="
													+ dt
													+ "&dsc=" + dsc + "'/>"
										});
					});
	function update() {
		var row = $("#dg").datagrid("getSelected");//row = null - false
		var inde = $('#dg').datagrid('getRowIndex', row)
		if (!row) {
			$.messager.alert('提示', '请选择你要修改的行', 'info');
			return false;
		}
		onDblClickCell(inde);

	}
	function onEndEdit(index, row) {
		var ed = $(this).datagrid('getEditor', {
			index : index,
			field : 'name'
		});
		row.productname = $(ed.target).combobox('getText');
	}
	function append() {
		if (endEditing()) {
			$('#dg').datagrid('appendRow', {
				status : 'P'
			});
			editIndex = $('#dg').datagrid('getRows').length - 1;
			var myDate = new Date();
			var y=myDate.getFullYear();    
			var m=myDate.getMonth()+1;       
			var d=myDate.getDate();  
			var h=myDate.getHours();       
			var mm=myDate.getMinutes();     
			var s=myDate.getSeconds();      
			var dt=y+"-"+m+"-"+d+" "+h+":"+mm+":"+s;
			$('#dg').datagrid('updateRow', {
	            index: editIndex,
	            row: {
	              dt:dt
	            },
	        });
			$('#dg').datagrid('selectRow', editIndex).datagrid('beginEdit',
					editIndex);
			
			
		}
	}
	function accept() {
		if (endEditing()) {
			var row = $("#dg").datagrid("getSelected");
			var inde = $('#dg').datagrid('getRowIndex', row)
			if (inde == -1) {
				return;
			}
			$('#dg').datagrid('acceptChanges');
			var id = row.id;
			//alert(id);
			var name = row.name;
			var dt = row.dt;
			var dsc = row.dsc;
			$.post("<c:url value='/manager/grade?cmd=update'/>", {
				"name" : name,
				"dsc" : dsc,
				"dt" : dt,
				"id" : id
			}, function(data) {
				if (data == 1) {
					//alert(110);
					return;
				}
				if (data == 2) {
					$.messager.alert('提示', '该年级已存在，不可重复添加!', "error");
				} else {
					$.messager.alert('提示', '添加失败!', "error");
				}
			}, "text");
		}
	}
	function reject() {
		$('#dg').datagrid('rejectChanges');
		editIndex = undefined;
	}
</script>
</html>

