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
<title>添加修改信息</title>
   <link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=path%>/css/style_main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path%>/themes/base/jquery.ui.all.css"/>
<link rel="StyleSheet" href="dtree.css" type="text/css" />
<script src="<%=path%>/dtree.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>

<script type="text/javascript">
var group = new dTree('group');
var root = 0;
var secRoot = 0;
var powerCode = false;
var check = "false";
group.add(root,-1,'权限群组','');
function checkNode(nodeId){
	group.checkNode(nodeId);
	iFrameHeight();
}
function iFrameHeight() { 
	var ifm= window.parent.document.getElementById("iframepage"); 
	var subWeb = window.parent.document.frames ? window.parent.document.frames["iframepage"].document : ifm.contentDocument; 
	if(ifm != null && subWeb != null) { 
	    if (subWeb.body.scrollHeight > 549){
	      ifm.height = subWeb.body.scrollHeight+200; 
	    }
		
	} 
} 

$(document).ready(function (){
	$("#d1").html("");
});
</script>
</head>
<body>
<form  id="form1"  name="f1" action="emp!updateGroup.action" method="post">
<input type="hidden" value="${role.roleId }" name="roleId"/>
<div style="clear:both; height:15px; line-height:15px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="main_table03">
  <tr>
    <td width="570" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="50%"><table width="167" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="left" class="back_main04"><span class="font_bold">角色管理编辑窗口</span></td>
          </tr>
        </table></td>
        <td width="50%" align="right"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
          <tr>
            <td align="center"><a class="link_white" href="role!queryAllRole.action?curPage=1">+ 返回列表</a></td>
          </tr>
        </table></td>
      </tr>
    </table>
    
    <table width="100%" border="0" cellpadding="0" cellspacing="10" bgcolor="#FFFFFF" class="border03">
          
          <tr>
            <td align="left">角色群组：
            <input disabled="disabled" type="text" name="roleName" value="${role.roleName }" id="textfield2" class="input" /></td>
          </tr>
          <tr>
            <td align="center" id="d1"><img src="images/jindu.jpg"/></td>
          </tr>
          <tr>
            <td align="left">
            <c:forEach items="${allList}" var="emp" >
              <c:forEach items="${empList}" var="emp_role">
              	<c:if test="${emp_role.empno == emp.empno}">
              	  <script type="text/javascript">
              	    check="true";
              	  </script>
              	</c:if>
              </c:forEach>
              <script type="text/javascript">
                
                if (root == 0 || root != '${emp.orgName}'){
                	group.add('${emp.orgName }',0,'${emp.orgName }',check,'${emp.empno}');
                	root = '${emp.orgName}';
                }
                if (secRoot == 0 || secRoot != '${emp.orgName }'+"-"+'${emp.dname}' || root != '${emp.orgName}'){
                    
                	group.add('${emp.orgName }'+"-"+'${emp.dname}',root,'${emp.dname }',check,'${emp.empno}');
					secRoot = '${emp.orgName }'+"-"+'${emp.dname}';
                }
                group.add('${emp.empno }',secRoot,'${emp.ename }',check,'${emp.empno}','empnos');
                check = 'false';
              </script>
            </c:forEach>
            <script type="text/javascript">
               document.write(group);
               //powerId.openAll();
            </script>
            </td>
          </tr>
        </table>
    </td>
    <td width="140" align="right"><table width="100%" border="0" cellspacing="15" cellpadding="0">

        <tr>
          <td align="center"><input type="image" src="images/main_btn4.gif" onclick="return confirm('本次修改将会影响群组权限，新的权限将在重新登录后体现,是否确定提交？')"/></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="15" cellpadding="0">
        <tr>
          <td align="center"><img src="images/main_btn8.gif" width="104" height="34" /></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="40" align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="top">&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>
