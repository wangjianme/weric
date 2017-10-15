Ext.onReady(function(){
	var control = new Ext.BoxComponent({
		el:'control',
		region:'center'
	});
	eastPanel = new Ext.Panel({
		region:'east',
		title:'控制台',
		width:150,
		maxWidth:180,
		split:true,
		layout:'border',
		collapsible:true,
		items:[control]
	});
	var center = new Ext.BoxComponent({
		el:'examMain',
		region:'center'
	}); 
	
	var view = new Ext.Viewport({
		layout:'border',
		items:[eastPanel,center]
	});
});