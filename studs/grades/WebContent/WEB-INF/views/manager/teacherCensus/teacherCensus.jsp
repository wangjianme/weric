
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/default/easyui.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/icon.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.easyui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/locale/easyui-lang-zh_CN.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/highcharts.js'/>"></script>
</head>
<body>
	<div id="tt" class="easyui-tabs" style="width: 100%;">
		<div title="按职称统计" style="padding: 20px;">
			<div id="container"
				style="min-width: 400px; height: 400px; margin: 0 auto"></div>
		</div>
		<div title="按学历统计" style="padding: 20px;">
			<div id="container2"
				style="min-width: 400px; height: 400px; margin: 0 auto"></div>
		</div>
	</div>

</body>
<script type="text/javascript">
	$(function() {
		var tab = $('#tt').tabs('getSelected');
		var index = $('#tt').tabs('getTabIndex', tab);
		titles();

		$('#tt').tabs({
			onSelect : function(title, index) {
				if (index == 0) {
					titles();
				}
				if (index == 1) {
					degree();
				}
			}
		});

	});

	function depteSlect() {
		var dept;
		$.ajax({
			url : "<c:url value='/manager/teacherCensusServlet'/>",
			async : false,
			success : function(data) {
				dept = data;
				dept = eval(dept);
			}
		});
		return dept;
	}
	function titles() {
		var titles;
		$
				.ajax({
					url : "<c:url value='/manager/teacherCensusServlet?cmd=titleSelect'/>",
					async : false,
					success : function(data) {
						titles = data;
						titles = eval(titles);
					}
				});
		$(function() {
			var chart;
			$(document).ready(function() {
				chart = new Highcharts.Chart({
					credits : {
						enabled : false
					},
					chart : {
						renderTo : 'container',//div 标签  
						type : 'column',//图表类型  折线 line,曲线spline, 点 scatter, splinearea bar,pie,area,column 
						backgroundColor : "#EAF7FF",//图表背景色  
						borderRadius : 15,//图表边框圆角角度  
						plotBackgroundColor : "#6DBFBB",//主图表区背景颜色  
						plotBorderWidth : 2,//主图表边框宽度  
						shadow : true,//是否设置阴影  
						zoomType : 'xy'//拖动鼠标放大图表的方向  
					},
					title : {
						text : ''
					},
					colors : [ '#058DC7',//浅蓝
					'#FFFF00',//黄  
					'#FF00FF',//紫  
					//'#FFFFFF',//白
					'#FF0000',//红  
					'#00FF00',//绿  
					'#0000FF',//蓝  
					],
					xAxis : {
						categories : depteSlect()
					},
					yAxis : {
						allowDecimals : false,
						title : {
							text : '人数（人）'
						}
					},
					series : titles
				});
			});
		});

	}
	function degree() {
		var degree;
		$
				.ajax({
					url : "<c:url value='/manager/teacherCensusServlet?cmd=degreeSelect'/>",
					async : false,
					success : function(data) {
						degree = data;
						degree = eval(degree);
					}
				});
		$(function() {
			var chart;
			$(document).ready(function() {
				chart = new Highcharts.Chart({
					credits : {
						enabled : false
					},
					chart : {
						renderTo : 'container2',//div 标签  
						type : 'column',//图表类型  
						backgroundColor : "#EAF7FF",//图表背景色  
						borderRadius : 15,//图表边框圆角角度  
						plotBackgroundColor : "#6DBFBB",//主图表区背景颜色  
						plotBorderWidth : 2,//主图表边框宽度  
						shadow : true,//是否设置阴影  
						zoomType : 'xy'//拖动鼠标放大图表的方向  
					},
					title : {
						text : ''
					},
					colors : [
					// '#000000',//黑  
					'#FF0000',//红  
					'#00FF00',//绿  
					'#0000FF',//蓝  
					'#FFFF00',//黄  
					'#FF00FF',//紫  
					'#FFFFFF',//白
					],
					xAxis : {
						categories : depteSlect()
					},
					yAxis : {
						allowDecimals : false,
						title : {
							text : '人数（人）'
						}
					},
					series : degree
				});
			});
		});

	}
</script>
</html>

