Ext.onReady(function(){
	var loadMask = new Ext.LoadMask(Ext.getBody(),'请稍候...');
	var showMask = function(){
		loadMask.show();
	}
	var hideMask = function(){
		loadMask.hide();
	}
	Ext.Ajax.on('beforerequest',showMask,this);
	Ext.Ajax.on('requestcomplete',hideMask,this);
	Ext.Ajax.on('requestexception',hideMask,this);
	
	
	var proxy = new Ext.data.HttpProxy({
		url:path+'onlinerate!queryInfos.action'
	});
	var InfoStud = Ext.data.Record.create([
		'infoId','infoOe','oeExam','examName',
		'infoStud','studName','oeState','oeTeac',
		'infoBtime','infoScore','infoRate','infoTimein',
		'infoType','infoEtime','studCls','clsName'
	]);
	var infoReader = new Ext.data.JsonReader({
		root:'infos',
		idProperty:'infoId'
	},InfoStud);
	
	var infoStore = new Ext.data.GroupingStore({
		reader:infoReader,
		proxy:proxy,
		sortInfo:{
			field:'examName',
			direction:'ASC'
		},
		groupField:'examName'
	});
	infoStore.load({params:{infoRate:'2'}});				//2是指显示还没有阅卷的记录
	
	var infoGrid = new Ext.grid.GridPanel({
		store:infoStore,
		region:'center',
		view:new Ext.grid.GroupingView(
			{
				groupByText:'按此列分组',
				showGroupsText:'显示分组'
			}
		),
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				new Ext.grid.RowNumberer(),
				{id:'infoId',header:'ID',dataIndex:'infoId',hidden:true,hideable:false},
				{id:'infoOe',header:'infoOe',dataIndex:'infoOe',hidden:true,hideable:false},
				{id:'oeExam',header:'oeExam',dataIndex:'oeExam',hidden:true,hideable:false},
				{id:'examName',header:'试卷名称',dataIndex:'examName'},
				{id:'infoStud',header:'infoStud',dataIndex:'infoStud',hidden:true,hideable:false},
				{id:'studName',header:'学生名称',dataIndex:'studName'},
				{id:'clsName',header:'班级',dataIndex:'clsName'},
				{id:'infoBtime',header:'开考时间',dataIndex:'infoBtime'},
				{id:'infoTimein',header:'用时',dataIndex:'infoTimein'},
				{id:'infoScore',header:'得分',dataIndex:'infoScore'},
				{id:'oeTeac',header:'授课教师',dataIndex:'oeTeac'},
				{id:'infoType',header:'考试类型',dataIndex:'infoType',
					renderer:function(v){
						if(v=='0'){
							return '正常';
						}else{
							return '第'+v+'次补考';
						}
					}
				}
			]
		}),
		autoExpandColumn:'studName',
		tbar:new Ext.Toolbar({
			items:[
				'-',
				{
					xtype:'label',
					text:'试卷名称：'
				},
				{
					xtype:'textfield',
					id:'examName',
					width:100
				},
				'-',
				{
					xtype:'label',
					text:'班级：'
				},
				{
					xtype:'textfield',
					id:'clsName',
					width:100
				},
				'-',
				{
					xtype:'label',
					text:'状态：'
				},
				{
					xtype:'combo',
					mode:'local',
					triggerAction:'all',
					store:new Ext.data.ArrayStore({
						idIndex:0,
						fields:['id','text'],
						data:[['2','未阅卷'],['1','已阅卷'],['0','全部']]
					}),
					value:'2',
					valueField:'id',
					displayField:'text',
					editable:false,
					width:60,
					id:'infoRate'
				},
				'->',
				{
					xtype:'button',
					text:'查询',
					handler:function(){
						var examName = Ext.getCmp('examName').getValue();
						var clsName  = Ext.getCmp('clsName').getValue();
						var infoRate = Ext.getCmp('infoRate').getValue();
						infoStore.reload({params:{examName:examName,clsName:clsName,infoRate:infoRate}});			//注意要带上参数
					}
				}
			]
		}),
		buttons:[
			{
				text:'阅卷',
				handler:function(){
					var rc = infoGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','请选择一行数据！');
						return;
					}
					var w = infoGrid.getWidth();
					var h = infoGrid.getHeight();
					w = w-50;
					h = h-40;
					rateWin.setSize(w,h);
					rateWin.show(null,function(){
						var infoId = rc.get("infoId");
						var url = path+'onlinerate!queryAnsws.action?infoId='+infoId;
						rateWin.body.dom.innerHTML='<iframe frameborder="0" scrolling="auto" width="100%" height="100%" src="'+url+'"></iframe>';
					});
				}
			}
		],
		listeners:{
			afterrender:function(){
				infoStore.reload({params:{start:0,limit:15}});
			}
		}
	});
	
	var view = new Ext.Viewport({
		layout:'border',
		items:[infoGrid]
	});
	
	//阅卷
	var rateWin = new Ext.Window({
		title:'阅卷',
		modal:true,
		closeAction:'hide',
		buttons:[
			{
				text:'上一卷',
				handler:function(){
					var boo = infoGrid.getSelectionModel().hasPrevious();
					if(boo){
						infoGrid.getSelectionModel().selectPrevious(false);
						var rc = infoGrid.getSelectionModel().getSelected();
						var infoId = rc.get("infoId");
						var url = path+'onlinerate!queryAnsws.action?infoId='+infoId;
						rateWin.body.dom.innerHTML='<iframe frameborder="0" scrolling="auto" width="100%" height="100%" src="'+url+'"></iframe>';
					}else{
						Ext.Msg.alert('提示','已经是第一卷!');
					}
				}
			},
			{
				text:'下一卷',
				handler:function(){
					var boo = infoGrid.getSelectionModel().hasNext();
					if(boo){
						infoGrid.getSelectionModel().selectNext(false);
						var rc = infoGrid.getSelectionModel().getSelected();
						var infoId = rc.get("infoId");
						var url = path+'onlinerate!queryAnsws.action?infoId='+infoId;
						rateWin.body.dom.innerHTML='<iframe frameborder="0" scrolling="auto" width="100%" height="100%" src="'+url+'"></iframe>';
					}else{
						Ext.Msg.alert('提示','已经是最后一卷!');
					}
				}
			},
			{
				text:'关闭',
				handler:function(){
					rateWin.hide();
				}
			}
		]
	});
	//是否可以调用
	updateScore = function(sum){
		var rc = infoGrid.getSelectionModel().getSelected();
		rc.set('infoScore',sum);
	}
	
	
});