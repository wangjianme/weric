Ext.onReady(function(){
	Ext.QuickTips.init();
	courStore = new Ext.data.JsonStore({		//声明加载数据的Store
		url:path+'cour!query.action',
		idProperty:'cour_id',
		root:'cours',
		totalProperty:'count',
		fields:[
		{name:'courId',mapping:'cour_id',type:'string'},
		{name:'courName',mapping:'cour_name',type:'string'},
		{name:'majors',mapping:'majorIds',type:'string'},
		{name:'majorNames',mapping:'majorNames',type:'string'},
		{name:'courIspubs',mapping:'cour_ispubs',type:'string'},
		{name:'courInuse',mapping:'cour_inuse',type:'string'},
		{name:'courHour',mapping:'cour_hour',type:'int'},
		{name:'courDesc',mapping:'cour_desc',type:'string'}
		],
		listeners:{	//因为向下或是向上翻页时会只传start和limit参数，所以这儿添加beforeload事件，以添加表单中的参数，添加此监听后，包括刷新也会启到作用
			beforeload:function(tmpStore,opt){
				var params = queryCourForm.getForm().getFieldValues();
				Ext.apply(opt.params,params);
			}
		}
	});
	
	courStore.load({params:{start:0,limit:20}});
	
	courGrid = new Ext.grid.GridPanel({
		flex:1,
		frame:true,
		//style:{marginTop: '20px',marginLeft:'20px'},
		//style:{padding:'20px'},				//通过设置此属性可以设置组件之间的边距
		//padding:110,//同bodyStyle:'padding:100px'
		//margins:{top:120, right:0, bottom:0, left:0},
		//bodyStyle:'padding:100px',//设置里面的内容的边距
		//defaults:{bodyStyle:'padding:10px'},
		store:courStore,
		renderTo:Ext.getBody(),
		sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
		columns:[
		{id:'courId',header:'编号',dataIndex:'courId',hidden:true,hideable:false},
		{id:'courName',header:'名称',dataIndex:'courName',sortable:true},
		{id:'courHour',header:'课时',dataIndex:'courHour',sortable:true,width:60},
		{id:'majors',header:'专业ID',dataIndex:'majors',hidden:true,hideable:false},
		{id:'majorNames',header:'所属专业',dataIndex:'majorNames',width:150},
		{id:'courIspubs',header:'公共课',dataIndex:'courIspubs',sortable:true,renderer:function(v){
			if(v=="1"){
				return "是";
			}else{
				return "否";
			}
		}},
		{id:'courInuse',header:'状态',dataIndex:'courInuse',sortable:true,renderer:function(v){
			if(v=="1"){
				return "在用";
			}else{
				return "停用";
			}
		}},
		{id:'courDesc',header:'说明',dataIndex:'courDesc',sortable:false}
		],
		autoExpandColumn:'courDesc',
		bbar:new Ext.PagingToolbar({
			pageSize:20,
			store:courStore,
			displayInfo:true,
			displayMsg:'显示{0}/{1}of{2}',
			emptyMsg:'没有数据',
			plugins:new Ext.ux.ProgressBarPager()
		}),
		buttons:[
		{
			text:'增加',
			handler:function(){
				//手工清空所有信息
				courForm.getForm().findField('courId').setValue('');
				courForm.getForm().findField('courName').setValue('');
				courForm.getForm().findField('majors').setValue('');
				courForm.getForm().findField('majorNames').setValue('');
				courForm.getForm().findField('courIspubs').setValue('0');
				courForm.getForm().findField('courInuse').setValue('1');
				courForm.getForm().findField('courDesc').setValue('');
				courForm.getForm().findField('courHour').setValue(0);
				courWin.show();
			}
		},
		{
			text:'修改',
			handler:function(){
				var rc = courGrid.getSelectionModel().getSelected();
				if(!rc){
					Ext.Msg.alert('提示','请先选择一行数据!');
					return;
				}
				courForm.getForm().loadRecord(rc);
				courWin.show();
			}
		},
		{
			text:'删除',
			handler:function(){
				var rc = courGrid.getSelectionModel().getSelected();
				if(!rc){
					Ext.Msg.alert('提示','请先选择一行数据!');
					return;
				}
				Ext.MessageBox.confirm('提示','确定要删除吗?',function(btn){
					if(btn=='yes'){
						courForm.getForm().loadRecord(rc);
						courForm.getForm().doAction('submit',{
							url:path+'cour!del.action',
							method:'post',
							waitTitle:'删除',
							waitMsg:'删除中,请稍候...',
							success:function(frm,opt){
								if(opt.result.success==true){
									courStore.remove(rc);
								}else{
									Ext.Msg.alert('提示','删除不成功.');
								}
							},
							failure:function(frm,opt){
								alert("服务出错!");
							}
						});
					}
				});
			}
		}
		]
	});
	//功能显示区
	var courView = new Ext.Viewport({
		layout:'vbox',
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[queryCourForm,courGrid]
	});
});