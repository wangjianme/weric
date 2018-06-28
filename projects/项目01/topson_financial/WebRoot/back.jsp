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
    
    <title>回退意见窗口</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	  function sub(){
	    var value = document.f1.yijian.innerHTML;
	    if (value == ""){
	      alert("请填写意见");
	      return;
	    }
	    window.returnValue=value;
		window.close();
	  }
	</script>
  </head>
  
  <body style="background:url(<%=path %>/images/main_back01.gif) repeat-x;background-color:#ffffff;">
    <h1 align="center">回退意见窗口</h1>
    <form action="reb!back.action?rebId=${param.rebId }" method="post" name="f1">
      <table width="120" align="center" border="0" cellpadding="0" cellspacing="0" class="main_table02" >
        <tr>
          <td align="center">
            <textarea rows="8" cols="55" name="yijian" ${param.check }>${message.message }</textarea>
          </td>
        </tr>
        <tr>
          <td align="center">&nbsp;</td>
        </tr>
        <tr>
          <td align="center"><c:if test="${param.check != 'disabled'}"><input type="button" value="提交" onclick="sub();">&nbsp;&nbsp;&nbsp;&nbsp;</c:if><input type="button" value="关闭" onclick="window.close();"></td>
        </tr>
      
      </table>
    </form>
  </body>
</html>
