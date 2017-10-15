<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片</title>
</head>
<body>
	<c:url var="url" value="/manager/img/png.html">
		<c:param name="procdefId">${param.id}</c:param>
	</c:url>
	<img src="${url}">
</body>
</html>