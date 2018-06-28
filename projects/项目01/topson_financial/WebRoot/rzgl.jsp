<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  	window.location.href="log!queryAll.action?curPage="+curPage
  }

</script>
</head>
<body>
<form name="f1" method="post" action="dept!deleteCheck.action" >
<div style="clear:both; height:40px; line-height:40px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table02" >
  <tr>
    <td align="left"><table width="167" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="left" class="back_main04" ><span class="font_bold">日志管理页面</span></td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
    <td align="right" valign="top">
    </td>
  </tr>  
  <tr>
    <td colspan="3"> 
         <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dedede">
          <tr>
           <td width="20%" align="center" class="back_main03"><span class="font_bold">日志类别</span></td>
           <td width="20%"  align="center" class="back_main03"><span class="font_bold">日志内容</span></td>        
           <td width="25" align="center" class="back_main03"><span class="font_bold">日志时间</span></td>  
           <td width="15%" align="center" class="back_main03"><span class="font_bold">操作人</span></td> 
         </tr>
         <c:forEach items="${requestScope.pageModel.list}" var="log">
           <tr>
            <td align="center" bgcolor="#FFFFFF">${log.logType }</td>
            <td align="center" bgcolor="#FFFFFF">${log.content }</td>            
            <td align="center" bgcolor="#FFFFFF">${log.year }-${log.month }-${log.day } : ${log.time }</td>
            <td align="center" bgcolor="#FFFFFF">${log.emp.ename }</td>
          </tr>
         </c:forEach>
      </table>    
    </td>
  </tr>
  <tr>
    <td height="50" colspan="3" align="center">
       共${requestScope.pageModel.count}条记录,共分${requestScope.pageModel.totalPage}页,当前第${param.curPage}页
       <a href="log!queryAll.action?curPage=<c:if test="${param.curPage !=1}">${param.curPage-1 }</c:if><c:if test="${param.curPage ==1}">${param.curPage }</c:if>">上一页</a>|
       <a href="log!queryAll.action?curPage=<c:if test="${param.curPage != requestScope.pageModel.totalPage}">${param.curPage+1 }</c:if><c:if test="${param.curPage ==requestScope.pageModel.totalPage}">${requestScope.pageModel.totalPage }</c:if>">下一页</a>
      <select id="fenye" onchange="requestNewPage();">
        <c:forEach begin="1" end="${requestScope.pageModel.totalPage}" var="i" step="1">
           <option value="${i }" <c:if test="${param.curPage == i}">selected</c:if>>${ i}</option>
        </c:forEach>
      </select>
      <script >
        document.getElementById("fenye").focus();
      </script>
    </td>
  </tr>
  <tr>
    <td height="30" colspan="3" align="left">
      <input type="submit" name="button" id="button" value="删 除" onclick="return deleteCheck();"/> </td>
  </tr>
</table>
</form>
<div style="clear:both; height:10px; line-height:10px;"></div>
</body>
</html>
