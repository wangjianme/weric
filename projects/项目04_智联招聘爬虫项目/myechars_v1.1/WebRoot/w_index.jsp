<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <% 
    request.setCharacterEncoding("UTF-8");
	String city = request.getParameter("city");
	String job = request.getParameter("job");
    %>
    <title>demo</title>
    <link href="ui_css/main.css" rel="stylesheet"/>
    <link href="ui_css/dialog.css" rel="stylesheet"/>
    <link href="ui_css/table.css" rel="stylesheet">
    <link href="ui_css/form.css" rel="stylesheet"/>
    <link href="ui_css/calendar.css" rel="stylesheet"/>
    <link href="ui_css/loading_style.css" rel="stylesheet"/>
    <script type="text/javascript" src="ui_js/jquery.js"></script>
    <script type="text/javascript" src="ui_js/layer.js"></script>
    <script type="text/javascript" src="ui_js/ui.js"></script>
    <script type="text/javascript" src="ui_js/z.src.js"></script>
    <script type="text/javascript" src="ui_js/dateui.js"></script>
    <script type="text/javascript">
      var ui = new UI();
      var curd = new AjaxCURD();
      ui.init(function (){
          document.getElementById("a1").onclick=function (){
        	var obj = this;
        	curd.ajaxQueryWithShadowDivClose("POST","index.jsp",{"city":"<%=city%>","job":"<%=job%>"},function (html){
  				var frame = new Frame(obj.innerHTML);
  			    frame.init();
  			    var container = new JContainer();
  			    container.addHTML(html);
  			    frame.addComp(container);
  			    frame.showFrame();
  			    frame.setForeground();
  			    return frame;
  			});
  			return false;
          }
          document.getElementById("a2").onclick=function (){
         	 curd.ajaxQueryWithShadowDivClose("POST","topN.jsp",{"city":"<%=city%>","job":"<%=job%>"},function (html){
 	       		  var frame1 = new Frame("查询条目数");
 	       	      frame1.topFrame=true;
 	       	      frame1.init();
 	       	      frame1.setFrameBounds(300,150);
 	       	   	  frame1.frameToCenter();
 	       	      frame1.setResizeble(false);
 	       	      var container1 = new JContainer();
 	       	      container1.addHTML(html);
 	       	      frame1.addComp(container1);
 	       	      layerBean = $.fn.layer(frame1);
 	       	      frame1.onclose=function(){
 		       	    frame1.dispose();
 	       	        layerBean.layer.remove();
 	       	      }
              });
              return false;
          }
          document.getElementById("a3").onclick=function (){
        	    var obj = this;
        	    curd.ajaxQueryWithShadowDivClose("POST","area.jsp",{"city":"<%=city%>","job":"<%=job%>"},function (html){
  				var framex = new Frame(obj.innerHTML);
  			    framex.init();
  			    var containerx = new JContainer();
  			    containerx.addHTML(html);
  			    framex.addComp(containerx);
  			    framex.showFrame();
  			    framex.setForeground();
  			    return framex;
  			});
  			return false;
         }
         document.getElementById("a4").onclick=function (){
          	 curd.ajaxQueryWithShadowDivClose("POST","condition.jsp",{"city":"<%=city%>","job":"<%=job%>"},function (html){
  	       		  var frame1 = new Frame("企业明细查询");
  	       	      frame1.topFrame=true;
  	       	      frame1.init();
  	       	      frame1.setFrameBounds(300,150);
  	       	   	  frame1.frameToCenter();
  	       	      frame1.setResizeble(false);
  	       	      var container1 = new JContainer();
  	       	      container1.addHTML(html);
  	       	      frame1.addComp(container1);
  	       	      layerBean = $.fn.layer(frame1);
  	       	      frame1.onclose=function(){
  		       	    frame1.dispose();
  	       	        layerBean.layer.remove();
  	       	      }
               });
               return false;
           }
           document.getElementById("a5").onclick=function (){
        	  var obj = this;
        	  curd.ajaxQueryWithShadowDivClose("POST","fuli.jsp",{"city":"<%=city%>","job":"<%=job%>"},function (html){
  				  var frame = new Frame(obj.innerHTML);
  			      frame.init();
  			      var container = new JContainer();
  			      container.addHTML(html);
  			      frame.addComp(container);
  			      frame.showFrame();
  			      frame.setForeground();
  			      return frame;
  			  });
  			  return false;
          }
           document.getElementById("a6").onclick=function (){
         	  var obj = this;
         	  curd.ajaxQueryWithShadowDivClose("POST","blackList.jsp",{"city":"<%=city%>","job":"<%=job%>"},function (html){
   				  var frame = new Frame(obj.innerHTML);
   			      frame.init();
   			      var container = new JContainer();
   			      container.addHTML(html);
   			      frame.addComp(container);
   			      frame.showFrame();
   			      frame.setForeground();
   			      return frame;
   			  });
   			  return false;
           }
           document.getElementById("a7").onclick=function (){
          	  var obj = this;
          	  curd.ajaxQueryWithShadowDivClose("POST","chengzhang.jsp",{"city":"<%=city%>","job":"<%=job%>"},function (html){
    				  var frame = new Frame(obj.innerHTML);
    			      frame.init();
    			      var container = new JContainer();
    			      container.addHTML(html);
    			      frame.addComp(container);
    			      frame.showFrame();
    			      frame.setForeground();
    			      return frame;
    			  });
    			  return false;
            }
      });
    </script>
</head>
<!-- onselectstart="return false;" -->
<body onselectstart="return false;" background="images/blank.jpg">
<div class="navbar">
    <ul>
        <li><a href="#"  class="qx" id="a1">工资分布</a></li>
        <li><a href="#"  class="qx" id="a2">使用技术统计</a></li>
        <li><a href="#"  class="qx" id="a3">地区分布</a></li>
        <li><a href="#"  class="qx" id="a4">企业技术明细查询</a></li>
        <li><a href="#"  class="qx" id="a5">奇葩福利</a></li>
        <li><a href="#"  class="qx" id="a6">黑名单</a></li>
        <li><a href="#"  class="qx" id="a7">行业成长曲线</a></li>
    </ul>
    <span id="menu">菜单</span>
</div>
<div class="taskbar" id="taskBar">
</div>
  </body>
</html>
