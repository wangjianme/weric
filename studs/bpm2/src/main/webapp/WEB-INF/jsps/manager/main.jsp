<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<!-- 设置以下兼容ie9及以后的版本 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/bootstrap.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bootstrap-datetimepicker.css'/>"/>
<script type="text/javascript"
	src="<c:url value='/js/jquery-1.11.3.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/bootstrap-datetimepicker.js'/>"></script>
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
				<li><a href="<c:url value='/logout.html'/>">【退出】</a></li>
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
					<div class="panel-heading">流程定义列表</div>
					<table class="table table-hover">
						<caption>流程定义列表...</caption>
						<thead>
							<tr>
								<th>流程名称</th>
								<th>版本</th>
								<th>部署时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="map">
								<tr>
									<td>${map.name}</td>
									<td>${map.version}</td>
									<td>${map.dt}</td>
									<td><a target="_blank"
										href="<c:url value='/manager/png.html?id=${map.pid}'/>"> <span
											class="glyphicon glyphicon-picture"></span>
									</a> &nbsp; <a
										href="<c:url value='/manager/deleteprocess.html?id=${map.id}'/>">
											<span class="glyphicon glyphicon-remove-circle"></span>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
			</div>
			<!-- //部署列表 -->
		</div>

	</div>
</body>
</html>