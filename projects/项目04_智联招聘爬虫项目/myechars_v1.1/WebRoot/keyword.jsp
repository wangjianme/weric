<%@ page language="java" import="java.util.*,java.io.*,spider.bean.*,spider.util.*,spider.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
    <div id="main1" style="width: 600px;height:400px;"></div>
    <% 
	    request.setCharacterEncoding("UTF-8");
        String num = request.getParameter("num");
	    String city = request.getParameter("city");
		if (city == null){
			city = "北京";
		}
		String job = request.getParameter("job");
		if (job == null){
			job = "java";
		}
		String fileName = "c://zhilian//result_"+city+"_"+job+"_ETL.txt";
    	Analysis an = new Analysis(fileName,new ZhiLianProcessor());
	    List<String[]> values = an.getValueArray("keyWord");
		Map<String,Integer> keyMap = new HashMap<String,Integer>();
		for (String[] keyWords : values){
			for (String keyWord : keyWords){
				Integer keyCount = keyMap.get(keyWord);
				if (keyCount == null){
					keyCount = 0;
				}
				keyMap.put(keyWord, ++keyCount);
			}
		}
		Set<KeyBean> set = new TreeSet<KeyBean>();
		Set<String> keySet = keyMap.keySet();
		for (String key : keySet){
			KeyBean bean = new KeyBean(key,keyMap.get(key));
			set.add(bean);
		}
		set = WebUtil.showTopN(set, Integer.parseInt(num));
		int baseFloat = 0;
		Iterator<KeyBean> iter = set.iterator();
		KeyBean maxBean = iter.next();
		double max = maxBean.getCount();
		System.out.println(max);
		max = max/Math.pow(10,String.valueOf(max).length()-3);
		max = Math.ceil(max)*Math.pow(10,String.valueOf(max).length()-2);
		System.out.println(max);
    %>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main1'));
        var option = {
        	    dataset: {
        	        source: [
        	            ['score', 'amount', 'product'],
        	            <%
        	            	for (KeyBean bean : set){
        	            %>
        	            [<%=bean.getCount()/max*100%>, <%=bean.getCount()%>, '<%=bean.getKeyName()%>'],
        	            <%
        	            	}
        	            %>
        	        ]
        	    },
        	    tooltip : {
        	        trigger: 'item',
        	        formatter: function (params){
						return "";
        	        }
        	    },
        	    grid: {containLabel: true},
        	    xAxis: {name: 'amount'},
        	    yAxis: {type: 'category',axisLabel :{
                	interval:0,
                	rotate:0
                }},
        	    visualMap: {
        	        orient: 'horizontal',
        	        left: 'center',
        	        min: 10,
        	        max: 100,
        	        text: ['High Score', 'Low Score'],
        	        // Map the score column to color
        	        dimension: 0,
        	        inRange: {
        	            color: ['#D7DA8B', '#E15457']
        	        }
        	    },
        	    series: [
        	        {
        	            type: 'bar',
        	            encode: {
        	                // Map the "amount" column to X axis.
        	                x: 'amount',
        	                // Map the "product" column to Y axis
        	                y: 'product'
        	            }
        	        }
        	    ]
        	};
        	        

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
  </body>
</html>