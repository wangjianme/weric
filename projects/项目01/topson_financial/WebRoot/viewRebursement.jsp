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
<title>总表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style_main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script src="<%=path %>/js/calendar.js"></script>
<link rel="StyleSheet" href="dtree.css" type="text/css" />
<script src="<%=path%>/dtree.js" type="text/javascript" ></script>
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
function confirmShenhe(){
    if (confirm('此操作将会对该报销单产生审核通过效果，确认操作吗?')){
  	  window.location.href='reb!updateFlag.action?flag=4&rebId=${rebursement.rebId}&backPath=check';
    }
}
var sumValueArray = new Array();
var totalArray = new Array();
function iFrameHeight() { 
	var ifm= window.parent.document.getElementById("iframepage"); 
	var subWeb = window.parent.document.frames ? window.parent.document.frames["iframepage"].document : ifm.contentDocument; 
	if(ifm != null && subWeb != null) { 
	    if (subWeb.body.scrollHeight > 549){
	      ifm.height = subWeb.body.scrollHeight+180; 
	    }
		
	} 
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
function getDel(k){ 
$(k).parent().remove(); 
iFrameHeight();
}
function convertCurrency(currencyDigits) {
// Constants:
 var MAXIMUM_NUMBER = 99999999999.99;
 // Predefine the radix characters and currency symbols for output:
 var CN_ZERO = "零";
 var CN_ONE = "壹";
 var CN_TWO = "贰";
 var CN_THREE = "叁";
 var CN_FOUR = "肆";
 var CN_FIVE = "伍";
 var CN_SIX = "陆";
 var CN_SEVEN = "柒";
 var CN_EIGHT = "捌";
 var CN_NINE = "玖";
 var CN_TEN = "拾";
 var CN_HUNDRED = "佰";
 var CN_THOUSAND = "仟";
 var CN_TEN_THOUSAND = "万";
 var CN_HUNDRED_MILLION = "亿";
 var CN_SYMBOL = "";
 var CN_DOLLAR = "元";
 var CN_TEN_CENT = "角";
 var CN_CENT = "分";
 var CN_INTEGER = "整";
 
// Variables:
 var integral; // Represent integral part of digit number.
 var decimal; // Represent decimal part of digit number.
 var outputCharacters; // The output result.
 var parts;
 var digits, radices, bigRadices, decimals;
 var zeroCount;
 var i, p, d;
 var quotient, modulus;
 
// Validate input string:
 currencyDigits = currencyDigits.toString();
 if (currencyDigits == "") {
  alert("Empty input!");
  return "";
 }
 if (currencyDigits.match(/[^,.\d]/) != null) {
  alert("Invalid characters in the input string!");
  return "";
 }
 if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
  alert("Illegal format of digit number!");
  return "";
 }
 
// Normalize the format of input digits:
 currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
 currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
 // Assert the number is not greater than the maximum number.
 if (Number(currencyDigits) > MAXIMUM_NUMBER) {
  alert("Too large a number to convert!");
  return "";
 }
 
// Process the coversion from currency digits to characters:
 // Separate integral and decimal parts before processing coversion:
 parts = currencyDigits.split(".");
 if (parts.length > 1) {
  integral = parts[0];
  decimal = parts[1];
  // Cut down redundant decimal digits that are after the second.
  decimal = decimal.substr(0, 2);
 }
 else {
  integral = parts[0];
  decimal = "";
 }
 // Prepare the characters corresponding to the digits:
 digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
 radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
 bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
 decimals = new Array(CN_TEN_CENT, CN_CENT);
 // Start processing:
 outputCharacters = "";
 // Process integral part if it is larger than 0:
 if (Number(integral) > 0) {
  zeroCount = 0;
  for (i = 0; i < integral.length; i++) {
   p = integral.length - i - 1;
   d = integral.substr(i, 1);
   quotient = p / 4;
   modulus = p % 4;
   if (d == "0") {
    zeroCount++;
   }
   else {
    if (zeroCount > 0)
    {
     outputCharacters += digits[0];
    }
    zeroCount = 0;
    outputCharacters += digits[Number(d)] + radices[modulus];
   }
   if (modulus == 0 && zeroCount < 4) {
    outputCharacters += bigRadices[quotient];
   }
  }
  outputCharacters += CN_DOLLAR;
 }
 // Process decimal part if there is:
 if (decimal != "") {
  for (i = 0; i < decimal.length; i++) {
   d = decimal.substr(i, 1);
   if (d != "0") {
    outputCharacters += digits[Number(d)] + decimals[i];
   }
  }
 }
 // Confirm and return the final output string:
 if (outputCharacters == "") {
  outputCharacters = CN_ZERO + CN_DOLLAR;
 }
 if (decimal == "") {
  outputCharacters += CN_INTEGER;
 }
 outputCharacters = CN_SYMBOL + outputCharacters;
 return outputCharacters;
}
function sumNumbers(){
  var moneys = document.getElementsByName("rebMoney");
  var money = 0;
  for (var i = 0;i < moneys.length;i++){
    money+=new Number(moneys[i].value)
  }
  document.f1.moneyLower.value=money;
  document.f1.moneyUpper.value = convertCurrency(new Number(money));
  document.f1.balanceMoney.value=new Number(document.f1.accordMoney.value)-money;
  return false;
}
var show = false;
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
var check = "false";
var isOver = false;
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
    document.f1.totalMoney.value=totalMoney;
	return true;
}
function hidTemplate(){
	var e1 = document.getElementById("tempAdd");
	if (e1 != window.event.srcElement&&!isOver){
		e1.innerHTML = "显示模版";
	    templateDiv.style.display="none";
	    show=false;	
	}
}
function changeClick(){
	isOver = !isOver;
}
</script> 
    <!--提示框样式-->
    <style type="text/css">
	 .input{ width:90%; border:none;}
	 .show{ margin-top:20px;}
	 .input1{ width:50%; border:none;}
	</style>
