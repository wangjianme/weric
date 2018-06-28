<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>明细表</title>
<link href="css/style_main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<link rel="StyleSheet" href="dtree.css" type="text/css" />
<script src="<%=path%>/dtree.js" type="text/javascript" ></script>
<link href="css.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/calendar.js"></script>
 <style type="text/css">
	 input.text1{ width:90%; border:0px;}
	 input.text2{ width:50%; border:0px;}
	 div.show{ margin-top:20px;}
 </style>
<script type="text/javascript">
var template = new dTree('template');
var root = 0;
template.add(root,-1,'模版选择','');
function checkNode(nodeId){
	template.checkNode(nodeId);
	iFrameHeight();
}
</script>
<script> 
var sumValueArray = new Array();
var totalArray = new Array();
function iFrameHeight() { 
	var ifm= window.parent.document.getElementById("iframepage"); 
	var subWeb = window.parent.document.frames ? window.parent.document.frames["iframepage"].document : ifm.contentDocument; 
	if(ifm != null && subWeb != null) { 
	    if (subWeb.body.scrollHeight > 549){
	      ifm.height = subWeb.body.scrollHeight+200; 
	    }
		
	} 
} 
function getDel(k){ 
$(k).parent().remove(); 
iFrameHeight();
} 
function getInsert(k){ 
	$(k).parent().clone().insertAfter($(k).parent());
	iFrameHeight();
} 
function jisuan(){
	var temps = document.getElementsByName("temp");
	for (var i = 0;i < temps.length;i++){
		if (temps[i].checked){
			sumValue(temps[i].value);
		}
	}
	return false;
}
function sumValue(index){
	var totalBox = totalArray[index];
	var idBox = sumValueArray[index];
	for (var i =0;i < idBox.length;i++){
		var sumValues = document.getElementsByName("detailValue-"+idBox[i]);
		var sum = 0;
		for (var j =0;j < sumValues.length-1;j++){
			var value = new Number(sumValues[j].value);
			if (isNaN(value)){
				alert("需要进行统计的列值不为数字形式!");
				return;
			}
			sum+=value;
		}
		sumValues[sumValues.length-1].value=sum;
	}
	var totalValue = 0;
	for (var i = 0;i < totalBox.length;i++){
		var baseValue = document.getElementsByName("detailValue-"+totalBox[i]);
		baseValue = baseValue[baseValue.length-1].value;
		var value = new Number(baseValue);
		if (isNaN(value)){
			alert("总费用计算中包含有非数字字段");
			return;
		}else{
			totalValue+=value;
		}
	}
	document.getElementById("total"+index).value=totalValue;
	document.getElementById("hidtotal"+index).value=totalValue;
}
var show = false;
var isOver = false;
function showTemplateDiv(){
	var e1 = document.getElementById("tempAdd");
	if (!show){
		e1.innerHTML = "隐藏模版";
	    templateDiv.style.display="block";
	    show=true;
	}else{
		e1.innerHTML = "显示模版";
	    templateDiv.style.display="none";
	    show=false;		
	}
	
    
    return false;
}
function checkNext(){
	var totalMoneys = document.getElementsByName("totals");
	var totalMoney = 0;
    for (var i = 0;i < totalMoneys.length;i++){
       var value = new Number(totalMoneys[i].value);
       if (isNaN(value)){
		  alert("费用总计中包含有非数字！");
		  return false;
       }
       totalMoney+=value;
    }
    document.form1.totalMoney.value=totalMoney;
	return true;
}
function hidTemplate(){
	var e1 = document.getElementById("tempAdd");
	if (e1 != window.event.srcElement&&!isOver){
		e1.innerHTML = "显示模版";
	    templateDiv.style.display="none";//block
	    show=false;	
	}
}
function changeClick(){
	isOver = !isOver;
}
</script> 
<!--提示框样式-->

