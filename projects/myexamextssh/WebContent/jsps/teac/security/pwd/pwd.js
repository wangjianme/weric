Ext.onReady(function(){
	var form = new Ext.form.FormPanel({
		labelAlign:'right',
		labelSeparator:'：',
		frame:true,
		method:'post',
		url:path+'teacpwd!changepwd.action',
		region:'center',
		labelWidth:100,
		items:[
			{
				xtype:'textfield',
				name:'oldpwd',
				allowBlank:false,
				inputType:'password',
				fieldLabel:'旧密码'
			},
			{
				xtype:'textfield',
				name:'newpwd',
				allowBlank:false,
				inputType:'password',
				fieldLabel:'新密码'
			},
			{
				xtype:'textfield',
				name:'repwd',
				allowBlank:false,
				inputType:'password',
				fieldLabel:'再次入新密码'
			}
		],
		buttons:[
			{
				text:'保存',
				handler:function(){
					var boo = form.getForm().isValid();
					if(!boo){
						return;
					}
					var pwd1 = form.getForm().findField('newpwd').getValue();
					var pwd2 = form.getForm().findField('repwd').getValue();
					if(pwd1.trim()!=pwd2.trim()){
						Ext.Msg.alert('提示','两次密码输入不一致！',function(){
							form.getForm().findField('repwd').focus();
						});
						return;
					}
					form.getForm().doAction('submit',{
						waitTitle:'正在修改..',
						waitMsg:'正在修改.....',
						success:function(f,opt){
							if(opt.result.success==true){
								if(opt.result.changed==false){
									Ext.Msg.alert('提示','旧密码输入不正确!');
								}else{
									Ext.Msg.alert('提示','密码修改成功!');
								}
							}else{
								Ext.Msg.alert('提示','保存不成功.');
							}
						},
						failure:function(f,opt){
							Ext.Msg.alert('服务提示','保存不成功.');
						}
					});
					
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
	var view = new Ext.Viewport({
		layout:'border',
		items:[
			form
		]
	});
});