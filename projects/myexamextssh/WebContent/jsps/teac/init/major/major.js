Ext.onReady(function(){
	Ext.QuickTips.init();
	var majorStore = new Ext.data.JsonStore({
		idProperty:'major_id',
		root:'majors',
		url:path+'major!query.action',
		fields:[
		{name:'majorId',mapping:'major_id',type:'string'},
		{name:'majorName',mapping:'major_name',type:'string'},
		{name:'majorDept',mapping:'major_dept',type:'string'},
		{name:'deptName',mapping:'dept_name',type:'string'},
		{name:'majorEdusys',mapping:'major_edusys',type:'string'},
		{name:'edusysName',mapping:'edusys_name',type:'string'},
		{name:'majorDesc',mapping:'major_desc',type:'string'},
		{name:'majorInuse',mapping:'major_inuse',type:'string'}
		]
	});
	majorStore.load();
	
	var majorGrid = new Ext.grid.GridPanel({
		//title:'专业列表',
		//width:500,
		//height:300,
		//region:'center',
		frame:true,
		store:majorStore,
		autoScroll:true,
		flex:1,
		sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
		autoExpandColumn:'majorDesc',
		columns:[
		{id:'majorId',header:'编号',dataIndex:'majorId',hidden:true,hideable:false},
		{id:'majorName',header:'名称',dataIndex:'majorName',sortable:true},
		{id:'majordept',header:'deptid',dataIndex:'majorDept',hidden:true,hideable:false},
		{id:'deptName',header:'院系',dataIndex:'deptName',sortable:true},
		{id:'majorEdusys',header:'majorEdusys',dataIndex:'majorEdusys',hidden:true,hideable:false},
		{id:'edusysName',header:'学制',dataIndex:'edusysName',sortable:true},
		{id:'majorInuse',header:'在用否',dataIndex:'majorInuse',sortable:true,
			renderer:function(v){
				if(v=="1"){
					return "在用";
				}else{
					return "停用";
				}
			}
		},
		{id:'majorDesc',header:'说明',dataIndex:'majorDesc',sortable:false}
		],
				buttons:[
		{
			text:'增加',
			handler:function(){
				formWin.setTitle('增加');
				majorForm.getForm().findField('majorId').setValue('');
				majorForm.getForm().findField('majorName').setValue('');
				majorForm.getForm().findField('majorDept').setValue('');
				majorForm.getForm().findField('deptName').setValue('');
				majorForm.getForm().findField('majorDesc').setValue('');
				majorForm.getForm().findField('majorInuse').setValue('1');
				formWin.show();
			}
		},
		{
			text:'修改',
			handler:function(){
				var rec = majorGrid.getSelectionModel().getSelected();
				if(!rec){
					Ext.Msg.alert('提示','请选择一行数据!');
					return;
				}
				formWin.setTitle('修改');
				majorForm.getForm().loadRecord(rec);
				formWin.show();
			}
		},
		{
			text:'删除',
			handler:function(){
				var rec = majorGrid.getSelectionModel().getSelected();
				if(!rec){
					Ext.Msg.alert('提示','请选择一行数据!');
					return;
				}
				Ext.MessageBox.confirm('提示','确定要删除此专业吗?',function(btn){
					if(btn=='yes'){
						Ext.Ajax.request({
							url:path+'major!del.action',
							method:'post',
							params:{majorId:rec.get('majorId')},
							success:function(res,opt){
								var r = Ext.util.JSON.decode(res.responseText);
								if(r.success==true){
									majorStore.remove(rec);
								}else{
									Ext.Msg.alert('提示','删除不成功!!');
								}
							},
							failure:function(res,opt){
								Ext.Msg.alert('服务提示','删除不成功!!');
							}
							
						});
					}
				});
			}
		}
		]
	});
	//学制下拉列表
	edusysStore = new Ext.data.JsonStore({
		url:path+'edusys!query.action',
		root:'edusys',
		idProperty:'edusysId',
		fields:['edusysId','edusysName']
	});
	edusysStore.load();
	edusysComboBox = new Ext.form.ComboBox({
		store:edusysStore,
		triggerAction:'all',
		displayField:'edusysName',
		valueField:'edusysId',
		mode:'remote',
		width:120,
		allowBlank:false,
		editable:false,	
		emptyText:'请选择学制',
		fieldLabel:'学制',
		labelSeparator:'：',
		name:'edusys',
		hiddenName:'majorEdusys',
		hiddenValue:'edusysId'
	});
	
	//查询用的表单
	queryMajor = new Ext.form.FormPanel({
		frame:true,
		align:'left',
		autoHeight:true,
		items:[
			{
				xtype:'panel',
				width:520,
				align:'left',
				layout:'column',
				items:[
					{
						xtype:'panel',
						columnWidth:.3,
						layout:'form',
						labelSeparator:'：',
						labelAlign:'right',
						labelWidth:60,
						items:[
							{
								xtype:'textfield',
								name:'majorName',
								width:80,
								fieldLabel:'专业'
							}
						]
					},
					{
						xtype:'panel',
						columnWidth:.3,
						layout:'form',
						labelAlign:'right',
						labelWidth:60,
						labelSeparator:'：',
						items:[
							{
								xtype:'combo',
								fieldLabel:'学制',
								store:edusysStore,
								triggerAction:'all',
								displayField:'edusysName',
								valueField:'edusysId',
								mode:'remote',
								width:80,
								style:{valign:'bottom'},
								editable:false,	
								emptyText:'请选择学制',
								name:'majorEdusys',
								hiddenName:'majorEdusys',
								hiddenValue:'edusysId'
							}
						]
					},
					{
						xtype:'panel',
						columnWidth:.4,
						layout:'column',
						items:[
							{
								xtype:'textfield',
								name:'majorDept',
								hidden:true
							},
							{
								xtype:'panel',
								layout:'form',
								labelAlign:'right',
								columnWidth:.9,
								labelWidth:60,
								labelSeparator:'：',
								items:[
									{
										xtype:'textfield',
										fieldLabel:'院系',
										width:100,
										readOnly:true,
										name:'deptName'
									}
								]
							},
							{
								xtype:'button',
								text:'...',
								handler:function(){
									selectedForm = queryMajor;				//注意指定是提交表单
									deptWin.show();
								}
							}
						]
					}
				]
			}
		],
		buttons:[
		{
			text:'查询',
			handler:function(){
				var vv = queryMajor.getForm().getFieldValues();			//直接得到表单中所有值,以key/value对形式出现
				//alert(vv.deptName+","+vv.majorName+","+vv.majorDept);
				majorStore.reload({params:vv});							//只要reload时将参数传到后台就可以重新载入数据
			}
		},
		{
			text:'清空',
			handler:function(){
				queryMajor.getForm().reset();
			}
		}
		]
		
	});
	
	
	
//	var majorWin = new Ext.Window({
//		width:550,
//		height:420,
//		layout:'border',
//		closable:false,
//		//autoHeight:true,
//		items:[queryMajor,majorGrid],
//	});
//	majorWin.show();
	var majorView = new Ext.Viewport({
		layout:'vbox',
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[queryMajor,majorGrid]
	});
	
	
	
	//保存或是修改的表单
	majorForm = new Ext.form.FormPanel({
		width:300,
		height:300,
		url:path+'major!save.action',
		method:'post',
		frame:true,
		labelAlign:'right',
		labelSeparator:'：',
		items:[
		{
			xtype:'hidden',
			name:'majorId',
			autoWidth:true,
			fieldLabel:'ID'
		},
		{
			xtype:'textfield',
			name:'majorName',
			fieldLabel:'专业名称'
		},
		edusysComboBox,
		{
			xtype:'hidden',
			name:'majorDept',
			fieldLabel:'部门ID'
		},
		{
			xtype:'panel',
			layout:'hbox',
			fieldLabel:'部门',
			items:[
			{
				xtype:'textfield',
				name:'deptName',
				allowBlank:false,
				readOnly:true
			},
			{
				xtype:'button',
				text:'...',
				handler:function(){
					selectedForm = majorForm;				//注意指定是提交表单
					deptWin.show();
				}
			}
			]
		},
		{
			xtype:'textarea',
			name:'majorDesc',
			fieldLabel:'说明',
			autoWidth:true
		},
		{
			xtype:'radiogroup',
			id:'majorInuse',
			fieldLabel:'在用否',
			items:[
			{
				boxLabel:'在用',
				name:'majorInuse',
				inputValue:'1'
			},
			{
				boxLabel:'停用',
				name:'majorInuse',
				inputValue:'0'
			}
			]
		}
		]
	});
	var formWin = new Ext.Window({
		modal:true,
		title:'增加',
		width:300,
		height:300,
		closable:true,
		closeAction:'hide',
		autoHeight:true,
		items:[majorForm],
		buttons:[
		{
			text:'保存',
			handler:function(){
				var vb = majorForm.getForm().isValid();
				if(!vb){
					return;
				}
				majorForm.getForm().doAction('submit',{
					waitTitle:'保存中...',
					waitMsg:'正在保存,请稍候....',
					success:function(frm,opt){
						if(opt.result.success==true){
							var mm = opt.result.major;
							if(opt.result.save==true){
								mm = new Major({majorId:mm.majorId,
												majorName:mm.majorName,
												majorDept:mm.majorDept,
												deptName:majorForm.getForm().findField('deptName').getValue(),
												majorEdusys:mm.majorEdusys,
												edusysName:edusysComboBox.getRawValue(),
												majorInuse:mm.majorInuse,
												majorDesc:mm.majorDesc
										});
								majorStore.insert(0,mm);				//在第一行写入
								formWin.hide();							//隐藏表单
								majorGrid.getSelectionModel().selectFirstRow();//选中第一行
								majorGrid.getView().scrollToTop();			//自动滚动到最前面
							}else{
								var rc = majorGrid.getSelectionModel().getSelected();
								rc.set('majorName',mm.majorName);
								rc.set('majorDept',mm.majorDept);
								rc.set('deptName',majorForm.getForm().findField('deptName').getValue());
								rc.set('majorEdusys',mm.majorEdusys);
								rc.set('edusysName',edusysComboBox.getRawValue());
								rc.set('majorInuse',mm.majorInuse);
								rc.set('majorDesc',mm.majorDesc);
								formWin.hide();
							}
							
						}else{
							Ext.Msg.alert('提示','保存不成功..');
						}
					},
					failure:function(frm,opt){
						Ext.Msg.alert('服务提示','保存不成功..');
					}
				});
			}
		},
		{
			text:'关闭',
			handler:function(){
				formWin.hide();
			}
		}
//		,
//		{
//			text:'测试',
//			handler:function(){
//				var aa = edusysComboBox.getRawValue();
//				alert(aa);
//			}
//		}
		]
	});
	//以下对所有ajax请求添加一个遮罩层..
	var loadMsg = new Ext.LoadMask(majorView.getEl(),{msg:'正在操作...'});
	var showSpinner=function(){
		loadMsg.show();
	}
	var hideSpinner=function(){
		loadMsg.hide();
	}
	Ext.Ajax.on('beforerequest', showSpinner, this);
	Ext.Ajax.on('requestcomplete', hideSpinner, this);

	
	//声明一个Major对像
	var Major = Ext.data.Record.create([
	{id:'majorId',type:'string'},
	{id:'majorName',type:'string'},
	{id:'majorDept',type:'string'},
	{id:'deptName',type:'string'},
	{id:'majorInuse',type:'string'},
	{id:'majorDesc',type:'string'}
	])
	
});