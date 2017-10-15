Ext.onReady(function(){
	
	toLogin = true;
	login = function(){
		window.location.href=path+"admin.action";
	}
	
	
	var north = new Ext.BoxComponent({				//上面的表头
		cls:'headerDiv',
		el:'header',
		region:'north',
		height:30,
		margins : '0 0 0 0'
	});
	
	var toolbar = new Ext.Toolbar({
		items:[
			{
				text:'开始',
				menu:[
					{
						text:'关闭所有',
						handler:function(){
							var len = center.items.length;
							for(var i=len-1;i>0;i--){
								center.items.get(i).destroy();
							}
						}
					},
					{
						text:'显示主页',
						handler:function(){
							center.setActiveTab(center.items.get(0));
						}
					},
					{
						text:'刷新当前页',
						handler:function(){
							var p = center.getActiveTab();
							//alert(p.body.dom.innerHTML);
							p.body.dom.innerHTML=p.body.dom.innerHTML;
						}
					},
					'-',
					{
						text:'退出',
						handler:function(){
							window.location.href=path+'admin!loginOut.action';
						}
					}
				]
			},
			'->',
			{
				text:'刷新当前页面',
				handler:function(){
					var p = center.getActiveTab();
					p.body.dom.innerHTML=p.body.dom.innerHTML;
				}
			}
		]
	});
	
	
	var center = new Ext.TabPanel({					//中间的选项卡
		frame:true,
		region:'center',
		tbar:toolbar,
		enableTabScroll:true,
		items:[
		{
			title:'主页',
			xtype:'panel',
			frame:true,
			items:[
				
			]
		}
		],
		activeTab:0
	});
	
	var tree = new Ext.tree.TreePanel({
		id:'tree',
		flex:1,
		dataUrl:path+'menu.action',
		root:{
			nodeType:'async',
			text:'根菜单',
			id:'ROOT'
		},
		listeners:{
			beforeload:function(node){
				tree.loader.dataUrl=path+'menu.action?parentId='+node.id;
			},
			click:function(node){
				if(node.isLeaf()){
					//alert(node.id+","+node.text+","+node.attributes.url);
					var tab = center.getItem('Tab_'+node.id);
					if(!tab){
						tab = new Ext.Panel({
							id:'Tab_'+node.id,
							title:node.text,
							closable:true,
							frame:true,
							html:'<iframe frameborder="0" width="100%" height="100%" src="'+node.attributes.url+'"></iframe>'
						});
						center.add(tab);
					}
					center.setActiveTab(tab);
				}
			}
		}
	});
	var west = new Ext.Panel({
		region:'west',
		titleCollapse:true,
		collapsible:true,
		title:'功能区',
		border:true,
		width:140,
		//split:true,
		layout:'vbox',
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[tree]
	});
	
	var view = new Ext.Viewport({					//主体部分
		layout:'border',
		items:[north,center,west]	
	});
	
});