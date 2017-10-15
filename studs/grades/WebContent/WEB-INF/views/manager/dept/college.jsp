<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-menubutton"
				data-options="menu:'#me',iconCls:'icon-add',toggle:true,group:'g2',plain:false">增加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',toggle:true,group:'g2',plain:false"
				onclick="edit()">修改</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',toggle:true,group:'g2',plain:false"
				onclick="del()">删除</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',toggle:true,group:'g2',plain:false"
				onclick="reload()">刷新</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-expanded',toggle:true,group:'g2',plain:false"
				onclick="expandAll()">展开</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-collapsed',toggle:true,group:'g2',plain:false"
				onclick="collapseAll()">收缩</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-search',toggle:true,group:'g2',plain:false"
				onclick="actionSe()">查询</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-undo',toggle:true,group:'g2',plain:false"
				onclick="cancel()" style="display: none" id="undoBtn">撤消编辑</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-save',toggle:true,group:'g2',plain:false"
				onclick="save()" style="display: none" id="saveBtn">保存</a>
		</div>
		
		<div id="se" style="display: none">
			<form id="searchForm">
				<table style="height: 100%; width: 100%;">
					<tr>
						<td class="labelAlignRight">所属机构：</td>
						<td><input id="1" name="parent" style="width: 120px;"
							class="easyui-combobox" data-options="url: '<c:url value='/manager/dept/college?cmd=combotree'/>',valueField:'id',textField:'text',panelHeight:'auto',editable:false,requried:true,value:'全部'"
							type="text"></td>
						<td class="labelAlignRight">机构名称：</td>
						<td><input name="text" id="2" class="easyui-textbox"
							style="width: 120px;" /></td>
						<td class="labelAlignRight">办公地点：</td>
						<td><input name="addr" id="3" class="easyui-textbox"
							style="width: 120px;" /></td>
							<td class="labelAlignRight">办公电话：</td>
						<td><input name="tel" id="4" class="easyui-textbox"
							style="width: 120px;" /></td>
							<td style="width: 70px;"></td>
						<td><a id="btnSearch" href="javascript:void(0);"
							onclick="searchSome()" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" style="width: 73px">查找</a> <a
							id="btnClear" onclick="clearAll()" href="javascript:void(0);"
							class="easyui-linkbutton" data-options="iconCls:'icon-undo'"
							style="width: 73px">重置</a>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<table id="tg" title="齐鲁工业大学学院管理" style="width: 100%; height: 450px"
		data-options="
				collapsible: true,
				fitColumns: true,
				url: '<c:url value='/manager/dept/college'/>',
				method: 'get',
				toolbar:'#tb',
				iconCls:'iconCls',
				idField: 'id',
				treeField: 'text',
				pagination: true,
				pageSize: 10,
				pageList: [10,20,30],
				onContextMenu: onContextMenu,
				onDblClickRow:onDblClickRow,
				loadMsg:'拼命加载中，请稍候...'
				
			">
		<thead>
			<tr>
				<th data-options="field:'text',editor:'text'" width="25%"
					align="left"><center>学院名称</center></th>
				<th data-options="field:'addr',editor:'text'" width="25%"
					align="center">办公地点</th>
				<th data-options="field:'tel',editor:'text'" width="25%"
					align="center">办公电话</th>
				<th data-options="field:'person',editor:'numberbox'" width="25%"
					align="center">人员配置</th>
			</tr>
		</thead>
	</table>

	<div id="me" style="width: 100px;">
		<div data-options="iconCls:'icon-mini-add'" onclick="_addOne()">一级机构</div>
		<div data-options="iconCls:'icon-mini-add'" onclick="_addTwo()">子级机构</div>
	</div>
	<%-- 菜单 --%>
	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div onclick="addChild()" data-options="iconCls:'icon-add'">下级机构</div>
		<div onclick="del()" data-options="iconCls:'icon-remove'">删除当前</div>
		<div onclick="reload()" data-options="iconCls:'icon-reload'">刷新页面</div>
		<div class="menu-sep"></div>

		<div onclick="expandOne()" data-options="iconCls:'icon-expanded'">展开</div>
		<div onclick="collapseOne()" data-options="iconCls:'icon-collapsed'">收缩</div>
	</div>

	<div id="addOne" class="easyui-dialog" title="一级机构"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,iconCls:'icon-add',closed:true,
		buttons:[{text:'保存',iconCls:'icon-save',
		handler:function(){_saveOne();}},
		{text:'关闭',iconCls:'icon-cancel',
		handler:function(){_cancelOne();}}]">
		<form id="addOneForm">
			<table>
				<tr>
					<td><label>机构类别：</label></td>
					<td><select class="easyui-combobox" name="isdept"
						style="width: 150px;"
						data-options="panelHeight:'auto',editable:false,requied:true,editable:false">
							<option value="1">学院</option>
							<option value="0">其他</option>
					</select></td>
				</tr>
				<tr>
					<td><label>机构名称：</label></td>
					<td><input name="iconCls" value="icon-onedept" type="hidden"><input
						type="text" class="easyui-textbox" name="text"
						style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>办公地点：</label></td>
					<td><input type="text" class="easyui-textbox" name="addr"
						style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>办公电话：</label></td>
					<td><input type="text" class="easyui-textbox" name="tel"
						style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>人员标配：</label></td>
					<td><input type="text" class="easyui-textbox" name="person"
						style="width: 150px;"></td>
				</tr>

			</table>
		</form>
	</div>

	<div id="addTwo" class="easyui-dialog" title="子级机构"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,iconCls:'icon-add',closed:true,
		buttons:[{text:'保存',iconCls:'icon-save',handler:function(){_saveTwo();}},
		{text:'关闭',iconCls:'icon-cancel',handler:function(){_cancelTwo();}}]">
		<form id="addTwoForm">
			<table>
				<tr>
					<td><label>机构名称：</label></td>
					<td><input name="iconCls" value="icon-twodept" type="hidden">
						<input name="isdept" value="0" type="hidden"><input
						type="text" class="easyui-textbox" name="text"
						style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>上属机构：</label></td>
					<td><input id="cbt" name="parent" class="easyui-combotree"
						data-options="url: '<c:url value='/manager/dept/college?cmd=combotree'/>',
					method:'post',required:true,panelHeight:'150px'"
						style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>办公地点：</label></td>
					<td><input type="text" class="easyui-textbox" name="addr"
						style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>办公电话：</label></td>
					<td><input type="text" class="easyui-textbox" name="tel"
						style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>人员标配：</label></td>
					<td><input type="text" class="easyui-textbox" name="person"
						style="width: 150px;"></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="addChild" class="easyui-dialog" title="下级机构"
		style="padding: 10px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,iconCls:'icon-add',closed:true,
		buttons:[{text:'保存',iconCls:'icon-save',handler:function(){saveChild();}},
		{text:'关闭',iconCls:'icon-cancel',handler:function(){cancelChild();}}]">
		<form id="addChildForm">
			<table>
				<tr>
					<td><label>机构名称：</label></td>
					<td><input name="iconCls" value="icon-twodept" type="hidden"><input
						name="isdept" value="0" type="hidden"><input type="text"
						class="easyui-textbox" name="text" style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>办公地点：</label></td>
					<td><input type="text" class="easyui-textbox" name="addr"
						style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>办公电话：</label></td>
					<td><input type="text" class="easyui-textbox" name="tel"
						style="width: 150px;"></td>
				</tr>
				<tr>
					<td><label>人员标配：</label></td>
					<td><input type="text" class="easyui-textbox" name="person"
						style="width: 150px;"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		<%--
         //查询时的二级联动
		var a = $('#1').combobox({
            editable:false,  
            valueField:'id',  
            textField:'text',  
            onSelect: function (record) {
            	 if(record.id=='-1'){
                	 $('#2').combobox('disable');
                	 }
                b.combobox({
                    disabled: false,
                    url: "<c:url value='/manager/dept/college?cmd=combobox&isdept="+record.id+"'/>" ,
                    valueField: 'id',
                    textField: 'text'
                }).combobox('clear');
            }
        });
        var b = $('#2').combobox({
            disabled: true,
            valueField: 'id',
            textField: 'text'
        });
