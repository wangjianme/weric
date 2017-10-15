Ext.onReady(function(){
	quesStore = new Ext.data.JsonStore({
		url:path+'ques!query.action',
		root:'queses',
		isProperty:'quesId',
		totalProperty:'count',
		fields:['quesId','quesTitle','quesLevel','quesState','quesCate',
			     'cateName','quesCour','courName','quesLevel'
		],
		listeners:{
			beforeload:function(st,opts){
				var param = quesQueryForm.getForm().getFieldValues();
				//alert(Ext.util.JSON.encode(param));
				Ext.apply(opts.params,param);
			}
		}
	});
	quesStore.load({params:{start:0,limit:15}});
	
	var quesGrid = new Ext.grid.GridPanel({
		flex:1,
		store:quesStore,
		frame:true,
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				new Ext.grid.RowNumberer(),
				{id:'quesId',header:'id',dataIndex:'quesId',hidden:true,hideable:false},
				{id:'quesTitle',header:'知识点',dataIndex:'quesTitle',width:200},
				{id:'quesCate',header:'cate',dataIndex:'quesCate',hidden:true,hideable:false},
				{id:'cateName',header:'题型',dataIndex:'cateName'},
				{id:'quesCour',header:'cour',dataIndex:'quesCour',hidden:true,hideable:false},
				{id:'courName',header:'课程',dataIndex:'courName'},
				{id:'quesLevel',header:'难度',dataIndex:'quesLevel',renderer:function(v){
					if(v=='1'){
						return '一般';
					}else if(v=='2'){
						return '难';
					}else if(v=='3'){
						return '很难';
					}else{
						return '简单';
					}
				}},
				{id:'quesState',header:'状态',dataIndex:'quesState',renderer:function(v){
					if(v=='1'){
						return '可用';
					}else{
						return '停用';
					}
				}}
			]
		}),
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		autoExpandColumn:'quesState',
		bbar:new Ext.PagingToolbar({
			store:quesStore,
			pageSize:15,
			displayInfo:true,
			displayMsg:'显示{0}-{1}共{2}',
			emptyMsg:'没有数据',
			plugins:new Ext.ux.ProgressBarPager()
		}),
		buttons:[
			{
				text:'预览',
				handler:function(){
					var rc = quesGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','请先选择一行数据!');
					}else{
						window.open(path+'ques!queryOneQues.action?quesId='+rc.get('quesId'),'_blank');
					}
				}
			},
			{
				text:'删除',
				handler:function(){
					var rc = quesGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','请先选择一行数据!');
					}else{
						Ext.Msg.confirm('提示','确定要删除吗?',function(btn){
							if(btn=='yes'){
								Ext.Ajax.request({
									url:path+'ques!del.action',
									method:'post',
									params:{quesId:rc.get('quesId')},
									success:function(res,opt){
										var result = Ext.util.JSON.decode(res.responseText);
										if(result.success==true){
											//Ext.Msg.alert('提示','删除成功');
											quesStore.remove(rc);
										}else{
											Ext.Msg.alert('提示','删除不成功');
										}
									},
									failure:function(res,opt){
										Ext.Msg.alert('服务提示','删除不成功');
									}
								});
							}
						});
					}
				}
			},
			{
				text:'增加新试题',
				handler:function(){
					window.location.href=path+'ques!toAdd.action';
				}
			}
		]	
	});
	var quesView = new Ext.Viewport({
		layout:'vbox',
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[quesQueryForm,quesGrid]
	});
	
});