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
	
	var examStore = new Ext.data.JsonStore({
		id:'oeId',
		idProperty:'oeId',
		url:path+'studexam!queryOpenedExam.action',
		root:'exams',
		fields:['oeId','oeExam','examName','examScore','examTime',
		        'examPass','examCour','courName','oeStud','oeType',
		        'oeStud'
		]
	});
	examStore.load();
	
	var examGrid = new Ext.grid.GridPanel({
		id:'examGrid',
		store:examStore,
		renderTo:'dd',
		height:300,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		cm:new Ext.grid.ColumnModel({
			defaults:{sortable:true},
			columns:[
				new Ext.grid.RowNumberer(),
				{id:'oeId',dataIndex:'oeId',header:'oeId',hidden:true,hideable:false},
				{id:'oeExam',header:'oeExam',dataIndex:'oeExam',hidden:true,hideable:false},
				{id:'oeStud',header:'oeStud',dataIndex:'oeStud',hidden:true,hideable:false},
				{id:'examName',header:'考试名称',dataIndex:'examName'},
				{id:'examScore',header:'总分',dataIndex:'examScore'},
				{id:'examPass',header:'分数线',dataIndex:'examPass'},
				{id:'examTime',header:'考试时间',dataIndex:'examTime'},
				{id:'courName',header:'课程',dataIndex:'courName'},
				{id:'oeType',header:'考试类型',dataIndex:'oeType',
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
		buttons:[
			{
				text:'进入考试',
				handler:function(){
					var rc = examGrid.getSelectionModel().getSelected();
					if(!rc){
						Ext.Msg.alert('提示','选择要参加的考试!');
					}else{
						var html = '试卷名称：'+rc.get('examName')+'<br/>'+
								   '总分：'+rc.get('examScore')+'<br/>'+
								   '考试时间：'+rc.get('examTime')+'<br/>'+
								   '<font color="red">确定进入考试吗？考试开始将进入倒计时状态！</font>';
						
						Ext.Msg.show({
							modal:true,
							title:'提示',
							width:400,
							msg:html,
							buttons:Ext.MessageBox.YESNO,
							fn:winResult,
							icon:Ext.MessageBox.QUESTION
						});
						//window.location.href=path+"joinexam!toExaming.action?examId="+rc.get('oeExam');		//最后的参数为0，说是不是补考
					}
				}
			}
		]
	});
	
	var winResult = function(btn){
		if(btn=='yes'){
			var rc = examGrid.getSelectionModel().getSelected();
			var oeId = rc.get('oeId');
			var url = path+'studexam!beginExaming.action';
			//alert(url);
			Ext.Ajax.request({
				url:url,
				method:'post',
				params:{oeId:oeId},
				success:function(resp,opts){
					var result = Ext.util.JSON.decode(resp.responseText);
					if(result.success==true){
						if(result.examIsOver==true){					//说明考试已经完成，应该转到考试成绩页面
							var infoId = result.infoId;
							alert("infoId:"+infoId);
							window.location.href=path+"studexam!examScore.action";
						}else{
							//alert("转到考试页面。。。,直接转就行，因为所内容都在Session中");
							window.location.href=path+'studexam!toExamingPage.action';
						}
					}else{
						Ext.Msg.alert('提示','操作不成功!');
					}
				},
				failure:function(resp,opts){
					alert("操作不成功!")
				}
			});
		}
	}
	
});