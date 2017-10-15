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
  			<input id="chk" onclick="setMark(this);" type="checkbox"/><font color="blue" size="2">标记位,当答案不确定时选中。</font>
  			<br/>
  			<div id="mainForm"></div>
  			<br/>
  			<div id="answer"></div>
  		</div>
  	</div>
  </body>
  <script language="javascript">
  	 Ext.onReady(function(){
		var cate = '<c:out value="${requestScope.equs.eqCate}"/>';
		var viewForm = new Ext.form.FormPanel({
			renderTo:'mainForm',
			labelAlign:'right',
			autoScroll:true,
			buttonAlign:'center',
			labelWidth:80,
			width:600,
			labelSeparator:'：',
			method:'post',
			items:[
			    {
			    	xtype:'hidden',
			    	name:'eqId',
			    	value:'<c:out value="${requestScope.equs.eqId}"/>'
				},
				{
					xtype:'hidden',
					name:'answId',
					value:'<c:out value="${requestScope.equs.answId}"/>'
				},
				{
					xtype:'textfield',
					fieldLabel:'题型',
					readOnly:true,
					cls:'tt',
					value:'<c:out value="${requestScope.equs.cateName}"/>'
				},
				{
					xtype:'textfield',
					fieldLabel:'分值 ',
					readOnly:true,
					cls:'tt',
					value:'<c:out value="${requestScope.equs.eqScore}"/>分'
				},
				{
					xtype:'panel',
					fieldLabel:'题干',
					readOnly:true,
					height:120,
					border:true,
					autoScroll:true,
					cls:'tt',
					html:'<c:out value="${requestScope.equs.eqBody}" escapeXml="false"/>'
				}
			],
			buttons:[
				{
					text:'保存且上一题',
					id:'pre',
					handler:function(){
						viewForm.getForm().doAction('submit',{
							url:path+'studexam!saveAnsw.action',
							method:'post',
							success:function(frm,opts){
								if(opts.result.success==true){
									var isBlank = opts.result.blank;
									var chked = Ext.getDom('chk').checked;
									viewForm.getForm().findField('answId').setValue(opts.result.answId);
									parent._pre(isBlank,chked);
								}else{
									alert("保存不成功。。。。");
								}
							},
							failure:function(frm,opts){
								alert('...保存不成功。');
							}
						});
					}
				},
				{
					text:'保存且下一题',
					id:'next',
					handler:function(){
						viewForm.getForm().doAction('submit',{
							url:path+'studexam!saveAnsw.action',
							method:'post',
							success:function(frm,opts){
								if(opts.result.success==true){
									var isBlank = opts.result.blank;
									var chked = Ext.getDom('chk').checked;
									viewForm.getForm().findField('answId').setValue(opts.result.answId);
									parent._next(isBlank,chked);
								}else{
									alert("保存不成功。。。。");
								}
							},
							failure:function(frm,opts){
								alert('...保存不成功。');
							}
						});
					}
				},
				{
					text:'提交',
					id:'submit',
					handler:function(){
						parent.submitExam();
					}
				}
			],
			listeners:{
				beforeshow:function(){
					var len = viewForm.items.length;
					if(cate==2){		//判断题
						var choose = {
							xtype:'panel',
							border:true,
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
						viewForm.insert(viewForm.items.length,choose);
						viewForm.doLayout();
						var vls = '<c:out value="${requestScope.equs.answAnswer}"/>';
						if(vls){
							var ra = Ext.query('input[type="radio"][value="'+vls+'"]');
							ra[0].checked=true;
						}
					}else if(cate==3){		
						var vals = '<c:out value="${requestScope.equs.eqChoose}"/>';
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
							fieldLabel:'选项区',
							items:array
						}
						viewForm.insert(viewForm.items.length,choose);
						viewForm.doLayout();
						
						var vls = '<c:out value="${requestScope.equs.answAnswer}"/>';
						//alert("radio:"+vls);
						if(vls){
							var ra = Ext.query('input[type="radio"][value="'+vls+'"]');
							//alert(ra);
							ra[0].checked=true;
						}
					}else if(cate==4){    //多选题目
						var vals = '<c:out value="${requestScope.equs.eqChoose}"/>';
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
							fieldLabel:'选项区',
							items:array
						}
						viewForm.insert(viewForm.items.length,choose);
						viewForm.doLayout();
						var vls = '<c:out value="${requestScope.equs.answAnswer}"/>';
						if(vls){
							var vvs = vls.split(',');
							for(var j=0;j<vvs.length;j++){
								var ra = Ext.query('input[type="checkbox"][value="'+vvs[j]+'"]');
								ra[0].checked=true;
							}
						}
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
						viewForm.insert(viewForm.items.length,choose);
						viewForm.doLayout();
						var vls = '<c:out value="${requestScope.equs.answAnswer}" escapeXml="false"/>';
						if(vls){
							Ext.getCmp('vac').setValue(vls);
						}
					}else{								//填空题则什么也不做
						var vls = '<c:out value="${requestScope.equs.answAnswer}"/>';
						if(vls){
							var txt = Ext.query('input[name="values"]');
							txt[0].value=vls;
						}
					}
				}
			}
		});
		viewForm.show();

		//设置标记
		setMark = function(obj){
			var boo = obj.checked;
			//parent.setMark(boo);
		}
  	 });
  </script>
</html>