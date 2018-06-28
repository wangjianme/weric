<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源查询</title>
<link href="css/style_main.css" rel="stylesheet" type="text/css" />
<script>
  function requestNewPage(){
  	var pageObj = document.getElementById("fenye");
  	var curPage = pageObj[pageObj.selectedIndex].value;
  	window.location.href="reb!queryMyRebursement.action?curPage="+curPage
  }
  function confirmUpdate(){
  	return confirm("此操作将会把该报销单提交到部门审核，确认提交吗?");
  }
  function confirmDelete(){
	return confirm("此操作将删除报销单数据，确定要删除吗?");
  }
  function deleteCheck(){
	var deptnos = document.getElementsByName("deptnos");
	for (var i = 0;i < deptnos.length;i++){
		if (deptnos[i].checked){
			return confirm("此操作将删除所选数据，确定删除吗?"); 
		}
    }
    alert("尚未选择要删除数据");
	return false;
  }
</script>
</head>
<body>
<div style="clear:both; height:40px; line-height:40px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table02" >
  <tr>
    <td align="left" width="25%"><table width="167" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="left" class="back_main04" ><span class="font_bold">个人报销单管理页面</span></td>
      </tr>
    </table></td>
    <td width="45%" align="left">
    <form name="f2" method="post" action="reb!queryByRebNumber.action" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td align="center" width="80%">报销单编号:<input type="text" name="rebNumber" value="${param.rebNumber }"/></td>
        <td align="left"><input type="submit" value="查询" onclick="return checkRebId();"/></td>
        <td align="left"></td>
      </tr>
    </table>
    <script>
      function checkRebId(){
         var value = document.f2.rebNumber.value;
         if (value == ""){
			alert("请填写报销单编号!");
			return false;
         }
         return true;
      }
      function showMessage(rebId){
    	if (confirm("需要查看意见吗?")){
   			var returnv=window.showModalDialog("reb!queryMessage.action?rebId="+rebId+"&check=disabled","回退意见","dialogTop=200;dialogLeft=300;dialogHeight=250px;dialogWidth=500px;help=no;fullscreen=1;status=no;center=yes");
   		}
   		return false;
      }
    </script>
    </form>
    
    </td>
    <td align="right"  width="30%">
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
      <tr>
        <td align="center"><a class="link_white" href="reb!queryDept.action">+ 添加报销单</a></td>
      </tr>
    </table></td>
  </tr>  
  <tr>
    <td colspan="3"> 
         <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dedede">
          <tr>
           <td width="20%" align="center" class="back_main03"><span class="font_bold">报销单编号</span></td>
           <td align="center" class="back_main03"><span class="font_bold">申请人</span></td>
           <td align="center" class="back_main03"><span class="font_bold">机构</span></td>
           <td align="center" class="back_main03"><span class="font_bold">申请日期</span></td>
           <td align="center" class="back_main03"><span class="font_bold">状态</span></td>           
           <td width="30%" align="center" class="back_main03"><span class="font_bold">操作</span></td>         
         </tr>
         <c:forEach items="${requestScope.pageModel.list}" var="reb">
           <tr>
            <td width="20%" align="center" class="back_main03"><span class="font_bold">${reb.rebNumber }</span></td>
            <td align="center" class="back_main03"><span class="font_bold">${reb.emp.ename }</span></td>
            <td align="center" class="back_main03"><span class="font_bold">${reb.orgName }</span></td>
            <td align="center" class="back_main03"><span class="font_bold">${reb.rebDate }</span></td>
            <td align="center" class="back_main03"><span class="font_bold"><c:if test="${reb.flag ==0}">未提交</c:if><c:if test="${reb.flag ==6 || reb.flag==8 || reb.flag==1 || reb.flag==3 || reb.flag==2}">待审核</c:if><c:if test="${reb.flag ==7}"><a href='' class="link_blue" onclick="return showMessage('${reb.rebId}');">回退单</a></c:if><c:if test="${reb.flag ==4}">审核完成</c:if></span></td>       
            <td align="center" class="back_main03">
                <c:if test="${reb.flag == 6 || reb.flag==4 || reb.flag==8 || reb.flag==1 || reb.flag==3 || reb.flag==2}">
                  【<a href='reb!queryOne.action?rebId=${reb.rebId }&oper=disabled' class="link_blue">查看</a>】
                </c:if>
                <c:if test="${reb.flag==4}">
                【<a href='reb!queryOne.action?rebId=${reb.rebId }&path=print&type=mx' target="_blank" class="link_blue">打印明细单</a>】
                </c:if>
                <c:if test="${reb.flag==0 || reb.flag==7}">
                【<a href='reb!queryOne.action?rebId=${reb.rebId }&oper=edit' class="link_blue">编辑</a>】 
                【<a href='reb!updateFlag.action?flag=5&rebId=${reb.rebId }' onclick="return confirmDelete();">删除</a>】
                
                <c:if test="${reb.flag==0}">
                【<a href='reb!updateFlag.action?flag=6&rebId=${reb.rebId }' onclick="return confirmUpdate();">提交审核</a>】
               </c:if> 
               <c:if test="${reb.flag==7}">
                【<a href='reb!updateFlag.action?flag=8&rebId=${reb.rebId }' onclick="return confirmUpdate();">提交审核</a>】
               </c:if> 
               
               </c:if>
               </td>
          </tr>
         </c:forEach>
      </table>    
    </td>
  </tr>
  <tr>
    <td height="50" colspan="3" align="center">
       共${requestScope.pageModel.count}条记录,共分${requestScope.pageModel.totalPage}页,当前第${param.curPage}页
       <c:if test="${requestScope.pageModel.count > 6}">
          <a href="reb!queryMyRebursement.action?curPage=<c:if test="${param.curPage !=1}">${param.curPage-1 }</c:if><c:if test="${param.curPage ==1}">${param.curPage }</c:if>">上一页</a>|
          <a href="reb!queryMyRebursement.action?curPage=<c:if test="${param.curPage != requestScope.pageModel.totalPage}">${param.curPage+1 }</c:if><c:if test="${param.curPage ==requestScope.pageModel.totalPage}">${requestScope.pageModel.totalPage }</c:if>">下一页</a>
       </c:if>
      <c:if test="${requestScope.pageModel.count <= 6}">
          上一页|下一页
       </c:if>
      <select id="fenye" onchange="requestNewPage();">
        <c:forEach begin="1" end="${requestScope.pageModel.totalPage}" var="i" step="1">
           <option value="${i }" <c:if test="${param.curPage == i}">selected</c:if>>${ i}</option>
        </c:forEach>
      </select>
      <script >
        document.getElementById("fenye").focus();
      </script>
      <c:if test="${fn:length(pageModel.list) == 0}">
        <script>
          alert("查找不到数据!");
        </script>
      </c:if>
    </td>
  </tr>
</table>
</form>
<div style="clear:both; height:10px; line-height:10px;"></div>
</body>
</html>
