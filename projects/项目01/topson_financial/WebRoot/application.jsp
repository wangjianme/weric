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
	<script>
		var help = false;
		function helpme(message,e){
		     if (help){
		      var t=e.offsetTop;
			  var l=e.offsetLeft;
			  while(e=e.offsetParent){
			     t+=e.offsetTop;
			     l+=e.offsetLeft;
			  }
		      var obj = document.getElementById("helpme");
		      obj.style.top=t-35;
		      obj.style.left=l+140;
		      obj.style.display="block";
		      var messageobj = document.getElementById("helpmessage");
		      messageobj.innerHTML=message;
		    }
		}
		function helpon(){
		    help= !help;
		    var value = "-关闭帮助";
		    if (!help){
		    	value="+开启帮助";
		    	var obj = document.getElementById("helpme");
		    	obj.style.display="none";
		    }
		    document.getElementById("helpon").innerHTML=value;
		    return false;
		}
		function checkNext(){
            var formObjes = document.f1.elements;
            for (var i =0;i < formObjes.length;i++){
              if (formObjes[i].type != 'hidden' && formObjes[i].type != 'submit'){
                 if (formObjes[i].value == ""){
					alert(formObjes[i].title+"必须填写");
					return false;
                 }
              }
            }
			return true;
	    }
	</script> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/style_main.css" rel="stylesheet" type="text/css" />
	<script src="<%=path %>/js/jquery-1.6.2.min.js"></script>
	<script>
	    $(function() {
	    $("#TextBox2").datepicker($.datepicker.regional["zh-CN"]);	
	});
	</script>
	<script src="<%=path %>/js/calendar.js"></script>
	
</head>
<body>
 <div id="helpme" style="position:absolute;width:100px;height:50px;display:none">
   <table border="0" cellpadding="0" cellspacing="0" class="back_top03">
      <tr>
        <td><span class="font_bold" id="helpmessage"></span></td>
      </tr>
    </table>
</div>
<form  name="f1" method="post" action="reb!queryAllTemplate.action">
<input type="hidden" name="rebUser" value="${sessionScope.emp.empno }" />
<div style="clear:both; height:15px; line-height:15px;"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="main_table03">
  <tr>
    <td width="570" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="55%"><table width="167" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="left" class="back_main04"><span class="font_bold">报销单申请窗口</span></td>
          </tr>
        </table></td>
        <td width="30%" align="right">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
            <tr>
              <td align="center">通过xls导入</td>
            </tr>
          </table>
        </td>
        <td width="15%" align="right">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="back_main05">
            <tr>
              <td align="center"><a class="link_white" href="" onclick="return helpon();" id="helpon">+ 开启帮助</a></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <table width="100%" border="0" cellpadding="0" cellspacing="10" bgcolor="#FFFFFF" class="border03">
          
          
          <tr>
            <td align="left">填写日期：
            <% 
               java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd");
               Date date = new Date();
               String stringDate = fmt.format(date);
            %>
            <input type="hidden" name="rebDate" id="fileField2" class="input" readonly="readonly"  value="<%=stringDate %>"  onfocus="helpme('默认为当前日期，如需改写请遵循格式',this);"/>            
            <%=stringDate %>
            </td>
          </tr>
          <tr>
            <td align="left">申&nbsp;请&nbsp;人：       
            <input type="text"  value="${sessionScope.emp.ename }" disabled="disabled" id="textfield" class="input"  title="申请人" onclick="helpme('申请人姓名，默认为当前登陆人(必填!)',this);"/>
             </td>
          </tr>
          <tr>
            <td align="left">申请人数：       
            <input type="text"  value="1"  name="peopleNumber" class="input"  title="申请人数" onclick="helpme('申请的总人数为同去同回人数,默认为1(必填!)',this);"/>
             </td>
          </tr>
          <tr>
            <td align="left">出差日期：
            <!--129行去掉readonly="readonly" 便于不同的浏览器支持日期控件进行填写   -->
            <input type="text" name="startDate" id="fileField2" class="input" value="" title="出差日期" onclick="calendar()" onfocus="helpme('默认为当前日期，如需改写请遵循格式',this);"/></td>
          </tr>
          <tr>
            <td align="left">返回日期：
            <!-- 129行去掉readonly="readonly" 便于不同的浏览器支持日期控件进行填写   -->
            <input type="text" name="backDate" id="fileField2" class="input" title="返回日期" value="<%=stringDate %>" onclick="calendar()" onfocus="helpme('默认为当前日期，如需改写请遵循格式',this);"/><font color="red">*此处填写返回日的下车日期(格式如：2014-05-01)</font></td>
          </tr>
          <tr>
            <td align="left">出差任务：
            <input type="text" name="duty"  class="input"  onfocus="helpme('申请人出差任务(必填!)',this);" title="出差任务" /></td>
          </tr>
          <tr>
            <td align="left">机&nbsp;&nbsp;&nbsp;&nbsp;构：            
            <input type="text" name="orgName" class="input" title="机构"  value="${sessionScope.emp.orgName }" onfocus="helpme('机构名称(必填!)',this);"/></td>
          </tr>
          <tr>
            <td align="left">项目编号：            
            <input type="text" name="proNumber" class="input" title="项目编号"  onfocus="helpme('项目编号(必填!)',this);"/></td>
          </tr>
          <tr>
            <td align="left">与借金额：            
            <input type="text" name="accordMoney"class="input" title="与借金额"  onfocus="helpme('实际借出金额(必填,小写数字!)',this);" value="0"/></td>
          </tr>
          <tr>
            <td align="left">补助扣减：            
            <input type="text" name="koujian"class="input" title="补助扣减金额"  onfocus="helpme('补助扣减金额',this);" value="0"/></td>
          </tr>
          <tr>
            <td align="left">单&nbsp;&nbsp;&nbsp;&nbsp;位：            
	           <input type="text" name="dname" class="input" title="单位"  value="${sessionScope.emp.dname }" onfocus="helpme('单位名称(必填!)',this);"/>
            </td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td width="140" align="right">
      <table width="100%" border="0" cellspacing="15" cellpadding="0">
        <tr>
          <td align="center"><input type="submit" value="下一步" onclick="return checkNext();"/></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
