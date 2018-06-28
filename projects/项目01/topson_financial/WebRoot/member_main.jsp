<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>黑龙江省同信通信规划设计有限公司</title>
<style>
*{margin:0px; padding:0px; border:0px;}
#idScroller{
	line-height:30px;
	width:242px;
	height:248px;
	overflow:hidden;
}
</style>
<link href="css/style_frame.css" rel="stylesheet" type="text/css" />
<link href="css/style_main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
function MM_preloadimages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadimages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>
<SCRIPT type=text/javascript> 
var $ = function (id) { 
return "string" == typeof id ? document.getElementById(id) : id; 
}; 
var Class = { 
create: function() { 
return function() { 
this.initialize.apply(this, arguments); 
} 
} 
} 
Object.extend = function(destination, source) { 
for (var property in source) { 
destination[property] = source[property]; 
} 
return destination; 
} 
function addEventHandler(oTarget, sEventType, fnHandler) { 
if (oTarget.addEventListener) { 
oTarget.addEventListener(sEventType, fnHandler, false); 
} else if (oTarget.attachEvent) { 
oTarget.attachEvent("on" + sEventType, fnHandler); 
} else { 
oTarget["on" + sEventType] = fnHandler; 
} 
}; 
var Scroller = Class.create(); 
Scroller.prototype = { 
initialize: function(idScroller, idScrollMid, options) { 
var oThis = this, oScroller = $(idScroller), oScrollMid = $(idScrollMid); 
this.SetOptions(options); 
this.Side = this.options.Side || ["up"];//方向 
this.scroller = oScroller; //对象 
this.speed = this.options.Speed; //速度 
this.timer = null; //时间 
this.pauseHeight = 0; //定高 
this.pauseWidth = 0; //定宽 
this.pause = 0; //定高(宽) 
this.side = 0; //参数 
//用于上下滚动 
this.heightScroller = parseInt(oScroller.style.height) || oScroller.offsetHeight; 
this.heightList = oScrollMid.offsetHeight; 
//用于左右滚动 
this.widthScroller = parseInt(oScroller.style.width) || oScroller.offsetWidth; 
this.widthList = oScrollMid.offsetWidth; 
//js取不到css设置的height和width 
oScroller.style.overflow = "hidden"; 
oScrollMid.appendChild(oScrollMid.cloneNode(true)); 
oScrollMid.appendChild(oScrollMid.cloneNode(true)); 
addEventHandler(oScroller, "mouseover", function() { oThis.Stop(); }); 
addEventHandler(oScroller, "mouseout", function() { oThis.Start(); }); 
this.Start(); 
}, 
//设置默认属性 
SetOptions: function(options) { 
this.options = {//默认值 
Step: 1,//每次变化的px量 
Speed: 20,//速度(越大越慢) 
Side: ["up"],//滚动方向:"up"是上，"down"是下，"left"是左，"right"是右 
PauseHeight: 0,//隔多高停一次 
PauseWidth: 0,//隔多宽停一次 
//当上下和左右一起使用时必须设置PauseHeight和PauseWidth来设置转向位置 
PauseStep: 3000//停顿时间(PauseHeight或PauseWidth大于0该参数才有效) 
}; 
Object.extend(this.options, options || {}); 
}, 
//转向 
Turn: function() { 
//通过设置方向数组的排列来转向 
this.Side.push(this.Side.shift().toLowerCase()); 
}, 
//上下滚动 
ScrollUpDown: function() { 
this.pause = this.pauseHeight; 
this.scroller.scrollTop = this.GetScroll(this.scroller.scrollTop, this.heightScroller, this.heightList, 
this.options.PauseHeight); 
this.pauseHeight = this.pause; 
var oThis = this; 
this.timer = window.setTimeout(function(){ oThis.Start(); }, this.speed); 
}, 
//左右滚动 
ScrollLeftRight: function() { 
this.pause = this.pauseWidth; 
//注意:scrollLeft超过1400会自动变回1400 注意长度 
this.scroller.scrollLeft = this.GetScroll(this.scroller.scrollLeft, this.widthScroller, this.widthList, 
this.options.PauseWidth); 
this.pauseWidth = this.pause; 
var oThis = this; 
this.timer = window.setTimeout(function(){ oThis.Start(); }, this.speed); 
}, 
//获取设置滚动数据 
GetScroll: function(iScroll, iScroller, iList, iPause) { 
var iStep = this.options.Step * this.side; 
if(this.side > 0){ 
if(iScroll >= (iList * 2 - iScroller)){ iScroll -= iList; } 
} else { 
if(iScroll <= 0){ iScroll += iList; } 
} 
this.speed = this.options.Speed; 
if(iPause > 0){ 
if(Math.abs(this.pause) >= iPause){ 
this.speed = this.options.PauseStep; this.pause = iStep = 0; this.Turn(); 
} else { 
this.pause += iStep; 
} 
} 
return (iScroll + iStep); 
}, 
//开始 
Start: function() { 
//document.getElementById("test").innerHTML+=sTurn+","; 

//方向设置 
switch (this.Side[0].toLowerCase()) { 
case "right" : 
if(this.widthList < this.widthScroller) return; 
this.side = -1; 
this.ScrollLeftRight(); 
break; 
case "left" : 
if(this.widthList < this.widthScroller) return; 
this.side = 1; 
this.ScrollLeftRight(); 
break; 
case "down" : 
if(this.heightList < this.heightScroller) return; 
this.side = -1; 
this.ScrollUpDown(); 
break; 
case "up" : 
default : 
if(this.heightList < this.heightScroller) return; 
this.side = 1; 
this.ScrollUpDown(); 
} 
}, 
//停止 
Stop: function() { 
clearTimeout(this.timer); 
} 
}; 
</SCRIPT> 
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="main_table">
  <tr>
    <td width="10" align="left"><img src="images/main_img01.jpg" width="10" height="203" /></td>
    <td width="490"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="50%" align="center"><table width="95%" border="0" cellspacing="10" cellpadding="0">
          <tr>
            <td align="center"><img src="images/logo2.jpg" width="200" height="58" /></td>
          </tr>
          <tr>
            <td align="left">服务电话：0451-53969898<br />
              传真电话：0451-53969889<br />
              客服邮箱：hr@hljtx.com<br />
              公司网址：http://www.hljtx.com</td>
          </tr>
        </table></td>
        <td width="50%" align="center"><table width="90%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><iframe src="http://www.thinkpage.cn/weather/weather.aspx?uid=&cid=101050101&l=zh-CHS&p=CMA&a=1&u=C&s=5&m=1&x=1&d=3&fc=C6C6C6&bgc=&bc=&ti=1&in=1&li=2&ct=iframe" frameborder="0" scrolling="no" width="200" height="190" allowTransparency="true"></iframe></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    <td width="10" align="right"><img src="images/main_img02.jpg" width="10" height="203" /></td>
  </tr>
