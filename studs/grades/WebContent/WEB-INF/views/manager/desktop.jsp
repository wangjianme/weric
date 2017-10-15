<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/default/easyui.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/icon.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.easyui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/locale/easyui-lang-zh_CN.js'/>"></script>
</head>
<body class="easyui-layout">
	<div region="north"
		style="height: 100px; line-height: 95px; padding-left: 50px; padding-right: 20px; background-color: #eaf2ff">
		<font color="blue" size="20"> 教学管理系统 </font> <span
			style="float: right;"> 【${user.name}】 | 【<a
			href="${pageContext.request.contextPath}/index.html"}>退出</a>】
		</span>
	</div>
	<div region="west" split="true" title="菜单" style="width: 160px;">
		<ul class="easyui-tree" id="tree"
			data-options="url:'<c:url value='/manager/menu'/>'"></ul>
	</div>
	<div region="center" title="工作区"
		style="padding: 0px; overflow: hidden;">
		<iframe id="dataFrame" frameborder="0" width="100%" height="100%"></iframe>

	</div>
</body>
<script type="text/javascript">
	var path = "<c:url value='/'/>";
	$(function() {
		var tree = $('#tree').tree({
			onClick : function(node) {
				boo = tree.tree("isLeaf", node.target);
				if (boo) {
					var url = node.url;
					url = path + url;
					$("#dataFrame").attr("src", url);
				}
			}
		});
	});
</script>
</html>