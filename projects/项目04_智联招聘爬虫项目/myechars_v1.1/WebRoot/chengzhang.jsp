<%@ page language="java" import="java.util.*,java.io.*,po.*,spider.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'area.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="echarts.min.js"></script>
  </head>
  
  <body>
  <% 
	    request.setCharacterEncoding("UTF-8");
		String city = request.getParameter("city");
		String[] jobs = new String[]{"软件行业","销售行业","服务行业","人力资源"};
		
		
    %>
    <div id="main8" style="width: 1000px;height:400px;"></div>
    
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        
        var dataValue = [];
        <%
        for (int i = 0;i < jobs.length ; i++){
        	
			Analysis an = new Analysis("c://zhilian//result_北京_"+jobs[i]+"_ETL.txt",new ZhiLianProcessor());
			List<Map<Object,String[]>> list1 = an.findListByInterval("workExp", 1, "0-0");
			List<Map<Object,String[]>> list2 = an.findListByInterval("workExp", 1, "1-3");
			List<Map<Object,String[]>> list3 = an.findListByInterval("workExp", 1, "3-5");
			List<Map<Object,String[]>> list4 = an.findListByInterval("workExp", 1, "5-10");
			List<Map<Object,String[]>> list5 = an.findListByInterval("workExp", 1, "10");
			float f1 = an.avg("pay", 1, list1);
			float f2 = an.avg("pay", 1, list2);
			float f3 = an.avg("pay", 1, list3);
			float f4 = an.avg("pay", 1, list4);
			float f5 = an.avg("pay", 1, list5);
			System.out.println(jobs[i]);
	    %>
	    dataValue[<%=i%>] = new Array();
		dataValue[<%=i%>][0] = <%=f1%>;
		dataValue[<%=i%>][1] = <%=f2%>;
		dataValue[<%=i%>][2] = <%=f3%>;
		dataValue[<%=i%>][3] = <%=f4%>;
		dataValue[<%=i%>][4] = <%=f5%>;
	    <%
		}
        %>
        var myChart = echarts.init(document.getElementById('main8'));
        option = {
        	    title: {
        	        text: '堆叠区域图'
        	    },
        	    tooltip : {
        	        trigger: 'axis',
        	        axisPointer: {
        	            type: 'cross',
        	            label: {
        	                backgroundColor: '#6a7985'
        	            }
        	        }
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
        	            data : ['无经验','1-3年','3-5年','5-10年','10年以上']
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'value'
        	        }
        	    ],
        	    series : [
        	        {
        	            name:'软件行业',
        	            type:'line',
        	            data:[dataValue[0][0], dataValue[0][1], dataValue[0][2], dataValue[0][3], dataValue[0][4]]
        	        },
        	        {
        	            name:'销售行业',
        	            type:'line',
        	            data:[dataValue[1][0], dataValue[1][1], dataValue[1][2], dataValue[1][3], dataValue[1][4]]
        	        },
        	        {
        	            name:'服务行业',
        	            type:'line',
        	            data:[dataValue[2][0], dataValue[2][1], dataValue[2][2], dataValue[2][3], dataValue[2][4]]
        	        },
        	        {
        	            name:'人力资源',
        	            type:'line',
        	            data:[dataValue[3][0], dataValue[3][1], dataValue[3][2], dataValue[3][3], dataValue[3][4]]
        	        }
        	    ]
        	};
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        
    </script>
  </body>
</html>
