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
	
	var lostStudsStore = new Ext.data.JsonStore({					//查询所有已经设置的补考，但没有参加考试的
		url:path+'lostexam!queryResitStuds.action',
		root:'resitStuds',
		idProperty:'oeId',
		fields:[
			'oeId','oeExam','examName','oeStud',
			'studName','oeTeac','oeRate',
			'oeType','studCls','clsName','oeInfo'
		]
	});
	lostStudsStore.load();
	var lostStudsGrid = new Ext.grid.GridPanel({
		region:'center',
		store:lostStudsStore,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				new Ext.grid.RowNumberer(),
				{id:'oeId',header:'oeId',dataIndex:'oeId',hidden:true,hideable:false},
				{id:'oeInfo',header:'oeInfo',dataIndex:'oeInfo',hidden:true,hideable:false},
				{id:'examName',header:'考试名称',dataIndex:'examName'},
				{id:'studName',header:'学生名称',dataIndex:'studName'},
				{id:'clsName',header:'班级',dataIndex:'clsName'},
				{id:'oeTeac',header:'授课教师',dataIndex:'oeTeac'},
				{id:'oeRate',header:'阅卷教师',dataIndex:'oeRate'},
				{id:'oeType',header:'考试类型',dataIndex:'oeType',
					renderer:function(v){
						return '第'+v+'次补考';
					}
				}
			]
		}),
		autoExpandColumn:'studName',
		buttons:[
			{
				text:'显示考试不及格学生',
				tooltip:'在显示的页面上选择要补考的学生',
				tooltipType:'title',
				handler:function(){
					lostStudWin.show();
				}
			},
			{
				text:'删除',							//删除时必须确实尚没有参加考试，否则不能删除，删除时除删除lost记录外还要修改info表中的信息
				handler:function(){
					var rc = lostStudsGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','请选择要删除的记录!');
					}else{
						var oeId 	= rc.get('oeId');
						var infoId 	= rc.get('oeInfo');
						var param   = {oeId:oeId,infoId:infoId};
						param 		= Ext.util.JSON.encode(param);
						alert(param);
						Ext.Ajax.request({
							url:path+'lostexam!deleteLostStud.action',
							method:'post',
							params:{lostStuds:param},
							success:function(resp,opts){
								var result = Ext.util.JSON.decode(resp.responseText);
								if(result.success==true){
									if(result.inuse==true){
										Ext.Msg.alert('提示','学生已经参加考试，不能删除！');
									}else{
										lostStudsStore.remove(rc);
									}
								}else{
									Ext.Msg.alert('提示','删除不成功!');
								}
							},
							failure:function(resp,opts){
								alert("操作不成功!")
							}
						});
					}
				}
			}
		]
	});
	
	var view = new Ext.Viewport({
		layout:'border',
		items:[lostStudsGrid]
	});
	
	//以下显示应该参加补考的学生名单
	var lostStudStore = new Ext.data.JsonStore({
		url:path+'lostexam!queryLostStud.action',
		root:'lostStuds',
		idProperty:'infoId',
		fields:[
			'infoId','infoRate','infoStud','studName','infoScore',
			'infoType','infoOe','oeExam','examName','examScore',
			'examPass','oeTeac','oeRate','infoRate'
		]
		
	});
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	var lostStudGrid = new Ext.grid.GridPanel({
		store:lostStudStore,
		region:'center',
		sm:sm,
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				sm,
				{id:'infoId',header:'infoId',dataIndex:'infoId',hidden:true,hideable:false},
				{id:'infoStud',header:'infoStud',dataIndex:'infoStud',hidden:true,hideable:false},
				{id:'studName',header:'学生姓名',dataIndex:'studName'},
				{id:'oeExam',header:'oeExam',dataIndex:'oeExam',hidden:true,hideable:false},
				{id:'examName',header:'试卷',dataIndex:'examName'},
				{id:'infoOe',header:'infoOe',dataIndex:'infoOe',hidden:true,hideable:false},
				{id:'examScore',header:'总分',dataIndex:'examScore',width:50},
				{id:'examPass',header:'分数线',dataIndex:'examPass',width:50},
				{id:'infoScore',header:'考分',dataIndex:'infoScore',width:50},
				{id:'infoType',header:'考试类型',dataIndex:'infoType',
					renderer:function(v){
						if(v==0){
							return '正常考试';
						}else{
							return '第'+v+'次补考';
						}
					}
				},
				{id:'oeTeac',header:'授课教师',dataIndex:'oeTeac'},
				{id:'infoRate',header:'阅卷教师',dataIndex:'infoRate',
					renderer:function(v){
						if(v==''){
							return '<font color="red">未阅卷</font>';
						}else{
							return v;
						}
					}
				}		//这是实际的阅卷人
			]
		}),
		autoExpandColumn:'studName'
	});
	
	var lostStudWin = new Ext.Window({
		title:'不及格学生列表',
		modal:true,
		width:800,
		height:400,
		layout:'border',
		closeAction:'hide',
		items:[lostStudGrid],
		buttons:[
			{
				text:'添加',
				handler:function(){
					var rcs = lostStudGrid.getSelectionModel().getSelections();
					if(rcs.length==0){
						Ext.Msg.alert('提示','请选择要添加的数据行!');
					}else{
						var array 		= new Array();			//向后台提交数据
						for(var i=0;i<rcs.length;i++){
							array.push(rcs[i].data);			//只取出数据
						}
						var  rr = Ext.util.JSON.encode(array);
						Ext.Ajax.request({
							url:path+'lostexam!saveLostStuds.action',
							method:'post',
							params:{lostStuds:rr},
							success:function(resp,opts){
								var result = Ext.util.JSON.decode(resp.responseText);
								if(result.success==true){
									lostStudWin.hide();
									lostStudsStore.reload();
								}else{
									Ext.Msg.alert('提示','操作不成功!');
								}
							},
							failure:function(resp,opts){
								alert('操作不成功!');
							}
						});
					}
				}
			},
			{
				text:'关闭',
				handler:function(){
					lostStudWin.hide();
				}
			}
		],
		listeners:{
			show:function(){
				lostStudStore.reload();
			}
		}
	});
	
});