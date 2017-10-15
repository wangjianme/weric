<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/bootstrap.css'/>" />
<script type="text/javascript"
	src="<c:url value='/js/jquery-1.11.3.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-default">
			<div class="navbar-header">
				<a class="navbar-brand">流程定义管理</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="active"><a>${user}</a></li>
			</ul>
			<ul class="nav navbar-nav pull-right">
				<li><a href="<c:url value='/logout'/>">【退出】</a></li>
			</ul>
		</nav>
		<!-- 数据部分 -->
		<div class="row">
			<!-- 菜单部分 -->
			<div class="col-md-3">
				<div class="panel panel-primary">
					<div class="panel-heading">菜单</div>
					<div class="list-group">
						<a class="list-group-item"
							href="<c:url value='/manager/main.html'/>">显示流程定义</a> <a
							class="list-group-item" style="background: lightgray;"
							href="<c:url value='/manager/deploy.html'/>">部署流程</a>
					</div>
				</div>
			</div>
			<!-- //菜单部分完成 -->
			<!-- 部署列表部分 -->
			<div class="col-md-9">
				<div class="panel panel-primary">
					<div class="panel-heading">部署流程定义</div>
					<div class="panel-body">
						<form action="<c:url value='/manager/deployment.html'/>"
							method="post">
							<input type="hidden" name="name" value="${name}"> <input
								type="hidden" name="fileName" value="${fileName}">
							<h4>流程定义文件上传成功!</h4>
							<br> 流程定义名称：${name}<br>
							<button class="btn btn-default">直接部署</button>
							<a href="#" class="btn btn-link">放弃部署</a>
						</form>
					</div>
				</div>
			</div>
			<!-- //部署列表 -->
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
	});
</script>
</html>