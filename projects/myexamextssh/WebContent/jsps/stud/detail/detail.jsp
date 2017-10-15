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
	<link rel="stylesheet" type="text/css"
			href="<c:url value='/ext/ux/fileuploadfield/css/fileuploadfield.css'/>" />
	<script type="text/javascript" src="<c:url value='/ext/adapter/ext/ext-base.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-all.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ext/ext-lang-zh_CN.js'/>"></script>
	<script type="text/javascript"
			src="<c:url value='/ext/ux/fileuploadfield/FileUploadField.js'/>"></script>
	<script language="javascript">
		Ext.BLANK_IMAGE_URL = "<c:url value='/ext/resources/images/default/s.gif'/>"; //设置空白图片的位置
		var path = "<c:url value='/'/>";
	</script>
	<script type="text/javascript" src="<c:url value='/jsps/stud/detail/detail.js'/>"></script>
  </head>
  <body>
  	
  </body>
  <script language="javascript">
  	 Ext.onReady(function(){
		var form = new Ext.form.FormPanel({
			region:'center',
			frame:false,
			padding:'10 10 10 10',
			labelAlign:'right',
			labelSeparator:'：',
			defaults:{width:160,allowBlank:false},
			items:[
			       {
					 xtype:'hidden',
					 name:'studId',
					 fieldLabel:'StudID',
					 value:'<c:out value="${sessionScope.stud.studId}"/>'
				   },
				   {
					   xtype:'textfield',
					   name:'studName',
					   fieldLabel:'姓名',
					   value:'<c:out value="${sessionScope.stud.studName}"/>'
				   },
				   {
					   xtype:'textfield',
					   name:'studNo',
					   fieldLabel:'学号',
					   value:'<c:out value="${sessionScope.stud.studNo}"/>'
				   },
				   {
					   xtype:'textfield',
					   name:'studSid',
					   fieldLabel:'身份证号',
					   value:'<c:out value="${sessionScope.stud.studSid}"/>'
				   },
				   {
					   xtype:'combo',
					   name:'studSex',
					   fieldLabel:'性别',
					   store:new Ext.data.ArrayStore({
							idIndex:0,
							fields:['id','text'],
							data:[['0','女'],['1','男']]
					   }),
					   hiddenName:'studSex',
					   valueField:'id',
					   triggerAction:'all',
					   editable:false,
					   mode:'local',
					   displayField:'text',
					   value:'<c:out value="${sessionScope.stud.studSex}"/>'
				   },
				   {
					   xtype:'datefield',
					   name:'studBirth',
					   fieldLabel:'出生日期',
					   editable:false,
					   format:'Y-m-d',
					   value:'<c:out value="${sessionScope.stud.studBirth}"/>'
				   },
				   {
					   xtype:'textfield',
					   name:'studTel',
					   fieldLabel:'电话',
					   allowBlank:true,
					   value:'<c:out value="${sessionScope.stud.studTel}"/>'
				   },
				   {
					   xtype:'textfield',
					   name:'studQq',
					   fieldLabel:'QQ',
					   allowBlank:true,
					   value:'<c:out value="${sessionScope.stud.studQq}"/>'
				   },
				   {
					   xtype:'textfield',
					   name:'studEmail',
					   fieldLabel:'Email',
					   allowBlank:true,
					   validType:'email',
					   value:'<c:out value="${sessionScope.stud.studEmail}"/>'
				   },
				   {
					   xtype:'textarea',
					   name:'studAddr',
					   fieldLabel:'地址',
					   value:'<c:out value="${sessionScope.stud.studAddr}"/>'
				   },
				   {
					   xtype:'panel',
					   fieldLabel:'头像',
					   layout:'hbox',
					   border:false,
					   items:[
							{
								   xtype:'box',
								   fieldLabel:'头像',
								   html:'<img id="img" src=<c:url value="/images/${sessionScope.stud.studPic}"/> width="80" height="100"></img>'
							},
							{
								   xtype:'button',
								   text:'更新<br/>头像',
								   handler:function(){
									   imgWin.show();
								   }
							}
					   ]
				   }

			]
			,
			buttons:[
			         {
				         text:'保存',
				         handler:function(){
							var o = form.getForm().getFieldValues(true);
							var vv = Ext.util.JSON.encode(o);
							if(vv=="{}"){
							   Ext.Msg.alert('提示','没有修改任何内容！');								
							   return;
							}else{
								o.studId=form.getForm().findField('studId').getValue();
								vv = Ext.util.JSON.encode(o);
								//alert('>>>:'+vv);
								Ext.Ajax.request({
									url:path+'studdetail!save.action',
									method:'post',
									params:{json:vv},
									success:function(resp,opts){
										var result = Ext.util.JSON.decode(resp.responseText);
										if(result.success==true){
											if(result.valid==true){
												window.location.href=path+"studdetail.action";
											}else{
												Ext.Msg.alert('提示','你输入的身份证号已经存在！');
											}
										}else{
											Ext.Msg.alert('提示','操作不成功!');
										}
									},
									failure:function(resp,opts){
										alert("操作不成功!");
									}
								});
							}
			         	 }
				     },
				     {
					     text:'重置',
					     handler:function(){
				    	 	form.getForm().reset();
				     	 }
				     }
			]
		});

		var imgForm = new Ext.form.FormPanel({
			fileUpload:true,
			labelAlign:'right',
			frame:true,
			labelSeparator:'：',
			labelWidth:80,
			items:[
			       {
			    	    xtype:'fileuploadfield',
					    fieldLabel:'相片',
						name:'file',
						width:170,
						buttonText:'选择..'
				   }
			]
		});
		var imgWin = new Ext.Window({
			modal:true,
			width:300,
			height:200,
			autoHeight:true,
			closeAction:'hide',
			items:[imgForm],
			buttons:[
			         {
				         text:'上传',
				         handler:function(){
			        	 	imgForm.getForm().doAction('submit',{
								url:path+'studdetail!upload.action',
								method:'post',
								success:function(frm,opts){
									if(opts.result.success==true){
										if(opts.result.img==true){
											Ext.Msg.alert('提示','上传成功，刷新显示新头像!',function(){
												imgWin.hide();
											});
										}else{
											Ext.Msg.alert('提示','不支持的文件类型，请上传[JPEG]格式的图片!');
										}
									}else{
										Ext.Msg.alert('提示','操作失败!');
									}
			        	 		},
			        	 		failure:function(frm,opts){
									alert('操作失败!');
				        	 	}
				        	});
			         	 }
				     },
				     {
					     text:'关闭',
					     handler:function(){
							imgWin.hide();
					     }
				     }
			]
		});
		

		
		var view = new Ext.Viewport({
			frame:true,
			layout:'border',
			items:[form]
		});	
  	 });
  </script>
</html>