<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("utf-8");
response.setCharacterEncoding("utf-8");
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
  	//(新)queryDeptRebursementCheck---queryMyRebursementCheck(原始)
  	window.location.href="reb!queryDeptRebursementCheck.action?curPage="+curPage
  }
  function confirmUpdate(){
  	return confirm("此操作将会把该报销单提交到财务审核，确认提交吗?");
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
  function openDialog(rebId){
	if (confirm("需要填写回退说明?")){
		var returnv=window.showModalDialog("back.jsp","回退意见","dialogTop=200;dialogLeft=300;dialogHeight=250px;dialogWidth=500px;help=no;fullscreen=1;status=no;center=yes");
		if(returnv != undefined){
			window.location.href="reb!back.action?rebId="+rebId+"&message="+returnv+"&flag=7&backPath=deptCheck";
		}
	}else{
      window.location.href="reb!updateFlag.action?flag=7&rebId="+rebId+"&backPath=deptCheck";
	}
	return false;
  }
  function showMessage(rebId){
  	if (confirm("需要查看意见吗?")){
 			var returnv=window.showModalDialog("reb!queryMessage.action?rebId="+rebId+"&check=disabled","回退意见","dialogTop=200;dialogLeft=300;dialogHeight=250px;dialogWidth=500px;help=no;fullscreen=1;status=no;center=yes");
 		}
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
        <td align="left" class="back_main04" ><span class="font_bold">部门报销单管理页面</span></td>
      </tr>
    </table></td>
    <td width="45%" align="left">
    <!-- action里面queryCheckByRebNumber改为：queryCheckByRebNumber1 -->
    <form name="f2" method="post" action="reb!queryCheckByRebNumber1.action" >
    <table width="150%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td align="right" width="50%">报销单号:<input type="text" name="rebNumber" value="${param.rebNumber }"/></td>
       <td align="center" width="50%">工号:<input type="text" name="empno" value="${param.empno }"/></td>
       <td align="left" valign="bottom"><input type="submit" value="查询" onClick=" return query();"/></td>
      </tr>
    </table>
    <script>
    function query(){
      var value = document.f2.rebNumber.value;
      var value1 = document.f2.empno.value;
      if(value==""&& value1==""){
      alert("请输入查询信息！");
      return false;
      //window.returnValue=rebNumber&&empno;
      }
      }
    
     /*  function checkRebId(){
         var value = document.f2.rebNumber.value;
         if (value == ""){
			alert("请填写报销单编号!");
			return false;
         } else {
         var value = document.f2.empno.value;
         if(value==""){
         alert("请填写员工编号！");
         return false;
         }
         }
         return true; 
      } */
       
    </script>
    </form>
    </td>
    <td align="right"  width="30%">
     </td>
  </tr>  
  <tr>
    <td colspan="3"> 
         <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dedede">
          <tr>
           <td width="15%" align="center" class="back_main03"><span class="font_bold">报销单编号</span></td>
           <td align="center" class="back_main03"><span class="font_bold">申请人</span></td>
           <td align="center" class="back_main03"><span class="font_bold">机构</span></td>
           <td align="center" class="back_main03"><span class="font_bold">申请日期</span></td>
           <td align="center" class="back_main03"><span class="font_bold">状态</span></td>           
           <td width="45%" align="center" class="back_main03"><span class="font_bold">操作</span></td>         
         </tr>
         <c:forEach items="${requestScope.pageModel.list}" var="reb">
           <tr>
            <td width="20%" align="center" class="back_main03"><span class="font_bold">${reb.rebNumber }</span></td>
            <td align="center" class="back_main03"><span class="font_bold">${reb.emp.ename }</span></td>
            <td align="center" class="back_main03"><span class="font_bold">${reb.orgName }</span></td>
            <td align="center" class="back_main03"><span class="font_bold">${reb.rebDate }</span></td>
            <td align="center" class="back_main03"><span class="font_bold"><c:if test="${reb.flag ==1 || reb.flag==3}">待审核</c:if><c:if test="${reb.flag ==2}"><a href='' class="link_blue" onclick="return showMessage('${reb.rebId}');">回退单</a></c:if><c:if test="${reb.flag ==6}">审核中</c:if><c:if test="${reb.flag ==8}"><a href='' class="link_blue" onclick="return showMessage('${reb.rebId}');">回退还审</c:if></a></span></td>       
            <td align="center" class="back_main03">
              【<a href='reb!queryOne.action?rebId=${reb.rebId }&oper=disabled&check=check' class="link_blue">查看</a>】
               <c:if test="${reb.flag == 6 || reb.flag == 8 || reb.flag == 2}">
               	【<a href='reb!updateFlag.action?flag=1&rebId=${reb.rebId }&backPath=deptCheck' onclick="return confirmUpdate();">提交审核</a>】
             	【<a href='reb!updateFlag.action?flag=7&rebId=${reb.rebId }&backPath=deptCheck' class="link_blue" onclick="return openDialog('${reb.rebId }')">回退</a>】
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
          <a href="reb!queryDeptRebursementCheck.action?curPage=<c:if test="${param.curPage !=1}">${param.curPage-1 }</c:if><c:if test="${param.curPage ==1}">${param.curPage }</c:if>">上一页</a>|
          <a href="reb!queryDeptRebursementCheck.action?curPage=<c:if test="${param.curPage != requestScope.pageModel.totalPage}">${param.curPage+1 }</c:if><c:if test="${param.curPage ==requestScope.pageModel.totalPage}">${requestScope.pageModel.totalPage }</c:if>">下一页</a>
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
<div style="clear:both; height:10px; line-height:10px;"></div>
</body>
</html>
