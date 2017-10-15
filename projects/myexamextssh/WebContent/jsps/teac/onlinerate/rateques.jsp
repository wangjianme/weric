<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		.txt{
			border-top:0px;
			border-left:0px;
			border-right:0px;
			border-bottom:1px solid gray;
			width: 60px;
		}
	</style>
  </head>
  <body style="font-size:9pt;">
  	<div align="center" style="font:normal;font-size:9pt;">
  		<font style="font-size:15pt;font:bold;text-decoration:underline;">${exam.examName}</font>
  		<br/>
  		课程：${exam.courName}，时间：${exam.examTime}分钟，总分：${exam.examScore}
  		<br/>
  		考生姓名：${exam.studName}，得分：<input type="text" class="txt" readOnly id="infoScore" value="${exam.infoScore}"/>
  		<input type="hidden" id="infoId" value="${exam.infoId}"/>
  		<hr width="50%"/>
  	</div>
  	<form name="form1" id="form1" method="post">
  	<c:forEach items="${exam.examcates}" var="examcate">
  		<p style="color:blue;font:bold;">
  			${examcate.ecSeq}、${examcate.ecCatename}(共${examcate.ecQty}题，${examcate.ecScore}分)
  		</p>
  		<br/>
  		<c:forEach items="${examcate.quess}" var="ques">
  			<p id="${ques.eqId}">
  				${ques.eqSeq}、${ques.eqBody}(${ques.eqScore}分)
  				<br/>
  				<c:if test="${examcate.ecCate==3 || examcate.ecCate==4}">
  					<c:forTokens items="${ques.eqChoose}" delims="###" var="choose" varStatus="codeIdx">
  						<c:set var="d" scope="page" value="${codeIdx.index+65}"></c:set>
  						<%=(char)Integer.parseInt(""+pageContext.getAttribute("d"))%>、${choose}
  						<br/>
  					</c:forTokens>
  				</c:if>
  				<br/>
  				<font color="red">学生答案：${ques.answAnswer}</font>
  				<br/>
  				得分：
  				<c:if test="${ques.answAnswer!=null && ques.answAnswer!=''}">
	  				<input type="text" readOnly="true" class="txt" name="answScore" value="${ques.answScore}"/>
	  				，修改得分：
	  				<select name="eqScore">
	  					<c:forEach begin="0" end="${ques.eqScore}" var="score">
	  						<c:if test="${score==ques.answScore}">
	  							<option value="${score}" selected>${score}</option>
	  						</c:if>
	  						<c:if test="${score!=ques.answScore}">
	  							<option value="${score}">${score}</option>
	  						</c:if>
	  					</c:forEach>
	  				</select>
	  				<input type="hidden" name="answId" value="${ques.answId}"/>
  				</c:if>
  			</p>
  			<hr/>
  		</c:forEach>
  	</c:forEach>
  	</form>
  	<hr width="70%" color="blue"/>
  	<div style="width:100%;text-align: center;">
  		<table>
  			<tr>
  				<td>
  					<div id="ss"/>
  				</td>
  				<td>
  					<div id="rr"/>
  				</td>
  			</tr>
  		</table>
  	</div>
  	<br/>
  	<br/>
  </body>
  <script language="javascript">
  		Ext.onReady(function(){
  			var loadMask = new Ext.LoadMask(Ext.getBody(),'操作中，请稍候...');
  			var showMask = function(){
  				loadMask.show();
  			}
  			var hideMask = function(){
  				loadMask.hide();
  			}
  			Ext.Ajax.on('beforerequest',showMask,this);
  			Ext.Ajax.on('requestcomplete',hideMask,this);
  			Ext.Ajax.on('requestexception',hideMask,this);

  	  		
			var vv = Ext.query('input[name=values]');
			for(var i=0;i<vv.length;i++){
				vv[i].disabled=true;
			}
			var btn = new Ext.Button({
				text:'保存',
				width:120,
				renderTo:'ss',
				handler:function(){
					var eqScore 	= Ext.query('select[name="eqScore"]');	//用户选择的分
					var ids     	= Ext.query('input[name="answId"]');	//id
					var answScore 	= Ext.query('input[name="answScore"]'); //原分数
					var scoreArray = new Array();
					var idArray    = new Array();

					var destObjs = new Array();							    //用于保存目标分数对像
					var srcObjs  = new Array();								//修改后的分数对像
					
					for(var i=0;i<eqScore.length;i++){
						if(eqScore[i].value!=answScore[i].value){
							idArray.push(ids[i].value);
							scoreArray.push(eqScore[i].value);

							destObjs.push(answScore[i]);
							srcObjs.push(eqScore[i]);
						}
					}
					if(idArray.length==0){									//没有做任何修改
						return;
					}
					var infoId = Ext.get('infoId').getValue();
					Ext.Ajax.request({
						url:path+'onlinerate!updateScore.action',
						method:'post',
						params:{answId:idArray,answScore:scoreArray,infoId:infoId},
						success:function(resp,opts){
							var result = Ext.util.JSON.decode(resp.responseText);
							if(result.success==true){
								for(var i=0;i<srcObjs.length;i++){
									destObjs[i].value=srcObjs[i].value;
								}
								var sum 	 = result.sum;
								Ext.query('input[id="infoScore"]')[0].value=sum;
								parent.updateScore(sum);
								Ext.Msg.alert('提示','保存成功，最后得分：['+sum+']分');
							}else{
								Ext.Msg.alert('提示','操作失败!');
							}
						},
						failure:function(resp,opts){
							alert('失败');
						}
					});
				}
			});
			var btn = new Ext.Button({
				text:'重置',
				width:120,
				renderTo:'rr',
				handler:function(){
					var form = Ext.query('form');
					form[0].reset();
				}
			});
  		});
  </script>
</html>