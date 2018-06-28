<%@ page language="java" import="java.util.*,java.io.*,po.*,spider.*" pageEncoding="UTF-8"%>
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
	<% 
	request.setCharacterEncoding("UTF-8");
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
	float minLower = an.min("pay", 0);
	float maxLower = an.max("pay", 0);
	
	float minUpper = an.min("pay", 1);
	float maxUpper = an.max("pay", 1);
	System.out.println("最高工资下限："+minUpper);
	System.out.println("最高工资上限："+maxUpper);
	float avgLower = an.avg("pay", 0);
	float avgUpper = an.avg("pay", 1);
	System.out.println("最低工资平均："+avgLower);
	System.out.println("最高工资平均："+avgUpper);
	Map<Object,String[]> maxLine = an.getMaxLine();
	Map<Object,String[]> minLine = an.getMinLine();
	System.out.println("最高工资公司:"+maxLine.get("compy")[0]+" 工作经验要求:"+maxLine.get("workExp")[0]+"-"+maxLine.get("workExp")[1]);
	System.out.println("最低工资公司:"+minLine.get("compy")[0]+" 工作经验要求:"+minLine.get("workExp")[0]+"-"+minLine.get("workExp")[1]);
	int count = an.count("pay", 0, "<10000",true);
	System.out.println("工资区间小于10000的有"+count);
	List<Map<Object,String[]>> map1 = an.findListByInterval("pay", 1, "0-10000");
	List<Map<Object,String[]>> map2 = an.findListByInterval("pay", 1, "10000-20000");
	List<Map<Object,String[]>> map3 = an.findListByInterval("pay", 1, "20000-30000");
	List<Map<Object,String[]>> map4 = an.findListByInterval("pay", 1, "30000-50000");
	List<Map<Object,String[]>> map5 = an.findListByInterval("pay", 1, "50000");
	float v1 = an.avg("workExp", 0,map1,false);
	float v2 = an.avg("workExp", 1,map1,false);
	System.out.println("工资在0-10K之间共"+map1.size()+"家 平均工作经验:"+v1+"-"+v2);
	float v3 = an.avg("workExp", 0, map2,false);
	float v4 = an.avg("workExp", 1, map2,false);
	System.out.println("工资在10K-20K之间共"+map2.size()+"家 的平均工作经验:"+v3+"-"+v4);
	float v5 = an.avg("workExp", 0,map3,false);
	float v6 = an.avg("workExp", 1,map3,false);
	System.out.println("工资在20K-30K之间共"+map3.size()+"家 的平均工作经验:"+v5+"-"+v6);
	float v7 = an.avg("workExp", 0,map4,false);
	float v8 = an.avg("workExp", 1,map4,false);
	System.out.println("工资在30K-50K之间共"+map4.size()+"家 的平均工作经验:"+v7+"-"+v8);
	float v9 = an.avg("workExp", 0,map5,false);
	float v10 = an.avg("workExp", 1,map5,false);
	System.out.println("工资在50K以上的共"+map5.size()+"家 平均工作经验:"+v9+"-"+v10);
	int count1 = an.count("workExp", 0, "^0$",false);
	System.out.println("其中不需要考虑工作经验的:"+count1+"家");
	float v11 = an.avg("pay",0,an.findListByInterval("workExp", 0, "0-0"));
	float v12 = an.avg("pay",1,an.findListByInterval("workExp", 0, "0-0"));
	System.out.println("平均工资:"+v11+"-"+v12);
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
	System.out.println(set);
	%>
  </head>
  
  <body>
    <div id="main" style="width: 600px;height:400px;"></div>
    <% 
	    out.println("最低工资下限："+minLower+"</br>");
		out.println("最高工资上限："+maxUpper+"</br>");
		out.println("最低工资平均："+avgLower+"</br>");
		out.println("最高工资平均："+avgUpper+"</br>");
		out.println("最高工资公司:"+maxLine.get("compy")[0]+" 工作经验要求:"+maxLine.get("workExp")[0]+"-"+maxLine.get("workExp")[1]+"</br>");
		out.println("最低工资公司:"+minLine.get("compy")[0]+" 工作经验要求:"+minLine.get("workExp")[0]+"-"+minLine.get("workExp")[1]+"</br>");
		out.println("工资区间小于10000的有"+count+"</br>");
		out.println("其中不需要考虑工作经验的:"+count1+"家"+"</br>");
		out.println("平均工资:"+v11+"-"+v12+"</br>");
    %>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var workExp = ["<%=v1%>-<%=v2%>","<%=v3%>-<%=v4%>","<%=v5%>-<%=v6%>","<%=v7%>-<%=v8%>","<%=v9%>-<%=v10%>"];
        var myChart = echarts.init(document.getElementById('main'));
        var weatherIcons = {
        	    'Sunny': './data/asset/img/weather/sunny_128.png',
        	    'Cloudy': './data/asset/img/weather/cloudy_128.png',
        	    'Showers': './data/asset/img/weather/showers_128.png'
        	};

        	option = {
        	    title: {
        	        text: '工资分布统计数据',
        	        subtext: '来自智联招聘',
        	        left: 'center'
        	    },
        	    tooltip : {
        	        trigger: 'item',
        	        formatter: function (params){
						return "平均工作经验要求: "+workExp[params.dataIndex]+" 年";
        	        }
        	    },
        	    legend: {
        	        // orient: 'vertical',
        	        // top: 'middle',
        	        bottom: 10,
        	        left: 'center',
        	        data: ['0-10K', '10K-20K','20K-30K','30K-50K','50K以上']
        	    },
        	    series : [
        	        {
        	            type: 'pie',
        	            radius : '65%',
        	            center: ['50%', '50%'],
        	            selectedMode: 'single',
        	            data:[
        	                {value:<%=map1.size()%>, name: '0-10K'},
        	                {value:<%=map2.size()%>, name: '10K-20K'},
        	                {value:<%=map3.size()%>, name: '20K-30K'},
        	                {value:<%=map4.size()%>, name: '30K-50K'},
        	                {value:<%=map5.size()%>, name: '50K以上'}
        	            ],
        	            itemStyle: {
        	                emphasis: {
        	                    shadowBlur: 10,
        	                    shadowOffsetX: 0,
        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
        	                }
        	            }
        	        	
        	        }
        	    ]
        	};

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
  </body>
</html>
