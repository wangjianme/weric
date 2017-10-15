Ext.onReady(function(){
	var courStore = new Ext.data.JsonStore({
		id:'courId',
		root:'cours',
		url:path+'examset!queryCour.action',
		totalProperty:'count',
		idProperty:'courId',
		fields:['courId','courName'],
		listeners:{
			beforeload:function(s,opt){
				var courName = Ext.getCmp("courName");
				if(courName){
					Ext.apply(opt.params,{
						courName:courName.getValue()
					});
				}
			}
		}
	});	
	courStore.load({params:{start:0,limit:10}});
	
	var courGrid = new Ext.grid.GridPanel({
		region:'center',
		id:'courGrid',
		width:200,
		store:courStore,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				{id:'courId',header:'id',dataIndex:'courId',hidden:true,hideable:false},
				{id:'courName',header:'课程名称',dataIndex:'courName'}
			]
		}),
		autoExpandColumn:'courName',
		bbar:new Ext.PagingToolbar({
			store:courStore,
			pageSize:10,
			displayInfo:true,
			displayMsg:'{0}-{1}of{2}',
			emptyMsg:'没有数据'
		}),
		tbar:[
			{
				xtype:'label',
				text:'课程名称：'
			},
			{
				xtype:'textfield',
				width:100,
				id:'courName'
			},
			'->',
			{
				xtype:'button',
				text:'查询',
				handler:function(){
					courStore.reload({params:{start:0,limit:10}});
				}
			}
		]
	});
	
	courWin = new Ext.Window({
		modal:true,
		title:'选择课程',
		width:400,
		height:300,
		layout:'border',
		items:[courGrid],
		closeAction:'hide',
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
					courWin.hide();
				}
			}
		]
	});
	var selectRow = function(){
		var rc = courGrid.getSelectionModel().getSelected();
		if(!rc){
			Ext.Msg.alert('提示','请选择一行数据!');
			return;
		}else{
			addExamForm.getForm().findField("courName").setValue(rc.get("courName"));
			addExamForm.getForm().findField("examCour").setValue(rc.get("courId"));
			courWin.hide();
		}
	}
});