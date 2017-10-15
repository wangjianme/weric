<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<title>Insert title here</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:true"
		style="height: 100px; background-color: #eaf2ff;">
		<span
			style="float: left; font-size: 23px; height: 90px; line-height: 90px;">
			<img width="90" height="90"
			src="${pageContext.request.contextPath}/static/images/cd.jpg">
			河南财经政法大学学生信息管理系统
		</span> <span
			style="float: right; margin-right: 50px; vertical-align: bottom; height: 90px; line-height: 90px;">
			当前用户：【${user.name}】 | <a
			href="${pageContext.request.contextPath}/login">退出</a>
		</span>
	</div>
	<div data-options="region:'west',title:'功能菜单',split:true"
		style="width: 180px;">
		<ul id="tree" class="easyui-tree"
			data-options="url:'${pageContext.request.contextPath}/menu'"></ul>
	</div>
	<div data-options="region:'center',title:'工作区'" style="padding: 0px;">
		<iframe id="dataFrame" name="dataFrame" frameborder="0" width="100%"
			height="100%"></iframe>
	</div>
</body>
<script type="text/javascript">
   var contextPath = "${pageContext.request.contextPath}";
	$('#tree').tree({
		onClick : function(node) {
			var boo = $("#tree").tree("isLeaf", node.target);
			if (boo) {
				var url = contextPath+node.res;
				$("#dataFrame").attr("src",url);
			}
		}
	});
</script>
</html>