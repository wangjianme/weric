<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="<c:url value='/js/jquery.min.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/bootstrap/css/bootstrap-datetimepicker.min.css'/>">
<script type="text/javascript"
	src="<c:url value='/bootstrap/js/bootstrap.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/bootstrap/js/bootstrap-datetimepicker.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/bootstrap/js/bootstrap-datetimepicker.zh-CN.js'/>"></script>
<style type="text/css">
.div {
	position: absolute;
	background: lightgray;
	border: 1px solid gray;
	left: 0px;
	top: 0px;
	width: 300px;
	height: 200px;'
	border-radius: 15px;
	display: none;
	padding: 15px;
	word-break: break-all;
	word-wrap: break-word;
}
</style>
</head>
<body>
	<%@include file="/WEB-INF/views/htmls/stud/nav.jsp"%>
	<div class="container">
		<div class="row">	
				<button id="btnQself" class="btn btn-primary" width="100px">学生个人信息查看</button>
				<button id="btnQscore" class="btn btn-primary">成绩信息查看</button>
				<button id="btnQcourse" class="btn btn-primary">学生个人课表</button>
				<button id="btnSelect" class="btn btn-primary">网上选课</button>
				<button id="Upd" class="btn btn-primary">修改密码</button>			
		</div>
		
		<div  id="one" style="display:block;">	
		<form id="f1" action="<c:url value='/slogin?cmd=queryself'/>" method="post">	
		<table  id="tb" class="table border table-hover">	 
 			<!--  <thead style="background-color: red;">
   			 <tr><th colspan="2">学生个人信息</th></tr>
  			</thead> -->
 			 <c:forEach items="${list}" var="s">
    		 <tr class="active">
     		 	<td>学号</td>
    	    	<td>${s.no}</td>
  			 </tr>
    		 <tr class="success">
      			<td>姓名</td>
      			<td>${s.name}</td>
    		 </tr>
    		 <tr class="info">
      			<td>性别</td>
      			<td>${s.sex}</td>
    		 </tr>
    		 <tr class="warning">
      			<td>省份证</td>
      			<td>${s.idcard}</td>
    		 </tr>
    		 <tr class="danger">
      			<td>年龄</td>
      			<td>${s.age}</td>
    		 </tr>
    		 <tr class="active">
      			<td>手机号</td>
      			<td>${s.tel}</td>
    		 </tr>
    		 <tr class="success">
      			<td>住址</td>
      			<td>${s.addr}</td>
    		 </tr>
    		 <tr class="info">
      			<td>身高</td>
      			<td>${s.height}</td>
       		 </tr>
    		 <tr class="danger">
      			<td>体重</td>
      			<td>${s.wight}</td>
    		 </tr>
    		 <tr class="active">
      			<td>QQ号</td>
      			<td>${s.qq}</td>
    		 </tr>
    		 <tr class="success">
      			<td>邮箱</td>
      			<td>${s.email}</td>
    		 </tr>
    		 <tr class="info">
      			<td>民族</td>
      			<td>${s.minzu}</td>
    		 </tr>
    		 <tr class="warning">
         		<td>级别</td>
      		 	<td>${s.jibie}</td>
    		 </tr>
    		 <tr class="danger">
      			<td>年级</td>
      			<td>${s.clasid}</td>
    		 </tr>
    		 </c:forEach>
  					
		</table>
		
		</form>	
		</div>	
	</div>
	<!-- 以下是修改密码的界面 -->
	<div class="modal fade" id="updateModal" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">修改密码</h4>
				</div>
				<div class="modal-body">
					<form id="updateForm" action="" class="form-horizontal">
						<input type="hidden" name="code0">
						<div class="form-group">
							<label class="col-md-3 control-label">原密码：</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="code1" id="pwd">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">新密码：</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="code2">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">新密码：</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="code3">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" id="btnUpateSure" class="btn btn-primary">确定修改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#btnQself").click(function(){
		
			document.getElementById('one').style.display='none';
			$("#f1").submit();
		});
		$("#btnQscore").click(function(){
		
			window.location="<c:url value='/htmls/stud/score.html'/>";
		});			
		var updateRow = null;
		//修改的界面
		$("#Upd").click(function() {
			var id = $(this).parents("tr").attr("id");
			updateRow = $(this).parent().parent();
			$("#updateModal").modal("show");
		});
		//确定修改
		$("#btnUpateSure").click(
						function() {
							var oldPwd = $("#pwd").val();//获取里面值
							if ($.trim(oldPwd) == "") {
								alert("请输入旧密码!");
								return false;
							}
							//一次获取表单中的所有值
							var param = $("#updateForm").serialize();
							//alert(param);
							//发送ajax请求
							$.post("<c:url value='/spwd?cmd=spwd'/>",param,function(data) {
								if (data == "1") {
									$("#updateModal").modal("hide");
									alert("密码修改成功");
								} else {
									alert("旧密码输入错误或两次输入不一样！");
								}
							}, "text");							
						});
		$("#btnSelect").click(function(){
			window.location="<c:url value='#'/>";
			alert("现在不是选课时间！");
			});
		//时间
		$("#datatime").datetimepicker({
			autoclose : true
		});
				});
</script>
</html>