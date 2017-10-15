<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>MyExam</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="在线考试系统">
	<link rel="stylesheet" type="text/css" href="<c:url value='/ext/resources/css/ext-all.css'/>"/>
	<script type="text/javascript" src="<c:url value='/ext/adapter/ext/ext-base.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-all.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-lang-zh_CN.js'/>"></script>
	<script language="javascript">
		Ext.BLANK_IMAGE_URL = "<c:url value='/ext/resources/images/default/s.gif'/>"; //设置空白图片的位置
		var path = "<c:url value='/'/>";
	</script>
  	<style type="text/css">
  		/**
  			red
  		*/
  		.labelState1{
  			width:20px;
  			height:20px;
  			background-color:#FF5151;
  			text-align:center;
  			vertical-align:bottom;
  			cursor:hand;
  			margin-top:2px;
  		}
  		/**
  			green
  		*/
  		.labelState2{
  			width:20px;
  			height:20px;
  			background-color:#28FF28;
  			text-align:center;
  			vertical-align:bottom;
  			cursor:hand;
  			margin-top:2px;
  		}
  		/**
  			yellow
  		*/
  		.labelState3{
  			width:20px;
  			height:20px;
  			background-color:#FFFF37;
  			text-align:center;
  			vertical-align:bottom;
  			cursor:hand;
  			margin-top:2px;
  		}
  		/**
  			white
  		*/
  		.labelState4{
  			width:20px;
  			height:20px;
  			background-color:white;
  			text-align:center;
  			vertical-align:bottom;
  			cursor:hand;
  			margin-top:2px;
  		}
  	</style>
  </head>
  <body>
  	<table width="100%" height="100%" border="0">
  		<COLGROUP width="80%"></COLGROUP>
  		<COLGROUP width="20%"></COLGROUP>
  		<tr>
  			<td height="30">
  				<table width="100%" border="0">
  					<tr>
  						<td align="center">
  							${exam.examName}
  						</td>
  						<td width="130">
  							<div id="timerDiv" style="float:right">用時：</div>
  						</td>
  					</tr>
  				</table>
  			</td>
  			<td rowspan="2" id="menu" style="display:none;">
  				<%int i=0; %>
	  			<fieldset id="set1" style="border:1px solid gray;width:100%;height:100%;font-size:9pt;font:bold;overflow:auto;">
					<legend><c:out value="${exam.examName}"/></legend>
					<c:forEach items="${exam.cates}" var="cate" varStatus="idx">
						<fieldset style="padding-top:5px;border:1px solid blue;width:100%;height:120px;font-size:9pt;font:bold;overflow:auto;">
							<legend onclick="aa(this);" style="cursor:hand;"><c:out value='${cate.ecCatename}'/></legend>
							<div>
							<p>共${cate.ecQty}题 (${cate.ecScore}分)</p>
							<c:forEach items="${cate.quess}" var="ques" varStatus="ix">
								<label onmouseover="cgc1(this);" score="0" idx="<%=i++%>" onmouseout="cgc2(this);" class="labelState1" onClick="show(this);" quesid='<c:out value="${ques.eqQuesid}"/>' id="<c:out value='${ques.eqId}'/>">${ques.eqSeq}</label>
							</c:forEach>
							</div>
						</fieldset>
					</c:forEach>
				</fieldset>
  			</td>
  		</tr>
  		<tr>
			<td>
				<iframe id="dd" src="examview!toView.action" frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>
			</td>  		
  		</tr>
  	</table>
  </body>
  <script language="javascript">
  		var aa = function(obj){
			if(obj.parentNode.style.height=='0px'){
				obj.parentNode.childNodes[1].style.display="";
				obj.parentNode.style.height=120;
			}else{
				obj.parentNode.childNodes[1].style.display="none";
				obj.parentNode.style.height=0;
			}
  	  	}
  	  	var label;
  	  	var show = function(obj){
			//alert(obj.id);
			if(label && label.className!='labelState3'){
				label.className="labelState2";
			}
			state = "labelState2";
			obj.className="labelState4";
			label=obj;
			idx = obj.idx;
			Ext.getDom('dd').src=path+'examview!queryOneEq.action?eqId='+obj.id;
  	  	}
  	  	//
  	  	var state;
  	  	var cgc1 = function(obj){
			state = obj.className;
			obj.className="labelState4";
  	  	}
  	  	var cgc2 = function(obj){
			obj.className = state;
	  	}
		//显示控制栏
		var labels;
		var idx = 0;
	  	var showMenu = function(){
			var mm = Ext.getDom('menu');
			mm.style.display="block";
			labels = Ext.query('label');
			labels[0].click();
			idx=0;
			startTime();
		}
		//下一题
		var next = function(score){
			labels[idx].score=score;
			if(idx>=labels.length-1){
				//已经是最后一个
				return;
			}
			idx++;
			labels[idx].click();
		}
		//上一题
		var pre = function(score){
			labels[idx].score=score;
			if(idx<=0){
				return;
			}
			idx--;
			labels[idx].click();
		}
		//设置标记
		var setMark = function(boo){
			if(boo==true){
				labels[idx].className='labelState3';
			}else{
				labels[idx].className='labelState2';
			}
		}
		//计算总分
		var sumScore = function(boo){
			var sum = 0;
			for(var i=0;i<labels.length;i++){
				sum = sum+parseInt(labels[i].score);
			}
			alert("你最后的得分为："+sum);
		}
		//
		var startTime = function(){
			Ext.getDom("timerDiv").innerText="剩余："+time+"分钟";
			timeHandler = window.setInterval(setRemainTime,1000*60);	
		}
		var time = '<c:out value="${exam.examTime}"/>';//分钟
		var timeHandler;
		var setRemainTime = function(){
			time = parseInt(time)-1;
			Ext.getDom("timerDiv").innerText="剩余："+time+"分钟";
			if(time<=0){
				window.clearInterval(timeHandler);
			}
		}
  </script>
</html>