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
<body>


<body style="margin: 15px">
	<form id="f1">
		<table>
			<tr>
				<td>旧密码：</td>
				<td><input type="password" name="pwd" id="pwd"
					class="easyui-textbox" style="width: 190px;"></td>
			</tr>
			<tr>
				<td>新密码：</td>
				<td><input type="password" name="newPwd" id="newPwd"
					class="easyui-textbox" style="width: 190px;"></td>
			</tr>
			<tr>
				<td>确认新密码：</td>
				<td><input type="password" name="newPwd2" id="newPwd2"
					class="easyui-textbox" style="width: 190px;"  validType="equals['#newPwd']"></td>
			</tr>
			<tr>
				<td></td>
				<td><a id="btnSave" data-options="iconCls:'icon-save'"
					class="easyui-linkbutton">修改</a> <a
					data-options="iconCls:'icon-clear'" id="btnClear" class="easyui-linkbutton">清除</a></td>
			</tr>
		</table>
	</form>

</body>
<script type="text/javascript">
	$(function() {//页面装备事件
		//选择页面上的btnSave元素
		$("#btnSave").click(function() {
			var oldPwd = $("#pwd").val();//获取里面值
			if ($.trim(oldPwd) == "") {
				$.messager.alert('提示', '请输入旧密码!', "error");
				return false;
			}
			//再验证两个密码是否一样
			var npwd=$("#newPwd").val();
			var npwd2=$("#newPwd2").val();
		
			if(npwd==npwd2){
				//获取表单中的所有数据
				var param = $("#f1").serialize();
				//直接提交
				var path = "<c:url value='/manager/pwd?cmd=pwd'/>";
				//1:地址，2：参数，3：成功以后的回调,4:返回的数据类型
				$.post(path, param, function(data) {
					if (data == "1") {
						$.messager.alert('提示', '密码修改成功!', "info");
						
					} else {
						$.messager.alert('提示', '旧密码输入错误', "error");
					}
				}, "text");
			}else{
				$.messager.alert('提示', '新密码两次输入不一致!', "error");
				}
				
		
		});
	
		$("#btnClear").click(function(){
			//$("#f1"):jquery对象
			//$("#f1")[0] - DOM对象
			//$("#f1")[0].reset();
			//document.getElementById("f1").reset();
			$("#f1").form("clear");
		});
	});
</script>
</html>









 

<!--<body style="margin: 15px">
	<table id="roleGrid" class="easyui-datagrid"
		style="width: 100%; height: 250px" title="角色管理列表" toolbar="#tb"
		pagination="false"
		data-options="url:'<c:url value=''/>',singleSelect:true,fitColumns:true">
		<thead>
			<tr>

			</tr>
		</thead>
	</table>

  -->

























