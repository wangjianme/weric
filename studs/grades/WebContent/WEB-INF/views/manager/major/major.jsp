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
</head>
<body>
	<table id="tt" data-options="toolbar:'#ft'"></table>
	<div id="ft" style="padding: 2px 5px;">
		<a href="javascript:void(0)"
			onclick="javascript:$('#addPanal').panel('open')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">增加</a> <a
			href="javascript:update()" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true">修改</a> <a href="javascript:remove()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a> <a
			href="javascript:reject()" class="easyui-linkbutton"
			iconCls="icon-undo" plain="true">撤销</a> <a href="javascript:save()"
			class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
		<span id="tb" style=" padding: 2px 5px; float: right;">
			学院: <input id="dept" class="easyui-combobox" name="dept"
			style="width: 100px"
			data-options="valueField:'id',textField:'major',url:'<c:url value='/manager/major?cmd=selectDept'/>'" />
			专业: <input id="major" class="easyui-combobox" name="dept"
			style="width: 100px"
			data-options="valueField:'id',textField:'major',url:'<c:url value='/manager/major?cmd=selectMajor'/>'" />
			<a href="javascript:search()" class="easyui-linkbutton"
			iconCls="icon-search">Search</a>
		</span>
	</div>
	<div id="addPanal" class="easyui-dialog" title="增加专业"
		style="width: 400px; height: 220px;"
		data-options="iconCls:'icon-add', closed: true,resizable:true,modal:true">
		<div align="center" style="margin: 30px;">
			学院： <input id="dept2" class="easyui-combobox" name="dept"
				style="width: 160px" editable="false"
				data-options=" required:true,valueField:'id',textField:'major',url:'<c:url value='/manager/major?cmd=selectDept'/>'" /><br>
			<br> 专业： <input id="major2" class="easyui-textbox" name="dept"
				data-options="required:true" style="width: 160px;" /><br> <br>
			描述： <input id="dsc2" class="easyui-textbox" name="dsc"
				data-options="required:true" missingMessage="可以为空"
				style="width: 160px;" />
		</div>
		<div data-options="region:'south',border:false"
			style="text-align: right; margin-right: 5px;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
				href="javascript:void(0)" onclick="javascript:ensure()"
				style="width: 80px">确定</a> <a class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
				onclick="javascript:$('#addPanal').panel('close')"
				style="width: 80px">取消</a>
		</div>
	</div>
	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div data-options="name:'add',iconCls:'icon-add'" onclick="add()">增加</div>
		<div data-options="name:'update',iconCls:'icon-edit'"
			onclick="update()">修改</div>
		<!-- <div data-options="name:'remove',iconCls:'icon-remove'"
			onclick="remove()">删除</div> -->
		<div data-options="name:'reject',iconCls:'icon-undo'"
			onclick="reject()">撤销</div>
		<div data-options="name:'save',iconCls:'icon-save'" onclick="save()">保存</div>
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
		$("#seh").click(function() {
			if ($("#tb").css("display") == "none") {
				$("#tb").css("display", "block");
			} else {
				$("#tb").css("display", "none");
			}
		});
		ensure = function() {
			var dept = $('#dept2').combo('getText');
			var major = $("#major2").val();
			var dsc = $("#dsc2").val();

			if (dept == "") {
				alert("学院不能为空！");
				return;
			}
			if (major == "") {
				alert("专业不能为空！");
				return;
			}
			$.post("<c:url value='/manager/major?cmd=save'/>", {
				"major" : major,
				"dsc" : dsc,
				"groupName" : dept
			}, function(data) {
				if (data != 0) {
					$("#tt").datagrid('reload');
					$('#addPanal').panel('close');
				} else {
					$.messager.alert('提示', '增加失败!', "error");
				}
			}, "text");

		}

		search = function() {
			var deptVal = $('#dept').combo('getValue');
			var majorVal = $('#major').combo('getValue');
			$('#tt')
					.datagrid(
							{
								url : "<c:url value='/manager/major?cmd=select&deptVal="
										+ deptVal
										+ "&majorVal="
										+ majorVal
										+ "'/>"
							});
		}
		reject = function() {
			var node = $('#tt').treegrid('getSelected');

			$('#tt').datagrid('rejectChanges');
		}
		update = function() {
			var row = $("#tt").datagrid("getSelected");//row = null - false
			var inde = $('#tt').datagrid('getRowIndex', row)
			if (!row) {
				$.messager.alert('提示', '请选择你要修改的行', 'info');
				return false;
			}

			$('#tt').datagrid('endEdit', inde);
			$('#tt').datagrid('beginEdit', inde);

		}

		save = function() {
			var row = $("#tt").datagrid("getSelected");
			var inde = $('#tt').datagrid('getRowIndex', row)
			if (!row) {
				return false;
			}
			$('#tt').datagrid('acceptChanges');
			var major = row.major;
			var dsc = row.dsc;
			var id = row.id;
			//alert(row.groupName);
			$.post("<c:url value='/manager/major?cmd=save'/>", {
				"major" : major,
				"dsc" : dsc,
				"groupName" : row.groupName,
				"id" : id
			}, function(data) {
				if (data != 0) {

				} else {
					$.messager.alert('提示', '保存失败!', "error");
				}
			}, "text");
		}

		remove = function() {
			var row = $("#tt").datagrid("getSelected");
			var inde = $('#tt').datagrid('getRowIndex', row)
			if (!row) {
				$.messager.alert('提示', '请选择你要删除的行', 'info');
				return false;
			}
			$.messager.confirm("提示", "确定要删除吗?", function(ok) {
				if (ok) {

					$.ajax({
						type : "POST",
						url : "<c:url value='/manager/major?cmd=delete'/>",
						dataType : "text",
						 data: "id="+row.id,
						success : function(dat) {
							//alert(dat)
							if (dat != 0) {
								$('#tt').datagrid('deleteRow', inde);
							} else {
								$.messager.alert('提示', '失败!请先去 删除有关该专业的信息', "error");
							}
						},
						error : function() {
							alert("请先去 删除有关该专业的信息");
						}
					});

					/* $.post("<c:url value='/manager/major?cmd=delete'/>", {
						"id" : row.id
					}, function(data) {
						aler(data)
						if (data != 0) {
							$('#tt').datagrid('deleteRow', inde);
						} else {
							$.messager.alert('提示', '失败!', "error");
						}
					}, "text"); */

				}
			});

		}
		add = function() {
			var row = $("#tt").datagrid("getSelected");
			var inde = $('#tt').datagrid('getRowIndex', row)
			if (!row) {
				$.messager.alert('提示', '请选择中行(即：添加的位置！)', 'info');
				return false;
			}
			$('#tt').datagrid('insertRow', {
				index : inde + 1,
				row : {
					groupName : row.groupName,
					id : "",
					major : "",
					dsc : ""
				}
			});
			$('#tt').datagrid('selectRow', inde + 1);
			$('#tt').datagrid('endEdit', inde + 1);
			$('#tt').datagrid('beginEdit', inde + 1);

		}

	});

	$.extend($.fn.datagrid.methods, {
		editCell : function(jq, param) {
			return jq.each(function() {
				var opts = $(this).datagrid('options');
				var fields = $(this).datagrid('getColumnFields', true).concat(
						$(this).datagrid('getColumnFields'));
				for (var i = 0; i < fields.length; i++) {
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor1 = col.editor;
					if (fields[i] != param.field) {
						col.editor = null;
					}
				}
				$(this).datagrid('beginEdit', param.index);
				var ed = $(this).datagrid('getEditor', param);
				if (ed) {
					if ($(ed.target).hasClass('textbox-f')) {
						$(ed.target).textbox('textbox').focus();
					} else {
						$(ed.target).focus();
					}
				}
				for (var i = 0; i < fields.length; i++) {
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor = col.editor1;
				}
			});
		},
		enableCellEditing : function(jq) {
			return jq.each(function() {
				var dg = $(this);
				var opts = dg.datagrid('options');
				opts.oldOnClickCell = opts.onClickCell;
				opts.onClickCell = function(index, field) {
					if (opts.editIndex != undefined) {
						if (dg.datagrid('validateRow', opts.editIndex)) {
							dg.datagrid('endEdit', opts.editIndex);
							opts.editIndex = undefined;
						} else {
							return;
						}
					}
					dg.datagrid('selectRow', index).datagrid('editCell', {
						index : index,
						field : field
					});
					opts.editIndex = index;
					opts.oldOnClickCell.call(this, index, field);
				}
			});
		}
	});

	$(function() {
		$('#tt').datagrid().datagrid('enableCellEditing');
	})
	$(function() {
		$('#tt').datagrid({
			title : '专业设置',
			width : "100%",
			remoteSort : false,
			nowrap : false,
			fitColumns : true,
			singleSelect : true,
			url : "<c:url value='/manager/major'/>",
			columns : [ [ {
				field : 'department',
				title : '学院',
				width : 30
			}, {
				field : 'id',
				title : 'id',
				width : 30,
				hidden : true
			}, {
				field : 'major',
				title : '专业',
				width : 30,
				align : 'left',
				editor : 'textbox'
			}, {
				field : 'dsc',
				title : '说明',
				width : 80,
				align : 'left',
				editor : 'textbox'
			} ] ],
			groupField : 'groupName',
			view : groupview,
			groupFormatter : function(value, rows) {
				return "<b style='color: #419641;'>" + value + "</b>";
			}
		});
	});
</script>
</html>

