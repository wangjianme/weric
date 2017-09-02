<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>最简单的页面,最实在的干货</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/bootstrap/css/bootstrap.min.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/bootstrap/js/bootstrap.min.js'/>"></script>
<script type="text/javascript" src="//player.youku.com/jsapi"></script>
</head>
<body>
	<div class="container">
		<div class="row" style="border-bottom: 1px solid lightgray;">
			<div class="col-md-4">
				<div class="row">
					<div class="col-md-6">
						<img src="<c:url value='/imgs/sync.png'/>" width="100">
					</div>
					<div class="col-md-6">
						<h3>同步在线</h3>
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<h3>最简单的页面,最实惠的干货....</h3>
			</div>
			<!-- 搜索框 -->
			<div class="col-md-3">
				<h3></h3>
				<div class="input-group">
					<input type="text" class="form-control" placeholder="搜索你想要文档或视频">
					<span class="input-group-btn">
						<button class="btn btn-default" type="button">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<ol class="breadcrumb" style="background: #FFF;">
				<li><a href="#">文档</a></li>
				<li><a href="#">视频</a></li>
				<li class="active">同步课</li>
				<li class="pull-right"><a href="#">登录</a></li>
				<li class="pull-right"><a href="#">注册</a></li>
			</ol>
		</div>
		<!-- video -->
		<div class="row" style="background: #000;">
			<div id="youkuplayer"
				style="width: 960px; height: 540px; margin: 0px auto;"></div>
			<script type="text/javascript">
				function play(vid) {
					//使用youkuapi播放以获取广告收入
					player = new YKU.Player('youkuplayer', {
						styleid : '0',
						client_id : 'd61b0f8dbdc849e3',
						vid : vid,
						newPlayer : true,
						autoplay : false
					});
				}
				//自动播放一个
				play("XMTg5MTU5MDE4OA==");
			</script>
		</div>
		<!-- 数据 -->
		<div class="row">
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<img src="<c:url value='/imgs/test.jpg'/>" alt="通用的占位符缩略图">
					<div class="caption">
						<h3>缩略图标签</h3>
						<p>一些示例文本。一些示例文本。</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<img src="<c:url value='/imgs/test.jpg'/>" alt="通用的占位符缩略图">
					<div class="caption">
						<h3>缩略图标签</h3>
						<p>一些示例文本。一些示例文本。</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<img src="<c:url value='/imgs/test.jpg'/>" alt="通用的占位符缩略图">
					<div class="caption">
						<h3>缩略图标签</h3>
						<p>一些示例文本。一些示例文本。</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<img src="<c:url value='/imgs/test.jpg'/>" alt="通用的占位符缩略图">
					<div class="caption">
						<h3>缩略图标签</h3>
						<p>一些示例文本。一些示例文本。</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<img src="<c:url value='/imgs/test.jpg'/>" alt="通用的占位符缩略图">
					<div class="caption">
						<h3>缩略图标签</h3>
						<p>一些示例文本。一些示例文本。</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<img src="<c:url value='/imgs/test.jpg'/>" alt="通用的占位符缩略图">
					<div class="caption">
						<h3>缩略图标签</h3>
						<p>一些示例文本。一些示例文本。</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<img src="<c:url value='/imgs/test.jpg'/>" alt="通用的占位符缩略图">
					<div class="caption">
						<h3>缩略图标签</h3>
						<p>一些示例文本。一些示例文本。</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<img src="<c:url value='/imgs/test.jpg'/>" alt="通用的占位符缩略图">
					<div class="caption">
						<h3>缩略图标签</h3>
						<p>一些示例文本。一些示例文本。</p>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<img src="<c:url value='/imgs/test.jpg'/>" alt="通用的占位符缩略图">
					<div class="caption">
						<h3>缩略图标签</h3>
						<p>一些示例文本。一些示例文本。</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>