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
	<form id="s" action="<c:url value='/slogin?cmd=querys'/>" method="post">
	<div id="score" style="display: block">
	<div class="row" >
			<div class="col-md-9">				
					<div class="input-group">
						<span class="input-group-addon">课程类型：</span> <input id="0" type="text" value="${m.id }"
							name="id" class="form-control"> <span
							class="input-group-addon">学期：</span> <input id="1" type="text" value="${m.score}"
							name="term" class="form-control"> <span
							class="input-group-addon"> <a id="search" href="javascript:search();">
								查询</a><span class="glyphicon glyphicon-search"><span>&nbsp;&nbsp;&nbsp;&nbsp;<a  href="/grades/htmls/stud/sshow.html">返回</a></span></span>		
						</span>
					</div>
			</div>
		</div>
		<table id="tb" class="table border table-hover">
			<thead>
				<tr>
					<td>ID</td>
					<td>学生ID</td>
					<td>成绩</td>
					<td>学期</td>
					<td>课程类型</td>
					<td>课程时间</td>
					<td>教师</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="m">
					<tr id="score">
						<td>${m.id}</td>
						<td>${m.courseid}</td>
						<td>${m.score}</td>
						<td>${m.term}</td>
						<td>${m.type}</td>
						<td>${m.datetime}</td>
						<td>${m.teacher}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
<%-- 		<ul class="pagination">
			<li><a href="<c:url value='/m?page=1'/>"> 首页 </a></li>
			<c:if test="${page!=1}">
				<li><a href="<c:url value='/m?page=${param.page-1}'/>">
						&laquo; </a></li>
			</c:if>
			<c:forEach begin="${startNum}" end="${endNum}" var="page">
				<li <c:if test="${param.page==page}">class="active"</c:if>><a
					href="<c:url value='/m?page=${page}'/>">${page}</a></li>
			</c:forEach>
			<c:if test="${page!=map.pageCount}">
				<li><a href="<c:url value='/m?page=${param.page+1}'/>">
						&raquo; </a></li>
			</c:if>
			<li><a href="<c:url value='/m?page=${map.pageCount}'/>">
					尾页</a></li>
		</ul>
		<label>共${map.pageCount}页，当前是${page}页</label> --%>
	</div>
	</form>
	</div>
</body>
<script type="text/javascript">
$(function() {
	$("#search").click(function(){
		$("#s").submit();
	});
});
</script>
</html>