</head>
<body onclick="hidTemplate();">

<form method="post" id="form1" action="reb!addDetailValue.action?path=update" name="f1">
<input type="hidden" name="totalMoney" value=""/>
<div id="templateDiv" onmouseover="changeClick();" onmouseout="changeClick();" style="position:absolute;width:250px;height:200px;background-color:rgb(235,235,235);overflow:auto;display:none;border:solid 1px red">
     <c:forEach items="${tempList}" var="temp" >
       <c:forEach items="${distinctTempList}" var="checkTempId">
         <c:if test="${checkTempId == temp.tempId}">
           <script>
             check = "true";
           </script>
         </c:if>
       </c:forEach>
       <script type="text/javascript">
         if (root == 0 || root != '${temp.type.typeName}'){
             template.add('${temp.type.typeName}',0,'${temp.type.typeName}',false,'${emp.empno}');
             template.add('${temp.tempName}','${temp.type.typeName}','${temp.tempName}',check,'${temp.tempId}','temp','#box${temp.tempId}');
         	root = '${temp.type.typeName}';
         }else{
             template.add('${temp.tempName }',root,'${temp.tempName}',check,'${temp.tempId}','temp','#box${temp.tempId}');
         }
         check="false";
       </script>
     </c:forEach>
     <script type="text/javascript">
        document.write(template);
     </script>
