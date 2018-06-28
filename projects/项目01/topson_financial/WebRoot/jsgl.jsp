<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PU
BLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源查询</title>
<link href="css/style_main.css" rel="stylesheet" type="text/css" />
<script>
  function requestNewPage(){
  	var pageObj = document.getElementById("fenye");
  	var curPage = pageObj[pageObj.selectedIndex].value;
  	window.location.href="role!queryAllRole.action?curPage="+curPage
  }
</script>
</head>
<body>
<div style="clear:both; height:40px; line-height:40px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table02" >
  <tr>
    <td width="180" align="left"><table width="167" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="left" class="back_main04"><span class="font_bold">角色管理列表页面</span></td>
      </tr>
    </table></td>
    <td width="503" align="left">&nbsp;</td>
    <td width="27" align="right" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
      <tr>
        <td align="center"><a class="link_white" href="jsgl_add.jsp">+ 添加角色</a></td>
      </tr>
    </table></td>
  </tr>  
  <tr>
    <td colspan="3"> 
         <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dedede">
          <tr>
           <td align="center" class="back_main03"><span class="font_bold">选择</span></td>
           <td align="center" class="back_main03"><span class="font_bold">角色编号</span></td>
           <td align="center" class="back_main03"><span class="font_bold">角色名称</span></td>        
           <td align="center" class="back_main03"><span class="font_bold">操作</span></td>                   
         </tr>
         <c:forEach items="${pageModel.list}" var="role">
           <tr>
            <td height="35" align="center" bgcolor="#FFFFFF"><input type="checkbox" name="checkbox2" id="checkbox9" /></td>
            <td align="center" bgcolor="#FFFFFF">${role.roleId }</td>
            <td align="center" bgcolor="#FFFFFF">${role.roleName }</td>
            <td align="center" bgcolor="#FFFFFF">【<a href='role!queryOne.action?roleId=${role.roleId }' class="link_blue">修改</a>】 【<a href='role!delete.action?roleId=${role.roleId }' onclick="return confirm('确定要删除当前记录吗?')">删除</a>】【<a href='power!queryAllPower.action?roleId=${role.roleId }' class="link_blue">权限分配</a>】【<a href='emp!queryEmpByRoleId.action?roleId=${role.roleId }' class="link_blue">权限组分配</a>】</td>
           </tr>
         </c:forEach>
          
      </table>    </td>
  </tr>
  <tr>
    <td height="50" colspan="3" align="center">
       共${requestScope.pageModel.count}条记录,共分${requestScope.pageModel.totalPage}页,当前第${param.curPage}页
       <a href="role!queryAllRole.action?curPage=<c:if test="${param.curPage !=1}">${param.curPage-1 }</c:if><c:if test="${param.curPage ==1}">${param.curPage }</c:if>">上一页</a>|
       <a href="role!queryAllRole.action?curPage=<c:if test="${param.curPage != requestScope.pageModel.totalPage}">${param.curPage+1 }</c:if><c:if test="${param.curPage ==requestScope.pageModel.totalPage}">${requestScope.pageModel.totalPage }</c:if>">下一页</a>
      <select id="fenye" onchange="requestNewPage();">
        <c:forEach begin="1" end="${requestScope.pageModel.totalPage}" var="i" step="1">
           <option value="${i }" <c:if test="${param.curPage == i}">selected</c:if>>${ i}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td height="30" colspan="3" align="left">
      <input type="submit" name="button" id="button" value="删 除" /> <input type="submit" name="button2" id="button2" value="返 回" onclick="javascript:history.go(-1);" /></td>
  </tr>
</table>
<div style="clear:both; height:10px; line-height:10px;"></div>
</body>
</html>

