Ext.onReady(function(){
	
	var teacSexRender=function(v){
		if(v=="1"){
			return '<font color="red">男</font>';
		}else{
			return '<font color="green">女</font>';
		}
	}
	var teacState = function(value,cellMeta,record,rowIndex,columnIndex,store){
		if(value=='0'){
			return '待审批';
		}else if(value=='1'){
			return '正常';
		}else if(value=='2'){
			return '不通过';
		}else if(value=='3'){
			return '禁用';
		}
	}
	
	var teacStore = new Ext.data.JsonStore({
		url:path+'teac!query.action',
		root:'teacs',
		idProperty:'teac_id',
		totalProperty:'count',
		fields:[
		{name:'teacId',type:'string',mapping:'teac_id'},
		{name:'teacName',type:'string',mapping:'teac_name'},
		{name:'teacSex',type:'string',mapping:'teac_sex'},
		{name:'teacDept',type:'string',mapping:'teac_dept'},
		{name:'deptName',type:'string',mapping:'dept_name'},
		{name:'teacState',type:'string',mapping:'teac_state'},
		{name:'teacEmail',type:'string',mapping:'teac_email'},
		{name:'teacTime',type:'string',mapping:'teac_time'},
		{name:'teacTel',type:'string',mapping:'teac_tel'}
		]
	});
	teacStore.load({params:{start:0,limit:10}});
	teacGrid = new Ext.grid.GridPanel({
		//title:'注册用户',
		//width:600,
		//height:320,
		region:'center',
		store:teacStore,
		columns:[
		{id:'teacId',header:'ID',dataIndex:'teacId',hidden:true,hideable:false},
		{id:'teacName',header:'姓名',dataIndex:'teacName',sortable:true},
		{id:'teacSex',header:'性别',dataIndex:'teacSex',width:50,renderer:teacSexRender,sortable:true},
		{id:'teacDept',header:'teacDept',dataIndex:'teacDept',hidden:true,hideable:false},
		{id:'deptName',header:'部门',dataIndex:'deptName',sortable:true},
		{id:'teacState',header:'状态',dataIndex:'teacState',width:70,renderer:teacState,sortable:true},
		{id:'teacEmail',header:'邮箱',dataIndex:'teacEmail',sortable:true},
		{id:'teacTime',header:'注册时间',dataIndex:'teacTime',sortable:true},
		{id:'teacTel',header:'电话',dataIndex:'teacTel',sortable:true}
		],
		autoExpandColumn:'teacTel',
		bbar: new Ext.PagingToolbar({								//使用分页
		            pageSize: 10,											//指定每页显示的数量
		            store: teacStore,											//指定数据存储仓库
		            displayInfo: true,										//是否显示提示信息
		            displayMsg: '数据显示 {0} - {1} of {2}',
		            emptyMsg: "没有数据"
		}),
		sm: new Ext.grid.RowSelectionModel({singleSelect:true})   //指定列表为单选
		,
				buttons:[
		{
			text:'查询',
			handler:function(){
				//使用vbox也可很好和布局,
				alert("在弹出框上.输入用户的查询的条件后执行同一个查询方法!!");
			}
		},
		{
			text:'审批',
			handler:function(){
				//alert("选中一行后,在弹出的表单上完成修改并保存的功能.修改成功后,在修改显示表表格中的值");
				var record = teacGrid.getSelectionModel().getSelected();
				if(!record){
					Ext.Msg.alert('提示','请选中要修改的记录行!');
					return;
				}else{
					updateForm.getForm().loadRecord(record);
					updateWin.show();	
				}
			}
		},
		{
			text:'分配角色',
			handler:function(){
				var record = teacGrid.getSelectionModel().getSelected();
				if(!record){
					Ext.Msg.alert('提示','请选中一行记录!');
					return;
				}
				roleWin.show();
			}
		},
		{
			text:'删除',
			handler:function(){
				var record = teacGrid.getSelectionModel().getSelected();
				if(!record){
					Ext.Msg.alert('提示','请选中一行记录!');
					return;
				}
				Ext.MessageBox.confirm('提示','将删除与此用户相关的所有信息!<br/>确定要删除吗?',function(btn){
					if(btn=="yes"){
						Ext.Ajax.request({
							url:path+'teac!delete.action',
							method:'post',
							params:{teac_id:record.get("teacId")},
							success:function(res,opt){
								var r = Ext.util.JSON.decode(res.responseText);
								if(r.success==true){
									teacStore.remove(record);
								}else{
									Ext.Msg.alert('提示','删除失败!');
								}
							},
							failure:function(res,opt){
								alert("服务访问失败!");
							}
							
						});	
					}
				},Ext.MessageBox.YESNO);
			}
		}
		]
	});
	
	
//	var teacWin = new Ext.Window({
//		width:620,
//		height:300,
//		autoHeight:true,
//		closable:false,
//		items:[teacGrid],
//
//	});
//	teacWin.show();
	var teacView = new Ext.Viewport({
		layout:'border',
		items:[teacGrid]
	});
	
});