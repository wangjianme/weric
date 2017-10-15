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
	var editingId;
	function edit() {
		var row = $('#tg').treegrid('getSelected');
		if (!row) {
			$.messager.alert('提示', '请先选择行！', 'info');
			return;
		}
		if (row.state == 'closed') {
			$.messager.alert('提示', '只能修改课程信息！', 'info');
			return;
		}
		if (row) {
			editingId = row.id
			$('#tg').treegrid('beginEdit', editingId);
		}
	}
	function save() {

		if (editingId != undefined) {
			var t = $('#tg');
			t.treegrid('endEdit', editingId);
			var row = $('#tg').treegrid('getSelected');
			var sign = row.sign;
			//alert(sign)
			var courseid = row.id;
			var name = row.name;
			var must = row.must;
			var score = row.score;
			var dsc = row.dsc;
			/* alert(sign)
			alert(courseid)
			alert(name)
			alert(must)
			alert(score)
			alert(dsc) */
			$.post("<c:url value='/manager/course?cmd=update'/>", {
				"courseid" : courseid,
				"name" : name,
				"sign" : sign,
				"must" : must,
				"score" : score,
				"dsc" : dsc

			}, function(data) {
				if (data == 0) {
					alert("更新失败!");
				}
			}, "text");

		}
	}
	function cancel() {
		if (editingId != undefined) {
			$('#tg').treegrid('cancelEdit', editingId);
			editingId = undefined;
			$('#tg').treegrid('remove', timestamp);
		}
	}
