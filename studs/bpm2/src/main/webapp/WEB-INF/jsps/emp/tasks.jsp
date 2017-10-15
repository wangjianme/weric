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
					<div class="panel-heading">待办任务</div>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>名称</th>
								<th>申请时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${tasks}" var="map">
								<tr>
									<td>${map.taskName}</td>
									<td>${map.taskDate}</td>
									<td><c:choose>
											<%--如果为当前用户自己申请的，则显示执行，取消 --%>
											<c:when
												test="${pageScope.map.vars.applyUser==sessionScope.user}">
												<a href="javascript:_submit('${map.taskId}');">执行</a>
												<a href="javascript:_cancel('${map.taskId}');">取消</a>
											</c:when>
											<%--如果不是当前用户申请的，由显示同意拒绝 --%>
											<c:otherwise>
												<a href="javascript:_agree('${map.taskId}');">同意</a>
												<a href="javascript:_denie('${map.taskId}');">拒绝</a>
											</c:otherwise>
										</c:choose> <a
										href='<c:url value="/emp/png.html?procdefid=${map.procdefid}&taskdefkey=${map.taskDefKey}"/>'
										target="_blank">查看</a></td>
								</tr>
								<tr id="${map.taskId}" style="background: lightgray;">
									<td colspan="4" style="border-bottom: 1px solid black;">
										请假人：${map.vars.applyUser} <br>请假天数：${map.vars.days}天 <br>
										请假时间：${map.vars.from}到${map.vars.to}<br> 事由：${map.vars.msg}"
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="confrimDialog">
		<div class="modal-dialog" style="width: 300px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">取消请假</h4>
				</div>
				<div class="modal-body" id="modalBody">
					<input type="hidden" id="taskId"> 是否取消请假?
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" id="cancelBtnSure">确定</button>
					<button class="btn btn-primary" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="agreeDialog">
		<div class="modal-dialog" style="width: 300px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">审批</h4>
				</div>
				<div class="modal-body" id="modalBody">
					<form id="agreeForm" role="form">
						<input type="text" name="taskId" id="taskId">
						<div class="form-group">
							<div class="control-label">是否同意：</div>
							<div>
								<label class="checkbox-inline"> <input type="radio"
									name="agree" value="true"> 同意
								</label> <label class="checkbox-inline"> <input type="radio"
									name="agree" value="false"> 拒绝
								</label>
							</div>
						</div>
						<div class="form-group">
							<label>原因：</label>
							<textarea rows="2" class="form-control" name="msg"></textarea>
						</div>
					</form>

				</div>
				<div class="modal-footer">
					<button class="btn btn-default" id="agreeBtnSure">确定</button>
					<button class="btn btn-primary" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function _cancel(taskId) {
		$("#taskId").val(taskId);
		$("#confrimDialog").modal("show");
	}

	/**
	执行
	 */
	function _submit(taskId) {
		$.post("<c:url value='/emp/submit.html'/>", {
			taskId : taskId
		}, function(data) {
			alert("返回值是:" + data);
		}, "text");
	}
	$("#cancelBtnSure").click(function() {
		var id = $("#taskId").val();
		var param = {
			taskId : id
		};
		$.post("<c:url value='/emp/cancel.html'/>", param, function(data) {
			if (data == "success") {
				//取消成功
				$("#confrimDialog").modal("hide");
				//删除<tr id="3853">的上一个兄弟元素和删除自己
				$("#" + id).prev("TR").remove().end().remove();
			}
		}, "text");
	});
	/**
	显示同意或拒绝界面
	*/
	function _agree(taskId) {
		$("#agreeDialog #taskId").val(taskId);
		$("#agreeForm input[name='agree'][value='true']").prop("checked","checked");
		$("#agreeDialog").modal("show");
	}
	function _denie(taskId) {
		$("#agreeDialog #taskId").val(taskId);
		$("#agreeForm input[name='agree'][value='false']").prop("checked","checked");
		$("#agreeDialog").modal("show");
	}
	//提交同意或是拒绝
	$("#agreeBtnSure").click(function(){
		var param = $("#agreeForm").serialize();
		$.post("<c:url value='/emp/agree.html'/>",param,function(data){
			alert("data is:"+data);
			if(data=="1"){
				window.location="<c:url value='/emp/tasks.html'/>";
			}
		},"text");
	});
</script>
</html>