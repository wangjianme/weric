<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nav class="navbar navbar-default navbar-static-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<h2><img src="<c:url value='/logo/logo_school.png'/>" />学生端口	</h2>	
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#" style="height:90px;width: 70px;">Home</a></li>
			</ul>
			<ul class="nav navbar-nav pull-right" style="margin-right: 30px">
				<li><a href="../navbar/">欢迎您： 【${stud.name}】 </a></li>
				<li><a href="${pageContext.request.contextPath}/#">|&nbsp;&nbsp;退出</a></li>
			</ul>
		</div>
	</div>
</nav>