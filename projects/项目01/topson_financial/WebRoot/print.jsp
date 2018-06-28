<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>黑龙江省同信通信规划设计有限公司</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!--提示框样式-->
    <style type="text/css">
	 .input{ width:90%; border:none;}
	 body{
		background-color:#ffffff;
		text-align:left;
		margin:0px;
		color: #5c5c5c;
		font-size: 50px;
	  }
	</style>
	<style type="text/css" media=print>
     .noprint{display : none }
	</style>
	<script language="javascript"> 
function printsetup(){ 
// 打印页面设置 
wb.execwb(8,1); 
} 
function printpreview(){ 
// 打印页面预览 

wb.execwb(7,1); 
} 

function printit() 
{ 
if (confirm('确定打印吗？')) { 
  wb.execwb(6,6);
} 
} 
</script> 
</head> 
  <body>
  <c:if test="${param.type=='reb'}">
  <br/>
      <table width="80%" border="0" align="right" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="center">
		      <table width="100%"  border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;">
		        <tr>
		          <td align="center" colspan="3"><font size="6px" >差旅费报销单</font></td>
		        </tr>
		        <tr>
		        <!-- 加 -->
		        <td width="20%" align="right">
		        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		          <tr>
		            <td align="center">工号:${emp.empno }</td>
		          </tr>
		        </table>
		        </td>
		          <td width="20%" align="right">
		            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		              <tr>
		               <td align="center">填报日期:${rebursement.rebDate }</td>
		            </tr>
		           </table>
		        </td>
		        <td width="20%" align="right">
		        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		          <tr>
		            <td align="center">出发日期:${rebursement.startDate }</td>
		          </tr>
		        </table>
		        </td>
		        <td width="20%" align="right">
		        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		          <tr>
		            <td align="center">返回日期:${rebursement.backDate }</td>
		          </tr>
		        </table>
		        </td>
		        <!-- 2014-05-12 添加报销单编号 （打印差旅费报销单时新添加的）-->
		        <td width="20%" align="right">
		        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		          <tr>
		            <td align="center">报销单编号:${rebursement.rebNumber }</td>
		          </tr>
		        </table>
		        </td>
		      </tr>
		    </table>
		    <table border="1" align="center" style="border-collapse:collapse;" width="100%" bgcolor="#FFFFFF" class="border03">
		        <tr>
		          <td colspan="9">
		            <table border="0" width="100%">
		              <tr>
		                <td  width="10%"></td>
		                <td  width="20%" align="left">机构:${rebursement.orgName}</td>
			            <td  width="20%" align="left">部门:${rebursement.dname}</td>
			            <td  width="20%" align="left">出差任务:${rebursement.duty}</td>
			            <td  width="20%"  align="left">项目编号:${rebursement.proNumber}</td>
		              </tr>
		            </table>
		          </td>
		          
		        </tr>
		        <tr>
		          <td  width="11%" align="left">日期</td>
		          <td  width="12%" align="left">出发地</td>
		          <td  width="11%" align="left">日期</td>
		          <td  width="12%" align="left">到达地</td>
		          <td  width="10%" align="left">交通费</td>
		          <td  width="10%" align="left">其他费用</td>
		          <td  width="10%" align="left">住宿费</td>
		          <td  width="10%" align="left">出差补贴</td>
		          <td  width="10%" align="left">合计</td>
		        </tr>
		        <tbody id="addTr">
		        
		        <c:forEach items="${rebursement.rebItemList}" var="item" varStatus="status">
		        <tr>
		          <td>${item.beginDate }</td>
		          <td>${item.beginPlace }</td>
		          <td>${item.endDate }</td>
		          <td>${item.endPlace }</td>
		          <td>${item.cityTraffic }</td>
		          <td>${item.others }</td>
		          <td>${item.zsMoney }</td>
		          <c:choose>
		            <c:when test="${status.index == 0}">
		               <td>${item.butie -rebursement.koujian }</td>
		            </c:when>
		            <c:otherwise>
		               <td>${item.butie }</td>
		            </c:otherwise>
		          </c:choose>
		          <c:choose>
		            <c:when test="${status.index == 0}">
		              <td>${item.total-rebursement.koujian }</td>
		            </c:when>
		            <c:otherwise>
		              <td>${item.total}</td>
		            </c:otherwise>
		          </c:choose>
		          
		          
		        </tr> 
		        </c:forEach>
		        <c:forEach begin="1" end="${5-fn:length(rebursement.rebItemList)}" var="i" step="1">
		        <tr>
		          <td>&nbsp;</td>
		          <td>&nbsp;</td>
		          <td>&nbsp;</td>
		          <td>&nbsp;</td>
		          <td>&nbsp;</td>
		          <td>&nbsp;</td>
		          <td>&nbsp;</td>
		          <td>&nbsp;</td>
		          <td>&nbsp;</td>
		        </tr> 
		        </c:forEach>
		        </tbody> 
		        <tr>
		          <td colspan="2" rowspan="3" align="left" >报销金额（大写）</td>
		          <td colspan="4" rowspan="3" align="left" >${rebursement.moneyUpper }</td>
		          <td align="left" >报销金额</td>
		          <td colspan="2" align="left" >${rebursement.moneyLower }</td>
		        </tr>
		        <tr>
		          <td align="left" >予借金额</td>
		          <td colspan="2" align="left" >${rebursement.accordMoney }</td>
		        </tr>
		        <tr>
		          <td align="left" >结余超支</td>
		          <td colspan="2"  align="left" >${rebursement.balanceMoney }</td>
		        </tr>
		        <tr>
		          <td  colspan="9" align="left">
		             <table border="0" width="100%">
		               <tr>
		                 <td width="10%"></td>
		                 <!-- 2014-05-12将负责人、复核人、审核人、报销人。修改为如下 -->
		                 <td width="20%">机构负责人:</td>
		                 <td width="20%">财务审核人:</td>
		                 <td width="20%">直属上级:</td>
		                 <td width="20%">报销人:${rebursement.emp.ename }</td>
		               </tr>
		             </table>
		          </td>
		        </tr>
		      </table>
		    </td>
		  </tr>
		  <tr>
	          <td  colspan="9" align="center" height="100">
	            
	          </td>
          </tr>
		  <tr>
	          <td  colspan="9" align="center">
	             <table border="1" width="30%" style="border-collapse:collapse;" class="border03">
	               <tr>
	                 <td width="30%" height="70px" valign="middle" align="center">实际费用</td>
	                 <td width="30%" height="70px"  valign="middle" align="center">补贴费用</td>
	                 <td width="30%" height="70px"  valign="middle" align="center">合计</td>
	               </tr>
	               <tr>
	                 <td width="30%" height="50px" valign="middle" align="center">${sum.total }</td>
	                 <td width="30%" height="50px"  valign="middle" align="center">${sum.butie-rebursement.koujian  }</td>
	                 <td width="30%" height="50px"  valign="middle" align="center">${sum.zongji -rebursement.koujian }</td>
	               </tr>
	             </table>
	          </td>
          </tr>
		  <tr>
		    <td height="40" align="center" valign="bottom">&nbsp;</td>
		    <td align="right" valign="top">&nbsp;</td>
		  </tr>
		</table>
    </c:if>
    <c:if test="${param.type=='mx'}">
      <br/>
	  <h3 align="center">出差补助费用明细单</h3>
	  <br/>
	<div align="center">  
    <table width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" class="border03">
      <tr>
        <td>
 		<c:set var="oldTempId" value="0"></c:set>
    	<c:set var="oldGroupId" value="0"></c:set>
    	<c:forEach items="${detailList}" var="detail">
      	  <c:if test="${detail.template.tempId != oldTempId}">
            <c:if test="${oldTempId != 0}">
		        </tr>
		      </table>
		    </div>  
              <c:set var="oldGroupId" value="0"></c:set>
            </c:if>
  		    <div id="box${detail.template.tempId }" class="show">
            <table width="100%" border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
		      <tr>
		        <td colspan="9">${detail.template.tempName }</td>
		        </td>
		      </tr>
			  <tr>
			    <c:forEach items="${detail.template.itemList}" var="item" >
			      <td>${item.tempItemName }</td>
			    </c:forEach>
			     <script>
		           sumValueArray[${detail.template.tempId }] = new Array();
		         </script>
			  </tr>
			  <tbody id="addTr${detail.template.tempId}">
			  <c:set var="oldTempId" value="${detail.template.tempId}"></c:set>
	        </c:if>
	        <c:if test="${detail.groupId != oldGroupId}">
	          <c:if test="${oldGroupId != 0 }">
	            </tr> 
		      </c:if>
		      <c:if test="${detail.detailValue == '合计'}">
		      </tbody>
		      </c:if> 
			  <tr>
			  <c:set var="oldGroupId" value="${detail.groupId}"></c:set>
		    </c:if>
		    <td>${detail.detailValue }</td>
    	  </c:forEach>
          </tr>
          <tr>
            <td colspan="9">
              <table width="100%">
                <tr>
                <!-- 2014-05-12审核人 改为直属上级 -->
                  <td align="right">直属上级:</td>
                  <td align="center" width="30%">&nbsp;</td>
                  <td align="left">报销人:${rebursement.emp.ename }</td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
         
	    </div>
	  </div>
      </c:if>
      
      <br/>
      <br/>
      <OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id=wb name=wb width=0></OBJECT> 
      <table>
       
      </table>
      
  </body>
</html>
