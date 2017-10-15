Ext.onReady(function(){
	var majorStore = new Ext.data.JsonStore({
		idProperty:'majorId',
		root:'majors',
		url:path+'cour!queryMajor.action',
		fields:[
			{name:'majorId',mapping:'majorId',type:'string'},
			{name:'majorName',mapping:'majorName',type:'string'}
		]
	});
	//majorStore.load();
	
	
	var ssm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	var majorGrid = new Ext.grid.GridPanel({
		width:220,
		height:300,
		region:'center',
		store:majorStore,
		autoScroll:true,
		sm:ssm,
		columns:[
			ssm,
			{id:'majorId',header:'编号',dataIndex:'majorId',hidden:true,hideable:false},
			{id:'majorName',header:'名称',dataIndex:'majorName',sortable:true}
		],
		autoExpandColumn:'majorName'
	});
	
	//这是GridPanel显示完成后最后调用的事件,显示了以后选中某些数据
	majorGrid.getView().on('refresh',function(){//每次调用JsonStore的reload事件，
		var majorIds = courForm.getForm().findField('majors').getValue();//数据类型为:"1,3252ks222"
		if(majorIds!=""){//如果不是空
			var majors = majorIds.split(",");
			var recs = [];
			for(var i=0;i<majors.length;i++){
				var rc = majorStore.getById(majors[i]);//通过此方法获取每一个Record对像
				recs.push(rc);//放到Array中
			}
			majorGrid.getSelectionModel().selectRecords(recs);//选中数组中的所有Record对像
		}else{//如果是空则全部清除选中
			majorGrid.getSelectionModel().clearSelections();
		}
	});
	
	majorWin = new Ext.Window({
		modal:true,
		width:220,
		height:300,
		autoHeight:true,
		title:'专业',
		closeAction:'hide',
		items:[majorGrid],
		buttons:[
		{
			text:'确定',
			handler:function(){
				var rcs = majorGrid.getSelectionModel().getSelections();
				if(rcs.length==0){
					courForm.getForm().findField("majors").setValue('');
					courForm.getForm().findField("majorNames").setValue('');
				}else{
					var majorIds = "";
					var majorNames = "";
					for(var i=0;i<rcs.length;i++){
						if(majorIds==""){
							majorIds=rcs[i].get('majorId');
							majorNames=rcs[i].get('majorName');
						}else{
							majorIds=majorIds+","+rcs[i].get('majorId');
							majorNames=majorNames+","+rcs[i].get('majorName');
						}
					}
					courForm.getForm().findField("majors").setValue(majorIds);
					courForm.getForm().findField("majorNames").setValue(majorNames);
				}
				majorWin.hide();
			}
		},
		{
			text:'关闭',
			handler:function(){
				majorWin.hide();
			}
		}
//		,
//		{
//			text:'测',
//			handler:function(){
//				var majorIds = "1,402880062bb2e743012bb2e8d1140001";
//				if(majorIds!=""){
//					var majors = majorIds.split(",");
//					var recs = [];
//					for(var i=0;i<majors.length;i++){
//						var rc = majorStore.getById(majors[i]);
//						recs.push(rc);
//					}
//					alert(recs.length);
//					majorGrid.getSelectionModel().selectRecords(recs);
//				}
//			}
//		}
		],
		listeners:{
			show:function(){
				majorStore.reload();
			}
		}
	});
});