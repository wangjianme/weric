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
<title>黑龙江省同信通信规划设计有限公司-登录页面</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
function changeimg()
{
  var myimg = document.getElementById("code"); 
  now = new Date(); 
  myimg.src="makeCertPic.jsp?code="+now.getTime();
}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; 
  for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++)
   x.src=x.oSrc;
}
function MM_preloadimages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadimages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; //获取该函数的参数数组
  document.MM_sr=new Array;//定义一个document下的一个数组
   for(i=0;i<(a.length-2);i+=3)
	   if ((x=MM_findObj(a[i]))!=null){
	      document.MM_sr[j++]=x; 
	      if(!x.oSrc)
	        x.oSrc=x.src; x.src=a[i+2];
	    }
}
//-->
</script>

</head>
<body onload="MM_preloadimages('images/login_btn02.gif')">

<div style="clear:both; height:80px; line-height:80px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="login_table">
  <tr>
    <td width="31" valign="bottom"><img src="images/login_img05.gif" width="31" height="65" /></td>
    <td width="296"><table width="296" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><img src="images/logo.jpg" width="296" height="105" /></td>
      </tr>
      <tr>
        <td valign="top" class="back_login02">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center"><div style="clear:both; height:6px; line-height:6px;"></div>
                <table width="82%" border="0" cellspacing="8" cellpadding="0">
                <c:if test="${operation != 'role'}">
                  <form id="form1" name="f1" action="emp!login.action" method="post">
                      ${result}
                      <tr>
                        <td colspan="2" align="left">用户名                        
                        <input type="text" name="empno" id="textfield" width="120" class="login_input" size="17" /></td>
                      </tr>
                      <tr>
                        <td colspan="2" align="left">密&nbsp;&nbsp;&nbsp;&nbsp;码
                          <input type="password" name="password" id="textfield2" width="120" class="login_input" size="17" ></td>
                      </tr>
                      <tr>
                        <td width="60%" align="left">验证码:
                          <!--<input type="text" name="checkcode" id="textfield3" class="login_input" width="60" size="7" />-->
                          <input name="yz" type="text" id="yz"class="login_input" width="60" size="7">
                         <img id="code" src="makeCertPic.jsp" title="看不清?点击更换" onclick="javascript:changeimg()"/>
                          </td>
<!--                         <td width="40%" align="left"><img id='imag' src="<%=path %>/code!code.action" width="80" height="30" /> -->
                        
                        </td>
                      </tr>
                     
                    </c:if>
                    
                    <c:if test="${operation == 'role'}">
                      <tr>
                        <td>
                          <form action="power!queryEmpPowerByRoleId.action" method="post">
                              您已登录成功，请选择身份:<br/><br/>
						  	  身份选择:<select name="roleId">
						  	    <c:forEach items="${roleList}" var="role"><!-- roleList -->
						  	      <option value="${role.roleId }">${role.roleName }</option>
						  	    </c:forEach>
						  	  </select>
						  <br/>
						  <br/>
                        </td>
                      </tr>
                    </c:if>
                  </table></td>
              </tr>
              <tr>
                <td height="90" align="center"><input type="image" src="images/login_btn01.gif" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image5','','images/login_btn02.gif',1)"/> </td>
              </tr>
            </table>
          </form>
        </td>
      </tr>
    </table></td>
    <td width="31" valign="bottom"><img src="images/login_img06.gif" width="31" height="65" /></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="login_table02">
  <tr>
    <td align="center">power by hljtx.com 2013</td>
  </tr>
  <tr>
    <td height="30" align="center" valign="bottom">黑龙江省同信通信规划设计有限公司</td>
  </tr>
</table>
</form>
</body>
</html>