</script>
</head>
<body>
	<div id='loadingDiv'
		style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: white; text-align: center;">
		<h1 style="top: 48%; position: relative;">
			<font color="#15428B">努力加载中···</font>
		</h1>
	</div>
	<div id="ft">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="append()">增加</a>
		<a href="javascript:remove()" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true">删除</a> <a
			href="javascript:void(0)" class="easyui-linkbutton" onclick="edit()"
			data-options="iconCls:'icon-edit',plain:true">修改</a> <a
			href="javascript:void(0)" class="easyui-linkbutton" onclick="save()"
			data-options="iconCls:'icon-save',plain:true">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="cancel()" data-options="iconCls:'icon-undo',plain:true">撤销</a>
		<%-- <span id="tb" style="padding: 2px 5px; float: right;"> 课程性质: <select
			id="cc00" class="easyui-combobox" name="cc" style="width: 100px">
				<option value="1">必修课</option>
				<option value="0">选修课</option>
				<option value="2">公开课</option>
		</select> 学院: <input id="dept00" class="easyui-combobox" name="dept"
			style="width: 100px"
			data-options="valueField:'id',textField:'major',url:'<c:url value='/manager/major?cmd=selectDept'/>'" />
			专业: <input id="major00" class="easyui-combobox" name="major"
			style="width: 100px"
			data-options="valueField:'id',textField:'major',url:'#'" /> 年级: <input
			id="grade00" class="easyui-combobox" name="name" style="width: 100px"
			data-options="valueField:'id',textField:'name',url:'<c:url value='/manager/grade?cmd=selectGrade'/>'" />
			课程: <input id="course00" class="easyui-textbox" name="course"
			style="width: 100px" /> <a href="javascript:search()"
			class="easyui-linkbutton" iconCls="icon-search">Search</a>
		</span> --%>
	</div>
	<table id="tg" title="课程设置" style="width: 100%;"
		class="easyui-treegrid"
		data-options="
			toolbar:'#ft',
				animate: true,
				collapsible: true,
				fitColumns: true,
				url: '<c:url value="/manager/course"/>',
				method: 'get',
				idField: 'id',
				treeField: 'dmg',
				pagination: true,
				pageSize: 10,
				pageList: [5,10,15,20]
			">
		<thead>
			<tr>
				<th data-options="field:'dmg'">学院/专业/年级</th>
				<th data-options="field:'sign',hidden:true"></th>
				<th data-options="field:'name',width:60,editor:'textbox'">课程名称</th>
				<th
					data-options="field:'must',width:60,editor:'textbox',formatter:formatMust">必修/选修</th>
				<th data-options="field:'pub',width:60,formatter:formatPub">公开课</th>
				<th data-options="field:'score',width:30,editor:'textbox'">学分</th>
				<th data-options="field:'dsc',width:80,editor:'textbox'">课程描述</th>
			</tr>
		</thead>
	</table>


	<div id="addPanal" class="easyui-dialog" title="增加课程"
		style="width: 330px;"
		data-options="iconCls:'icon-add', closed: true,resizable:true,modal:true,buttons:'#bb'">
		<div align="center" style="margin: 10px;">
			<form id="ff" method="post">
				<table>
					<tr>
						<td>学院名称：</td>
						<td><input id="dept" class="easyui-combobox" name="deptid"
							editable="false"
							data-options=" required:true,valueField:'id',textField:'major',url:'<c:url value='/manager/major?cmd=selectDept'/>'" />
						</td>
					</tr>
					<tr>
						<td>专业名称：</td>
						<td><input id="major" class="easyui-combobox" name="majorid"
							editable="false"
							data-options="required:true ,valueField:'id',textField:'major',url:'#'" />
						</td>
					</tr>
					<tr>
						<td>年级名称：</td>
						<td><input id="grade" class="easyui-combobox" name="gradeid"
							editable="false"
							data-options="required:true ,valueField:'id',textField:'major',url:'<c:url value='/manager/course?cmd=selectGrade'/>'" />
						</td>
					</tr>
					<tr>
						<td>课程名称：</td>
						<td><input id="name " class="easyui-textbox" name="name"
							data-options="required:true" /></td>
					</tr>
					<tr>
						<td>课程学分：</td>
						<td><input id="score" class="easyui-numberbox" name="score"
							data-options="required:true " missingMessage="不可以为0" /></td>
					</tr>
					<tr>
						<td>课程描述：</td>
						<td><input id="dsc" class="easyui-numberbox" name="dsc" /></td>
					</tr>
					<tr>
						<td>课程性质：</td>
						<td><div align="center">
								必修课<input type="radio" value="1" name="must" /> 选修课<input
									type="radio" value="0" name="must" />
							</div></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="addPanal2" class="easyui-dialog" title="选择增加课程的类型"
		style="width: 330px;"
		data-options="iconCls:'icon-add', closed: true,resizable:true,modal:true,buttons:'#bb2'">
		<div align="center" style="margin: 10px;">
			<form id="ff" method="post">
				<table>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;公开课：</td>
						<td><div align="center">
								<input type="radio" value="0" name="must0" />
							</div></td>
					</tr>
					<tr>
						<td>必修课/选修课：</td>
						<td><div align="center">
								<input type="radio" value="1" name="must0" />
							</div></td>
					</tr>
				</table>
			</form>
		</div>
	</div>


	<div id="bb2">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="javascript:ensure2()"
			style="width: 80px">确定</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
			onclick="javascript:$('#addPanal2').panel('close')"
			style="width: 80px">取消</a>
	</div>
	<div id="bb">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="javascript:ensure()"
			style="width: 80px">确定</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
			onclick="javascript:$('#addPanal').panel('close')"
			style="width: 80px">取消</a>
	</div>
	</div>
	<script type="text/javascript">
		(function($) {
			function pagerFilter(data) {
				if ($.isArray(data)) { // is array  
					data = {
						total : data.length,
						rows : data
					}
				}
				var target = this;
				var tg = $(target);
				var state = tg.data('treegrid');
				var opts = tg.treegrid('options');
				if (!state.allRows) {
					state.allRows = data.rows;
				}
				if (!opts.remoteSort && opts.sortName) {
					var names = opts.sortName.split(',');
					var orders = opts.sortOrder.split(',');
					state.allRows
							.sort(function(r1, r2) {
								var r = 0;
								for (var i = 0; i < names.length; i++) {
									var sn = names[i];
									var so = orders[i];
									var col = $(target).treegrid(
											'getColumnOption', sn);
									var sortFunc = col.sorter
											|| function(a, b) {
												return a == b ? 0 : (a > b ? 1
														: -1);
											};
									r = sortFunc(r1[sn], r2[sn])
											* (so == 'asc' ? 1 : -1);
									if (r != 0) {
										return r;
									}
								}
								return r;
							});
				}
				var topRows = [];
				var childRows = [];
				$.map(state.allRows, function(row) {
					row._parentId ? childRows.push(row) : topRows.push(row);
					row.children = null;
				});
				data.total = topRows.length;
				var pager = tg.treegrid('getPager');
				pager.pagination('refresh', {
					total : data.total,
					pageNumber : opts.pageNumber
				});
				opts.pageNumber = pager.pagination('options').pageNumber || 1;
				var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
				var end = start + parseInt(opts.pageSize);
				data.rows = topRows.slice(start, end).concat(childRows);
				return data;
			}

			var appendMethod = $.fn.treegrid.methods.append;
			var removeMethod = $.fn.treegrid.methods.remove;
			var loadDataMethod = $.fn.treegrid.methods.loadData;
			$
					.extend(
							$.fn.treegrid.methods,
							{
								clientPaging : function(jq) {
									return jq
											.each(function() {
												var tg = $(this);
												var state = tg.data('treegrid');
												var opts = state.options;
												opts.loadFilter = pagerFilter;
												var onBeforeLoad = opts.onBeforeLoad;
												opts.onBeforeLoad = function(
														row, param) {
													state.allRows = null;
													return onBeforeLoad.call(
															this, row, param);
												}
												var pager = tg
														.treegrid('getPager');
												pager
														.pagination({
															onSelectPage : function(
																	pageNum,
																	pageSize) {
																opts.pageNumber = pageNum;
																opts.pageSize = pageSize;
																pager
																		.pagination(
																				'refresh',
																				{
																					pageNumber : pageNum,
																					pageSize : pageSize
																				});
																tg
																		.treegrid(
																				'loadData',
																				state.allRows);
															}
														});
												tg.treegrid('loadData',
														state.data);
												if (opts.url) {
													tg.treegrid('reload');
												}
											});
								},
								loadData : function(jq, data) {
									jq
											.each(function() {
												$(this).data('treegrid').allRows = null;
											});
									return loadDataMethod.call(
											$.fn.treegrid.methods, jq, data);
								},
								append : function(jq, param) {
									return jq
											.each(function() {
												var state = $(this).data(
														'treegrid');
												if (state.options.loadFilter == pagerFilter) {
													$
															.map(
																	param.data,
																	function(
																			row) {
																		row._parentId = row._parentId
																				|| param.parent;
																		state.allRows
																				.push(row);
																	});
													$(this).treegrid(
															'loadData',
															state.allRows);
												} else {
													appendMethod
															.call(
																	$.fn.treegrid.methods,
																	$(this),
																	param);
												}
											})
								},
								remove : function(jq, id) {
									return jq
											.each(function() {
												if ($(this)
														.treegrid('find', id)) {
													removeMethod
															.call(
																	$.fn.treegrid.methods,
																	$(this), id);
												}
												var state = $(this).data(
														'treegrid');
												if (state.options.loadFilter == pagerFilter) {
													for (var i = 0; i < state.allRows.length; i++) {
														if (state.allRows[i][state.options.idField] == id) {
															state.allRows
																	.splice(i,
																			1);
															break;
														}
													}
													$(this).treegrid(
															'loadData',
															state.allRows);
												}
											})
								},
								getAllRows : function(jq) {
									return jq.data('treegrid').allRows;
								}
							});

		})(jQuery);

		function formatMust(value) {
			if (value == 0) {
				return "选修";
			}
			if (value == 1) {
				return "必修";
			}
		}
		function formatPub(value) {
			if (value == 0) {
				return "否";
			}
			if (value == 1) {
				return "是";
			}
		}

		$(function() {
			$('#tg').treegrid().treegrid('clientPaging');
		})
	</script>
