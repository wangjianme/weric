Ext.onReady(function(){
	Ext.QuickTips.init();
	var loadMask = new Ext.LoadMask(Ext.getBody(),{msg:'正在加载，稍候...'});
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
		url:path+'scoresquery!queryScores.action'
	});
	var Score = Ext.data.Record.create([
		'infoId','infoOe','oeExam','examName',
		'infoStud','studName','studCls','clsName',
		'examCour','courName','infoScore','infoTimein',
		'infoType','infoRate','oeTeac','infoResit',
		'examPass'
	]);
	var reader = new Ext.data.JsonReader({
		root:'scores',
		idProperty:'infoId',
		totalProperty:'count'
	},Score);
	var store = new Ext.data.GroupingStore({
		proxy:proxy,
		reader:reader,
		groupField:'clsName',
		listeners:{
			beforeload:function(s,opts){
				if(Ext.getCmp('clsName')){
					var clsName 	= Ext.getCmp('clsName').getValue();
					var examName 	= Ext.getCmp('examName').getValue();
					var courName  	= Ext.getCmp('courName').getValue();
					var studName 	= Ext.getCmp('studName').getValue();
					Ext.apply(opts.params,{
						clsName:clsName,
						examName:examName,
						courName:courName,
						studName:studName
					});
				}
			}
		}
	});
	var scoreGrid = new Ext.grid.GridPanel({
		store:store,
		region:'center',
		view:new Ext.grid.GroupingView(
			{
				groupByText:'按此列分组',
				showGroupsText:'显示分组'
			}
		),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				new Ext.grid.RowNumberer(),
				{id:'infoId',header:'infoId',dataIndex:'infoId',hidden:true,hideable:false},
				{id:'clsName',header:'班级',dataIndex:'clsName'},
				{id:'examName',header:'试卷',dataIndex:'examName'},
				{id:'studName',header:'学生',dataIndex:'studName'},
				{id:'courName',header:'课程',dataIndex:'courName'},
				{id:'examPass',header:'分数线',dataIndex:'examPass',width:50},
				{id:'infoScore',header:'得分',dataIndex:'infoScore',width:50},
				{id:'oeTeac',header:'授课教师',dataIndex:'oeTeac'},
				{id:'infoRate',header:'阅卷教师',dataIndex:'infoRate'},
				{id:'infoType',header:'考试类型',dataIndex:'infoType',
					renderer:function(v){
						if(v==0){
							return '正常考试';
						}else{
							return '第'+v+'次补考';
						}
					}
				}
			]
		}),
		autoExpandColumn:'examName',
		tbar:new Ext.Toolbar({
			items:[
				'-',
				{
					xtype:'label',
					text:'班级：'
				},
				{
					xtype:'textfield',
					width:80,
					id:'clsName'
				},
				'-',
				{
					xtype:'label',
					text:'试卷：'
				},
				{
					xtype:'textfield',
					width:100,
					id:'examName'
				},
				'-',
				{
					xtype:'label',
					text:'课程：'
				},
				{
					xtype:'textfield',
					width:100,
					id:'courName'
				},
				'-',
				{
					xtype:'label',
					text:'学生：'
				},
				{
					xtype:'textfield',
					width:100,
					id:'studName'
				},
				'->',
				{
					xtype:'button',
					text:'查询',
					handler:function(){
						store.reload({params:{start:0,limit:15}});
					}
				}
			]
		}),
		bbar:new Ext.PagingToolbar({
			store:store,
			pageSize:15,
			displayInfo:true,
			displayMsg:'{0}-{1}of{2}',
			emptyMsg:'没有数据'
		}),
		buttons:[
			{
				text:'显示图示',
				handler:function(){
					//alert('以图形的方式显示分数分布。');
					var url = "/myexamreport/preview?__report=reports/scores.rptdesign&__format=html";
					var clsName = Ext.getCmp("clsName").getValue();
					if(clsName.trim()!=""){
						url = url+"&clsName="+clsName.trim();
					}
					var examName = Ext.getCmp("examName").getValue();
					if(examName.trim()!=""){
						url = url+"&examName="+examName.trim();
					}
					var courName = Ext.getCmp("courName").getValue();
					if(courName.trim()!=""){
						url = url+"&courName="+courName.trim();
					}
					url = encodeURI(url);
					alert(url);
					window.open(url);
				}
			},
			{
				text:'数据导出',					//支持两种格式，1:文本格式，2:Excel格式
				handler:function(){
					var url = "/myexamreport/preview?__report=reports/scores.rptdesign&__format=xls";
					var clsName = Ext.getCmp("clsName").getValue();
					if(clsName.trim()!=""){
						url = url+"&clsName="+clsName.trim();
					}
					var examName = Ext.getCmp("examName").getValue();
					if(examName.trim()!=""){
						url = url+"&examName="+examName.trim();
					}
					var courName = Ext.getCmp("courName").getValue();
					if(courName.trim()!=""){
						url = url+"&courName="+courName.trim();
					}
					url = encodeURI(url);
					alert(url);
					window.open(url);
				}
			}
		],
		listeners:{
			afterrender:function(){
				store.load({params:{start:0,limit:15}});
			}
		}
	});
	var view = new Ext.Viewport({
		layout:'border',
		items:[scoreGrid]
	});
});