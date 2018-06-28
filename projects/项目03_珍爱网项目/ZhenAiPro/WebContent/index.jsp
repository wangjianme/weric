<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script src="js/echarts.min.js"></script>
<script src="js/jquery-1.4.2.js"></script>
<script src="map/js/china.js"></script>

</head>

<body>
	<div id="main" style="width: 600px;height:400px;"></div>
	This is my JSP page.
	<br>
	<script>
		var myChart = echarts.init(document.getElementById('main'));
		/*$.get('map/json/china.json', function(chinaJson) {
			echarts.registerMap('china', chinaJson);
			var chart = echarts.init(document.getElementById('main'));
			chart.setOption({
				series : [ {
					type : 'map',
					map : 'china'
				} ]
			});
		});*/
		var data = [ {
			name : '西藏',
			value : 34
		}, {
			name : '上海',
			value : 24
		}, {
			name : '广东',
			value : 25
		}, {
			name : '山西',
			value : 25
		}, {
			name : '云南',
			value : 25
		}, {
			name : '海南',
			value : 25
		}, {
			name : '辽宁',
			value : 25
		}, {
			name : '吉林',
			value : 25
		}, {
			name : '宁夏',
			value : 26
		}, {
			name : '江西',
			value : 26
		}, {
			name : '内蒙古',
			value : 26
		}, {
			name : '四川',
			value : 27
		}, {
			name : '陕西',
			value : 27
		}, {
			name : '重庆',
			value : 27
		}, {
			name : '台湾',
			value : 28
		}, {
			name : '江苏',
			value : 29
		}, {
			name : '贵州',
			value : 30
		}, {
			name : '北京',
			value : 30
		}, {
			name : '新疆',
			value : 31
		}, {
			name : '浙江',
			value : 31
		}, {
			name : '山东',
			value : 31
		}, {
			name : '甘肃',
			value : 250
		}, {
			name : '天津',
			value : 150
		}, {
			name : '河南',
			value : 32
		}, {
			name : '黑龙江',
			value : 32
		}, {
			name : '河北',
			value : 33
		}, {
			name : '湖南',
			value : 33
		}, {
			name : '安徽',
			value : 33
		}, {
			name : '湖北',
			value : 34
		}, {
			name : '香港',
			value : 34
		}, {
			name : '澳门',
			value : 35
		}, {
			name : '青海',
			value : 35
		}, {
			name : '福建',
			value : 36
		}, {
			name : '广西',
			value : 36
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
				text : '全国主要城市空气质量',
				subtext : 'data from PM25.in',
				sublink : 'http://www.pm25.in',
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
				data : [ 'pm2.5' ],
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
				name : 'pm2.5',
				type : 'scatter',
				coordinateSystem : 'geo',
				data : convertData(data),
				symbolSize : function(val) {
					return val[2] / 10;
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
					return val[2] / 10;
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

		myChart.setOption(option);
	</script>
</body>

</html>

