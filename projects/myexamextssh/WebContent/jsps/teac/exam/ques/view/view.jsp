<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>MyExam</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="在线考试系统">
	<link rel="stylesheet" type="text/css" href="<c:url value='/ext/resources/css/ext-all.css'/>"/>
	<script type="text/javascript" src="<c:url value='/ext/adapter/ext/ext-base.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-all.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-lang-zh_CN.js'/>"></script>
	<script language="javascript">
		Ext.BLANK_IMAGE_URL = "<c:url value='/ext/resources/images/default/s.gif'/>"; //设置空白图片的位置
		var path = "<c:url value='/'/>";
	</script>
	<style type="text/css">
		.tt{
			background: transparent;
			border:0px;
		}
	</style>
  </head>
  <body>
  	<div style="text-align:center;">
  		<div style="width:40%;text-align:left;">
  			<br/>
  			<br/>
  			<div id="mainForm"></div>
  			<br/>
  			<div id="answer"></div>
  			<hr/>
  		</div>
  	</div>
  </body>
  <script language="javascript">
  	 Ext.onReady(function(){
		var cate = '<c:out value="${ques.quesCate}"/>';
		//alert(cate);
		var level = '<c:out value="${ques.quesLevel}"/>';
		if(level=='1'){
			level = "一般";
		}else if(level=='2'){
			level="难";
		}else if(level=='3'){
			level = "很难";
		}else{
			level = "简单";
		}
		var viewForm = new Ext.form.FormPanel({
			//region:'center',
			//title:'试题',
			renderTo:'mainForm',
			//frame:true,
			labelAlign:'right',
			autoScroll:true,
			buttonAlign:'center',
			//border:false,
			labelWidth:80,
			width:600,
			labelSeparator:'：',
			method:'post',
			items:[
			    {
			    	xtype:'hidden',
			    	name:'quesId',
			    	value:'<c:out value="${ques.quesId}"/>'
				},
				{
					xtype:'textfield',
					fieldLabel:'类型',
					readOnly:true,
					cls:'tt',
					//border:false,
					value:'<c:out value="${ques.cateName}"/>'
				},
				{
					xtype:'textfield',
					fieldLabel:'课程',
					readOnly:true,
					cls:'tt',
					value:'<c:out value="${ques.courName}"/>'
				},
				{
					xtype:'textfield',
					fieldLabel:'难度',
					readOnly:true,
					cls:'tt',
					value:level
				},
				{
					xtype:'textfield',
					fieldLabel:'分值 ',
					readOnly:true,
					cls:'tt',
					id:'quesScore1',
					value:'<c:out value="${ques.quesScore}"/>分'
				},
				{
					xtype:'textfield',
					fieldLabel:'知识点',
					readOnly:true,
					cls:'tt',
					value:'<c:out value="${ques.quesTitle}"/>'
				},
				{
					xtype:'panel',
					fieldLabel:'题干',
					readOnly:true,
					//width:400,
					height:120,
					//boxMaxHeight:140,
					border:true,
					autoScroll:true,
					//height:200,
					cls:'tt',
					html:'<c:out value="${ques.quesBody}" escapeXml="false"/>'
				},
				{
					xtype:'textfield',
					fieldLabel:'得分',
					readOnly:true,
					id:'quesScore2'
				}
			],
			buttons:[
				{
					text:'提交',
					handler:function(){
						var vv = "";
						if(cate=='1'){	//填空
							var aa = Ext.query('[name="values"]');
							for(var i=0;i<aa.length;i++){
								if(vv==""){
									vv = aa[i].value.trim();
								}else{
									vv = vv+","+aa[i].value.trim();
								}
							}
							setScoreBlank(vv);
						}else if(cate=='2'){			//判断
							var aa = Ext.query('[name="values"]');
							for(var i=0;i<aa.length;i++){
								if(aa[i].checked==true){
									vv = aa[i].value;
								}	
							}
							setScoreChoose(vv);
						}else if(cate=='3'){				//单项选择
							var aa = Ext.query('[name="values"]');
							for(var i=0;i<aa.length;i++){
								if(aa[i].checked==true){
									vv = aa[i].value;
								}	
							}
							setScoreChoose(vv);
						}else if(cate=='4'){				//多选
							var aa = Ext.query('[name="values"]');
							for(var i=0;i<aa.length;i++){
								if(aa[i].checked==true){
									if(vv==""){
										vv = aa[i].value;
									}else{
										vv = vv+","+aa[i].value;
									}
								}	
							}
							setScoreChoose(vv);
						}else if(cate=='5'){	//主观,可以从用户的输入中查找相关字符,按多少的比例给分
							var aa = Ext.getCmp("vac");
							setScoreHtml(aa.getValue());
						}
					}
				},
				{
					text:'退出'
				}
			],
			listeners:{
				beforeshow:function(){
					var len = viewForm.items.length;
					if(cate==2){		//判断题
						var choose = {
							xtype:'panel',
							border:true,
							//title:'选项区',
							//collapsible:true,
							fieldLabel:'选项区',
							items:[
								{
								    xtype:'radio',
								    boxLabel:'对',
								    name:'values',
								    inputValue:'对'
								},
								{
								    xtype:'radio',
								    boxLabel:'错',
								    name:'values',
								    inputValue:'错'
								}
						     ]
						}
						viewForm.insert(viewForm.items.length-1,choose);
						viewForm.doLayout();
					}else if(cate==3){		//单项选择
						var vals = '<c:out value="${ques.quesChoose}"/>';
						var vvs = vals.split("###");
						var array = new Array();
						for(var i=0;i<vvs.length;i++){
							var ch = String.fromCharCode(65+i);
							var radio = {
								xtype:'radio',
								boxLabel:ch+'、'+vvs[i],
								name:'values',
								inputValue:ch
							};
							array.push(radio);
						}
						var choose = {
							xtype:'panel',
							//bodyStyle: 'padding-left:5px;',
							//collapsible:true,
							fieldLabel:'选项区',
							//title:'选项区',
							items:array
						}
						viewForm.insert(viewForm.items.length-1,choose);
						viewForm.doLayout();
					}else if(cate==4){    //多选题目
						var vals = '<c:out value="${ques.quesChoose}"/>';
						var vvs = vals.split("###");
						var array = new Array();
						for(var i=0;i<vvs.length;i++){
							var ch = String.fromCharCode(65+i);
							var radio = {
								xtype:'checkbox',
								boxLabel:ch+'、'+vvs[i],
								name:'values',
								inputValue:ch
							};
							array.push(radio);
						}
						var choose = {
							xtype:'panel',
							border:true,
							//bodyStyle: 'padding-left:5px;',
							//collapsible:true,
							fieldLabel:'选项区',
							//title:'选项区',
							items:array
						}
						viewForm.insert(viewForm.items.length-1,choose);
						viewForm.doLayout();
					}else if(cate==5){								//主观题
						var choose = {
							xtype:'panel',
							fieldLabel:'书写区',
							items:[
						       {
							        id:'vac',
									xtype:'htmleditor',
									region:'center',
									anchor:'90%',
									height:160,
									enableFont:false,
									enableLinks:false,
									name:'values'
							   }
							]
						}
						viewForm.insert(viewForm.items.length-1,choose);
						viewForm.doLayout();
					}else{								//填空题则什么也不做
						//var aa = Ext.query('input[name="values"]');
						//alert(aa.length);
					}
				}
			}
		});
		viewForm.show();

		//答案区
		var fieldset = new Ext.form.FieldSet({
			title:'答案区',
			width:300,
			renderTo:'answer',
			labelAlign:'right',
			labelWidth:80,
			collapsible:true,
			items:[
			       {
				       id:'answer1',
				       xtype:'textfield',
				       fieldLabel:'正确答案',
				       labelSeparator:'：',
				       readOnly:true,
				       cls:'tt',
				       value:'<c:out value="${ques.quesSolu}"/>'
				   },
				   {
					   id:'answer2',
					   xtype:'textfield',
					   fieldLabel:'你的答案',
					   labelSeparator:'：',
					   readOnly:true,
					   cls:'tt'
				   }
			]
		});
		fieldset.collapse();
		//设置选项的分数
		var setScoreChoose = function(vv){
			Ext.getCmp("answer2").setValue(vv);
			if(vv==Ext.getCmp("answer1").getValue()){
				Ext.getCmp("quesScore2").setValue(Ext.getCmp("quesScore1").getValue());
			}else{
				Ext.getCmp("quesScore2").setValue('0分');
			}
		}
		//设置增空的分数
		//以下的具体做法是先进行数组的排序,然后再全部转成小写后对比
		var setScoreBlank = function(vv){
			Ext.getCmp("answer2").setValue(vv);
			var array1 = Ext.getCmp("answer1").getValue().split(",");
			var array2 = vv.split(",");
			array1.sort();
			array2.sort();
			if(array1.toString().toLowerCase()==array2.toString().toLowerCase()){
				Ext.getCmp("quesScore2").setValue(Ext.getCmp("quesScore1").getValue());
			}else{
				Ext.getCmp("quesScore2").setValue('0分');
			}
		}
		//以下是判断主观题目得分,通过查找关键字的方式给分
		var setScoreHtml=function(vv){
			var array1 = Ext.getCmp("answer1").getValue().split(",");
			var len = array1.length;
			var ok = 0;
			for(var i=0;i<len;i++){
				if(vv.indexOf(array1[i])!=-1){
					ok++;
				}
			}
			ok = ok/len;
			var ss = parseInt(Ext.getCmp("quesScore1").getValue());
			ok = parseInt(ss*ok);
			Ext.getCmp("quesScore2").setValue(ok+'分');			
		}
  	 });
  </script>
</html>