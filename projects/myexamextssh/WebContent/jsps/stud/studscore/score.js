Ext.onReady(function(){
	Ext.QuickTips.init();
	var loadMask = new Ext.LoadMask(Ext.getBody(),{msg:'正在加载，请稍候...'});
	var showMask = function(){
		loadMask.show();
	}
	var hideMask = function(){
		loadMask.hide();
	}
	Ext.Ajax.on('beforerequest',showMask,this);
	Ext.Ajax.on('requestcomplete',hideMask,this);
	Ext.Ajax.on('requestexception',hideMask,this);
	
	var scoreStore = new Ext.data.JsonStore({
		url:path+'studscore!queryScores.action',
		root:'scores',
		idProperty:'infoId',
		fields:[
			'infoId','infoOe','oeExam','examName',
			'examScore','examPass','examCour','courName',
			'infoBtime','infoScore','infoRate','infoTimein',
			'infoType','infoEtime'
		]
	});
	scoreStore.load();
	var examGrid = new Ext.grid.GridPanel({
		id:'examGrid',
		store:scoreStore,
		region:'center',
		//renderTo:Ext.getBody(),
		height:300,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				new Ext.grid.RowNumberer(),
					{id:'infoId',dataIndex:'infoId',header:'infoId',hidden:true,hideable:false},
					{id:'examName',header:'试卷',dataIndex:'examName'},
					{id:'examScore',header:'总分',dataIndex:'examScore',width:50},
					{id:'examPass',header:'分数线',dataIndex:'examPass',width:50},
					{id:'infoScore',header:'考分',dataIndex:'infoScore',width:50,
						renderer:function(v,md,rc){
							if(v<rc.get('examPass')){
								return '<font color="red">'+v+'</font>';
							}else{
								return '<font color="green">'+v+'</font>';
							}
						}
					},
					{id:'courName',header:'课程',dataIndex:'courName'},
					{id:'infoRate',header:'阅卷人',dataIndex:'infoRate'},
					{id:'infoType',header:'考试类型',dataIndex:'infoType',
						renderer:function(v){
							if(v==0){
								return '正常考试';
							}else{
								return '第'+v+'次补考';
							}
						}
					},
					{id:'infoBtime',header:'考试时间',dataIndex:'infoBtime'},
					{id:'infoTimein',header:'用时',dataIndex:'infoTimein',width:50,
						renderer:function(v){
							return v+'分钟';
						}
					}
				]
		}),
		autoExpandColumn:'examName'
	});
	
	var view = new Ext.Viewport({
		layout:'border',
		items:[examGrid]
	});
});