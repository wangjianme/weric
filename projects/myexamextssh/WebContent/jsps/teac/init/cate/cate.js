Ext.onReady(function(){
	var cateStore = new Ext.data.JsonStore({
		url:path+'cate!query.action',
		idProperty:'cateId',
		root:'cates',
		fields:['cateId','cateName','cateOrder','cateKey','cateDesc']
	});
	cateStore.load();
	
	var rn = new Ext.grid.RowNumberer();
	
	var cateGrid = new Ext.grid.GridPanel({
		store:cateStore,
		region:'center',
		columns:[
			rn,
			{id:'cateId',header:'ID',dataIndex:'cateId',hidden:true,hideable:false},
			{id:'cateName',header:'名称',dataIndex:'cateName'},
			{id:'cateOrder',header:'排序',dataIndex:'cateOrder',sortable:true},
			{id:'cateKey',header:'关键字',dataIndex:'cateKey'},
			{id:'cateDesc',header:'说明',dataIndex:'cateDesc'}
		],
		autoExpandColumn:'cateDesc',
		sm:new Ext.grid.RowSelectionModel({singleSelect:true})
	});
	
	var cateView  = new Ext.Viewport({
		layout:'border',
		items:[cateGrid]
	});
	
});