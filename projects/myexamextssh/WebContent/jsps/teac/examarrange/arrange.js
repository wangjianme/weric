Ext.onReady(function(){
	//考试安排
	Ext.QuickTips.init();
	var loadMask = new Ext.LoadMask(Ext.getBody(),{msg:'正在加载，请稍候...'});
	var showMask = function(){
		loadMask.show();
	}
	var hideMask = function(){
		loadMask.hide();
	}
	Ext.Ajax.on('beforerequest',showMask,this);
	Ext.Ajax.on('requestcomplete',hideMask,this);
	Ext.Ajax.on('requestexception',hideMask,this);
	
	var examStore = new Ext.data.JsonStore({						//加载试卷的Store
		id:'examId',
		url:path+'examarrange!queryExam.action',
		idProperty:'examId',
		totalProperty:'count',
		root:'exams',
		fields:['examId','examName','examCour',
		        'courName','examTime','examScore',
		        'examMaker','examPass'
		],
		listeners:{
			beforeload:function(store,opts){
				var queryExamName = Ext.getCmp('queryExamName');
				if(queryExamName){
					Ext.apply(opts.params,{
						examName:queryExamName.getValue()
					});
				}
			}
		}
	});
	examStore.load({params:{start:0,limit:15}});
	
	var examGrid = new Ext.grid.GridPanel({
		flex:2,
		title:'试卷列表',
		frame:true,
		store:examStore,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				new Ext.grid.RowNumberer(),
				{id:'examId',header:'ID',dataIndex:'examId',hidden:true,hideable:false},
				{id:'examName',header:'名称',dataIndex:'examName'},
				{id:'examCour',header:'cour',dataIndex:'examCour',hidden:true,hideable:false},
				{id:'courName',header:'课程',dataIndex:'courName'},
				{id:'examTime',header:'时间',dataIndex:'examTime'},
				{id:'examScore',header:'总分',dataIndex:'examScore'},
				{id:'examPass',header:'分数线',dataIndex:'examPass'},
				{id:'examMaker',header:'出卷人',dataIndex:'examMaker'}
			]
		}),
		autoExpandColumn:'examName',
		bbar:new Ext.PagingToolbar({
			store:examStore,
			pageSize:15,
			displayInfo:true,
			displayMsg:'{0}-{1}of{2}',
			emptyMsg:'没有数据'
		}),
		tbar:new Ext.Toolbar({
			items:[
				'-',
				{
					xtype:'label',
					text:'试卷名称：'
				},
				{
					xtype:'textfield',
					width:120,
					id:'queryExamName'
				},
				'->',
				{
					xtype:'button',
					text:'查询',
					handler:function(){
						examStore.reload({params:{start:0,limit:15}});
					}
				}
			]
		}),
		buttons:[
			{
				text:'考试安排',									//根据选中的试卷id进入考试安排页面
				handler:function(){
					var rc = examGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','请选择一行数据！');
					}else{
						var examId = rc.get('examId');
						addStudWin.show(null,function(){
							openedStudStore.reload({params:{examId:examId}});
						});
					}
					
				}
			}
		]
	});
	var mainView = new Ext.Viewport({
		layout:'hbox',
		frame:true,
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[examGrid]
	});
	
	//以下显示试卷明细安排给了哪些学生,注意按班级分组
	var proxy = new Ext.data.HttpProxy({					//没有直接使用JsonStore,而是先使用一个HttpProxy
		url:path+'examarrange!queryOpenedStuds.action'
	});
	var OeStud = Ext.data.Record.create([					//声明一个学生对像
		{name:'oeId',type:'string',mapping:'oeId'},
		{name:'oeStud',type:'string',mapping:'oeStud'},
		{name:'studName',type:'string',mapping:'studName'},
		{name:'studCls',type:'string',mapping:'studCls'},
		{name:'clsName',type:'string',mapping:'clsName'},
		{name:'oeState',type:'string',mapping:'oeState'},
		{name:'oeTeac',type:'string',mapping:'oeTeac'},
		{name:'oeRate',type:'string',mapping:'oeRate'},
		{name:'oeType',type:'int',mapping:'oeType'},
		{name:'oeTime',type:'string',mapping:'oeTime'}
	]);
	var oeReader = new Ext.data.JsonReader({					//使用Reader来读取Student对像
		idProperty:'oeId',
		root:'oeStuds'
	},OeStud);
	var openedStudStore = new Ext.data.GroupingStore({				//注意：必须使用GroupingStore分组数据仓库
		proxy:proxy,
		reader:oeReader,
		groupField:'clsName'									//必须指定数据分组列，同时还可以指定正序还是倒序。
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	
	var openedStudGrid = new Ext.grid.EditorGridPanel({
		title:'学生信息',
		store:openedStudStore,
		sm:sm,
		region:'center',
		view:new Ext.grid.GroupingView(
			{
				groupByText:'按此列分组',
				showGroupsText:'显示分组'
			}
		),					//必须使用分组的视图
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				sm,
				{id:'oeId',header:'oeId',dataIndex:'oeId',hidden:true,hideable:false},
				{id:'oeStud',header:'oeStud',dataIndex:'oeStud',hidden:true,hideable:false},
				{id:'studName',header:'学生',dataIndex:'studName'},
				{id:'studCls',header:'studCls',dataIndex:'studCls',hidden:true,hideable:false},
				{id:'clsName',header:'班级',dataIndex:'clsName'},
				{id:'oeState',header:'状态',dataIndex:'oeState',
					renderer:function(v){
						if(v=='0'){
							return '<font color="red">禁止考试</font>';
						}else if(v=='2'){
							return '考试完成';
						}else{
							return '<font color="green">允许考试</font>';
						}
					},
					editor:new Ext.form.ComboBox({
						mode:'local',
						triggerAction:'all',
						editable:false,
						store:new Ext.data.ArrayStore({
							idIndex:0,
							fields:['id','text'],
							data:[[0,'禁止考试'],[1,'允许考试']]
						}),
						valueField:'id',
						displayField:'text',
						listeners:{
							select:function(combo,record,index){
								var rc = openedStudGrid.getSelectionModel().getSelected();
								if(rc.get('oeState')=='2'){
									openedStudGrid.stopEditing(true);
									return;
								}
								if(rc.get('oeState')!=combo.getValue()){			//向后台提交数据
									var oeState = rc.get('oeState');
									Ext.Ajax.request({
										url:path+'examarrange!updateState.action',
										method:'post',
										params:{oeId:rc.get('oeId'),oeState:combo.getValue()},
										success:function(resp,opts){
											var result = Ext.util.JSON.decode(resp.responseText);
											if(result.success!=true){
												rc.set('oeState',oeState);			//如果保存不成功，则置回以前的信息
											}
										},
										failure:function(resp,opts){
											alert('操作不成功!');
										}
									});
								}
							}
						}
					})
				},
				{id:'oeTeac',header:'授课教师',dataIndex:'oeTeac'},
				{id:'oeRate',header:'阅卷教师',dataIndex:'oeRate'},
				{id:'oeType',header:'考试类型',dataIndex:'oeType',
					renderer:function(v){
						if(v==0){
							return '正常';
						}else{
							return '第'+v+'次补考';
						}
					}
				},
				{id:'oeTime',header:'安排时间',dataIndex:'oeTime'}
			]
		})
	});
	var addStudWin = new Ext.Window({
		layout:'border',
		title:'增加',
		width:750,
		height:500,
		modal:true,
		closeAction:'hide',
		items:[openedStudGrid],
		buttons:[
			{
				text:'按班级增加',
				handler:function(){
					addFromClsWin.show();
				}
			},
			{
				text:'按学生增加',
				handler:function(){
					alert('在学生列表上选择多个或是一个学生，然后保存。开发中...');
				}
			},
			{
				text:'删除',
				handler:function(){
					var rcs = openedStudGrid.getSelectionModel().getSelections();
					if(rcs.length==0){
						Ext.Msg.alert('提示','请选择要删除的记录!');
					}else{
						Ext.Msg.confirm('提示','确定要删除这些记录吗？<br/>对于考试完成的数据不能删除，<br/>对于已经开始考试的数据也不能删除。',
						 function(btn){
							if(btn=='yes'){
								var oeids = new Array();
								Ext.each(rcs,function(rc){
									if(rc.get('oeState')!='2'){					//2：是已经考试完成的，不能删除
										oeids.push(rc.get('oeId'));
									}
								});
								if(oeids.length==0){
									Ext.Msg.alert('提示','没有能够删除的数据！');
									return;
								}
								Ext.Ajax.request({
									url:path+'examarrange!deleteStuds.action',		//删除已经安排的考试
									method:'post',
									params:{oeId:oeids},
									success:function(resp,opts){
										var result = Ext.util.JSON.decode(resp.responseText);
										if(result.success==true){
											var oeids = result.oeids;
											for(var i=0;i<oeids.length;i++){
												var rc = openedStudStore.getById(oeids[i]);			//根据id找到一个Record，然后删除
												openedStudStore.remove(rc);
											}
										}else{
											Ext.Msg.alert('提示','删除不成功！');
										}
									},
									failure:function(resp,opts){
										alert('操作不成功!')
									}
								});
							}
						});
					}
				}
			},
			{
				text:'关闭',
				handler:function(){
					addStudWin.hide();
				}
			}
		]
	});
	/**
	 * 按班级增加时的帮助
	 */
	var addFromClsForm = new Ext.form.FormPanel({
		frame:true,
		region:'center',
		labelAlign:'right',
		labelWidth:80,
		height:200,
		labelSeparator:'：',
		items:[
			{
				xtype:'panel',
				fieldLabel:'班级',
				layout:'hbox',
				items:[
					{
						xtype:'textfield',
						name:'clsName',
						readOnly:true,
						allowBlank:false
					},
					{
						xtype:'button',
						text:'...',
						handler:function(){
							clsWin.show();
						}
					},
					{
						xtype:'hidden',
						name:'clsId'								//隐藏列，班级id
					}
				]
			},
			{
				xtype:'panel',
				fieldLabel:'授课教师',
				layout:'hbox',
				items:[
					{
						xtype:'textfield',
						name:'teac',
						readOnly:true,
						allowBlank:false
					},
					{
						xtype:'button',
						text:'...',
						handler:function(){
							tr = 'teac';
							teacWin.show();
						}
					},
					{
						xtype:'hidden',
						name:'teacId'
					}
				]
			},
			{
				xtype:'panel',
				fieldLabel:'阅卷教师',
				layout:'hbox',
				items:[
					{
						xtype:'textfield',
						name:'rate',
						readOnly:true,
						allowBlank:false
					},
					{
						xtype:'button',
						text:'...',
						handler:function(){
							tr = 'rate';
							teacWin.show();
						}
					},
					{
						xtype:'hidden',
						name:'rateId'
					}
				]
			}
		]
	});
	var addFromClsWin = new Ext.Window({
		modal:true,
		width:300,
		height:200,
		//autoHeight:true,
		closeAction:'hide',
		layout:'border',
		title:'选择班级',
		items:[addFromClsForm],
		buttons:[
			{
				text:'保存',
				handler:function(){
					var boo = addFromClsForm.getForm().isValid();
					if(boo){
						var rc = examGrid.getSelectionModel().getSelected();
						var examId = rc.get('examId');
						addFromClsForm.getForm().doAction('submit',{
							url:path+'examarrange!saveFromCls.action',
							method:'post',
							waitMsg:'正在保存请稍候...',
							waitTitle:'提示',
							params:{examId:examId},
							success:function(frm,opts){
								if(opts.result.success==true){
									addFromClsWin.hide(null,function(){
										openedStudStore.reload({params:{examId:examId}});
									});
								}else{
									Ext.Msg.alert('提示','保存不成功!');
								}
							},
							failure:function(frm,opts){
								alert('操作失败!')								
							}
						});
					}
				}
			},
			{
				text:'重置',
				handler:function(){
					addFromClsForm.getForm().reset();
				}
			},
			{
				text:'关闭',
				handler:function(){
					addFromClsWin.hide();
				}
			}
		]
	});
	/**
	 * 以下是班级的帮助
	 */
	var clsStore = new Ext.data.JsonStore({
		url:path+'studmanager!queryCls.action',
		idProperty:'clsId',
		totalProperty:'count',
		root:'classes',
		fields:['clsId','clsName','clsMajor','majorName','edusysId','edusysName'],
		listeners:{
			beforeload:function(ss,opts){
				var clsName = Ext.getCmp('clsName').getValue();
				Ext.apply(opts.params,{
					studCls:clsName
				})
			}
		}
	});
	var clsGrid = new Ext.grid.GridPanel({
		width:400,
		height:400,
		region:'center',
		store:clsStore,
		frame:true,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				{id:'clsId',header:'clsId',dataIndex:'clsId',hidden:true,hideable:false},
				{id:'clsName',header:'班级名称',dataIndex:'clsName'},
				{id:'clsMajor',header:'clsMajor',dataIndex:'clsMajor',hidden:true,hideable:false},
				{id:'majorName',header:'专业',dataIndex:'majorName'},
				{id:'edusysId',header:'edusysId',dataIndex:'edusysId',hidden:true,hideable:false},
				{id:'edusysName',header:'学制',dataIndex:'edusysName'}
			]
		}),
		autoExpandColumn:'edusysName',
		bbar:new Ext.PagingToolbar({
			store:clsStore,
			pageSize:10,
			displayInfo:true,
			displayMsg:'数据{0}/{1}共{2}',
			emptyMsg:'没有数据'
		}),
		tbar:[
			{
				xtype:'label',
				text:'班级名称：'
			},
			{
				xtype:'textfield',
				id:'clsName',
				width:80
			},
			'->',
			{
				xtype:'button',
				text:'查询',
				handler:function(){
					clsStore.reload({params:{start:0,limit:10}});
				}
			}
		],
		listeners:{
			afterrender:function(){
				clsStore.load({params:{start:0,limit:10}});
			}
		}
	});
	var clsWin = new Ext.Window({
		modal:true,
		title:'选择班级',
		width:400,
		height:400,
		closeAction:'hide',
		layout:'border',
		items:[clsGrid],
		buttons:[
			{
				text:'确定',
				handler:function(){
					var rc = clsGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','请选择一行数据!');
					}else{
						addFromClsForm.getForm().findField('clsName').setValue(rc.get('clsName'));
						addFromClsForm.getForm().findField('clsId').setValue(rc.get('clsId'));
						clsWin.hide();
					}
				}
			},
			{
				text:'关闭',
				handler:function(){
					clsWin.hide();
				}
			}
		]
	});
	
	//教师帮助,使用班级管理中的教师帮助
	var tr = '';//用于确定是设置teac或是rate
	var teacStore = new Ext.data.JsonStore({
		url:path+'class!queryTeac.action',
		idProperty:'teac_id',
		totalProperty:'count',
		root:'teacs',
		fields:['teac_id','teac_name'],
		listeners:{
			beforeload:function(store,opt){							//这儿的两个参数中，前者是store本身，后者已经是store最后的参数列表
				var teac_name = Ext.getCmp('teac_name').getValue().trim();
				Ext.apply(opt.params, {
    				teacName:teac_name								//将新参数加入到以前的参数中
				});
			}
		}
	});
	var teacGrid = new Ext.grid.GridPanel({
		width:300,
		height:300,
		store:teacStore,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		columns:[
		{id:'teac_id',header:'id',dataIndex:'teac_id',hidden:true},
		{id:'teac_name',header:'姓名',dataIndex:'teac_name',sortable:false,menuDisabled:true}
		],
		autoExpandColumn:'teac_name',
		bbar:new Ext.PagingToolbar({
			store:teacStore,
			pageSize:10,
			displayInfo: true,
			displayMsg: '{0}-{1}of{2}',
		    emptyMsg: "没有数据"
		}),
		tbar:[
		'-',
		{
			xtype:'label',
			text:'教师：'
		},
		{
			xtype:'textfield',
			id:'teac_name',
			width:120
		},
		'->',
		{
			xtype:'button',
			text:'筛选',
			handler:function(){
				teacStore.reload({params:{start:0,limit:10}});				//重新从第一页开始
			}
		}
		],
		listeners:{
			rowdblclick:function(){
				selectRow();
			},
			afterrender:function(){
				teacStore.load({params:{limit:10,start:0}});			//当表单加载完成以后加载数据，这样可以保证id为teac_name的元素可用
			}
		}
	});
	var teacWin = new Ext.Window({
		modal:true,
		width:310,
		height:300,
		autoHeight:true,
		title:'选择教师',
		closable:true,
		closeAction:'hide',
		items:[teacGrid],
		buttons:[
		{
			text:'确定',
			handler:function(){
				selectRow();
			}
		},
		{
			text:'关闭',
			handler:function(){
				teacWin.hide();
			}
		}
		]
	});
	var selectRow = function(){
		var rc = teacGrid.getSelectionModel().getSelected();
		if(!rc){
			Ext.Msg.alert('提示','请选择一个教师!');
		}else{
			addFromClsForm.getForm().findField(tr).setValue(rc.get('teac_name'));
			teacWin.hide();
		}
	};
});