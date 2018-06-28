<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>cityCount</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!-- 引入 ECharts 文件 -->
    <script src="js/echarts.min.js"></script>
    <script src="map/js/province/guangdong.js"></script>

  </head>
  
  <body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
   <div id="main" style="width: 100%; height: 100%;"></div>
    <script type="text/javascript">
    	//alert(' ${requestScope.schoolMap["--"]} ');
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

    	var data = [{
			name : '佛山',
			value : ${requestScope.cityMap["广东佛山"]}
		}, {
			name : '广州',
			value : ${requestScope.cityMap["广东广州"]}
		},{
			name : '深圳',
			value : ${requestScope.cityMap["广东深圳"]}
		},{
			name : '东莞',
			value : ${requestScope.cityMap["广东东莞"]}
		},{
			name : '汕头',
			value : ${requestScope.cityMap["广东汕头"]}
		},{
			name : '潮州',
			value : ${requestScope.cityMap["广东潮州"]}
		},{
			name : '珠海',
			value : ${requestScope.cityMap["广东珠海"]}
		},{
			name : '韶关',
			value : ${requestScope.cityMap["广东韶关"]}
		},{
			name : '江门',
			value : ${requestScope.cityMap["广东江门"]}
		},{
			name : '梅州',
			value : ${requestScope.cityMap["广东梅州"]}
		}];
		var geoCoordMap = {
			'佛山' : [113.11,23.05],
			'广州' : [113.23,23.16],
			'深圳' : [114.07,22.62],
			'东莞' : [113.75,23.04],
			'汕头' :	[116.69,23.39],
			'潮州' :	[116.38,23.40],
			'珠海' :	[113.52,22.3],
			'韶关' : [113.62,24.84],
			'江门' : [113.06,22.61],
			'梅州' : [116.1,24.55]
		};

		var convertData = function(data) {
			var res = [];
			for ( var i = 0; i < data.length; i++) {
				var geoCoord = geoCoordMap[data[i].name];
				if (geoCoord) {
					res.push({
						name : data[i].name,
						value : geoCoord.concat(data[i].value)
					});
				}
			}
			return res;
		};

		option = {
			backgroundColor : '#404a59',
			title : {
				text : '单身人士分布图',
				subtext : 'data from trueLove',
				sublink : 'http://profile.zhenai.com/v2/personal/home.do',
				left : 'center',
				textStyle : {
					color : '#fff'
				}
			},
			tooltip : {
				trigger : 'item'
			},
			legend : {
				orient : 'vertical',
				y : 'bottom',
				x : 'right',
				data : [ 'person' ],
				textStyle : {
					color : '#fff'
				}
			},
			geo : {
				map : '广东',
				label : {
					emphasis : {
						show : false
					}
				},
				roam : true,
				itemStyle : {
					normal : {
						areaColor : '#323c48',
						borderColor : '#111'
					},
					emphasis : {
						areaColor : '#2a333d'
					}
				}
			},
			series : [ {
				name : 'person',
				type : 'scatter',
				coordinateSystem : 'geo',
				data : convertData(data),
				symbolSize : function(val) {
					return val[2] / 100;
				},
				label : {
					normal : {
						formatter : '{b}',
						position : 'right',
						show : false
					},
					emphasis : {
						show : true
					}
				},
				itemStyle : {
					normal : {
						color : '#ddb926'
					}
				}
			}, {
				name : 'Top 5',
				type : 'effectScatter',
				coordinateSystem : 'geo',
				data : convertData(data.sort(function(a, b) {
					return b.value - a.value;
				}).slice(0, 6)),
				symbolSize : function(val) {
					return val[2] / 100;
				},
				showEffectOn : 'render',
				rippleEffect : {
					brushType : 'stroke'
				},
				hoverAnimation : true,
				label : {
					normal : {
						formatter : '{b}',
						position : 'right',
						show : true
					}
				},
				itemStyle : {
					normal : {
						color : '#f4e925',
						shadowBlur : 10,
						shadowColor : '#333'
					}
				},
				zlevel : 1
			} ]
		};

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
   
  </body>
</html>
