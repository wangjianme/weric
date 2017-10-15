Ext.onReady(function(){
	studStore = new Ext.data.JsonStore({
		url:path+'studmanager!query.action',
		root:'studs',
		idProperty:'stud_id',
		totalProperty:'count',
		fields:[
			'studId','studNo','studName','studSex','studSid','studBirth',
			'studCls','clsName',
			'studRtime','studPic','studAddr','studTel',
			'studQq','studEmail','studDesc',
			'majorId','majorName',
			'edusysId','edusysName','studState'
		],
		listeners:{
			beforeload:function(ss,opts){
				var param = queryStudForm.getForm().getFieldValues();
				Ext.apply(opts.params,param);
			}
		}
	});
	studStore.load({params:{start:0,limit:10}});
	
	var expander = new Ext.ux.grid.RowExpander({
        tpl : new Ext.Template(
            '<p><b>相片:</b><img width=100 height=100 src='+path+'images/{studPic}></img><b>说明：</b>{studDesc}</p><br>'
        )
    });
	
	studGrid = new Ext.grid.GridPanel({
		store:studStore,
		flex:1,
		//region:'center',
		plugins:expander,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		autoExpandColumn:'studSid',
		cm:new Ext.grid.ColumnModel({
			defaults:{menuDisabled:true,sortable:true},
			columns:[
				 expander,
				{id:'studId',header:'id',dataIndex:'studId',hidden:true,hideable:false},
				{id:'studNo',header:'学号',dataIndex:'studNo'},
				{id:'studName',header:'姓名',dataIndex:'studName'},
				{id:'studSex',header:'性别',dataIndex:'studSex',width:60,
					renderer:function(v){
						if(v=="1"){
							return "<font color='red'>男</font>";
						}else{
							return "<font color='green'>女</font>";
						}
					}
				},
				{id:'studSid',header:'身份证号',dataIndex:'studSid'},
				{id:'studBirth',header:'出生日期',dataIndex:'studBirth'},
				{id:'studCls',header:'studCls',dataIndex:'studCls',hidden:true,hideable:false},
				{id:'clsName',header:'班级名称',dataIndex:'clsName'},
				{id:'majorId',header:'majorId',dataIndex:'majorId',hidden:true,hideable:false},
				{id:'majorName',header:'专业',dataIndex:'majorName'},
				{id:'edusysId',header:'edusysId',dataIndex:'edusysId',hidden:true,hideable:false},
				{id:'edusysName',header:'学制',dataIndex:'edusysName'},
				{id:'studRtime',header:'注册时间',dataIndex:'studRtime'},
				{id:'studAddr',header:'地址',dataIndex:'studAddr'},
				{id:'studTel',header:'电话',dataIndex:'studTel'},
				{id:'studQq',header:'QQ',dataIndex:'studQq'},
				{id:'studEmail',header:'邮箱',dataIndex:'studEmail'},
				{id:'studState',header:'状态',dataIndex:'studState',
					renderer:function(v){
						if(v=='1'){
							return '正常';
						}else if(v=='2'){
							return '流失';
						}else if(v=='3'){
							return '退学';
						}else{
							return '病假';
						}
					}
				}
				]
		}),
		bbar:new Ext.PagingToolbar({
			store:studStore,
			pageSize:10,
			displayInfo:true,
			displayMsg:'数据{0}/{1}共{2}'
		}),
		buttons:[
		{
			text:'修改',
			handler:function(){
				var rc = studGrid.getSelectionModel().getSelected();
				if(!rc){
					Ext.Msg.alert('提示','请选择要修改的记录!');
				}else{
					updateStudForm.getForm().loadRecord(rc);
					updateStudWin.show();
				}
			}
		},
		{
			text:'删除',
			handler:function(){
				var rc = studGrid.getSelectionModel().getSelected();
				if(!rc){
					Ext.Msg.alert('提示','请选择要删除的行!');
				}else{
					Ext.Msg.confirm('提示','确定要<font color="red">彻底</font>删除此学生记录吗？',function(btn){
						if(btn=='yes'){
							var stud = rc.get('studId');
							var pic = rc.get('studPic');
							Ext.Ajax.request({
								url:path+'studmanager!del.action',
								method:'post',
								params:{studId:stud,studPic:pic},
								success:function(res,opt){
									var result = Ext.util.JSON.decode(res.responseText);
									if(result.success==true){
										studStore.remove(rc);
									}
								},
								failure:function(res,opt){
									Ext.Msg.alert('服务','操作不成功!');
								}
							});
						}
					});
				}
			}
		}
		]
	});
	var studView = new Ext.Viewport({
		layout:'vbox',
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[queryStudForm,studGrid]
	});
});