<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
</head>
<body>
<div style="clear:both; height:40px; line-height:40px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table02" >
  <tr>
    <td width="180" align="left"><table width="167" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="left" class="back_main04"><span class="font_bold">用户管理列表页面</span></td>
      </tr>
    </table></td>
    <td width="503" align="left">&nbsp;</td>
    <td width="27" align="right" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
      <tr>
        <td align="center"><a class="link_white" href="yhgl_Add.html">+ 添加用户</a></td>
      </tr>
    </table></td>
  </tr>  
  <tr>
    <td colspan="3"> 
         <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dedede">
          <tr>
           <td align="center" class="back_main03"><span class="font_bold">选择</span></td>
           <td align="center" class="back_main03"><span class="font_bold">用户工号</span></td>
           <td align="center" class="back_main03"><span class="font_bold">姓名</span></td>
           <td align="center" class="back_main03"><span class="font_bold">部门名称</span></td>
           <td align="center" class="back_main03"><span class="font_bold">职位</span></td>        
           <td align="center" class="back_main03"><span class="font_bold">操作</span></td>                   
         </tr>
          <tr>
            <td height="35" align="center" bgcolor="#FFFFFF"><input type="checkbox" name="checkbox2" id="checkbox9" /></td>
            <td align="center" bgcolor="#FFFFFF">10000</td>
            <td align="center" bgcolor="#FFFFFF">李明</td>
            <td align="center" bgcolor="#FFFFFF">男</td>
            <td align="center" bgcolor="#FFFFFF">软件部</td>
            <td align="center" bgcolor="#FFFFFF">部门经理</td>
            <td align="center" bgcolor="#FFFFFF">【<a href='yhgl_add.html' class="link_blue">修改</a>】 【<a href='#'>删除</a>】【<a href='yhgl_qxfp.html' class="link_blue">角色分配</a>】</td>
          </tr>
      </table>    
    </td>
  </tr>
  <tr>
    <td height="50" colspan="3" align="center"><img src="images/fanye.jpg" width="343" height="22" /></td>
  </tr>
  <tr>
    <td height="30" colspan="3" align="left">
      <input type="submit" name="button" id="button" value="删 除" /> <input type="submit" name="button2" id="button2" value="返 回" onclick="javascript:history.go(-1);" /></td>
  </tr>
</table>
<div style="clear:both; height:10px; line-height:10px;"></div>
</body>
</html>
