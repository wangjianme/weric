<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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

function iFrameHeight() { 
	var ifm= window.parent.document.getElementById("iframepage"); 
	var subWeb = window.parent.document.frames ? window.parent.document.frames["iframepage"].document : ifm.contentDocument; 
	if(ifm != null && subWeb != null) { 
	    if (subWeb.body.scrollHeight > 549){
	      ifm.height = subWeb.body.scrollHeight+150; 
	    }
		
	} 
} 
iFrameHeight();
window.parent.scrollTo(0,0);
</script>
</head>

<body>
<div style="clear:both; height:40px; line-height:40px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table02" >
  <tr>
    <td align="left"><table width="167" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="left" class="back_main04"><span class="font_bold">错误信息确认</span></td>
      </tr>
    </table></td>
    
  </tr>  
  <tr>
    <td colspan="3" align="center" height="120">&nbsp; 
    </td>
  </tr>
  
  <tr>
    <td colspan="3" align="center"> 
      <font color='red'>出现了操作错误信息！此问题可能是因为未填写必要数据而导致，请联系管理员并按照操作手册重新操作！</font>
    </td>
    
  </tr>
  <% 
     Exception exception = (Exception)request.getAttribute("exception");
     exception.printStackTrace();
  %>
  <tr>
    <td height="50" colspan="3" align="center"></td>
  </tr>
  <tr>
    <td height="30" colspan="3" align="center">
      <input type="submit" name="button2" id="button2" value="返 回" onclick="javascript:history.go(-1);" />
    </td>
  </tr>
</table>
<div style="clear:both; height:10px; line-height:10px;"></div>
</body>
</html>

