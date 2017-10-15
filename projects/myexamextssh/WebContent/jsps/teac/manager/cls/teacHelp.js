Ext.onReady(function(){
	var teacStore = new Ext.data.JsonStore({
		url:path+'class!queryTeac.action',
		idProperty:'teac_id',
		totalProperty:'count',
		root:'teacs',
		fields:['teac_id','teac_name']
		,
		listeners:{
			beforeload:function(store,opt){//这儿的两个参数中，前者是store本身，后者已经是store最后的参数列表
				//lastOptions = myStore.lastOptions;				//获取最后的参数
				var teac_name = Ext.getCmp('teac_name').getValue().trim();
				Ext.apply(opt.params, {
    				teacName:teac_name								//将新参数加入到以前的参数中
				});
				//myStore.reload(lastOptions);
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
		{
			xtype:'label',
			text:'教师'
		},
		{
			xtype:'textfield',
			id:'teac_name',
			width:120
		},
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
				selectedRow();
			},
			afterrender:function(){
				teacStore.load({params:{limit:10,start:0}});			//当表单加载完成以后加载数据，这样可以保证id为teac_name的元素可用
			}
		}
	});
	
	
	
	teacWin = new Ext.Window({
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
				selectedRow();
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
	var selectedRow = function(){
		var rc = teacGrid.getSelectionModel().getSelected();
		if(!rc){
			Ext.Msg.alert('提示','请选中一行数据！');
			return;
		}
		selectedForm.getForm().findField('clsHead').setValue(rc.get('teac_id'));
		selectedForm.getForm().findField('teacName').setValue(rc.get('teac_name'));
		teacWin.hide();
	}
	
});