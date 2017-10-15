Ext.onReady(function(){
	var jsonRole = new Ext.data.JsonStore({
		id:'role',
		url:path+'teac!roles.action',
		fields:['role_id','role_name','role_desc','flag']
	});
	
	var checkBox = new Ext.grid.CheckboxSelectionModel({
		singleSelect:false
	});
	roleGrid = new Ext.grid.GridPanel({
		store:jsonRole,
		width:350,
		height:200,
		region:'center',
		autoExpandColumn:'role_desc',
		selModel:checkBox,				//添加上复选框
		columns:[
			checkBox,
			{id:'role_id',header:'编号',dataIndex:'role_id',hidden:true},
			{id:'role_name',header:'名称',dataIndex:'role_name',sortable:false,width:100,menuDisabled:true},
			{id:'role_desc',header:'说明',dataIndex:'role_desc',sortable:false,menuDisabled:true},
			{id:'flag',header:'Flag',dataIndex:'flag',hidden:true,renderer:function(value,cellMeta,record,rowIndex,columnIndex,store){
				if(value==true){
					checkBox.selectRow(rowIndex,true);			//设置某行为选中,同时保持以前的选中状态
					return true;
				}else{
					return false;
				}
			}}
		]
	});
	

	
	
	roleWin = new Ext.Window({
		title:'角色',
		width:360,
		height:200,
		autoHeight:true,
		modal:true,
		closable:false,
		items:[roleGrid],
		buttons:[
		{
			text:'确定',
			handler:function(){
				var array = roleGrid.getSelectionModel().getSelections();
//				//alert(array.length);
//				if(array.length==0){
//					return;
//				}
				var roleIds = "";
				for(var i=0;i<array.length;i++){
					var rec = array[i];
					if(roleIds==""){
						roleIds=rec.get("role_id");
					}else{
						roleIds=roleIds+","+rec.get("role_id");
					}
				}
				//alert(roleIds);
				var rc = teacGrid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url:path+'teac!saveRole.action',
					method:'post',
					params:{teac_id:rc.get('teacId'),role_ids:roleIds},
					success:function(res,opt){
						var r = Ext.util.JSON.decode(res.responseText);
						if(r.success==true){
							Ext.Msg.alert('提示','设置成功!',function(){
								roleWin.hide();
							});
						}else{
							Ext.Msg.alert('提示','设置不成功!');
						}
					},
					failure:function(res,opt){
						Ext.Msg.alert('提示','服务访问失败');
					}
				});
			}
		},
		{
			text:'关闭',
			handler:function(){
				roleWin.hide();
			}
		}
		],
		listeners:{
			show:function(){
				var rc = teacGrid.getSelectionModel().getSelected();
				jsonRole.reload({params:{teacId:rc.get('teacId')}});
			}
		}
	});
	
});