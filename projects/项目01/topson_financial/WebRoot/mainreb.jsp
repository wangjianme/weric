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
<script>
window.parent.scrollTo(0,0);
$(function(){ 
$("#getAtr").click(function(){ 
$str=''; 
$str+="<tr>"; 
$str+="<td><input type=\"hidden\" name=\"itemId\" value=\"0\"/><input type=\"text\" name=\"beginDate\" class=\"input\"/></td>";
$str+="<td><input type=\"text\"  name=\"beginPlace\" class=\"input\"/></td>";
$str+="<td><input type=\"text\" name=\"endDate\" class=\"input\"/></td>";
$str+="<td><input type=\"text\" name=\"endPlace\" class=\"input\"/></td>";
$str+="<td><input type=\"text\" name=\"cityTraffic\" class=\"input\"/></td>";
$str+="<td><input type=\"text\" name=\"others\" class=\"input\"/></td>";
$str+="<td><input type=\"text\" name=\"zsMoney\" class=\"input\"/></td>";
$str+="<td><input type=\"text\" name=\"butie\" class=\"input\"/></td>";
$str+="<td><input type=\"text\" name=\"total\" class=\"input\"/></td>";
$str+="</tr>"; 
$("#addTr").append($str); 
iFrameHeight();
}); 
}); 
function iFrameHeight() { 
	var ifm= window.parent.document.getElementById("iframepage"); 
	var subWeb = window.parent.document.frames ? window.parent.document.frames["iframepage"].document : ifm.contentDocument; 
	if(ifm != null && subWeb != null) { 
	    if (subWeb.body.scrollHeight > 549){
	      ifm.height = subWeb.body.scrollHeight; 
	    }
		
	} 
} 
function getDel(k){ 
	if (confirm("确定删除?")){
		$(k).parent().remove(); 
		iFrameHeight();
	}
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
  var moneys = document.getElementsByName("total");
  var money = 0;
  for (var i = 0;i < moneys.length;i++){
    money+=new Number(moneys[i].value)
  }
  money-=new Number(document.f1.koujian.value);
  document.f1.moneyLower.value=money;
  document.f1.moneyUpper.value = convertCurrency(new Number(money));
  document.f1.balanceMoney.value=new Number(document.f1.accordMoney.value)-money;
  return false;
}

</script> 
    <!--提示框样式-->
    <style type="text/css">
	 .input{ width:90%; border:none;}
	 .input1{ width:60%; border:none;}
	</style>
