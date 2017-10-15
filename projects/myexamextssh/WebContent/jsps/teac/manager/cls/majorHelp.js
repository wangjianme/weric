Ext.onReady(function(){
	var majorStore = new Ext.data.JsonStore({
		url:path+'class!queryMajor.action',
		idProperty:'majorId',
		root:'majors',
		fields:['majorId','majorName','deptName','majorEdusys','edusysName']
	});
	var majorGrid = new Ext.grid.GridPanel({
		store:majorStore,
		width:350,
		height:300,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				{id:'majorId',header:'ID',dataIndex:'majorId',hidden:true,hideable:false},
				{id:'majorName',header:'专业名称',dataIndex:'majorName'},
				{id:'deptName',header:'所属院系',dataIndex:'deptName'},
				{id:'majorEdusys',header:'edusysId',dataIndex:'majorEdusys',hidden:true,hideable:false},
				{id:'edusysName',header:'学制',dataIndex:'edusysName'}
			]
		}),
		autoExpandColumn:'majorName',
		listeners:{
			afterrender:function(){
				majorStore.load();
			},
			rowdblclick:function(){
				selectMajor();
			}
		}
	});
	majorWin = new Ext.Window({
		modal:true,
		title:'选择专业',
		width:350,
		height:300,
		autoHeight:true,
		closeAction:'hide',
		items:[majorGrid],
		buttons:[
		{
			text:'确定',
			handler:function(){
				selectMajor();
			}
		},
		{
			text:'关闭',
			handler:function(){
				majorWin.hide();
			}
		}
		]
	});
	var selectMajor = function(){
		var rc = majorGrid.getSelectionModel().getSelected();
		if(!rc){
			Ext.Msg.alert('提示','请选择一行数据!');
			return;
		}
		selectedForm.getForm().findField('clsMajor').setValue(rc.get('majorId'));
		selectedForm.getForm().findField('majorName').setValue(rc.get('majorName'));
		selectedForm.getForm().findField('edusysId').setValue(rc.get('majorEdusys'));
		selectedForm.getForm().findField('edusysName').setValue(rc.get('edusysName'));
		majorWin.hide();
	}
});