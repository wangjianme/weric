<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>最简单的页面,最实在的干货</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/bootstrap/css/bootstrap.min.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/bootstrap/js/bootstrap.min.js'/>"></script>
<style type="text/css">
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<h3>最简单的页面,最实惠的干货....</h3>
			<hr>
		</div>
		<div class="row">
			<ol class="breadcrumb" style="background: #FFF;">
				<li><a href="#">文档</a></li>
				<li><a href="#">视频</a></li>
				<li class="active">同步课</li>
				<li class="pull-right"><a href="#">登录</a></li>
				<li class="pull-right"><a href="#">注册</a></li>
			</ol>
		</div>
	</div>
</body>
</html>