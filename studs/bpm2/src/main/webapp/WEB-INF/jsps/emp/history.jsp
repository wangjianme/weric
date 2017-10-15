<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
					<div class="panel-heading">历史任务..</div>
					<table class="table table-hover">
						<thead>
							<tr>
								<td>名称</td>
								<td>结束时间</td>
								<td>查看</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="map">
								<tr>
									<td>${map.name}</td>
									<td><fmt:formatDate value="${map.endTime}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td><a
										href='<c:url value="/manager/png.html?procdefId=${map.proceDefId}"/>'
										target="_blank">查看</a></td>
								</tr>
								<tr>
									<td colspan="3" style="background: lightgray;">
										申请人：${map.applyUser}<br>
										时间：${map.from}到${map.to}<br>
										${map.msg}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	
</script>
</html>