</head>
<body onclick="hidTemplate();">
<form action="reb!addDetailValue.action" method="post" name="form1">
<input type="hidden" name="rebId" value=""/>
<input type="hidden" name="rebNumber" value=""/>
<input type="hidden" name="totalMoney" value=""/>
<input type="hidden" name="peopleNumber" value="${param.peopleNumber}"/>
<input type="hidden" name="startDate" class="input" value="${param.startDate}"/>
<input type="hidden" name="backDate" class="input" value="${param.backDate}"/>
<input type="hidden" name="accordMoney" class="input" value="${param.accordMoney}"/>
<input type="hidden" name="dname" class="input" value="${param.dname}"/>
<input type="hidden" name="orgName" class="input" value="${param.orgName}"/>
<input type="hidden" name="rebDate" class="input" value="${param.rebDate}"/>
<input type="hidden" name="proNumber" class="input" value="${param.proNumber}"/>
<input type="hidden" name="rebUser" value="${param.rebUser }" />
<input type="hidden" name="duty" value="${param.duty }" />
<input type="hidden" name="koujian" value="${param.koujian }" />
<div style="clear:both; height:15px; line-height:15px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%"><table width="167" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="left" class="back_main04"><span class="font_bold">明细表填写窗口</span></td>
          </tr>
        </table></td>
        <td width="30%" align="left">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
            <tr>
              <td align="center"><a href="" onclick="return showTemplateDiv()" class="link_white" id="tempAdd" >显示模版</a></td>
            </tr>
          </table>
        </td>
        <td width="30%" align="left">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <td align="center">出差人数:${param.peopleNumber}人</td>
              <td align="center">出发日期:${param.startDate}</td>
              <td align="center">返回日期:${param.backDate}</td>
            </tr>
          </table>
        </td>
        <td width="20%" align="right">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
            <tr>
              <td align="center"><a href="" onclick="return jisuan()" class="link_white" id="tempAdd">统计计算</a></td>
            </tr>
          </table>
        </td>
        </tr>
     </table>
     <div onmouseover="changeClick();" onmouseout="changeClick();" id="templateDiv" style="position:absolute;width:250px;height:200px;background-color:rgb(235,235,235);overflow:auto;display:none;border:solid 1px red">
          <c:forEach items="${tempList}" var="temp" >
            <script type="text/javascript">
              if (root == 0 || root != '${temp.type.typeName}'){
                  template.add('${temp.type.typeName}',0,'${temp.type.typeName}',false,'${emp.empno}');
                  template.add('${temp.tempName}','${temp.type.typeName}','${temp.tempName}',true,'${temp.tempId}','temp','#box${temp.tempId}');
              	root = '${temp.type.typeName}';
              }else{
                  template.add('${temp.tempName }',root,'${temp.tempName}',true,'${temp.tempId}','temp','#box${temp.tempId}');
              }
            </script>
          </c:forEach>
          <script type="text/javascript">
             document.write(template);
          </script>
     </div>
     <script>
        var e = document.getElementById("tempAdd");
        var t=e.offsetTop;
	    var l=e.offsetLeft;
	    while(e=e.offsetParent){
	      t+=e.offsetTop;
	      l+=e.offsetLeft;
	    }
	    var templateDiv = document.getElementById("templateDiv");
	    templateDiv.style.left=l;
	    templateDiv.style.top=t+15;
       </script>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" class="border03">
        <tr>
          <td>
          <c:forEach items="${tempList}" var="temp" >
            <script>
              sumValueArray[${temp.tempId}] = new Array();
              totalArray[${temp.tempId }] = new Array();
            </script>
            <div id="box${temp.tempId }" class="show" style="display:none">
	          <table width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
			    <tr>
			      <td colspan="${fn:length(temp.itemList) -1}">
			        <table border="0" cellpadding="0" cellspacing="0">
			          <tr>
			            <td width="30%" align="left">${temp.tempName }</td>
			            <td width="10%"></td>
			            <td align="left"><font color="red">${temp.tempDesc }</font></td>
			          </tr>
			        </table>
			      </td>
			      <td colspan="3" align="right">
			        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
			          <tr>
			            <td>
			              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
				            <tr>
				              <td align="center"><a href="#" onclick="sumValue(${temp.tempId })" class="link_white">统计计算</a></td>
				            </tr>
				          </table>
			            </td>
			            <td>
			              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
				            <tr>
				              <td align="center"><a href="#" id="getAtr${temp.tempId }" class="link_white">追加内容</a></td>
				            </tr>
				          </table>
			            </td>
			          </tr>
			        </table>
			      </td>
			    </tr>
			    
				<tr>
				 <c:forEach items="${temp.itemList}" var="item" >
				   <td>${item.tempItemName }</td>
				 </c:forEach>
				 <script>
				 $(function(){ 
		            $("#getAtr${temp.tempId}").click(function(){ 
		            $str=''; 
		            $str+="<tr>"; 
				    <c:forEach items="${temp.itemList}" var="item" >
				      <c:choose>
			           <c:when test="${item.valueType == 'date'}">
			             $str+="<td><input type='text' onclick='calendar();'  class='text1' name='detailValue-${item.tempItemId }'/></td>";
			           </c:when>
			           <c:when test="${item.valueType == 'int' || item.valueType =='float'}">
			             $str+="<td><input type='text'  class='text1' name='detailValue-${item.tempItemId }' value='0'/></td>";
			           </c:when>
			           <c:otherwise>
			           $str+="<td><input type='text' class='text1' name='detailValue-${item.tempItemId }'/></td>"
			           </c:otherwise>
			         </c:choose>
			        </c:forEach>
			        $str+="<td onClick='getInsert(this)'><a href='#'>插入</a></td>"; 
				    $str+="<td onClick='getDel(this)'><a href='#'>删除</a></td>"; 
	            	$str+="</tr>"; 
	            	$("#addTr${temp.tempId}").append($str);
	            	iFrameHeight(); 
	            	return false;}); 
	            	}); 
				 </script>
				</tr>
			    <tbody id="addTr${temp.tempId}">
			       <tr>
			       <c:forEach items="${temp.itemList}" var="item" >
			         <c:choose>
			           <c:when test="${item.valueType == 'date'}">
			             <td><input type="text" onclick="calendar();"  class="text1" name="detailValue-${item.tempItemId }"/></td>
			           </c:when>
			           <c:when test="${item.valueType == 'float' || item.valueType == 'int'}">
			             <td><input type="text" class="text1" name="detailValue-${item.tempItemId }" value="0"/></td>
			           </c:when>
			           <c:otherwise>
			             <td><input type="text" class="text1" name="detailValue-${item.tempItemId }"/></td>
			           </c:otherwise>
			         </c:choose>
				   </c:forEach>
				   <td onclick='getInsert(this)' width="20"><a href='#'>插入</a></td>
			       <td onclick='getDel(this)' width="20"><a href='#'>删除</a></td>
			      </tr>
			    </tbody> 
			     <tr>
                   <td width="94"><input type="text" readonly="readonly" class="text1" name="detailValue-${temp.itemList[0].tempItemId }" value="合计"/></td>
                  
                   <c:forEach begin="1" end="${fn:length(temp.itemList)-2}" var="i" step="1" >
			         <c:choose>
			           <c:when test="${temp.itemList[i].sumValue == 1}">
			             <td><input type="text" id="base${temp.itemList[i].tempItemId }" class="text1" name="detailValue-${temp.itemList[i].tempItemId }"/></td>
			             <script>
			               sumValueArray[${temp.tempId}].push(${temp.itemList[i].tempItemId });
			             </script>
			           </c:when>
			           <c:otherwise>
			             <td align="center"><input type="text"  readonly="readonly" class="text1" name="detailValue-${temp.itemList[i].tempItemId }" value="—"/></td>
			           </c:otherwise>
			         </c:choose>
			         <c:if test="${temp.itemList[i].totalValue == 1}">
		             <script>
		             totalArray[${temp.tempId}].push(${temp.itemList[i].tempItemId });
		             </script>
			         </c:if>
				   </c:forEach>
				   <td colspan="3">费用合计:<input type="hidden" readonly="readonly"  id="hidtotal${temp.tempId }" size="5" class="text2" name="totals" value=""/><input type="text" readonly="readonly"  id="total${temp.tempId }" size="5" class="text2" name="detailValue-${temp.itemList[fn:length(temp.itemList)-1].tempItemId }" value=""/></td>
                 </tr>
			  </table>
	          </div> 
          </c:forEach>
          </td>
        </tr>
      </table></td>
 
  </tr>
  <tr>
    <td height="40" align="center" valign="bottom"><input type="button" value="上一步" onclick="window.history.go(-1);" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="下一步"  onclick="return checkNext();"/></td>
    <td align="right" valign="top">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
  $("input[name='temp']").click(function(){
       
	   if($(this).is(":checked"))
	   {
		 $($(this).attr("title")).show(400);     
	   }else
	   {
		 $($(this).attr("title")).hide(400); 
	   }
	    iFrameHeight();
	  });
</script>
</body>
</html>
