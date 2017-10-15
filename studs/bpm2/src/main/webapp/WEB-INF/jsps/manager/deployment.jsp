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
				<%@include file="/WEB-INF/jsps/manager/menu.jsp"%>
			</div>
			<!-- //菜单部分完成 -->
			<!-- 部署列表部分 -->
			<div class="col-md-9">
				<div class="panel panel-primary">
					<div class="panel-heading">部署流程定义</div>
					<div class="panel-body">
						<h2>流程部署完成！</h2>
						<table class="table borderd">
							<tr>
								<td>流程部署ID：${deploymentId}</td>
							</tr>
							<tr>
								<td>流程实例ID：${procdefId}</td>
							</tr>
							<tr>
								<td>流程实例版本：${procdefVersion}</td>
							</tr>
							<tr>
								<td>流程实例名称：${procdefName}</td>
							</tr>
							<tr>
								<td>流程实例的key值：${procdefKey}</td>
							</tr>
						</table>
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