</table>
<div style="clear:both; height:50px; line-height:50px;"></div>
<!-- 
<div id="neirong">
  <div id="leftny">
    <div id="leftny_top"><span>通知</span></div>
     <div id="leftny_ny">
     <div style="height:5px; line-height:5px;"></div>
     <div id="idScroller">
      <div id="idScrollMid">
      <ul id="RunTopic">
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
        <li><span>2013年6月8日</span><a href="#">.新的报销单</a></li>
      </ul>
     </div>
    </div>
    <SCRIPT> 
new Scroller("idScroller", "idScrollMid",{ Side:["up",""], PauseHeight:31, PauseWidth:400 }); 
</SCRIPT> 			
     </div>
  </div>
  <div id="leftny2">
    <div id="leftny_top2"><span>常用工具</span></div>
    <div id="leftny_ny2">
      <ul>
        <li><span><a href="#">下载</a></span><a href="#">·通知通告标题通知通告标题</a></li>
        <li><span><a href="#">下载</a></span><a href="#">·通知通告标题通知通告标题</a></li>
        <li><span><a href="#">下载</a></span><a href="#">·通知通告标题通知通告标题</a></li>
        <li><span><a href="#">下载</a></span><a href="#">·通知通告标题通知通告标题</a></li>
        <li><span><a href="#">下载</a></span><a href="#">·通知通告标题通知通告标题</a></li>
        <li><span><a href="#">下载</a></span><a href="#">·通知通告标题通知通告标题</a></li>
        <li><span><a href="#">下载</a></span><a href="#">·通知通告标题通知通告标题</a></li>
        <li><span><a href="#">下载</a></span><a href="#">·通知通告标题</a></li>
      </ul>
    </div>
  </div>
  
</div>
 -->
</body>
</html>