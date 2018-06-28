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
		String job = request.getParameter("job");
		if (city == null){
			city = "北京";
		}
		if (job == null){
			job = "java";
		}
		String fileName = "c://zhilian//result_"+city+"_"+job+"_ETL.txt";
		Analysis an = new Analysis(fileName,new ZhiLianProcessor());
		List<Map<Object,String[]>> list = an.findListByExpression("city", 0, "-");
		Map<String,Integer> map = an.countInClass("city",0,list);
		Set<String> keySet = map.keySet();
		for (String key : keySet){
			Integer in = map.get(key);
			List<Map<Object,String[]>> list1 = an.findListByExpression("city",0,key);
			float f = an.avg("pay",1,list1);
			System.out.println(key+" 平均工资:"+f);
		}
    %>
    当前共统计了<%=list.size() %>条记录<br/>
    <div id="main3" style="width: 1000px;height:400px;"></div>
    
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var dataName = [];
        var dataValue = [];
        var dataAvgPay = [];
        var dataMaxPay = [];
        <%
        int index = 0;
        for (String key : keySet){
			Integer in = map.get(key);
			List<Map<Object,String[]>> list1 = an.findListByExpression("city",0,key);
			float f = an.avg("pay",1,list1);
			float max = an.max("pay",1,list1);
        %>
        dataName[<%=index%>] = "<%=key.split("-")[1]%>";
        dataValue[<%=index%>] = "<%=in%>";
        dataAvgPay[<%=index%>] = <%=f%>;
        dataMaxPay[<%=index%>] = <%=max%>;
        <%	
        	index++;
        }
		%>
        var myChart = echarts.init(document.getElementById('main3'));
        option = {
        	    xAxis: {
        	        type: 'category',
        	        axisLabel :{
                    	interval:0,
                    	rotate:40
                    },
        	        data: dataName
        	    },
        	    yAxis: {
        	    	
        	        type: 'value'
        	    },
        	    tooltip : {
        	        trigger: 'item',
        	        formatter: function (params){
						return dataName[params.dataIndex]+" 岗位数:"+dataValue[params.dataIndex]+" 平均薪资:"+dataAvgPay[params.dataIndex]+" 最高薪资:"+dataMaxPay[params.dataIndex];
        	        }
        	    },
        	    series: [{
        	        data: dataValue,
        	        type: 'bar'
        	    }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
  </body>
</html>
