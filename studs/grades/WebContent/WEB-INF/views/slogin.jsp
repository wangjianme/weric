<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="js/ajax.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="bootstrap/html5shiv.min.js"></script>
<script type="text/javascript" src="bootstrap/respond.min.js"></script>
<style type="text/css">
#f1 {
	width: 400px;
	margin: 0 auto;
	margin-top: 40px;
}
.login_right DL { MARGIN: 0px 0px 0px 105px; MIN-HEIGHT: 20px; WIDTH: 285px; _height: 20px }
</style>

</head>
<body>
	<div class="container" style="float:center;">
		<div class="login_logo"><h2><img src="logo/logo_school.png"><img src="logo/logo_jw.png"></h2>		
		</div>
		<div class="login_left" style="float:left;"><img class="login_pic" src="logo/login_pic.png"></div>
		<div class="login_right" style="float:left">	
		<form id="f1" class="form-horizontal" method="post" action="login">
			<h4 class="col-sm-offset-4">登录</h4>
			<div class="form-group">
				<label for="name" class="control-label col-sm-4">用户ID：</label>
				<div class="col-sm-8">
					<input type="text" id="name" class="form-control" name="name"
						placeholder="输入用户ID">
				</div>
			</div>
			<div class="form-group">
				<label for="pwd" class="col-sm-4 control-label">密码：</label>
				<div class="col-sm-8">
					<input type="password" class="form-control" id="pwd" name="pwd"
						placeholder="输入密码">
				</div>
			</div>
<!-- 			<div class="form-group">
				<label class="col-sm-4 control-label">验证码：</label>
				<div class="col-sm-8">
					<div class="has-success has-feedback"
						style="width: 40%; display: inline-block;">
						<input type="text" class="form-control" id="code" name="code" 
							placeholder="验证码" maxlength="4"> 							
							<span id="feedError"
							class="glyphicon glyphicon-remove form-control-feedback"
							style="color: red; display: none;"></span> 
							
							<span id="feedOk"
							class="glyphicon glyphicon-ok form-control-feedback"
							style="color: green; display: none;"></span>
					</div>
					<img src="img" id="img"> <a href="javascript:_chg();">看不清</a>
				</div>
			</div> -->
			<div class="col-sm-offset-4">
				<input type="button" id="btnLogin"
					class="btn btn-info col-sm-offset-2"  value="提交">
				<!-- <a type="button" href="views/reg.jsp" class="btn btn-link">注册</a> -->
			</div>
		</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		_chg= function(){
			var d = new Date();
			$("#img").attr("src","img?"+d.getTime());
		}

		$("#btnLogin").click(function(){
			var param = $("#f1").serialize();
			$.post("<c:url value='/slogin'/>", param, function(data) {
				if (data == "1") {
					window.location="<c:url value='/htmls/stud/sshow.html'/>";
				}else{
					alert("用户名或是密码错误!");
				}
			}, "text");			
		});


		/**
		  判断是否用户输入了四个验证码
		*/
		//---
		$("#code").keyup(function(){
			var val = $(this).val();
			if(val.length==4){
				//删除原来隐藏的信息
				//用ajax请求数据
				xhr.open("POST","validate",true);
				xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				xhr.onreadystatechange=function(){
					if(xhr.readyState==4){
						if(xhr.status==200){
							var boo = xhr.responseText;
							if(boo=="true"){
								//让按扭可用
								$("#btnLogin").removeProp("disabled");
								//让对号显示，同时让所有<span>隐藏
								$("#feedOk").show().siblings("span").hide();
							}else{
								$("#btnLogin").prop("disabled","disabed");
								$("#feedError").show().siblings("span").hide();
							}
						}
					}
				};
				xhr.send("code="+val);
			}
		});
   });
	
</script>



</html>