</body>
<script type="text/javascript">
	$(function() {
		$("#dept").combobox(
				{
					onSelect : function(record) {
						var deptid = record.id;
						//alert(deptid);

						$('#major').combobox('clear');
						$('#major').combobox(
								'reload',
								"<c:url value='/manager/course?cmd=selectMajor&deptid="
										+ deptid + "'/>");
					}
				});
		$("#dept00").combobox(
				{
					onSelect : function(record) {
						var deptid = record.id;
						//alert(deptid);

						$('#major00').combobox('clear');
						$('#major00').combobox(
								'reload',
								"<c:url value='/manager/course?cmd=selectMajor&deptid="
										+ deptid + "'/>");
					}
				});

		search = function() {
			var cc00=$('#cc00').combo('getValue');
			var dept00=$('#dept00').combo('getValue');
			var major00=$('#major00').combo('getValue');
			var gade00 = $('#grade00').combo('getValue');
			var course00=$('#course00').textbox('getText');
			//alert(0);
			$('#tg').treegrid(
								{
									url : "<c:url value='/manager/course?cmd=search&sign="
											+ cc00
											+ "&deptid="
											+ dept00
											+ "&majorid="
											+ major00
											+ "&gradeid="
											+ grade00
											+ "&name="
											+ course00
											+ "'/>"
								});
						
			 
		}

		$("#cc00").combobox({
			onSelect : function(record) {
				var v = record.value;
				if (v == 2) {
					$('#dept00').combo('clear');
					$('#dept00').combo('readonly');
					$('#major00').combo('clear');
					$('#major00').combo('readonly');
					$('#grade00').combo('clear');
					$('#grade00').combo('readonly');
				} else {
					$('#dept00').combo('readonly', false);
					$('#major00').combo('readonly', false);
					$('#grade00').combo('readonly', false);
				}
			}
		})

		ensure = function() {
			var param = $("#ff").serialize();
			//alert(param);
			$.post(
							"<c:url value='/manager/course?cmd=addCourse'/>",
							param,
							function(data) {
								if (data == 1) {
									$('#tg').treegrid('reload');
									$("#addPanal").window("close");
									var deptid = $("#dept").combo('getValue');
									var majorid = $("#major").combo('getValue');
									var gradeid = $("#grade").combo('getValue');

									$
											.ajax({
												type : "POST",
												url : "<c:url value='/manager/course?cmd=selectmg_id'/>",
												dataType : "text",
												data : "majorid=" + majorid
														+ "&gradeid=" + gradeid,
												success : function(dat) {
													var mg_id = dat;
													//alert(deptid + majorid
													//		+ gradeid)
													$("#tg")
															.treegrid(
																	{
																		onLoadSuccess : function() {
																			$(
																					'#tg')
																					.treegrid(
																							'expand',
																							deptid);
																			$(
																					'#tg')
																					.treegrid(
																							'expand',
																							majorid);
																			$(
																					'#tg')
																					.treegrid(
																							'expand',
																							mg_id);

																		}
																	});
												},
												error : function() {
													alert("展开失败！");
												}

											});
								} else {
									alert("增加失败！");
								}
							}, "text");
		}

		append = function() {
			$("#addPanal2").window("open");
		}
		ensure2 = function() {
			var must = $("input[name='must0']:checked").val();
			if (must == 'undefined') {
				$.messager.alert('提示', '请选择要添加的课程类型！', 'info');
			}
			if (must == 1) {
				$("#addPanal2").window("close");
				$("#addPanal").window("open");
			}
			if (must == 0) {
				timestamp = Date.parse(new Date());
				//alert(timestamp);
				$("#addPanal2").window("close");
				$('#tg').treegrid('append', {
					data : [ {
						id : timestamp,
						sign : timestamp,
						iconCls : 'icon-book_open',
						pub : 1,
						must : "",
						must:"null",
						dmg : "公开课"
					} ]
				});
				$('#tg').treegrid('select', timestamp);
				edit();
			}
			//alert(must)

		}

		remove = function() {
			var row = $("#tg").datagrid("getSelected");
			var inde = $('#tg').datagrid('getRowIndex', row)
			if (!row) {
				$.messager.alert('提示', '请选择你要删除的行', 'info');
				return false;
			}
			
			if (row.state != 'closed') {
				$.messager
						.confirm(
								"提示",
								"确定要删除吗?",
								function(ok) {
									$ .post(
													"<c:url value='/manager/course?cmd=selectparent_id'/>",
													{
														"id" : row.id
													}, function(data) {
														deptid = data.deptid;
														majorid = data.majorid;
													}, "json");
									if (ok) {
										$ .post(
														"<c:url value='/manager/course?cmd=delete'/>",
														{
															"id" : row.id
														},
														function(data) {
																//alert(row.id)
															if (data != 0) {
																$('#tg')
																		.treegrid(
																				'reload');
																$("#tg")
																		.treegrid(
																				{
																					onLoadSuccess : function() {
																						$(
																								'#tg')
																								.treegrid(
																										'expand',
																										deptid);
																						$(
																								'#tg')
																								.treegrid(
																										'expand',
																										majorid);
																						$(
																								'#tg')
																								.treegrid(
																										'expand',
																										row._parentId);

																					}
																				});

															} else {
																$.messager
																		.alert(
																				'提示',
																				'删除失败!',
																				"error");
															}
														}, "text");
									}
								})

			} else {
				$.messager.alert('提示', '只能删除课程信息！', 'info');
			}
		}

	});
</script>
<script type="text/javascript">
	//设置加载信息
	function closeLoading() {
		$("#loadingDiv").fadeOut("fast", function() {
			$(this).remove();
		});
	}
	var no;
	$.parser.onComplete = function() {
		if (no) {
			clearTimeout(no);
		}
		no = setTimeout(closeLoading, 1);
	}
</script>
</html>