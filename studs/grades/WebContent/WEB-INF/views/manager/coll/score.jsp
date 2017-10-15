<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息管理系统</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/default/easyui.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/themes/icon.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.easyui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/locale/easyui-lang-zh_CN.js'/>"></script>
<head>
  <script type="text/javascript" src="<c:url value='/js/highcharts.js'/>"></script>

  <script>
  			  
  </script>
</head>
<body>
  <table>

					<tr>
					<td >请选择您要查询的条件:</td>
						<td>学院名称：</td>
						<td><input id="dept" class="easyui-combobox" name="deptid"
							editable="false"
							data-options=" required:true,valueField:'id',textField:'major',url:'<c:url value='/manager/major?cmd=selectDept'/>'" />
						</td>
					
						<td>专业名称：</td>
						<td><input id="major" class="easyui-combobox" name="majorid"
							editable="false"
							data-options="required:true ,valueField:'id',textField:'major',url:'#'" />
						</td>
					
						<td>年级名称：</td>
						<td><input id="grade" class="easyui-combobox" name="gradeid"
							editable="false"
							data-options="required:true ,valueField:'id',textField:'major',url:'<c:url value='/manager/course?cmd=selectGrade'/>'" />
						</td>
						
						
					</tr>
	
				</table>

<div id="container" style="min-width:700px;height:400px;display:none "></div>
</body>
<script type="text/javascript">

function depteSlect() {
	var deid=$('#dept').combo('getValue');
	var dept;
	$
	.ajax({
		url : "<c:url value='/manager/coll/score/?cmd=execute&id="+deid+"'/>",
		async : false,
		success : function(data) {
			dept = data;
			dept = eval(dept);
		}
	});
	return dept;
}

function titles() {
	
	 var mgid=$('#major').combo('getValue');
	var titles;
	$
			.ajax({
				url : "<c:url value='/manager/coll/score?cmd=titleSelect&id="+mgid+"'/>",
				async : false,
				success : function(data) {
					titles = data;
					titles = eval(titles);
					//alert("000"+mgid);
				}
			});
	return titles;
}



$(function(){
	$(function () {
	   
	});	

	
$('#grade').combobox({
	onSelect: function(record){
		 $('#container').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: '各学院课程平均分'
		        },
		        subtitle: {
		            text: ''
		        },
		        xAxis: {
		            categories:depteSlect()
		             
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '成绩（分）'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                '<td style="padding:0"><b>{point.y:.1f} 分</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: titles()
		       /* 	
	      	[{
		            name: '高数',
		            data: [49.9]

		        }, {
		            name: '英语',
		            data: [83.6]

		        }, {
		            name: '宝盖',
		            data: [48.9]

		        }, {
		            name: '计算机',
		            data: [33.2]

		        }]*/
		    });
		$("#container").css("display","block");
	}
});

})

</script>
<script type="text/javascript">
$(function() {
	$("#dept").combobox(
			{
				onSelect : function(record) {
					var deptid = record.id;
					//alert(deptid);

					$('#major').combobox('clear');
					$('#major').combobox(
							'reload',
							"<c:url value='/manager/course?cmd=selectMajor&deptid="
									+ deptid + "'/>");
				}
			});
	$("#dept00").combobox(
			{
				onSelect : function(record) {
					var deptid = record.id;
					//alert(deptid);

					$('#major00').combobox('clear');
					$('#major00').combobox(
							'reload',
							"<c:url value='/manager/course?cmd=selectMajor&deptid="
									+ deptid + "'/>");
				}
			});
})


</script>
</html>