--%>
		//加载树网络
		$(function() {
			$('#tg').treegrid().treegrid('clientPaging');
		})

		//重置按钮点击事件
		clearAll = function() {
			$("#1").combobox("setValue", "全部");
			$("#2,#3,#4").textbox("setValue", "");
		};

		var open = false;
		actionSe = function() {
			if (editIndex != undefined) {
				$.messager.alert("系统提示", "请先结束编辑！");
				return false;
			} else {
				open = !open;
				open == true ? $('#se').show() : $('#se').hide();
			}
		};

		//查询按钮点击事件
		searchSome = function() {
			//$('#se').hide();
			//open = !open;
			//$('#tg').treegrid('loading');
			var param = $("#searchForm").serialize();

			$.post("<c:url value='/manager/dept/college?cmd=search'/>", param,
					function(data) {
						//clearAll();
						var d = data;
						$('#tg').treegrid('loadData', d);
					}, "json");
		};

		//显示右键添加下级机构页面
		addChild = function() {
			$("#addChild").dialog("open");
		};
		//关闭右键添加下级机构页面
		cancelChild = function() {
			$("#addChild").dialog("close");
		};

		//显示添加一级机构页面
		_addOne = function() {
			if (editIndex != undefined) {
				$.messager.alert("系统提示", "请先结束编辑！");
				return false;
			}
			$("#addOne").dialog("open");
		};
		//关闭添加一级机构页面
		_cancelOne = function() {
			$("#addOne").dialog("close");
		};

		//显示添加二级机构页面
		_addTwo = function() {
			if (editIndex != undefined) {
				$.messager.alert("系统提示", "请先结束编辑！");
				return false;
			}
			$("#addTwo").dialog("open");
		};

		//关闭添加子级机构页面
		_cancelTwo = function() {
			$("#addTwo").dialog("close");
		};

		//保存添加的一级机构数据
		_saveOne = function() {
			_cancelOne();
			var param = $("#addOneForm").serialize();
			$.post("<c:url value='/manager/dept/college?cmd=save'/>", param,
					function(data) {
						$('#tg').treegrid('reload');
						$('#cbt').combotree('reload');
					}, "json");

		};
		//保存添加的子级机构数据
		_saveTwo = function() {
			_cancelTwo();
			var param = $("#addTwoForm").serialize();
			$.post("<c:url value='/manager/dept/college?cmd=save'/>", param,
					function(data) {
						//$('#tg').treegrid('append',{
						//	parent: data.parent,  // the node has a 'id' value that defined through 'idField' property
						//	data: [{
						//	id:data.id,
						//	iconCls:data.iconCls,
						//	text: data.text,
						//	addr:data.addr,
						//	tel:data.tel,
						//	person:data.person
						//}]
						//});

						$('#tg').treegrid('reload');
						var node2 = $('#tg').treegrid('find', id);
						$('#tg').treegrid('expandTo', node2.target).treegrid(
								'select', node2.target);
						$('#cbt').combotree('reload');
					}, "json");

		};
		//保存右键添加的下级机构数据
		saveChild = function() {
			cancelChild();
			var node = $('#tg').treegrid('getSelected');
			var param = $("#addChildForm").serialize();
			param += "&parent=" + node.id;
			alert(param);
			$.post("<c:url value='/manager/dept/college?cmd=save'/>", param,
					function(data) {
						$('#tg').treegrid('append', {
							parent : node.id, // the node has a 'id' value that defined through 'idField' property
							data : [ {
								id : data.id,
								iconCls : data.iconCls,
								text : data.text,
								addr : data.addr,
								tel : data.tel,
								person : data.person
							} ]
						});
						var node2 = $('#tg').treegrid('find', id);
						$('#tg').treegrid('expandTo', node2.target).treegrid(
								'select', node2.target);
						$('#cbt').combotree('reload');
					}, "json");
		};

		//右键响应事件:
		//增加{学院|子机构}：add()
		//删除：del()
		//编辑：edit()
		onContextMenu = function(e, row) {
			e.preventDefault();
			$(this).treegrid('select', row.id);
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}

		//删除选定行
		del = function() {
			var node = $('#tg').treegrid('getSelected');
			if (!node) {
				$.messager.alert('提示', '请选择要删除的行!', "warning");
				return false;
			} else {
				$.messager
						.confirm(
								'提示',
								'确定要删除吗?',
								function(r) {
									if (r) {
										var id = node.id;
										var param = "id=" + id;
										$
												.post(
														"<c:url value='/manager/dept/college?cmd=del'/>",
														param,
														function(data) {
															if (data == "-1") {
																$.messager
																		.alert(
																				'提示',
																				'删除失败了',
																				"error");
															} else if (data == "1") {
																$("#tg")
																		.treegrid(
																				"remove",
																				id);
																$('#tg')
																		.treegrid(
																				'reload',
																				data);
																$('#cbt')
																		.combotree(
																				'reload');
															}
														}, "text");
									}
								});
			}
		}

		//编辑选定行
		var editIndex = undefined;
		edit = function() {

			var node = $('#tg').treegrid('getSelected');
			if (!node) {
				$.messager.alert('提示', '请选择要修改的行!', "warning");
				return false;
			}

			editIndex = node.id;
			$('#tg').treegrid('beginEdit', editIndex);
			$('#saveBtn ,#undoBtn').show();
		}

		//取消编辑
		cancel = function() {
			if (editIndex != undefined) {
				$('#tg').treegrid('cancelEdit', editIndex);
				editIndex = undefined;
				$('#saveBtn ,#undoBtn').hide()
			}
		};

		//保存编辑
		save = function() {
			if (editIndex != undefined) {
				var t = $('#tg');
				t.treegrid('endEdit', editIndex);
				var node = t.treegrid('getSelected');
				$('#saveBtn ,#undoBtn').hide()
				$.post("<c:url value='/manager/dept/college?cmd=upd'/>", {
					id : node.id,
					text : node.text,
					addr : node.addr,
					tel : node.tel,
					person : node.person
				}, function(data) {
					if (data == "-1") {
						$.messager.alert('提示', '更新失败了', "error");
					} else if (data == "1") {
						$('#tg').treegrid('reload', {
							id : node.id
						});
						$('#cbt').combotree('reload');
					}
				}, "text");

				editIndex = undefined;
				var persons = 0;
				var node = t.treegrid('getChildren');
				for (var i = 0; i < node.length; i++) {
					var p = parseInt(node[i].persons);
					if (!isNaN(p)) {
						persons += p;
					}
				}
			}
		}

		//双击点击事件 点击后弹出对话框进行更改操作 或者直接编辑,不能同时操作多行
		onDblClickRow = function(index) {
			if (editIndex != index) {
				//$('#tg').treegrid('endEdit', editIndex);
				$('#tg').treegrid('cancelEdit', editIndex);
				editIndex = undefined;
				$('#saveBtn ,#undoBtn').hide();
			}
			editIndex = index.id;
			$('#tg').treegrid('beginEdit', editIndex);
			$('#saveBtn ,#undoBtn').show();
		}

		//单击它行取消编辑
		//onClickRow = function(index) {
		//if (editIndex != index) {
		//	$('#tg').treegrid('endEdit', editIndex);
		//	$('#saveBtn ,#undoBtn').hide();
		//}
		//}

		//刷新页面
		reload = function() {
			$('#tg').treegrid('reload');
		}

		//收缩节点
		collapseAll = function() {
			$('#tg').treegrid('collapseAll');
		}

		//展开节点
		expandAll = function() {
			$('#tg').treegrid('expandAll');
		}

		collapseOne = function() {
			var node = $('#tg').treegrid('getSelected');
			var id = node.id;
			$('#tg').treegrid('collapse', id);
		}

		expandOne = function() {
			var node = $('#tg').treegrid('getSelected');
			var id = node.id;
			$('#tg').treegrid('expand', id);
		}
		document.onkeydown = function(e) {
			e: e ? e : event
			if (window.event.keyCode == 13) {
				searchSome();
			}
		}
	});
</script>
</html>