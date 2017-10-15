Ext.onReady(function(){
	var jsonRole = new Ext.data.JsonStore({
		id:'role',
		url:path+'roleAction!query.action',
		root:'roles',
		fields:['role_id','role_name','role_desc','role_candel']
	});
	jsonRole.load();
	roleGrid = new Ext.grid.GridPanel({
		id:'roleGrid',
		//title:'角色列表',
		store:jsonRole,
		//width:350,
		//height:200,
		//renderTo:'grid1',
		region:'center',
		sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
		autoExpandColumn:'role_desc',
		columns:[
			{id:'role_id',header:'编号',dataIndex:'role_id',hidden:true},
			{id:'role_name',header:'名称',dataIndex:'role_name',sortable:false,width:100,menuDisabled:true},
			{id:'role_desc',header:'说明',dataIndex:'role_desc',sortable:false,menuDisabled:true},
			{id:'role_candel',header:'可删除?',dataIndex:'role_candel',sortable:false,hidden:true}
		],
		buttons:[
			{
				text:'增加',
				handler:function(){
					addForm.getForm().findField("roleId").setValue("");
					addForm.getForm().findField("roleName").setValue("");
					addForm.getForm().findField("roleDesc").setValue("");
					addWin.show();
				}
			},
			{
				text:'修改',
				handler:function(){
					var roleRecord = roleGrid.getSelectionModel().getSelected();
					if(!roleRecord){
						Ext.Msg.alert('提示','请选中要修改的记录!');
						return;
					}else{
						addForm.getForm().findField("roleId").setValue(roleRecord.get("role_id"));
						addForm.getForm().findField("roleName").setValue(roleRecord.get("role_name"));
						addForm.getForm().findField("roleDesc").setValue(roleRecord.get("role_desc"));
						addWin.show();
					}
				}
			},
			{
				text:'删除',
				handler:function(){
					var roleRecord = roleGrid.getSelectionModel().getSelected();
					if(!roleRecord){
						Ext.Msg.alert("提示","请选中要删除的行．");
						return;
					}
					if(roleRecord.get("role_candel")=="0"){
						Ext.Msg.alert("提示","系统用户不能删除!");
						return;
					}
					Ext.MessageBox.confirm("提示","确定要删除这行记录吗？",function(btn){
						if(btn=="yes"){
							var roleId = roleRecord.get("role_id");
							Ext.Ajax.request({
								url:path+'roleAction!del.action',
								method:'post',
								params:{roleName:roleId},
								success:function(res,ops){
									var jsonObj = Ext.util.JSON.decode(res.responseText);
									if(jsonObj.success==true){
										jsonRole.remove(roleRecord);
									}
								},
								failure:function(res,ops){
									alert(res.responseText);
								}
							});
						}
					},Ext.MessageBox.YESNO);
				}
			},
			{
				text:'功能分配',
				handler:function(){
					var rec = roleGrid.getSelectionModel().getSelected();
					if(!rec){
						Ext.Msg.alert('MSG','请先选中一行数据!');
						return;
					}else{
						funcWin.show();
					}
				}
			}
		]
	});
	roleGrid.setAutoScroll(true);
	var roleWin = new Ext.Viewport({
		layout:'border',
		items:[roleGrid],
		buttons:[
			{
				text:'One'
			}
		]
	});
	var addForm = new Ext.form.FormPanel({
		frame:true,
		labelAlign:'right',
		labelWidth:80,
		items:[
			{				//增加 个id字段以确实是增加还是修改
				xtype:'hidden',
				name:'roleId'
			},
			{
				xtype:'textfield',
				name:'roleName',
				fieldLabel:'角色名称',
				labelSeparator:'：',
				allowBlank:false
			},
			{
				xtype:'textfield',
				name:'roleDesc',
				width:120,
				height:50,
				fieldLabel:'说明',
				labelSeparator:'：'
			}
		]		
	});
	
	var addWin = new Ext.Window({
		id:'addWin',
		title:'增加',
		width:250,
		height:130,
		autoHeight:true,
		modal:true,
		items:[addForm],
		closable:true,
		closeAction:'hide',
		resizable:false,
		buttons:[
			{
				text:'保存',
				handler:function(){
					if(!addForm.getForm().isValid()){				//没有写入数据则不提交
						return;
					};
					var roleName = addForm.getForm().findField("roleName").getValue();
					var idx = jsonRole.find("role_name",roleName.trim());
					if(idx>=0){
						Ext.Msg.alert("提示","此名称已经被使用，请换一个．");
						return;
					}
					addForm.getForm().submit({
						url:path+'roleAction!save.action',
						method:'post',
						success:function(f,op){
							if(op.result.success==true){
								if(op.result.save==true){				//如果是保存的话
									var Role = Ext.data.Record.create([
										{id:'role_id',type:'string'},
										{id:'role_name',type:'string'},
										{id:'role_desc',type:'string'},
										{id:'role_candel',type:'string'}
									]);
									var role = new Role({role_id:op.result.roleId,
														 role_name:op.result.roleName,
														 role_desc:op.result.roleDesc,
														 role_candel:op.result.roleCanDel
														 });
									jsonRole.add(role);
									roleGrid.getSelectionModel().selectLastRow();
								}else{
									var rec = roleGrid.getSelectionModel().getSelected();
									rec.set("role_name",op.result.roleName);
									rec.set("role_desc",op.result.roleDesc);
								}
								addWin.hide();
							}else{
								Ext.Msg.alert("MSG","服务器访问不成功.");
							}
						},
						failure:function(f,op){
							alert("Error....")
						}
					});
				}
			},
			{
				text:'关闭',
				handler:function(){
					addWin.hide();
				}
			}
		],
		listeners:{
			beforeshow:function(){
				//roleName.setValue('');
				//roleDesc.setValue('');
			}
		}
	});
});