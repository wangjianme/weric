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
		Map<String,Integer> map = an.countInClass("fuli", -1);
		int totalCount = an.getFileList().size();
    %>
    共统计<%=totalCount %>条记录,点击柱状图显示企业信息<br/>
    <div id="main7" style="width: 1000px;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var dataName = [];
        var dataValue = [];
        var dataAvgPay = [];
        var dataMaxPay = [];
        <%
        int index = 0;
        Set<String> keySet = map.keySet();
        for (String key : keySet){
			Integer in = map.get(key);
			List<Map<Object,String[]>> list1 = an.findListByExpression("fuli",-1,key);
			float f = an.avg("pay",1,list1);
        %>
        dataName[<%=index%>] = "<%=key%>";
        dataValue[<%=index%>] = "<%=in%>";
        dataAvgPay[<%=index%>] = <%=f%>;
        <%	
        	index++;
        }
		%>
        var myChart = echarts.init(document.getElementById('main7'));
        option = {
        	    xAxis: {
        	        type: 'category',
        	        axisLabel :{
                    	interval:0,
                    	rotate:90
                    },
        	        data: dataName
        	    },
        	    yAxis: {
        	    	
        	        type: 'value'
        	    },
        	    tooltip : {
        	        trigger: 'item',
        	        formatter: function (params){
						return dataName[params.dataIndex]+" 岗位数:"+dataValue[params.dataIndex]+" 平均薪资:"+dataAvgPay[params.dataIndex]+" 占总比例:"+Math.round(dataValue[params.dataIndex]/<%=totalCount%>*100,4)+"%";
        	        }
        	    },
        	    series: [{
        	        data: dataValue,
        	        type: 'bar'
        	    }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.on("click",function(e){
        	curd.ajaxQueryWithShadowDivClose("POST","test.jsp",{"city":"<%=city%>","job":"<%=job%>","name":e.name},function (html){
	       		  var frame1 = new Frame(e.name+"相关企业信息");
	       	      frame1.topFrame=true;
	       	      frame1.init();
	       	      frame1.setFrameBounds(650,350);
	       	   	  frame1.frameToCenter();
	       	      var container1 = new JContainer();
	       	      container1.addHTML(html);
	       	      frame1.addComp(container1);
	       	      layerBean = $.fn.layer(frame1);
	       	      frame1.onclose=function(){
		       	    frame1.dispose();
	       	        layerBean.layer.remove();
	       	      }
            });
        });
    </script>
  </body>
</html>
