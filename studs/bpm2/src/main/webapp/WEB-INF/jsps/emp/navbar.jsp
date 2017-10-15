<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><nav
	class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<!-- 当屏幕宽度就少时，显示在右则的菜单选项 -->
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar">
				<!-- 三条线 -->
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand">工作平台</a>
		</div>
		<!-- 通过navbar-collapse collapse指定可以将所有的菜单都收起来 -->
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a>${user}</a></li>
			</ul>
			<ul class="nav navbar-nav pull-right">
				<li><a href="<c:url value='/logout.html'/>">【退出】</a></li>
			</ul>
		</div>
	</div>
</nav>