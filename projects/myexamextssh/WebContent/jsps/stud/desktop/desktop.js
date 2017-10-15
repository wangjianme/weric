Ext.onReady(function(){
	toLogin = true;
	login   = function(){
		window.location.href=path+"login.action";
	}
	loginOut = function(){
		window.location.href=path+"login!loginOut.action";	
	}
	
	
	var north = new Ext.BoxComponent({
		region:'north',
		el:'header',
		cls:'headerDiv'
	});
	var menu = {
        //region: 'west',
		flex:1,
        //title: '功能区',
        xtype: 'treepanel',
        width: 150,
        autoScroll: true,
        split: true,
        border:false,
        loader: new Ext.tree.TreeLoader(),
        root: new Ext.tree.AsyncTreeNode({
            expanded: true,
            children: [{
                text: '在线考试',
                leaf: false,
                children:[
                	{
                		text:'参加考试',
                		leaf:true,
                		href:'studexam.action',
                		hrefTarget:'mainFrame'
                	}
                ]
            }, {
                text: '个人信息',
                leaf: false,
                children:[
                	{
                		text:'个人信息',
                		leaf:true,
                		href:'studdetail.action',
                		hrefTarget:'mainFrame'
                	},
                	{
                		text:'成绩查询',
                		leaf:true,
                		href:'studscore.action',
                		hrefTarget:'mainFrame'
                	},
                	{
                		text:'修改密码',
                		leaf:true,
                		href:'studrepwd.action',
                		hrefTarget:'mainFrame'
                	}
                ]
            }]
        }),
        rootVisible: false
	};

	var pic = new Ext.BoxComponent({
		height:150,
		el:'pic'
	});
	
	var west = new Ext.Panel({
		collapsible: true,
		layout:'vbox',
		region:'west',
		width:140,
		//split:true,
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[
			menu,pic
		]
	})
	
	
	
	var main = new Ext.BoxComponent({
		region:'center',
		el:'mainFrame'
	});

	var view = new Ext.Viewport({
		layout:'border',
		items:[west,main,north]
	});
});