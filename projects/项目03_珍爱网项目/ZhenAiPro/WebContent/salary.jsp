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
    
    <title>ageCount</title>
    
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

  </head>
  
  <body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 100%; height: 100%;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

      option = {
    title: {
        text: '单身人士之薪水篇'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['单身人士总数','单身男士总数','单身女士总数']
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['1000元以下','3000元','5000元','8000元','10000元','20000元','20000元以上']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
    	{
            name:'单身女士总数',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[${requestScope.salaryGirlMap["1000元以下"]}, ${requestScope.salaryGirlMap["1001-2000元"]+requestScope.salaryGirlMap["2001-3000元"]}, 
            ${requestScope.salaryGirlMap["3001-5000元"]}, ${requestScope.salaryGirlMap["5001-8000元"]}, ${requestScope.salaryGirlMap["8001-10000元"]},
            ${requestScope.salaryGirlMap["10001-20000元"]},${requestScope.salaryGirlMap["20001-50000元"]+requestScope.salaryGirlMap["50000元以上"]}]
        }
        ,
        {
            name:'单身男士总数',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[${requestScope.salaryBoyMap["1000元以下"]}, ${requestScope.salaryBoyMap["1001-2000元"]+requestScope.salaryBoyMap["2001-3000元"]}, 
            ${requestScope.salaryBoyMap["3001-5000元"]}, ${requestScope.salaryBoyMap["5001-8000元"]}, ${requestScope.salaryBoyMap["8001-10000元"]},
            ${requestScope.salaryBoyMap["10001-20000元"]},${requestScope.salaryBoyMap["20001-50000元"]+requestScope.salaryBoyMap["50000元以上"]}]
        },
        {
            name:'单身汪总数',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[${requestScope.salaryMap["1000元以下"]}, ${requestScope.salaryMap["1001-2000元"]+requestScope.salaryMap["2001-3000元"]}, 
            ${requestScope.salaryMap["3001-5000元"]}, ${requestScope.salaryMap["5001-8000元"]}, ${requestScope.salaryMap["8001-10000元"]},
            ${requestScope.salaryMap["10001-20000元"]},${requestScope.salaryMap["20001-50000元"]+requestScope.salaryMap["50000元以上"]}]
        }
        
    ]
};

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
   
  </body>
</html>
