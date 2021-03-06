Ext.onReady(function(){
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
	
	clsWin = new Ext.Window({
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
						Ext.Msg.alert('提示','请选择一行记录！');
					}else{
						updateStudForm.getForm().findField('studCls').setValue(rc.get('clsId'));
						updateStudForm.getForm().findField('clsName').setValue(rc.get('clsName'));
						updateStudForm.getForm().findField('majorId').setValue(rc.get('clsMajor'));
						updateStudForm.getForm().findField('majorName').setValue(rc.get('majorName'));
						updateStudForm.getForm().findField('edusysId').setValue(rc.get('edusysId'));
						updateStudForm.getForm().findField('edusysName').setValue(rc.get('edusysName'));
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
});