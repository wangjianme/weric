Ext.onReady(function(){
	Ext.QuickTips.init();
	var form = new Ext.form.FormPanel({
		region:'center',
		padding:'10 10 10 10',
		frame:true,
		labelSeparator:'：',
		labelAlign:'right',
		defaults:{width:150},
		items:[
			{
				xtype:'textfield',
				fieldLabel:'旧密码',
				inputType:'password',
				allowBlank:false,
				name:'oldPwd'
			},
			{
				xtype:'textfield',
				fieldLabel:'新密码',
				inputType:'password',
				allowBlank:false,
				name:'newPwd'
			},
			{
				xtype:'textfield',
				fieldLabel:'再次输入新密码',
				inputType:'password',
				allowBlank:false,
				name:'repwd'
			}
			
		],
		buttons:[
			{
				text:'保存',
				handler:function(){
					var boo = form.getForm().isValid();
					if(boo){
						var n1 = form.getForm().findField('newPwd').getValue();
						var n2 = form.getForm().findField('repwd').getValue();
						if(n1!=n2){
							Ext.Msg.alert('提示','两次新密码输入不一致!');
						}else{
							form.getForm().doAction('submit',{
								url:path+'studrepwd!update.action',
								method:'post',
								waitMsg:'执行中，稍候...',
								waitTitle:'稍候',
								success:function(frm,opts){
									if(opts.result.success==true){
										if(opts.result.valid==true){
											Ext.Msg.alert('提示','保存成功!');
										}else{
											Ext.Msg.alert('提示','旧密码输入不正确!');
										}
									}else{
										Ext.Msg.alert('提示','保存不成功!');
									}
								},
								failure:function(frm,opts){
									alert('操作不成功!')
								}
							});
						}
					}
				}
			}
		]
	});
	
	
	var view = new Ext.Viewport({
		layout:'border',
		items:[form]
	});
});