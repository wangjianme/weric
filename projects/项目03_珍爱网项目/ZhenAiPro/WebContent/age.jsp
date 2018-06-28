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
    <div id="main" style="width: 100%;height:100%;margin: 0px auto;"></div>
    <script type="text/javascript">
    	//alert(' ${requestScope.ageMap["20岁~29岁"]} ');
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

       option = {
    title: {
        text: '单身人士按年龄分布',
        subtext: '数据来自珍爱网'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend: {
        data: [ '人口总数']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
    },
    yAxis: {
        type: 'category',
        data: ['20-29岁','30-39岁','40-49岁','50-59岁','60-69岁','70岁以上']
    },
    series: [
        {
            name: '单身人士总数',
            type: 'bar',
            data: ['${requestScope.ageMap["20岁~29岁"]}',
            '${requestScope.ageMap["30岁~39岁"]}','${requestScope.ageMap["40岁~49岁"]}',
            '${requestScope.ageMap["50岁~59岁"]}','${requestScope.ageMap["60岁~69岁"]}',
            '${requestScope.ageMap["70岁~79岁"]+requestScope.ageMap["80岁~89岁"]+requestScope.ageMap["90岁~99岁"]}']
        },
        {
            name: '单身男士总数',
            type: 'bar',
            data: ['${requestScope.boyMap["20岁~29岁"]}',
            '${requestScope.boyMap["30岁~39岁"]}','${requestScope.boyMap["40岁~49岁"]}',
            '${requestScope.boyMap["50岁~59岁"]}','${requestScope.boyMap["60岁~69岁"]}',
            '${requestScope.boyMap["70岁~79岁"]+requestScope.boyMap["80岁~89岁"]+requestScope.boyMap["90岁~99岁"]}']
        },{
            name: '单身女士总数',
            type: 'bar',
            data: ['${requestScope.girlMap["20岁~29岁"]}',
            '${requestScope.girlMap["30岁~39岁"]}','${requestScope.girlMap["40岁~49岁"]}',
            '${requestScope.girlMap["50岁~59岁"]}','${requestScope.girlMap["60岁~69岁"]}',
            '${requestScope.girlMap["70岁~79岁"]+requestScope.girlMap["80岁~89岁"]+requestScope.girlMap["90岁~99岁"]}']
        }
    ]
};
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
   
  </body>
</html>
