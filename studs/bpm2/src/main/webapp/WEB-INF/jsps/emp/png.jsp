<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<c:url value='/js/jquery-1.11.3.js'/>"></script>
<title>图片</title>
<style type="text/css">
	/*声明一个DIV并设置它的边框和定位方式*/
	.div{
		position: absolute;
		z-index: 100;
		border: 4px solid blue;
		border-radius:10px;
	}	
</style>
</head>
<body>
	<c:url var="url" value="/emp/img/png.html">
		<c:param name="procdefid">${param.procdefid}</c:param>
	</c:url>
	<!-- 显示流程定义id -->
	<img src="${url}">
</body>
<script type="text/javascript">
	$(function(){
		var id = "${param.procdefid}";
		var taskdefkey="${param.taskdefkey}";
		var param = {procdefId:id,activityId:taskdefkey};
		//使用 ajax查询流程定义中某个任务ActivityID的具体位置，并进行绝对定位
		$.post("<c:url value='/emp/shape.html'/>",param,function(data){
			var div = "<div class='div'></div>";
			$(div).css("left",(data.x)+"px").css("top",(data.y)+"px").css("width",(data.width+8)+"px")
			.css("height",(data.height+8)+"px").appendTo("body");
		},"json");
	});
</script>
</html>