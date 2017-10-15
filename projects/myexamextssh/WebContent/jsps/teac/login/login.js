Ext.onReady(function(){
	if(parent.toLogin){				//从内部调用外部重新登录
		parent.login();
	}else{
		Ext.QuickTips.init();
		
		var name = new Ext.form.TextField({
			fieldLabel:'登录名<font color="red">*</font>',
			labelSeparator:'：',
			name:'teacName',
			enableKeyEvents:true,
			allowBlank:false,
			blankText:'用户名必须输入',
			maxLength:10,
			maxLengthText:'字符长度不能大于10个'
		});
		name.on("keyup",function(rf,e){
			if(e.getKey()==13){
				pwd.focus();
			}
		});
		var pwd = new Ext.form.TextField({
			inputType:'password',
			fieldLabel:'密码<font color="red">*</font>',
			labelSeparator:'：',
			name:'teacPwd',
			allowBlank:false,
			enableKeyEvents:true,					//加上此句才可以监听key事件
			blankText:'密码必须输入',
			maxLength:10,
			maxLengthText:'字符长度不能大于10个'
		});
		pwd.on("keyup",function(tf,e){
			if(e.getKey()==13){						//如果按的是回车则登录
				login();
			}
		});
		var form = new Ext.FormPanel({
			labelAlign:'right',
			defaults:{width:140},
			items:[name,pwd],
			frame:true,
			method:'post',
			buttons:[
			{
				text:'登录',
				handler:function(){
					var boo = form.getForm().isValid();
					if(boo){
						login();
					}
				}
			},
			{
				text:'注册',
				handler:function(){
					window.location.href=path+'teacReg.action';
				}
			},
			{
				text:'忘记密码',
				handler:function(){
					var teacName = name.getValue().trim();
					if(teacName==""){
						Ext.Msg.alert('提示','请输入用户名!');
					}else{
						Ext.Ajax.request({
							url:path+'teaclostpwd!getQues.action',
							method:'post',
							params:{name:teacName},
							success:function(resp,opts){
								var result = Ext.util.JSON.decode(resp.responseText);
								if(result.success==true){
									if(result.exsits==true){
										var ques = result.teacQues;
										var teacId = result.teacId;
										quesForm.getForm().findField('teacId').setValue(teacId);
										quesForm.getForm().findField('teacQues').setValue(ques);
										quesForm.getForm().findField('teacName').setValue(teacName);
										quesWin.show();
									}else{
										Ext.Msg.alert('提示','此用户名不存在！');
									}
								}else{
									Ext.Msg.alert('提示','操作不成功！');
								}
							},
							failure:function(resp,opts){
								alert("访问服务器失败!")
							}
						});
					}
				}
			}
			]
		});
		var win = new Ext.Window({
			id:'win',
			title:'教师登录',
			width:300,
			autoHeight:true,
			height:200,
			closable:false,
			items:[form]
		});
		win.show();
		//登录功能
		var login=function(){
			form.getForm().doAction("submit",{
				url:path+'admin!login.action',
				waitMsg:'正在登录请稍候...',
				waitTitle:'登录中...',
				success:function(f,op){
					if(op.result.success==true){
						window.location.href=path+"admin!loginSuccess.action";
					}else{
						Ext.Msg.alert("MSG","用户名或密码错误!");
					}
				},
				failure:function(f,op){
					Ext.Msg.alert("MSG","用户名或密码错误!");
				}
			});
		}
	}
	
	//以下是忘记密码的登录登录框
	var quesForm = new Ext.form.FormPanel({
		//title:'第一步：回答问题',
		labelAlign:'right',
		labelSeparator:'：',
		width:330,
		frame:true,
		defaults:{width:150},
		items:[
			{
				xtype:'hidden',
				name:'teacId'
			},
			{
				xtype:'textfield',
				name:'teacName',
				fieldLabel:'用户名',
				readOnly:true,
				cls:'txt',
				allowBlank:false
			},
			{
				xtype:'textfield',
				name:'teacQues',
				readOnly:true,
				cls:'txt',
				fieldLabel:'提示问题',
				allowBlank:false
			},
			{
				xtype:'textfield',
				name:'answer',
				fieldLabel:'提示问题答案',
				allowBlank:false
			}
		],
		buttons:[
			{
				text:'确定',
				handler:function(){
					var boo = quesForm.getForm().isValid();
					if(boo){
						quesForm.getForm().doAction('submit',{
							url:path+'teaclostpwd!toUpdate.action',
							method:'post',
							waitMsg:'稍候...',
							waitTitle:'稍候...',
							success:function(frm,opts){
								if(opts.result.success==true){
									if(opts.result.answer==true){
										var teacId = quesForm.getForm().findField('teacId').getValue();
										quesWin.hide(null,function(){
											quesForm.getForm().reset();
										});
										quesWin2.show(null,function(){
											updatePwdForm.getForm().reset();
											updatePwdForm.getForm().findField('teacId').setValue(teacId);
										});
									}else{
										Ext.Msg.alert('提示','密码提示问题答案不正确！');
									}
								}else{
									Ext.Msg.alert('提示','操作不成功！');
								}
							},
							failure:function(frm,opts){
								alert('操作不成功..');
							}
						});
					}
				}
			},
			{
				text:'关闭',
				handler:function(){
					quesWin.hide();
				}
			}
		
		]
	});
	
	var updatePwdForm = new Ext.form.FormPanel({
		labelAlign:'right',
		labelSeparator:'：',
		frame:true,
		items:[
			{
				xtype:'hidden',
				name:'teacId'
			},
			{
				xtype:'textfield',
				fieldLabel:'密码',
				name:'teacPwd',
				allowBlank:false,
				inputType:'password'
			},
			{
				xtype:'textfield',
				fieldLabel:'再次输入密码',
				name:'repwd',
				allowBlank:false,
				inputType:'password'
			}
		
		],
		buttons:[
			{
				text:'保存',
				handler:function(){
					var boo = updatePwdForm.getForm().isValid();
					if(boo){
						var pwd1 = updatePwdForm.getForm().findField('teacPwd').getValue();
						var pwd2 = updatePwdForm.getForm().findField('repwd').getValue();
						if(pwd1.trim()!=pwd2.trim()){
							Ext.Msg.alert('提示','两次密码输入不一致！');
						}else{
							updatePwdForm.getForm().doAction('submit',{
								url:path+'teaclostpwd!update.action',
								method:'post',
								success:function(frm,opts){
									if(opts.result.success==true){
										Ext.Msg.alert('提示','密码重新设置成功！',function(){
											quesWin2.hide();
										});
									}else{
										Ext.Msg.alert('提示','操作不成功!');
									}
								},
								failure:function(frm,opts){
									alert('操作不成功！')
								}
							});
						}
					}
				}
			},
			{
				text:'取消',
				handler:function(){
					quesWin2.hide();
				}
			}
		]
	});
	
	
	var quesWin  = new Ext.Window({
		modal:true,
		width:340,
		height:300,
		autoHeight:true,
		closeAction:'hide',
		items:[
			quesForm
		]
	});
	
	var quesWin2  = new Ext.Window({
		modal:true,
		width:340,
		height:300,
		autoHeight:true,
		closeAction:'hide',
		items:[
			updatePwdForm
		]
	});
	
	
});