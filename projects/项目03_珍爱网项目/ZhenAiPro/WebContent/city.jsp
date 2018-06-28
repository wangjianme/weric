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
    <script src="map/js/china.js"></script>
	<style type="text/css">
		body{
			margin: 0px;	
		}
	</style>
  </head>
  
  <body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 100%;height:100%;margin: 0px auto;"></div>
    <script type="text/javascript">
    	//alert(' ${requestScope.schoolMap["--"]} ');
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

    	var data = [ {
			name : '西藏',
			value : ${requestScope.cityMap["西藏"]}
		}, {
			name : '上海',
			value : ${requestScope.cityMap["上海"]}
		}, {
			name : '广东',
			value : ${requestScope.cityMap["广东"]}
		}, {
			name : '山西',
			value : ${requestScope.cityMap["山西"]}
		}, {
			name : '云南',
			value : ${requestScope.cityMap["云南"]}
		}, {
			name : '海南',
			value : ${requestScope.cityMap["海南"]}
		}, {
			name : '辽宁',
			value : ${requestScope.cityMap["辽宁"]}
		}, {
			name : '吉林',
			value : ${requestScope.cityMap["吉林"]}
		}, {
			name : '宁夏',
			value : ${requestScope.cityMap["宁夏"]}
		}, {
			name : '江西',
			value : ${requestScope.cityMap["江西"]}
		}, {
			name : '内蒙古',
			value : ${requestScope.cityMap["内蒙古"]}
		}, {
			name : '四川',
			value : ${requestScope.cityMap["四川"]}
		}, {
			name : '陕西',
			value : ${requestScope.cityMap["陕西"]}
		}, {
			name : '重庆',
			value : ${requestScope.cityMap["重庆"]}
		}, {
			name : '台湾',
			value : ${requestScope.cityMap["台湾"]}
		}, {
			name : '江苏',
			value : ${requestScope.cityMap["江苏"]}
		}, {
			name : '贵州',
			value : ${requestScope.cityMap["贵州"]}
		}, {
			name : '北京',
			value : ${requestScope.cityMap["北京"]}
		}, {
			name : '新疆',
			value : ${requestScope.cityMap["新疆"]}
		}, {
			name : '浙江',
			value : ${requestScope.cityMap["浙江"]}
		}, {
			name : '山东',
			value : ${requestScope.cityMap["山东"]}
		}, {
			name : '甘肃',
			value : ${requestScope.cityMap["甘肃"]}
		}, {
			name : '天津',
			value : ${requestScope.cityMap["天津"]}
		}, {
			name : '河南',
			value : ${requestScope.cityMap["河南"]}
		}, {
			name : '黑龙江',
			value : ${requestScope.cityMap["黑龙江"]}
		}, {
			name : '河北',
			value : ${requestScope.cityMap["河北"]}
		}, {
			name : '湖南',
			value : ${requestScope.cityMap["湖南"]}
		}, {
			name : '安徽',
			value : ${requestScope.cityMap["安徽"]}
		}, {
			name : '湖北',
			value : ${requestScope.cityMap["湖北"]}
		}, {
			name : '香港',
			value : ${requestScope.cityMap["香港"]}
		}, {
			name : '澳门',
			value : ${requestScope.cityMap["澳门"]}
		}, {
			name : '青海',
			value : ${requestScope.cityMap["青海"]}
		}, {
			name : '福建',
			value : ${requestScope.cityMap["福建"]}
		}, {
			name : '广西',
			value : ${requestScope.cityMap["广西"]}
		}];
		var geoCoordMap = {
			'西藏' : [ 91.11, 29.97 ],
			'上海' : [ 121.48, 31.22 ],
			'广东' : [ 113.23, 23.16 ],
			'山西' : [ 112.53, 37.87 ],
			'云南' : [ 102.73, 25.04 ],
			'海南' : [ 110.35, 20.02 ],
			'辽宁' : [ 123.38, 41.8 ],
			'吉林' : [ 125.35, 43.88 ],
			'宁夏' : [ 106.27, 38.47 ],
			'江西' : [ 115.89, 28.68 ],
			'内蒙古' : [ 111.65, 40.82 ],
			'四川' : [ 104.06, 30.67 ],
			'陕西' : [ 108.95, 34.27 ],
			'重庆' : [ 106.54, 29.59 ],
			'台湾' : [ 121.420757, 28.656386 ],
			'江苏' : [ 118.78, 32.04 ],
			'贵州' : [ 106.71, 26.57 ],
			'北京' : [ 116.46, 39.92 ],
			'新疆' : [ 87.68, 43.77 ],
			'浙江' : [ 120.19, 30.26 ],
			'山东' : [ 117, 36.65 ],
			'甘薯' : [ 103.73, 36.03 ],
			'天津' : [ 117.2, 39.13 ],
			'河南' : [ 113.65, 34.76 ],
			'黑龙江' : [ 126.63, 45.75 ],
			'河北' : [ 114.48, 38.03 ],
			'湖南' : [ 113, 28.21 ],
			'安徽' : [ 117.27, 31.86 ],
			'湖北' : [ 114.31, 30.52 ],
			'香港' : [ 114.15, 22.15],
			'澳门' : [113.54, 22.19],
			'青海' : [101.74,36.56],
			'福建' : [119.28,26.08],
			'广西' : [108.32,22.78]
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
				map : 'china',
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
					return val[2] / 200;
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
					return val[2] / 200;
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