</div>
<input type="hidden" name="rebId" value="${rebursement.rebId}"/>
<div style="clear:both; height:15px; line-height:15px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="left">
		    <table width="85%"  border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="20%"><table width="167" border="0" cellpadding="0" cellspacing="0">
		          <tr>
		            <td align="left" class="back_main04">报销单编号:<font color="red">${rebursement.rebNumber}</font></td>
		          </tr>
		        </table></td>
		        <td width="15%" align="left">
		        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		          <tr>
		            <td align="left">填报日期:<input type="hidden" name="rebDate"   value="${rebursement.rebDate }" />${rebursement.rebDate }</td>
		          </tr>
		        </table></td>
		        <td width="15%" align="right">
		        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		          <tr>
		            <td align="center">同行人数:
		              <c:choose>
		                <c:when test="${param.check != 'check'}">
		                  <input type="text" name="peopleNumber" size="2" ${ param.oper} value="${rebursement.peopleNumber }" />
		                </c:when>
		                <c:otherwise>
		                  ${rebursement.peopleNumber }人
		                </c:otherwise>
		              </c:choose>
		            </td>
		          </tr>
		        </table></td>
		        <td width="25%" align="right">
		        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		          <tr>
		            <td align="center">出发日期:
		              <c:choose>
		                <c:when test="${param.check != 'check'}">
		                  <input type="text" name="startDate" ${ param.oper} readonly="readonly" onclick="calendar();" value="${rebursement.startDate }" />
		                </c:when>
		                <c:otherwise>
		                  ${rebursement.startDate }
		                </c:otherwise>
		              </c:choose>
		            </td>
		          </tr>
		        </table>
		        </td>
		        <td width="25%" align="right">
		        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		          <tr>
		            <td align="center">返回日期:
		              <c:choose>
		                <c:when test="${param.check != 'check'}">
		                  <input type="text" name="backDate" ${ param.oper} readonly="readonly" onclick="calendar();" value="${rebursement.backDate }" />
		                </c:when>
		                <c:otherwise>
		                  ${rebursement.backDate }
		                </c:otherwise>
		              </c:choose>
		            </td>
		          </tr>
		        </table>
		        </td>
		      </tr>
		    </table>
		    <table border="1" align="left" style="border-collapse:collapse;" width="80%" bgcolor="#FFFFFF" class="border03">
		        <tr>
		          <td colspan="10">
		            <table border="0" width="100%">
		              <tr>
		                <td  width="10%"></td>
		                <td  width="20%" align="left">机构:
			              <c:choose>
			                <c:when test="${param.check != 'check'}">
			                  <input type="text" name="orgName"  class="input1" ${ param.oper} value="${rebursement.orgName} "/>
			                </c:when>
			                <c:otherwise>
			                  ${rebursement.orgName }
			                </c:otherwise>
			              </c:choose>
			            </td>
			            <td  width="20%" align="left">部门:
			              <c:choose>
			                <c:when test="${param.check != 'check'}">
			                  <input type="text" name="dname" class="input1" ${ param.oper} value="${rebursement.dname} "/>
			                </c:when>
			                <c:otherwise>
			                  ${rebursement.dname }
			                </c:otherwise>
			              </c:choose>
			            </td>
			            <td  width="20%" align="left">出差任务:
			              <c:choose>
			                <c:when test="${param.check != 'check'}">
			                  <input type="text" name="duty"  class="input1"  ${ param.oper} value="${rebursement.duty} "/>
			                </c:when>
			                <c:otherwise>
			                  ${rebursement.duty }
			                </c:otherwise>
			              </c:choose>
			            </td>
			            <td  width="20%"  align="left">项目编号:
			              <c:choose>
			                <c:when test="${param.check != 'check'}">
			                  <input type="text" name="proNumber" class="input1" ${ param.oper} value="${rebursement.proNumber}"/>
			                </c:when>
			                <c:otherwise>
			                  ${rebursement.proNumber }
			                </c:otherwise>
			              </c:choose>
			            </td>
		              </tr>
		            </table>
		          </td>
		          
		        </tr>
		        <tr>
		          <td  width="11%" align="left">日期</td>
		          <td  width="12%" align="left">出发地</td>
		          <td  width="11%" align="left">日期</td>
		          <td  width="12%" align="left">到达地</td>
		          <td  width="10%" align="left">城际交通费</td>
		          <td  width="10%" align="left">其他费用</td>
		          <td  width="10%" align="left">住宿费</td>
		          <td  width="8%" align="left">出差补贴</td>
		          <td  width="5%" align="left">扣减</td>
		          <td  width="7%" align="left">扣前合计</td>
		        </tr>
		        <tbody id="addTr">
		        <c:forEach items="${rebursement.rebItemList}" var="item">
		        <tr>
		          <td>
		           <c:choose>
	                <c:when test="${param.check != 'check'}">
	                  <input type="hidden" name="itemId" value="0"/><input   type="hidden" name="beginDate" class="input"  value="${item.beginDate }"/>
	                  <input  type="text"  disabled  class="input"  value="${item.beginDate }"/>
	                </c:when>
	                <c:otherwise>
	                  ${item.beginDate }
	                </c:otherwise>
	               </c:choose>
		          </td>
		          <td>
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden"  name="beginPlace" class="input"  readonly="readonly" value="${item.beginPlace }"/><input  type="text"  disabled  class="input"  value="${item.beginPlace }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${item.beginPlace }
	                  </c:otherwise>
	                </c:choose>
	              </td>
		          <td>
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="endDate" class="input"  readonly="readonly" value="${item.endDate }"/><input  type="text"  disabled  class="input"  value="${item.endDate }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${item.endDate }
	                  </c:otherwise>
	                </c:choose>
		          </td>
		          <td>
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="endPlace" class="input"  readonly="readonly" value="${item.endPlace }" /><input  type="text"  disabled  class="input"  value="${item.endPlace }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${item.endPlace }
	                  </c:otherwise>
	                </c:choose>
	              </td>
		          <td>
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="cityTraffic" class="input"  readonly="readonly" value="${item.cityTraffic }" /><input  type="text"  disabled  class="input"  value="${item.cityTraffic }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${item.cityTraffic }
	                  </c:otherwise>
	                </c:choose>
	              </td>
		          <td>
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="others" class="input"  readonly="readonly" value="${item.others }"/><input  type="text"  disabled  class="input"  value="${item.others }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${item.others }
	                  </c:otherwise>
	                </c:choose>
	              </td>
		          <td>
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="zsMoney" class="input"  readonly="readonly" value="${item.zsMoney }" }/><input  type="text"  disabled  class="input"  value="${item.zsMoney }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${item.zsMoney }
	                  </c:otherwise>
	                </c:choose>
	              </td>
		          <td>
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="butie" class="input"  readonly="readonly" value="${item.butie }" /><input  type="text"  disabled  class="input"  value="${item.butie }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${item.butie }
	                  </c:otherwise>
	                </c:choose>
	              </td>
	              <td>
	                 <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="text" class="input" name="koujian" value="${ rebursement.koujian}"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${ rebursement.koujian}
	                  </c:otherwise>
	                 </c:choose>
	              </td>
		          <td>
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="total" class="input"  readonly="readonly" value="${item.total}" /><input  type="text"  disabled  class="input"  value="${item.total }"/>
	                  </c:when>
	                  <c:otherwise>
	                    <font color="red">${item.total }</font>
	                  </c:otherwise>
	                </c:choose>
	              </td>
		        </tr> 
		        </c:forEach>
		        
		        </tbody> 
		        <tr>
		          <td colspan="2" rowspan="3" align="left" >报销金额（人民币大写）</td>
		          <td colspan="4" rowspan="3" align="left" >
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="moneyUpper"  readonly class="input" value="${rebursement.moneyUpper }"/><input  type="text"  disabled  class="input"  value="${rebursement.moneyUpper }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${rebursement.moneyUpper }
	                  </c:otherwise>
	                </c:choose>
		          </td>
		          <td align="left" >报销金额</td>
		          <td colspan="3" align="left" >
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="moneyLower" readonly class="input"  value="${rebursement.moneyLower }"/><input  type="text"  disabled  class="input"  value="${rebursement.moneyLower }"/>
	                  </c:when>
	                  <c:otherwise>
	                    <font color="red">${rebursement.moneyLower }</font>
	                  </c:otherwise>
	                </c:choose>
		          </td>
		        </tr>
		        <tr>
		          <td align="left" >予借金额</td>
		          <td colspan="3" align="left" >
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="text" name="accordMoney"  class="input" ${ param.oper} value="${rebursement.accordMoney }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${rebursement.accordMoney }
	                  </c:otherwise>
	                </c:choose>
		          </td>
		        </tr>
		        <tr>
		          <td align="left" >结余或超值</td>
		          <td colspan="3"  align="left" >
		            <c:choose>
	                  <c:when test="${param.check != 'check'}">
	                    <input type="hidden" name="balanceMoney" readonly class="input" value="${rebursement.balanceMoney }"/><input  type="text"  disabled  class="input"  value="${rebursement.balanceMoney }"/>
	                  </c:when>
	                  <c:otherwise>
	                    ${rebursement.balanceMoney }
	                  </c:otherwise>
	                </c:choose>
		          </td>
		        </tr>
		        <tr>
		          <td  colspan="10" align="left">
		             <table border="0" width="100%">
		               <tr>
		                 <td width="10%"></td>
		                 <!-- 2014-05-12将负责人、复核人、审核人、报销人。修改为如下 -->
		                 <td width="20%">机构负责人:</td>
		                 <td width="20%">财务审核人:</td>
		                 <td width="20%">直属上级:</td>
		                 <td width="20%">报销人:<input type="hidden" name="empno" class="input1" value="${rebursement.emp.empno }"/>&nbsp;${rebursement.emp.ename }</td>
		               </tr>
		             </table>
		          </td>
		        </tr>
		      </table>
		    </td>
		  </tr>
		  <tr>
		    <td height="40" align="center" valign="bottom">&nbsp;</td>
		    <td align="right" valign="top">&nbsp;</td>
		  </tr>
		</table>
    </td>
  </tr>
  <tr>
    <td >
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
    	  <td align="left">
    	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      		  <tr>
        		<td width="20%">
        		  <table width="167" border="0" cellpadding="0" cellspacing="0">
          			<tr>
            		  <td align="left" class="back_main04"><span class="font_bold">明细表填写窗口</span></td>
          			</tr>
        		  </table>
        		</td>
        		<c:if test="${param.check != 'check' && param.oper != 'disabled'}">
        		<td width="60%" align="left">
		          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
		            <tr>
		              <td align="center"><a href="" onclick="return showTemplateDiv()" class="link_white" id="tempAdd">显示模版</a></td>
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
		        </c:if>
        	  </tr>
     	   </table>
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
      		    <c:forEach items="${beanList}" var="bean" >
	            <script>
	              sumValueArray[${bean.title.tempId }] = new Array();
	              totalArray[${bean.title.tempId }] = new Array();
	            </script>
	            <div id="box${bean.title.tempId}" class="show" style="display:block">
		          <table width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
				    <tr>
				      <td colspan="${fn:length(bean.title.itemList)-1 }">
				        <table border="0" cellpadding="0" cellspacing="0">
				          <tr>
				            <td width="30%" align="left">${bean.title.tempName }</td>
				            <td width="10%"></td>
				            <td align="left"><font color="red"><c:if test="${param.oper != 'disabled'}">${bean.title.tempDesc }</c:if></font></td>
				          </tr>
				        </table>
				      </td>
				      <td colspan="2" align="right">
				        <c:if test="${ param.oper != 'disabled'}">
				        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
				          <tr>
				            <td>
				              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
					            <tr>
					              <td align="center"><a href="#" onclick="sumValue(${bean.title.tempId })" class="link_white">统计计算</a></td>
					            </tr>
					          </table>
				            </td>
				            <td>
				              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
					            <tr>
					              <td align="center"><a href="#" id="getAtr${bean.title.tempId }" class="link_white">追加内容</a></td>
					            </tr>
					          </table>
				            </td>
				          </tr>
				        </table>
				        </c:if>
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
				      </td>
				    </tr>
				    
					<tr>
					 <c:forEach items="${bean.headList}" var="item" >
					   <td>${item.tempItemName }</td>
					 </c:forEach>
					 <script>
					 $(function(){ 
			            $("#getAtr${bean.title.tempId}").click(function(){ 
			            $str=''; 
			            $str+="<tr>"; 
					    <c:forEach items="${bean.headList}" var="item" >
					      <c:choose>
				           <c:when test="${item.valueType == 'date'}">
				             $str+="<td><input type='text' onclick='calendar();' readonly='readonly' class='input' name='detailValue-${item.tempItemId }'/></td>";
				           </c:when>
				           <c:when test="${item.valueType == 'int' || item.valueType =='float'}">
				             $str+="<td><input type='text'  class='input' name='detailValue-${item.tempItemId }' value='0'/></td>";
				           </c:when>
				           <c:otherwise>
				           $str+="<td><input type='text' class='input' name='detailValue-${item.tempItemId }'/></td>"
				           </c:otherwise>
				         </c:choose>
				        </c:forEach>
					    $str+="<td onClick='getDel(this)'><a href='#'>删除</a></td>"; 
		            	$str+="</tr>"; 
		            	$("#addTr${bean.title.tempId}").append($str);
		            	iFrameHeight(); 
		            	return false;}); 
		            	}); 
					 </script>
					</tr>
				    <tbody id="addTr${bean.title.tempId}">
				       
				       <c:forEach items="${bean.bodyList}" var="detaiList" >
				       <tr>
				         <c:forEach items="${detaiList}" var="detail" >
				         <c:choose>
				           <c:when test="${detail.item.valueType == 'date'}">
				             <td><input type="text" onclick="calendar();"  ${ param.oper}  readonly="readonly" class="input" name="detailValue-${detail.item.tempItemId }" value="${detail.detailValue }"/></td>
				           </c:when>
				           <c:otherwise>
				             <td><input type="text" class="input"  ${ param.oper}  name="detailValue-${detail.item.tempItemId }" value="${detail.detailValue }"/></td>
				           </c:otherwise>
				         </c:choose>
					     </c:forEach>
				       <td onclick='getDel(this)' width="30"><a href='#'>删除</a></td>
				      </tr>
				      </c:forEach>
				    </tbody> 
				     <tr>
				     
	                   <td width="90"><input type="text" readonly="readonly" class="input" name="detailValue-${bean.footList[0].item.tempItemId }" value="合计"/></td>
	                  
	                   <c:forEach begin="1" end="${fn:length(bean.footList)-2}" var="i" step="1" >
				         <c:choose>
				           <c:when test="${bean.footList[i].item.sumValue == 1}">
				             <td><input type="text" id="base${bean.footList[i].item.tempItemId }" ${ param.oper} class="input" name="detailValue-${bean.footList[i].item.tempItemId }" value="${bean.footList[i].detailValue }"/></td>
				             <script>
				               sumValueArray[${bean.title.tempId}].push(${bean.footList[i].item.tempItemId });
				             </script>
				           </c:when>
				           <c:otherwise>
				             <td align="center"><input type="text" ${ param.oper} readonly="readonly" class="input" name="detailValue-${bean.footList[i].item.tempItemId }" value="${bean.footList[i].detailValue }"/></td>
				           </c:otherwise>
				         </c:choose>
				         <c:if test="${bean.footList[i].item.totalValue == 1}">
			               <script>
			                 totalArray[${bean.title.tempId}].push(${bean.footList[i].item.tempItemId });
			               </script>
				         </c:if>
					   </c:forEach>
					   <td width="120">费用合计:<input type="hidden" readonly="readonly"  id="hidtotal${bean.title.tempId }" size="5" class="text2" name="totals" value="${bean.footList[fn:length(bean.footList)-1].detailValue }"/><input type="text" readonly="readonly"  ${ param.oper} id="total${bean.title.tempId }" size="5" class="input1" name="detailValue-${bean.footList[fn:length(bean.footList)-1].item.tempItemId }" value="${bean.footList[fn:length(bean.footList)-1].detailValue }"/></td>
	                 </tr>
				  </table>
		          </div> 
	            </c:forEach>
			    <c:forEach items="${tempList}" var="temp" >
			       <c:set var="isExits" value="0"></c:set>
			       <c:forEach items="${distinctTempList}" var="checkTempId">
			         <c:if test="${checkTempId == temp.tempId}">
			           <c:set var="isExits" value="1"></c:set>
			         </c:if>
			       </c:forEach>
			       <c:if test="${isExits == 0}">
			      <script>
		            sumValueArray[${temp.tempId }] = new Array();
		            totalArray[${temp.tempId }] = new Array();
		          </script>
			      <div id="box${temp.tempId }" class="show" style="display:none">
			          <table width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
					    <tr>
					      <td colspan="${fn:length(temp.itemList)-1 }">
					        <table border="0" cellpadding="0" cellspacing="0">
					          <tr>
					            <td width="30%" align="left">${temp.tempName }</td>
					            <td width="10%"></td>
					            <td align="left"><font color="red"><c:if test="${param.oper != 'disabled'}">${temp.tempDesc }</c:if></font></td>
					          </tr>
					        </table>
					      </td>
					      <td colspan="2" align="right">
					        
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
					             $str+="<td><input type='text' onclick='calendar();' readonly='readonly' class='input' name='detailValue-${item.tempItemId }'/></td>";
					           </c:when>
					           <c:when test="${item.valueType == 'int' || item.valueType =='float'}">
					             $str+="<td><input type='text'  class='input' name='detailValue-${item.tempItemId }' value='0'/></td>";
					           </c:when>
					           <c:otherwise>
					           $str+="<td><input type='text' class='input' name='detailValue-${item.tempItemId }'/></td>"
					           </c:otherwise>
					         </c:choose>
					        </c:forEach>
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
					             <td><input type="text" onclick="calendar();" readonly="readonly" class="input" name="detailValue-${item.tempItemId }"/></td>
					           </c:when>
					           <c:when test="${item.valueType == 'float' || item.valueType == 'int'}">
					             <td><input type="text" class="input" name="detailValue-${item.tempItemId }" value="0"/></td>
					           </c:when>
					           <c:otherwise>
					             <td><input type="text" class="input" name="detailValue-${item.tempItemId }"/></td>
					           </c:otherwise>
					         </c:choose>
						   </c:forEach>
					       <c:if test="${param.oper != 'disabled'}"><td width="30" onClick='getDel(this)'><a href='#'>删除</a></td></c:if>
					      </tr>
					    </tbody> 
					     <tr>
		                   <td width="90"><input type="text" readonly="readonly" class="input" name="detailValue-${temp.itemList[0].tempItemId }" value="合计"/></td>
		                  
		                   <c:forEach begin="1" end="${fn:length(temp.itemList)-2}" var="i" step="1" >
					         <c:choose>
					           <c:when test="${temp.itemList[i].sumValue == 1}">
					             <td><input type="text" id="base${temp.itemList[i].tempItemId }" class="input" name="detailValue-${temp.itemList[i].tempItemId }"/></td>
					             <script>
					               sumValueArray[${temp.tempId}].push(${temp.itemList[i].tempItemId });
					             </script>
					           </c:when>
					           <c:otherwise>
					             <td align="left"><input type="text"  readonly="readonly" class="input" name="detailValue-${temp.itemList[i].tempItemId }" value="—"/></td>
					           </c:otherwise>
					         </c:choose>
					         <c:if test="${temp.itemList[i].totalValue == 1}">
				             <script>
				             totalArray[${temp.tempId}].push(${temp.itemList[i].tempItemId });
				             </script>
					         </c:if>
						   </c:forEach>
						   <td width="120">费用合计:<input type="hidden" readonly="readonly"  id="hidtotal${temp.tempId }" size="5" class="text2" name="totals" value=""/><input type="text" readonly="readonly"  id="total${temp.tempId }" size="5" class="input1" name="detailValue-${temp.itemList[fn:length(temp.itemList)-1].tempItemId }" value=""/></td>
		                 </tr>
					  </table>
			          </div> 
			       </c:if>
			    </c:forEach>
			  </td>
		     </tr>
		    </table>
		   </td>
		 
		  </tr>
		</table>
    </td>
    
  </tr>
  <tr>
    <td height="40" align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="top">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" align="center"><c:if test="${param.oper != 'disabled'}"><input type="submit" value="提交"  onclick="return checkNext();"/></c:if><c:if test="${param.check == 'check' && rebursement.flag != 3}"><input type="button" value="审核通过"  onclick="confirmShenhe();"/></c:if>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="window.history.go(-1);" /></td>
  </tr>
  <tr>
    <td height="40" align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="top">&nbsp;</td>
  </tr>
  <tr>
    <td height="40" align="center" valign="bottom">&nbsp;</td>
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
		 $($(this).attr("title")).hide(200); 
	   }
	   iFrameHeight();
	 });
  $(".tishi").focus(function(){
	    $(this).next(".box").show(200);
	  }).blur(function(){
		$(this).next(".box").hide(200);
	})
</script>
</body>
</html>
