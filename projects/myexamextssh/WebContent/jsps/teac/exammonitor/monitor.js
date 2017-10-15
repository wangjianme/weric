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
	
	
	var proxy = new Ext.data.HttpProxy({					//分组的必须使用这样的方式才可以显示
		url:path+'exammonitor!queryExamings.action'
	});
	var ExamingStud = new Ext.data.Record.create([			//创建一个具体类
		'infoId','infoOe','oeExam',
		'examName','examTime','examScore',
	    'infoStud','studName',
		'infoBtime','infoType','timeout'
	]);
	var examingStudReader = new Ext.data.JsonReader({		//使用Reader来读取类设置
		idProperty:'infoId',
		root:'examingStuds'
	},ExamingStud);
	
	var examsStore2 = new Ext.data.GroupingStore({			//使用GroupingStore来读取proxy和reader
		proxy:proxy,
		reader:examingStudReader,
		sortInfo:{
			field:'examName',
			direction:'ASC'
		},
		groupField:'examName'
	});
	examsStore2.load();
	
	var examsGrid = new Ext.grid.GridPanel({
		store:examsStore2,
		frame:true,
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
				{id:'infoId',header:'infoId',dataIndex:'infoId',hidden:true,hideable:false},
				{id:'infoOe',header:'infoOe',dataIndex:'infoOe',hidden:true,hideable:false},
				{id:'oeExam',header:'oeExam',dataIndex:'oeExam',hidden:true,hideable:false},
				{id:'studName',header:'学生姓名',dataIndex:'studName'},
				{id:'examName',header:'试卷名称',dataIndex:'examName'},
				{id:'infoBtime',header:'开考时间',dataIndex:'infoBtime'},
				{id:'examTime',header:'时间',dataIndex:'examTime'},
				{id:'examScore',header:'总分数',dataIndex:'examScore'},
				{id:'infoStud',header:'infoStud',dataIndex:'infoStud',hidden:true,hideable:false},
				{id:'infoType',header:'考试类型',dataIndex:'infoType',
						renderer:function(v){
							if(v==0){
								return '正常考试';
							}else{
								return '第'+v+'次补考';
							}
						}
				},
				{id:'timeout',header:'是否超时',dataIndex:'timeout',
					  renderer:function(v){
					  	if(v=='0'){
					  		return '<font color="green">考试中</font>';
					  	}else{
					  		return '<font color="red">离线超时</font>';
					  	}
					  }
				}
			]
		}),
		autoExpandColumn:'studName',
		frame:true,
		buttons:[
			{
				text:'提交<font color="red">选中</font>试卷',
				handler:function(){
					var rc = examsGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','请选中要提交的试卷！');
					}else{
						Ext.Msg.confirm('提示','确定要提交此学生的试卷吗？',function(btn){
							if(btn=='yes'){
								var infoId 		= rc.get('infoId');
								var oeId   		= rc.get('infoOe');
								var infoBtime 	= rc.get('infoBtime');
								var examTime	= rc.get('examTime');
								Ext.Ajax.request({
									url:path+'exammonitor!submitExam.action',
									method:'post',
									params:{infoId:infoId,oeId:oeId,infoBtime:infoBtime,examTime:examTime},
									success:function(resp,opts){
										var result = Ext.util.JSON.decode(resp.responseText);
										if(result.success==true){
											Ext.Msg.alert('提示','操作成功！',function(){
												examsStore2.remove(rc);	
											});
										}else{
											Ext.Msg.alert('提示','操作不成功!');
										}
									},
									failure:function(resp,opts){
										alert('操作不成功!')
									}
								});
							}
						});
					}
				}
			}
		]
	});
	
	var view = new Ext.Viewport({
		layout:'border',
		items:[examsGrid]
	});
});