</head>
<body>
<form method="post" id="form1" action="reb!saveRebursement.action" name="f1">
<input type="hidden" name="rebId" value="${param.rebId }"/>
<input type="hidden" name="totalMoney" value="${param.totalMoney }"/>
<input type="hidden" name="peopleNumber" value="${param.peopleNumber }"/>
<input type="hidden" name="koujian" value="${param.koujian }"/>
<div style="clear:both; height:15px; line-height:15px;"></div>
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%"><table width="167" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="left" class="back_main04"><span class="font_bold">报销单填写窗口</span></td>
          </tr>
        </table></td>
        
         <td width="23%" align="right">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
          <tr>
            <td align="center">填报日期:<input type="hidden" name="rebDate" readonly="readonly" onclick="calendar();" value="${param.rebDate }" />${param.rebDate }</td>
          </tr>
        </table></td>
         <td width="10%" align="right">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
          <tr>
            <td align="center">报销人数:${param.peopleNumber }人</td>
          </tr>
        </table></td>
        <td width="23%" align="right">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
          <tr>
            <td align="center">出发日期:<input type="hidden" name="startDate"  value="${param.startDate }" />${param.startDate }</td>
          </tr>
        </table>
        </td>
        <td width="24%" align="right">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
          <tr>
            <td align="center">返回日期:<input type="hidden" name="backDate"  value="${param.backDate }" />${param.backDate }</td>
          </tr>
        </table>
        </td>
      </tr>
    </table>
    <table border="1" align="center" style="border-collapse:collapse;" width="100%" bgcolor="#FFFFFF" class="border03">
        <tr>
          <td colspan="10">
            <table border="0" width="100%">
              <tr>
                <td  width="10%"></td>
                <td  width="20%" align="left">机构:<input type="text" readonly name="orgName" class="input1" value="${param.orgName} "/></td>
	            <td  width="20%" align="left">部门:<input type="text" readonly  name="dname" class="input1" value="${param.dname} "/></td>
	            <td  width="20%" align="left">出差任务:<input type="text" readonly  name="duty" class="input1" value="${param.duty} "/></td>
	            <td  width="20%"  align="left">项目编号:<input type="text" readonly  name="proNumber" class="input1" value="${param.proNumber}"/></td>
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
        
        <c:forEach items="${itemList}" var="item" varStatus="status">
        <tr>
          <td align="left"><input type="hidden" name="itemId" value="0"/><input  type="hidden" name="beginDate" class="input" value="${item.beginDate }"/>${item.beginDate }</td>
          <td align="left"><input type="hidden"  name="beginPlace" class="input"  value="${item.beginPlace }"/>${item.beginPlace }</td>
          <td align="left"><input type="hidden" name="endDate" class="input"  value="${item.endDate }"/>${item.endDate }</td>
          <td align="left"><input type="hidden" name="endPlace" class="input"  value="${item.endPlace }"/>${item.endPlace }</td>
          <td align="left"><input type="hidden" name="cityTraffic" class="input"  value="${item.cityTraffic }"/>${item.cityTraffic }</td>
          <td align="left"><input type="hidden" name="others" class="input"  value="${item.others }"/>${item.others }</td>
          <td align="left"><input type="hidden" name="zsMoney" class="input"  value="${item.zsMoney }"/>${item.zsMoney }</td>
          <td align="left"><input type="hidden" name="butie" class="input"  value="${item.butie }"/>${item.butie }</td>
          <td align="left">
            <c:choose>
              <c:when test="${status.index == 0}">
                ${param.koujian }
              </c:when>
              <c:otherwise>
                  0.0
              </c:otherwise>
            </c:choose>
          </td>
          <td align="left"><input type="hidden" name="total" class="input"  value="${item.total}"/>${item.total }</td>
        </tr> 
        </c:forEach>
        
        </tbody> 
        <tr>
          <td colspan="2" rowspan="3" align="left" >报销金额（人民币大写）</td>
          <td colspan="4" rowspan="3" align="left" ><input type="text" name="moneyUpper"  readonly class="input"/></td>
          <td align="left" >报销金额</td>
          <td colspan="3" align="left" ><input type="text" name="moneyLower" readonly class="input"/></td>
        </tr>
        <tr>
          <td align="left" >予借金额</td>
          <td colspan="3" align="left" ><input type="text" name="accordMoney" readonly class="input" value="${param.accordMoney }"/></td>
        </tr>
        <tr>
          <td align="left" >结余或超值</td>
          <td colspan="3"  align="left" ><input type="text" name="balanceMoney" readonly class="input"/></td>
        </tr>
        <tr>
          <td  colspan="10" align="left">
             <table border="0" width="100%">
               <tr>
                 <td width="10%"></td>
                 <td width="20%">负责人:</td>
                 <td width="20%">复核人:</td>
                 <td width="20%">审核人:</td>
                 <td width="20%">报销人:<input type="hidden" name="empno" class="input1" value="${sessionScope.emp.empno }"/>&nbsp;${sessionScope.emp.ename }</td>
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
    <td colspan="2" align="center"><input type="button" value="上一步"  onclick="window.history.go(-1);"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="完成"  onclick="return checkBoxes();"/></td>
  </tr>
</table>
</form>
<script type="text/javascript">
sumNumbers();
</script>
</body>
</html>
