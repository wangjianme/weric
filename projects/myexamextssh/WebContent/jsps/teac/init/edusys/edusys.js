Ext.onReady(function(){
	var edusysStore = new Ext.data.JsonStore({
		url:path+'edusys!query.action',
		root:'edusys',
		idProperty:'edusysId',
		fields:['edusysId','edusysName','edusysMonth','edusysDesc']
	});	
	edusysStore.load();
	var edusysGrid = new Ext.grid.EditorGridPanel({
		//width:400,
		//height:300,
		region:'center',
		store:edusysStore,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),//设置为单行选中模式
		autoExpandColumn:'edusysDesc',
		cm:new Ext.grid.ColumnModel({
			defaults:{menuDisabled:true},
			columns:[
			new Ext.grid.RowNumberer(),
			{id:'edusysId',header:'ID',dataIndex:'edusysId',hidden:true},
			{id:'edusysName',header:'名称',dataIndex:'edusysName',sortable:true,editor:new Ext.form.TextField()},
			{id:'edusysMonth',header:'月数',dataIndex:'edusysMonth',sortable:true,editor:new Ext.form.NumberField()},
			{id:'edusysDesc',header:'说明',dataIndex:'edusysDesc',editor:new Ext.form.TextArea({width:100,height:60})}
			]
		}),
				buttons:[
		{
			text:'增加',
			handler:function(){
				var Edusys = edusysGrid.getStore().recordType;//快速创建一个Edusys类
				var edusys = new Edusys({//创建一个空的对像
					edusysId:'',
					edusysName:'',
					edusysMonth:0,
					edusysDesc:''
				});
				edusysGrid.stopEditing(true);//停止编辑且取消已经编辑的
				edusysStore.insert(0,edusys);//插入到第一行，TODO:以下如果不设置则新增加记录如果不修改则不会在已经修改记录中
				edusys.dirty=true;			 //首先设置此记录已经为脏记录
				edusys.modified=edusys;		 //保存此记录的原始值
				if(edusysStore.getModifiedRecords().indexOf(edusys)==-1){//检查在修改中是否已经有此记录，肯定没有
					edusysStore.getModifiedRecords().push(edusys);//将此记录增加到已经修改记录集中
				}//设置脏记录完成
				edusysGrid.getSelectionModel().selectFirstRow();
				edusysGrid.startEditing(0,2);//开始编辑指定的行和列
			}
		},
		{
			text:'保存',
			handler:function(){
				edusysGrid.stopEditing();
				var arr = edusysStore.getModifiedRecords();
				if(arr=="" || arr.length<=0){
					alert("没有修改数据");
					return;
				}
				
				//以下为了能创建带有阴塞的对话框所以声明了很多变量，开始验证
				var msg = "";
				var boo = false;
				var celIndex = 0;
				var rowIndex = 0;
				for(var i=0;i<arr.length;i++){
					var rc = arr[i];
					if(rc.get('edusysName').trim()==''){
						msg="名称必须输入！";
						boo = true;
						celIndex = edusysGrid.getColumnModel().findColumnIndex("edusysName");
						rowIndex = edusysStore.indexOf(rc);
						break;
					}
					if(rc.get('edusysMonth')==''){
						boo = true;
						msg="月份必须输入！";
						celIndex = edusysGrid.getColumnModel().findColumnIndex("edusysMonth");
						rowIndex = edusysStore.indexOf(rc);
						break;
					}
				}
				if(boo){
					Ext.Msg.alert('提示',msg,function(){
						edusysGrid.startEditing(rowIndex,celIndex);
					});
					return;
				}
				//以下验证完成，以下开始提交数据
				var jsonArray = new Array();
				Ext.each(arr,function(ar,idx){
					jsonArray.push(ar.data);
				});
				var params = Ext.util.JSON.encode(jsonArray);
				Ext.Ajax.request({
					url:path+'edusys!save.action',
					method:'post',
					params:{json:params},
					success:function(res,opt){
						var rsu = Ext.util.JSON.decode(res.responseText);
						var rr = rsu.list;
						Ext.each(arr,function(rec,idx){
							var tmp = new Ext.data.Record(rr[idx]);
							Ext.apply(rec.data,tmp.data);//只copy里面的data比较稳定
						});
						edusysStore.commitChanges();	//通过提交所以修改的方式修改所有store中的record，然后
						edusysGrid.getView().refresh();//不要忘记刷新视图呀。。。。
					},
					failure:function(res,opt){
						Ext.Msg.alert('服务提示','操作不成功。');
					}
				
				});
			}
		},
		{
			text:'删除',
			handler:function(){
				var rec = edusysGrid.getSelectionModel().getSelected();
				if(!rec){
					Ext.Msg.alert('提示','请选择一行数据!');
					return;
				}
				if(rec.get('edusysId')==''){
					edusysStore.remove(rec);//直接在页面删除指定的记录
					return;
				}
				Ext.MessageBox.confirm('提示','确定要删除['+rec.get('edusysName')+']吗？',function(btn){
					if(btn=='yes'){
						Ext.Ajax.request({
							url:path+'edusys!del.action',
							method:'post',
							params:{edusysId:rec.get('edusysId')},
							success:function(res,opt){
								var result = Ext.util.JSON.decode(res.responseText);
								if(result.success==true){
									edusysStore.remove(rec);//删除指定的记录
								}else{
									Ext.Msg.alert('提示','操作不成功。<br/>'+result.msg);
								}
							},
							failure:function(res,opt){
								Ext.Msg.alert('提示','服务访问不成功。');
							}
						});
					}
				});
			}
		}
		]
	});
//	var edusysWin = new Ext.Window({
//		title:'学制',
//		width:410,
//		height:300,
//		closable:false,
//		autoHeight:true,
//		items:[edusysGrid],
//	});
//	edusysWin.show();
	var edusysView = new Ext.Viewport({
		layout:'border',
		items:[edusysGrid]
	});
	
	
	//以下定义ajax提交时的遮罩层
	var mask = new Ext.LoadMask(edusysView.getEl(),{msg:'数据操作中...'});
	var bf =function(){
		mask.show();
	}
	var rc =function(){
		mask.hide();
	}
	Ext.Ajax.on('beforerequest',bf);
	Ext.Ajax.on('requestcomplete',rc);
	Ext.Ajax.on('requestexception',rc);
});