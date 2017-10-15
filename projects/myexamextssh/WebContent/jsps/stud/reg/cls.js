Ext.onReady(function(){
	var edusysStore = new Ext.data.JsonStore({
		url:path+'studreg!queryEdusys.action',
		root:'edusys',
		idProperty:'edusysId',
		fields:['edusysId','edusysName']
	});
	edusysStore.load();
	var edusysCombo = new Ext.form.ComboBox({
		store:edusysStore,
		fieldLabel:'学制',
		mode:'remote',
		width:80,
		editable:false,
		triggerAction:'all',
		valueField:'edusysId',
		displayField:'edusysName',
		name:'edusysId',
		hiddenName:'edusysId',
		listeners:{
			select:function(ths,rec,idx){
				//alert(idx);
				var edusys = rec.get("edusysId");
				majorStore.reload({params:{edusysId:edusys}});
				majorCombo.clearValue();
			}
		}
	});
	
	var majorStore = new Ext.data.JsonStore({
		url:path+'studreg!queryMajor.action',
		root:'majors',
		idProperty:'majorId',
		fields:['majorId','majorName']
	});
	var majorCombo = new Ext.form.ComboBox({
		store:majorStore,
		fieldLabel:'专业',
		mode:'remote',
		width:80,
		editable:false,
		triggerAction:'all',
		valueField:'majorId',
		displayField:'majorName',
		name:'majorId',
		hiddenName:'majorId'
	});
	var clsName = new Ext.form.TextField({
		name:'clsName',
		width:80,
		fieldLabel:'班级名称'
	});
	
	var clsStore = new Ext.data.JsonStore({
		url:path+'studreg!queryCls.action',
		root:'cls',
		idProperty:'clsId',
		fields:['clsId','clsName','teacName']
	});
	clsStore.load();
	var clsGrid = new Ext.grid.GridPanel({
		store:clsStore,
		width:320,
		height:400,
		region:'center',
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			columns:[
				{id:'clsId',header:'ID',dataIndex:'clsId',hidden:true,hideable:false},
				{id:'clsName',header:'名称',dataIndex:'clsName'},
				{id:'teacName',header:'班主任',dataIndex:'teacName'}
			]
		}),
		autoExpandColumn:'teacName'
	});
	
	
	
	clsWin =new Ext.Window({
		modal:true,
		width:330,
		height:400,
		layout:'border',
		closeAction:'hide',
		title:'选择班级',
		items:[
			clsGrid
		],
		tbar: [
			{
				xtype: 'buttongroup',
		        columns: 1,
		        title: '班级名称',
		        items:[clsName]
			},
		   {
				xtype: 'buttongroup',
		        columns: 1,
		        title: '学制',
		        items:[edusysCombo]
			},
			{
				xtype: 'buttongroup',
		        columns: 1,
		        title: '专业',
		        items:[majorCombo]
			},
			{
				text:'查询',
				handler:function(){
					var cls = clsName.getValue();
					var edusys=edusysCombo.getValue();
					var major =majorCombo.getValue();
					clsStore.reload({params:{
						clsName:cls,
						edusysId:edusys,
						majorId:major
					}});
				}
			}
	    ],
	    buttons:[
	    	{
	    		text:'确定',
	    		handler:function(){
	    			var rc = clsGrid.getSelectionModel().getSelected();
	    			if(!rc){
	    				Ext.Msg.alert('提示','请选择一行数据!');
	    			}else{
	    				form.getForm().findField("studCls").setValue(rc.get('clsId'));
	    				form.getForm().findField("studClsName").setValue(rc.get('clsName'));
	    				clsWin.hide();
	    			}
	    		}
	    	},
	    	{
	    		text:'关闭',
	    		handler:function(){
	    			clsWin.hide();
	    		}
	    	}
	    ]
	});
		
});