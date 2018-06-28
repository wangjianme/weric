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
    
    <title>schoolCount</title>
    
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
    	//alert(' ${requestScope.schoolMap["--"]} ');
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

   		option = {
   		 title: {
        text: '单身汪们按学历分布图',
        left: 'center',
        top: 20,
        textStyle: {
            color: '#ccc'
        }
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data:['--','高中及以下','中专','大专','大学本科','硕士','博士']
    },
    series: [
        {
            name:'学历',
            type:'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:${requestScope.schoolMap["--"]}, name:'没写的'},
                {value:${requestScope.schoolMap["高中及以下"]}, name:'高中及以下'},
                {value:${requestScope.schoolMap["中专"]}, name:'中专'},
                {value:${requestScope.schoolMap["大专"]}, name:'大专'},
                {value:${requestScope.schoolMap["大学本科"]}, name:'大学本科'},
                {value:${requestScope.schoolMap["硕士"]}, name:'硕士'},
                {value:${requestScope.schoolMap["博士"]}, name:'博士'}
            ]
        }
    ]
};
   		
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
   
  </body>
</html>
