<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="panel panel-primary">
	<div class="panel-heading">菜单</div>
	<div class="list-group">
		<!-- 工作平台，和用户刚登录以后显示的功能一样，即查询所有最新的流程定义 -->
		<a class="list-group-item" href="<c:url value='/emp/main.html'/>">工作平台</a>
		<a class="list-group-item" href="<c:url value='/emp/tasks.html'/>">待办任务</a>
		<a class="list-group-item"
			href="<c:url value='/emp/unfinishtasks.html'/>">进行中任务</a><a
			class="list-group-item" href="<c:url value='/emp/history.html'/>">历史任务</a>
	</div>
</div>