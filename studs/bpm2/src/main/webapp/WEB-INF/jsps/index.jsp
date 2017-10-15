<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/bootstrap.css'/>">
<script type="text/javascript"
	src="<c:url value='/js/jquery-1.11.3.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
</head>
<body>
	<form class="col-md-4 col-md-offset-4 form-horizontal"
		action="<c:url value='/login.html'/>" method="post">
		<h4 style="text-align: center;">Activiti bpm示例</h4>
		<div class="form-group">
			<label class="control-label col-sm-4">登录用户：</label>
			<div class="col-sm-8">
				<select name="name" class="form-control">
					<option value="-1">-------选择--------</option>
					<option value="张三">员工：张三</option>
					<option value="李四">经理：李四</option>
					<option value="王五">总经理：王五</option>
					<option value="管理员">系统管理员：管理员</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<input type="submit" class="btn btn-primary btn-block" value="登录">
		</div>
	</form>
</body>
</html>