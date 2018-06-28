<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加修改信息</title>
<link href="css/style_main.css" rel="stylesheet" type="text/css" />
<script>
  function checkPassword(){
  	var value = document.form1.password.value;
  	if (value.length < 6 || value.length > 20){
  		alert("密码长度必须在6到20位之间!");
  		return false;
  	}
  	return true;
  }
</script>
</head>
<body>
<form action="emp!updatePassword.action" name="form1" method="post">
<div style="clear:both; height:15px; line-height:15px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="main_table03">
  <tr>
    <td width="570" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="50%"><table width="167" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="left" class="back_main04"><span class="font_bold">用户密码编辑窗口</span></td>
          </tr>
        </table></td>
        <td width="50%" align="right"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellpadding="0" cellspacing="10" bgcolor="#FFFFFF" class="border03">
          <tr>
            <td align="left">用户密码：
              <input type="text" name="password"  id="textfield2" class="input" value="${sessionScope.emp.password }"/></td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td><input type="submit" value="提交" onclick="return checkPassword();"/></td>
  </tr>
  <tr>
    <td height="40" align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="top">&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>

