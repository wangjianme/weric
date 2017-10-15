Ext.onReady(function(){
	Ext.QuickTips.init();
	selectedForm = null;							//选择的是哪一个表单
	clsStore = new Ext.data.JsonStore({			//班级的信息数据库
		url:path+'class!query.action',
		idProperty:'cls_id',
		totalProperty:'count',
		root:'classes',
		fields:['clsId','clsName','clsBtime','clsEtime','clsState',
				'clsHead','teacName',
				'clsMajor','majorName',
				'edusysId','edusysName'
		],
		listeners:{
			beforeload:function(st,opts){
				var param = queryClsForm.getForm().getFieldValues();
				Ext.apply(opts.params,param);
			}
		}
	});
	clsStore.load({params:{start:0,limit:10}});		//初始只显示没有毕业的班级
	clsGrid = new Ext.grid.GridPanel({
		flex:1,
		frame:true,
		store:clsStore,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				{id:'clsId',header:'clsId',dataIndex:'clsId',hidden:true,hideable:false},
				{id:'clsName',header:'班级名称',dataIndex:'clsName'},
				{id:'clsHead',header:'clsHeader',dataIndex:'clsHead',hidden:true,hideable:false},
				{id:'teacName',header:'班主任',dataIndex:'teacName'},
				{id:'clsMajor',header:'major',dataIndex:'clsMajor',hidden:true,hideable:false},
				{id:'majorName',header:'专业',dataIndex:'majorName'},
				{id:'edusysId',header:'edusysId',dataIndex:'edusysId',hidden:true,hideable:false},
				{id:'edusysName',header:'学制',dataIndex:'edusysName'},
				{id:'clsBtime',header:'建班时间',dataIndex:'clsBtime'},
				{id:'clsEtime',header:'毕业时间',dataIndex:'clsEtime'},
				{id:'clsState',header:'状态',dataIndex:'clsState',renderer:function(v){
					if(v=="1"){
						return "正常";
					}else if(v=='2'){
						return "已毕业";
					}else{
						return '<font color="red">停用</font>';
					}
				}}
			]
		}),
		autoExpandColumn:'clsState',
		bbar:new Ext.PagingToolbar({
			store:clsStore,
			pageSize:10,
			displayInfo: true,
			displayMsg: '数据显示 {0}/{1} of {2}',
		    emptyMsg: "没有数据",
		    plugins:new Ext.ux.ProgressBarPager()
		}),
		buttons:[
		{
			text:'增加',
			handler:function(){
				selectedForm = clsForm;
				clsWin.setTitle('增加');
				clsWin.show(null,function(){
					clsForm.getForm().findField('clsBtime').setValue(new Date());
				});
			}
		},
		{
			text:'修改',
			handler:function(){
				var rc = clsGrid.getSelectionModel().getSelected();
				if(!rc){
					Ext.Msg.alert('提示','请选择一行数据');
					return;
				}else{
					selectedForm = clsForm;
					clsWin.setTitle('修改');
					clsWin.show(null,function(){
						clsForm.getForm().loadRecord(rc);
					});
				}
			}
		},
		{
			text:'删除',
			handler:function(){
				var rc = clsGrid.getSelectionModel().getSelected();
				if(!rc){
					Ext.Msg.alert('提示','请选择一行数据');
					return;
				}else{
					Ext.Msg.confirm('提示','删除此班会同时删除此班的所有<font color="red">学生</font>，<br/>' +
									'请谨慎操作！<br/>确定要删除此班级吗？',function(btn){
						if(btn=='yes'){
							clsForm.getForm().loadRecord(rc);
							clsForm.getForm().doAction('submit',{
								url:path+'class!del.action',
								method:'post',
								waitTitle:'正在删除',
								waitMsg:'正在删除，请稍候...',
								success:function(frm,opt){
									if(opt.result.success==true){
										clsStore.remove(rc);
									}else{
										Ext.Msg.alert('提示','删除不成功．');
									}
								},
								failure:function(frm,opt){
									Ext.Msg.alert('服务','删除不成功．');
								}
							});
						}
					});
				}
			}
		}
		]
	});
	var mainView = new Ext.Viewport({
		layout:'vbox',
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[queryClsForm,clsGrid]
	});
});