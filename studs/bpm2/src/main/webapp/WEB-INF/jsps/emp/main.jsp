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
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/bootstrap-datetimepicker.css'/>" />
<script type="text/javascript"
	src="<c:url value='/js/jquery-1.11.3.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/bootstrap-datetimepicker.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/bootstrap-datetimepicker.zh-CN.js'/>"></script>
</head>
<body>
	<div class="container">
		<!-- 静态导入菜单 -->
		<%@ include file="/WEB-INF/jsps/emp/navbar.jsp"%>
		<!-- 数据部分 -->
		<div class="row">
			<!-- 菜单部分 -->
			<div class="col-md-3">
				<%@ include file="/WEB-INF/jsps/emp/menu.jsp"%>
			</div>
			<!-- //菜单部分完成 -->
			<!-- //内容部分 -->
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">工作平台</div>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>名称</th>
								<th>申请</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${procdefs}" var="map">
								<tr>
									<td>${map.name}</td>
									<td><a href="javascript:_apply('${map.key}');">申请</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- 申请的div对话框 -->
	<div class="modal" id="applyDialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">请假流程申请</h4>
				</div>
				<div class="modal-body" id="modalBody">
					<form id="applyForm" role="form">
						<input type="hidden" name="key" id="key">
						<div class="form-group form-group-sm">
							<label class="control-label">请假天数：</label>
							<div class="input-group input-group-sm">
								<input class="form-control" name="days"> <span
									class="input-group-addon">天</span>
							</div>

						</div>
						<div class="form-group form-group-sm">
							<label class="control-label">时间：</label>
							<div class="input-group input-group-sm">
								<input type="text" class="form-control" name="from"
									id="dateFrom"> <span class="input-group-addon">到</span>
								<input type="text" class="form-control" name="to" id="dateTo">
							</div>
						</div>
						<div class="form-group">
							<div class="form-group">
								<label class="control-label">事由：</label>
								<textarea class="form-control" rows="2" name="msg"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" id="btnSure" class="btn btn-primary">确定</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
<script type="text/javascript">
	function _apply(key) {
		$("#applyDialog form #key").val(key);
		$("#applyDialog").modal("show");
	}

	$("#btnSure").click(function() {
		var param = $("#applyForm").serialize();
		//alert(param);
		$.post("<c:url value='/emp/apply.html'/>", param, function(data) {
			//alert("保存成功，请在个人任务是查看你的流程!");#$("#bt"json");
			$("#modalBody").html("保存成功，请在<a href='#'>待办任务</a>中开始这个流程!");
			$("#btnSure").remove();
		}, "text");
	});

	$("#dateFrom").datetimepicker({
		language : 'zh-CN',
		autoclose : true,
		todayBtn : true
	});
	$("#dateTo").datetimepicker({
		language : 'zh-CN',
		autoclose : true
	});
</script>
</html>