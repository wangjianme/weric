 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/locale/easyui-lang-zh_CN.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	
</script>
<script type="text/javascript">
	function _ha(value) {
		if (value == "0") {
			return "<font color='green'>正常考试</font>";
		} else if (value == "1") {
			return "<font color='red'>补考</font>";
		} else if (value == "2") {
			return "<font color='red'>重修</font>";
		} else {
			return "<font color='red'>清考</font>";
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
	<div
		style="border-bottom: 1px solid gray; padding-bottom: 10px; margin-bottom: 10px">
		<a href="#" id="btnAdd" class="easyui-linkbutton c1"
			data-options="iconCls:'icon-add'">增加</a> <a
			href="javascript:update()" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true">修改</a><a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a> <a href="javascript:remove()"
			class="easyui-linkbutton c3">删除</a> <a id="search"
			class="easyui-linkbutton" iconCls="icon-search" plain="true"></a> <span
			id="search2" style="display: none; float: right;"> 课程:<input
			id="g" class="easyui-combobox" name="name" style="width: 100px"
			data-options="valueField:'id',textField:'name',url:'<c:url value='/manager/score?cmd=query1'/>'" />
			 学期:<input id="e" type="text" class="easyui-combobox"
			style="width: 100px;">
							
				
			  <a
			id="search3" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	</div>

	<table id="tb" class="easyui-datagrid" style="width: 100%;"
		pagination="true"
		data-options="url:'<c:url value='/manager/score'/>',fitColumns:true,singleSelect:true">
		<thead>
			<tr>
				<th data-options="field:'studid',width:50,editor:'textbox'" >姓名</th>
				<th data-options="field:'courseid',width:50,editor:'textbox'">课程</th>
				<th data-options="field:'score',width:50,editor:'textbox'">分数</th>
				<th data-options="field:'term',width:50,editor:'textbox'">学期</th>
				<th data-options="field:'datetime',width:50,editor:'textbox'">时间</th>
				<th data-options="field:'type',width:50,formatter:_ha,editor:'textbox'">考试类型</th>
				<th data-options="field:'teacher',width:50,editor:'textbox'">老师</th>
			</tr>
		</thead>
	</table>

	<div id="addDialog" class="easyui-dialog" title="成绩录入系统"
		style="width: 500px; height: 300px;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:'#bb'">
		<form id="ff" method="post">
			<table>
				<tr>
					<td>姓名：</td>
					<td><input id="n" class="easyui-combobox" type="text"
						name="studid"
						data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/manager/score?cmd=query1',method:'get'" /></td>
				</tr>
				<tr>
					<td>课程：</td>
					<td><input id="c" class="easyui-combobox" type="text"
						name="courseid"
						data-options="valueField:'courseid',textField:'name',url:'${pageContext.request.contextPath}/manager/score?cmd=query2',method:'get'" /></td>

				</tr>
				<tr>
					<td>分数：</td>
					<td><input class="easyui-textbox" type="text" name="score"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td>学期：</td>
					<td><select class="easyui-combobox" name="term"
						style="width: 100px;">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
					</select>  
				</tr>
				<tr>
					<td>考试情况：</td>
					<td><input type="radio" name="type" value="0">正常成绩<input
						type="radio" name="type" value="1">补考成绩<input type="radio"
						name="type" value="2">重修成绩<input type="radio" name="type"
						value="3">清考成绩</td>
				</tr>
				<tr>
					<td>老师：</td>
					<td><input class="easyui-textbox" type="text" name="teacher"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td>时间：</td>
					<td><input id="dt" class="easyui-textbox" type="text"
						name="datetime" data-options="required:true" /></td>
				</tr>
			</table>
		</form>

	</div>
	<div id="bb">
		<a href="#" style="width: 80px" class="easyui-linkbutton"
			id="loginBtn">保存</a> <a href="#" class="easyui-linkbutton"
			style="width: 80px" id="BackBtn">取消</a>
	</div>

</body>
<script type="text/javascript">
	var myDate = new Date();
	var y = myDate.getFullYear();
	var m = myDate.getMonth() + 1;
	var d = myDate.getDate();
	var h = myDate.getHours();
	var mm = myDate.getMinutes();
	var s = myDate.getSeconds();
	var dt = y + "-" + m + "-" + d + " " + h + ":" + mm + ":" + s;
	$("#dt").val(dt)

	$("#BackBtn").click(function() {
		$("#addDialog").dialog("close");
	});
	$("#btnAdd").click(function() {
		$("#addDialog").dialog("open");
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
			var row = $("#tb").datagrid("getSelected");//row = null - false
			var inde = $('#tb').datagrid('getRowIndex', row)
			if (!row) {
				$.messager.alert('提示', '请选择你要修改的行', 'info');
				return false;
			}

			$('#tb').datagrid('endEdit', inde);
			$('#tb').datagrid('beginEdit', inde);

		}

	});
	$("#loginBtn").click(
			function() {
				var param = $("#ff").serialize();
				alert(param);
				$.post("<c:url value='/manager/score?cmd=save'/>", param,
						function(data) {
							$('#tb').datagrid('reload');
							$("#addDialog").dialog("close");
						}, "json");
			});
	$("#search3")
			.click(
					function() {
						var name = $("#g").combo('getText');
						
						//alert(name);
						var dt = $("#d").datebox('getValue');
						//alert(dt);
						var dsc = $("#e").combo('getText');
						alert(dsc);
						$('#tb')
								.datagrid(
										{ 
											url : "<c:url value='/manager/score?cmd=select&courseid="
													+ name
													+ "&datetime="
													+ dt
													+ "&term=" + dsc + "'/>"
										},"json");
					});
	function accept() {
		
			var row = $("#tb").datagrid("getSelected");
			var inde = $('#tb').datagrid('getRowIndex', row)
			if (inde == -1) {
				return;
			}
			$('#tb').datagrid('acceptChanges');
			//alert(000)
			var id = row.id;
			var studid = row.studid;
			//alert(studid);
			var course=row.course;
			var score = row.score;
			var term = row.term;
			alert(term);
			var type = row.type;
			var datetime = row.datetime;
			var teacher = row.teacher;
			$.post("<c:url value='/manager/score?cmd=update'/>", {
				"id" : id,
				"studid" : studid,
				"course" : course,
				"score" : score,
				"term" : term,
				"type" : type,
				"datetime" : datetime,
				"teacher" : teacher
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
	remove = function() {
		var row = $("#tb").datagrid("getSelected");
		var id = row.id;
		alert(id);
		var inde = $('#tb').datagrid('getRowIndex', row)
		if (!row) {
			$.messager.alert('提示', '请选择你要删除的行', 'info');
			return false;
		}
		$.messager.confirm("提示", "确定要删除吗?", function(ok) {
			if (ok) {

				$.post("<c:url value='/manager/score?cmd=delete'/>", {
					"id" : id
				}, function(data) {
					if (data != 0) {
						$('#tb').datagrid('reload');
					} else {
						$.messager.alert('提示', '失败!', "error");
					}
				}, "text");
			}
		});

	}
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