Ext.onReady(function(){
	var loadMask = new Ext.LoadMask(Ext.getBody(),{msg:'正在处理，请稍候...'});
	var showMask = function(){
		loadMask.show();
	}
	var hideMask = function(){
		loadMask.hide();
	}
	Ext.Ajax.on('beforerequest',showMask,this);
	Ext.Ajax.on('requestcomplete',hideMask,this);
	Ext.Ajax.on('requestexception',hideMask,this);
	
	var examStore = new Ext.data.JsonStore({
		id:'examId',
		url:path+'examset!queryExam.action',
		idProperty:'examId',
		root:'exams',
		totalProperty:'count',
		fields:['examId','examName','examTime','examCour','examScore','courName','examState','examPass']
	});
	examStore.load({params:{start:0,limit:10}});
	
	
	
	var examGrid = new Ext.grid.EditorGridPanel({
		flex:1,
		frame:true,
		id:'examGrid',
		store:examStore,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				new Ext.grid.RowNumberer(),
				{id:'examId',header:'ID',dataIndex:'examId',hidden:true,hideable:false},
				{id:'examName',header:'试卷名称',dataIndex:'examName'},
				{id:'examTime',header:'时间',dataIndex:'examTime'},
				{id:'examCour',header:'cour',dataIndex:'examCour',hidden:true,hideable:false},
				{id:'courName',header:'课程名称',dataIndex:'courName'},
				{id:'examScore',header:'总分',dataIndex:'examScore'},
				{id:'examPass',header:'及格线',dataIndex:'examPass'},
				{id:'examState',header:'状态',dataIndex:'examState',
					renderer:function(v){
						if(v=='0'){
							return '关闭';
						}else{
							return '正常';
						}
					
					},
					editor:new Ext.form.ComboBox({
						mode:'local',
						triggerAction:'all',
						editable:false,
						store:new Ext.data.ArrayStore({
							idIndex:0,
							fields:['id','text'],
							data:[['1','正常'],['0','关闭']]
						}),
						displayField:'text',
						valueField:'id',
						listeners:{
							select:function(combo){
								var rc = examGrid.getSelectionModel().getSelected();
								if(rc.get('examState')!=combo.getValue()){
									var oldVal = rc.get('examState');
									var examId 		= rc.get('examId');
									var examState 	= combo.getValue();
									Ext.Ajax.request({
										url:path+'examset!updateState.action',
										method:'post',
										params:{examId:examId,examState:examState},
										success:function(resp,opts){
											var result = Ext.util.JSON.decode(resp.responseText);
											if(result.success!=true){				//如果修改不成功则设置成原来的值
												rc.set('examState',oldVal);
											}
										},
										failure:function(resp,opts){
											alert('操作不成功.');
										}
									});
								}
								examGrid.stopEditing();							//停止编辑并显示为生效
							}
						}
					})
				}
			]
		}),
		autoExpandColumn:'examName',
		bbar:new Ext.PagingToolbar({
			store:examStore,
			pageSize:10,
			displayInfo:true,
			displayMsg:'{0}-{1}共{2}',
			emptyMsg:'没有数据'
		}),
		buttons:[
			{
				text:'增加新试卷',
				handler:function(){
					window.location.href=path+'examset!toAdd.action';
				}
			},
			{
				text:'删除',
				handler:function(){
					var rc = examGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','请选择要删除的记录!');
					}else{
						var examId = rc.get('examId');
						Ext.Ajax.request({
							method:'post',
							url:path+'examset!delExam.action',
							params:{examId:examId},
							success:function(resp,opts){
								var result = Ext.util.JSON.decode(resp.responseText);
								if(result.success==true){
									if(result.candel==false){
										Ext.Msg.alert('提示','已经安排考试，不能删除！');
									}else{
										examStore.remove(rc);
									}
								}else{
									alert('删除不成功');
								}
							},
							failure:function(resp,opts){
								alert('操作不成功');
							}
						});
					}
				}
			},
			{
				text:'预览',
				handler:function(){
					var rc = examGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','请选择一行数据!');
					}else{
						var	vv = Ext.getDom('_examId');
						vv.value=rc.get('examId');
						Ext.getDom('viewForm').submit();
					}
				}
			}
		]
	});
	var examView = new Ext.Viewport({
		id:'examView',
		layout:'vbox',
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[examGrid]
	});
	//修改状态的小窗口
	var stateForm = new Ext.form.FormPanel({
		url:path+'',
		method:'post'
		
	});
});