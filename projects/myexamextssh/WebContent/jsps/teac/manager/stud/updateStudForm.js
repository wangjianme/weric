Ext.onReady(function() {
	// 修改状态的窗口
	var stateCombo = new Ext.form.ComboBox({
		mode : 'local',
		fieldLabel : '状态',
		labelSeparator : '：',
		editable : false,
		width : 100,
		name:'studState',
		triggerAction : 'all',
		store : new Ext.data.ArrayStore({
					idField : 'id',
					fields : ['id', 'text'],
					data : [['1', '正常'], ['2', '流失'], ['3', '退学'],['4','病假']]
				}),
		valueField : 'id',
		displayField : 'text',
		hiddenName:'studState'
	});
	
	updateStudForm = new Ext.form.FormPanel({
		frame:true,
		labelAlign:'right',
		labelWidth:60,
		height:200,
		width:300,
		region:'center',
		labelSeparator:'：',
		defaults:{width:150},
		items:[
			{
				xtype:'textfield',
				name:'studNo',
				fieldLabel:'学号'
			},
			{
				xtype:'textfield',
				name:'studName',
				fieldLabel:'姓名',
				disabled:true
			},
			{
				xtype:'panel',
				fieldLabel:'班级',
				layout:'column',
				items:[
					{
						xtype:'textfield',
						name:'clsName',
						columnWidth:.8,
						readOnly:true
					},
					{
						xtype:'button',
						columnWidth:.2,
						text:'...',
						handler:function(){
							clsWin.show();
						}
					}
				]
			},
			stateCombo,
			{
				xtype:'hidden',
				name:'studCls'
			},
			{
				xtype:'hidden',
				name:'majorId'
			},
			{
				xtype:'hidden',
				name:'majorName'
			},
			{
				xtype:'hidden',
				name:'edusysId'
			},
			{
				xtype:'hidden',
				name:'edusysName'
			},
			{
				xtype:'hidden',
				name:'studId'
			}
		]
	});
	
	
	
	updateStudWin = new Ext.Window({
		modal : true,
		title : '修改部分信息',
		width :300,
		height:200,
		layout : 'border',
		closeAction : 'hide',
		items : [
			updateStudForm
		],
		buttons : [{
			text : '保存',
			handler : function() {
				var rc = studGrid.getSelectionModel().getSelected();
				if (rc.get('studState') == stateCombo.getValue() && 
					rc.get('studCls')==updateStudForm.getForm().findField('studCls').getValue() &&
					rc.get('studNo')==updateStudForm.getForm().findField('studNo').getValue()) {
					Ext.Msg.alert('提示','没有修改任何信息!');
				} else {
					updateStudForm.getForm().doAction('submit',{
						url:path+'studmanager!update.action',
						method:'post',
						waitTitle:'稍候',
						waitMsg:'正在保存设置，请稍候...',
						success:function(f,opt){
							if(opt.result.success==true){
								var frm = updateStudForm.getForm();
								rc.set('studNo',frm.findField('studNo').getValue());
								rc.set('studCls',frm.findField('studCls').getValue());
								rc.set('clsName',frm.findField('clsName').getValue());
								rc.set('majorId',frm.findField('majorId').getValue());
								rc.set('majorName',frm.findField('majorName').getValue());
								rc.set('edusysId',frm.findField('edusysId').getValue());
								rc.set('edusysName',frm.findField('edusysName').getValue());
								rc.set('studState',frm.findField('studState').getValue());
								updateStudWin.hide();
							}else{
								Ext.Msg.alert('提示','操作不成功。');
							}
						},
						failure:function(f,opt){
							Ext.Msg.alert('服务提示','操作不成功。');
						}
						
					});
				}
			}
		}, {
			text : '关闭',
			handler : function() {
				updateStudWin.hide();
			}
		}]
	});

});