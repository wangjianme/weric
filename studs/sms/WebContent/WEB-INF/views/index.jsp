<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<style type="text/css">
#formTable td {
	padding: 5px;
}
</style>
</head>
<body>
	<div id="win" class="easyui-window" title="用户登录" collapsible="false"
		minimizable="false" maximizable="false" closable="false"
		style="padding: 5px; width: 300px;"
		data-options="iconCls:'icon-man',modal:false">
		<form id="form1" name="form1">
			<table id="formTable">
				<tr>
					<td>用户名：</td>
					<td><input class="easyui-textbox"
						data-options="iconCls:'icon-man'" id="name" name="name"
						style="width: 160px"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" class="easyui-textbox"
						data-options="iconCls:'icon-lock'" name="pwd" style="width: 160px"></td>
				</tr>
				<tr>
					<td>验证码：</td>
					<td><input type="text" class="easyui-textbox" name="sCode"
						style="width: 80px"> <img src="img" id="_img"> <a
						href="javascript:chg();">看不清</a></td>
				</tr>
				<c:if test="${not empty sessionScope.error}">
					<tr>
						<td colspan="2" align="center"><font color="red">你还没有登录，请先登录!</font>
						</td>
					</tr>
					<c:remove var="error" scope="session" />
				</c:if>
				<tr>
					<td></td>
					<td align="center"><a id="btn" href="javascript:_submit();"
						style="width: 60px;" class="easyui-linkbutton">登录</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	function _submit() {
		//获取里面的元素
		var param = $("#form1").serialize();
		//获取用户名：
		var nm = $("#name").val();//使用jquery的方式=document.getElementById("name").value;
		if (nm == "") {
			$.messager.alert("提示", "请输入用户名!", "error");
			return false;
		} else {
			var date = new Date();
			var time = date.getTime();//loong
			time +=1000*60*60*24*7;
			date = new Date(time);
			document.cookie ="loginName=" + escape(nm) + "; expires="
					+ date.toGMTString();
		}

		$.post("login", param, function(data) {
			if (data == "1") {
				//$.messager.alert("提示", "登录成功了", "info");
				//去主界面显示
				window.location = "manager/main.html";
			} else if (data == "0") {
				$.messager.alert("提示", "你的用户名或密码错误", "error");
			} else if (data == "2") {
				$.messager.alert("提示", "验证码输入错误!", "error");
			}
		}, "text");
	}

	function chg() {
		//直接使用jquery的代码修改页面的值
		var d = new Date();
		var time = d.getTime();
		//document.getElementById("_img")
		var url = "img?" + time;
		$("#_img").attr("src", url);
	}
	
	//读取cookie
	var cookies = 
		document.cookie;
	//根据; slipt
	var cs = cookies.split("; ");
	for(var i=0;i<i<cs.length;i++){
		var cookie = cs[i];
		var ck = cookie.split("=");
		if(ck[0]=="loginName"){
			var value = ck[1];
			value=unescape(value);
			console.log(value);
			$("#name").val(value);
			break;
		}
		
	}
	    
</script>
</html>