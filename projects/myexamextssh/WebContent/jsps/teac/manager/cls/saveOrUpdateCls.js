Ext.onReady(function(){
	clsForm = new Ext.form.FormPanel({		//查询用的表单
		frame:true,
		width:400,
		height:200,
		autoHeight:true,
		labelSeparator:'：',
		items:[
		{
			xtype:'hidden',
			name:'clsId',
			fieldLabel:'ID'
		},
		{
			xtype:'textfield',
			name:'clsName',
			fieldLabel:'班级名称',
			allowBlank:false
		},
		{
			xtype:'panel',
			layout:'hbox',
			fieldLabel:'班主任',
			items:[
			{
				xtype:'textfield',
				name:'clsHead',
				hidden:true
			},
			{
				xtype:'textfield',
				readOnly:true,
				allowBlank:false,
				name:'teacName'
			},
			{
				xtype:'button',
				text:'...',
				handler:function(){
					teacWin.show();
				}
			}
			]
		},
		{
			xtype:'panel',
			layout:'hbox',
			fieldLabel:'专业',
			items:[
			{
				xtype:'textfield',
				name:'clsMajor',
				hidden:true
			},
			{
				xtype:'textfield',
				name:'majorName',
				readOnly:true,
				allowBlank:false
			},
			{
				xtype:'button',
				text:'...',
				handler:function(){
					majorWin.show();
				}
			}
			]
		},
		{
			xtype:'combo',
			fieldLabel:'状态',
			mode: 'local',
			name:'clsState',
			triggerAction:'all',
			editable:false,
			value:'1',
			store:new Ext.data.ArrayStore({
				id:0,
				fields:['id','text'],
				data:[['1','正常'],['2','已毕业'],['3','停用']]
			}),
			valueField:'id',
			displayField:'text',
			hiddenValue:'id',
			hiddenName:'clsState'
		},
		{
			xtype:'datefield',
			fieldLabel:'开班时间',
			editable:false,
			name:'clsBtime',
			format:'Y-m-d'
		},
		{
			xtype:'datefield',
			fieldLabel:'毕业时间',
			editable:false,
			name:'clsEtime',
			format:'Y-m-d'
		},
		{
			xtype:'hidden',
			name:'edusysId'
		},
		{
			xtype:'hidden',
			name:'edusysName'
		}
		]
	});
	//以下是弹出页面
	clsWin = new Ext.Window({
		modal:true,
		title:'查询',
		width:400,
		height:300,
		autoHeight:true,
		renderTo:Ext.getBody(),			//为了可以直接使用上面的表单，所以事先加载一下此window
		closeAction:'hide',
		items:[clsForm],
		buttons:[
		{
			text:'确定',
			handler:function(){
				var boo = clsForm.getForm().isValid();
				if(boo){
					clsForm.getForm().doAction('submit',{
						url:path+'class!save.action',
						method:'post',
						waitTitle:'操作中',
						waitMsg:'正在操作，请稍候...',
						success:function(frm,opt){
							if(opt.result.success==true){
								var cls = opt.result.cls;
								var cc = new Ext.data.Record(cls);	//直接创建cls对像,以下再设置新值
								if(opt.result.save==true){	//新增加,下面再新值，有则覆盖，无则增加
									cc.set('teacName',clsForm.getForm().findField('teacName').getValue());
									cc.set('majorName',clsForm.getForm().findField('majorName').getValue());
									cc.set('edusysId',clsForm.getForm().findField('edusysId').getValue());
									cc.set('edusysName',clsForm.getForm().findField('edusysName').getValue());
									clsStore.insert(0,cc);
									clsGrid.getSelectionModel().selectFirstRow();
								}else{					//修改，以下先获取原Record对像
									var rc = clsGrid.getSelectionModel().getSelected();
									Ext.apply(rc.data,cc.data);//将新值Copy到rc原Record中
									rc.set('teacName',clsForm.getForm().findField('teacName').getValue());
									rc.set('majorName',clsForm.getForm().findField('majorName').getValue());
									cc.set('edusysId',clsForm.getForm().findField('edusysId').getValue());
									cc.set('edusysName',clsForm.getForm().findField('edusysName').getValue());
									rc.commit();				//必须commit以刷新视图
								}
								clsWin.hide();
							}else{
								Ext.Msg.alert('提示','操作不成功．');
							}
						},
						failure:function(frm,opt){
							Ext.Msg.alert('服务提示','操作不成功．');
						}
					});
				}
			}
		},
		{
			text:'关闭',
			handler:function(){
				clsWin.hide();
			}
		}
		],
		listeners:{
			show:function(){
				clsForm.getForm().reset();
			}
		}
	});
});