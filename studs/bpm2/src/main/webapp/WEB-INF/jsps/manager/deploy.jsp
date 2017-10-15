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
						<form class="form-horizontal" method="post"
							style="width: 400px; margin: 0px auto;"
							action="<c:url value='/manager/upload.html'/>"
							enctype="multipart/form-data">
							<div class="form-group">
								<label for="firstname" class="col-sm-4 control-label">流程定义名称：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="name" name="name"
										placeholder="流利定义名称">
								</div>
							</div>
							<div class="form-group">
								<input type="file" name="file" id="file" style="display: none;">
								<label class="col-sm-4 control-label">bpmn压缩文件</label>
								<div class="col-sm-8">
									<!-- input-group -->
									<div class="input-group">
										<input type="text" class="form-control" id="file1"
											placeholder="选择文件..." readonly="readonly"> <span
											class="input-group-btn">
											<button class="btn btn-default" id="_btnBrowser"
												type="button">选择...</button>
										</span>
									</div>
									<!-- /input-group -->
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-8 col-sm-offset-4">
									<input type="submit" class="btn btn-default" value="上..传">
								</div>
							</div>
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
		$("#_btnBrowser").click(function() {
			$("#file").click();
		});
		/**
		处理Chrome在获取file中的value时的C:\fakepath的问题
		 */
		$("#file").change(function() {
			var val = $(this).val();
			val = val.replace(/^[Cc]:\\fakepath\\/, "");
			$("#file1").val(val);
		});
	});
</script>
</html>