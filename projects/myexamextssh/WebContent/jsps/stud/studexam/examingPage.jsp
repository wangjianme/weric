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
	<script type="text/javascript" src="<c:url value='/jsps/stud/studexam/examingPage.js'/>"></script>
	<style type="text/css">
		.whiteLabel{
			width:10px;
			height:10px;
			background-color:white;
			margin:2px;
			cursor:hand;
		}
		.yellowLabel{
			width:10px;
			height:10px;
			background-color:#F9F900;
			margin:2px;
			cursor:hand;
		}
		.greenLabel{
			width:10px;
			height:10px;
			background-color:#28FF28;
			margin:2px;
			cursor:hand;
		}
		.blueLabel{
			width:10px;
			height:10px;
			background-color:#9393FF;
			margin:2px;
			cursor:hand;
		}
		legend {
			font-size:8pt;
			cursor:hand;
		}
		fieldset {
			margin-top:5px;
		}
	</style>
  </head>
  <body>
  		<input type="hidden" id="examId" value="${exam.examId}"/>
  		<input type="hidden" id="examTime" value="${exam.examTime}"/>
  		<input type="hidden" id="infoId" value="${exam.infoId}"/>
		<div id="control" style="display:block;">
			<div id="header" style="font:bold;font-size:9pt;margin-top:5px;">
				<p>${exam.examName}</p>
				<p>时间：
					<label id="remain">${exam.remain}</label>分钟
					<label id="second">60</label>秒
				</p>
			</div>
			<%int i=1; %>
			<c:forEach items="${exam.cates}" var="cate" varStatus="idx">
				<fieldset style="padding-top:5px;border:1px solid blue;width:100%;height:120px;font-size:9pt;font:bold;overflow:auto;">
					<legend onclick="sh(this);"><c:out value='${cate.ecCatename}'/></legend>
					<div>
					<p>共${cate.ecQty}题 (${cate.ecScore}分)</p>
					<c:forEach items="${cate.quess}" var="ques" varStatus="ix">
						<label class="blueLabel" score="0" idx="<%=i++%>" onClick="_show(this);" 
						       quesid='<c:out value="${ques.eqQuesid}"/>' 
						       id="<c:out value='${ques.eqId}'/>"  
						       eqSeq='${ques.eqSeq}'>${ix.index+1}</label>
					</c:forEach>
					</div>
				</fieldset>
			</c:forEach>
		</div>
		<iframe id="examMain" frameborder="0" name="examMain"></iframe>
  </body>
  <script type="text/javascript">
  	  function sh(obj){
	     var fd = obj.parentNode;
	     if(fd.children[1].style.display=="none"){
	    	 fd.children[1].style.display="block"
	    	 obj.parentNode.style.height=120;
		 }else{
			 fd.children[1].style.display="none"
			 obj.parentNode.style.height=0;
		 }
  	  }
  	  //显示控制台,同时还要将info表中写入信息
  	  var labels;
  	  var idx=0;
  	  var timeHandler;
  	  
  	  //显示点击的试题
  	  var tmpLabel;
  	  var tmpClassName;
  	  function _show(obj){
  	  	idx = obj.idx-1;
  	  	if(tmpLabel){
			tmpLabel.className=tmpClassName;
  	  	}
  	  	tmpClassName = obj.className;			//保存到临时变量中
  	  	tmpLabel=obj;
		obj.className="whiteLabel";
		var infoId=Ext.get('infoId').getValue();
		var url = path+'studexam!queryOneQues.action?quesId='+obj.id+'&infoId='+infoId;
		//alert(url);
		Ext.getDom('examMain').src=url;
  	  }
  	  
  	  //下一个
  	  function _next(isBlank,chked){
  		 if(!isBlank){
			if(chked){
				labels[idx].className='yellowLabel';
				tmpClassName='yellowLabel';
			}else{
				labels[idx].className='greenLabel';
				tmpClassName='greenLabel';
			}
			
		 }
  		 tmpLabel=labels[idx];
		 if(idx>=(labels.length-1)){
			return;
		 }
		 idx++;
		 labels[idx].click();
  	  }
  	  
  	  //上一个
  	  function _pre(isBlank,chked){
  		if(!isBlank){
			if(chked){
				labels[idx].className='yellowLabel';
				tmpClassName='yellowLabel';
			}else{
				labels[idx].className='greenLabel';
				tmpClassName='greenLabel';
			}
			
		 }
  		 tmpLabel=labels[idx];
  		if(idx<=0){
			return;
		 }
		 idx--;
		 labels[idx].click();
  	  }

  	  //提交试卷
  	  function submitExam(){
		　var blankQues = Ext.query("label[class='blueLabel']");
		  var len = blankQues.length;
		  if(len>0){
		  	Ext.Msg.confirm('提示','尚有['+len+']道试题没有完成，确定要交卷吗？',function(btn){
				if(btn=='yes'){
					//直接去计算分值
					calculateScore();
				}
			});
		  }else{
			  //直接去计算分值
			  calculateScore();
		  }
  	  }
  	  
  	  //直接提交计算得分
	  function calculateScore(){
		 Ext.Ajax.request({
			url:path+'studexam!calculateScore.action',
			method:'post',
			success:function(resp,opts){
				var result = Ext.util.JSON.decode(resp.responseText);
				if(result.success==true){
					alert('操作成功,转到显示成绩页面。');
					window.location.href=path+"studexam!examScore.action";
				}else{
					Ext.Msg.alert('提示','操作不成功!');
				}
			},
		 	failure:function(resp,opts){
				alert('操作不成功...');
			}			
		 });
	  }
	  
	  //到时间以后的自动提交
	  function autoSubmit(){
		　alert("你的考试时间已经结束，系统将自动交卷！");
		  calculateScore();
	  }
  	  
  	  //倒计时开始
  	  var sec=60;
  	  function beginTime(){
		   Ext.getDom("second").innerText=sec;
		   sec--;
		   if(sec<=0){
				sec=60;
				var rem = Ext.getDom("remain").innerText;
				rem = parseInt(rem);
				rem--;
				Ext.getDom('remain').innerText=rem;
				if(rem<=0){
					autoSubmit();
				}
		   }
  	  }
  	  
  	 //初始调用,如倒计时,选中第一个试题等操作
  	 function init(){
  		 timeHandler = window.setInterval(beginTime,1000);
  		 labels=Ext.query('label[idx]');
  	   	 labels[idx].click();
  	 }
  	 init();
 	 //将已经做过的试题设置为绿色
  	 function selectDoneQues(){
  		'<c:forEach items="${exam.answs}" var="answ">'
      		var eq = '<c:out value="${answ.answEq}"/>';
			var lbl = Ext.query('label[id="'+eq+'"]');
			lbl[0].className="greenLabel";
      	'</c:forEach>'
 	 }
  	 selectDoneQues();
  </script>
</html>