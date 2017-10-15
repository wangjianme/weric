//给指定的用户分配功能
Ext.onReady(function(){
	//分配功能权限
	menuTree = new Ext.tree.TreePanel({
		dataUrl:path+'func!query.action',
		//renderTo:'funcDiv1',
		//rootVisible: false,
		height:270,
		autoScroll:true,
        frame: true,
		root:{
			nodeType:'async',
			text:'根菜单',
			id:'ROOT'
		},
		listeners:{
			beforeload:function(node){
				var rec = roleGrid.getSelectionModel().getSelected();
				var roleId=rec.get("role_id");
				var url=path+'func!query.action?parentId='+node.id+'&roleId='+roleId;
				menuTree.loader.dataUrl=url;
			}
			/**,
			checkchange:function(node,checked){
				if(node.isLeaf()){
					checkParent(node,checked);
				}else{
					checkChildren(node,checked);
				}
			}
			*/
		}
		
	});
	
	var nodeCheckChange = function(node,checked){
		menuTree.removeListener('checkchange',nodeCheckChange);					//取消绑定
		checkChildren(node,checked);
		checkParent(node,checked);
	}
	menuTree.addListener('checkchange',nodeCheckChange);						//必须这样,才可以通过removeListener方式解除
	
	
	
	funcWin = new Ext.Window({
		modal:true,
		title:'功能',
		width:300,
		height:350,
		closable:false,
		items:[menuTree],
		//autoLoad:{url:path+'func!init.action',scripts:true,nocache:true},
		//html:'',
		buttons:[
		{
			text:'保存',
			handler:function(){
				var rec = roleGrid.getSelectionModel().getSelected();
				var roleId=rec.get("role_id");
				var array = menuTree.getChecked("id");					//只需要id字段
				//alert(array);
				Ext.Ajax.request({
					url:path+'func!save.action',
					method:'post',
					params:{roleId:roleId,menuIds:array},
					success:function(res,op){
						var result = Ext.util.JSON.decode(res.responseText);
						if(result.success==true){
							Ext.Msg.alert("提示","设置成功!",function(){
								funcWin.hide();
							});
						}else{
							Ext.Msg.alert("提示","保存<font color='red'>不</font>成功!");
						}
					},
					failure:function(res,op){
						alert("failure");
					}
				});
			}
		},
		{
			text:'关闭',
			handler:function(){
				funcWin.hide();
			}
		}
		],
		listeners:{
			show:function(){
				menuTree.getRootNode().reload();
				menuTree.expandAll();
			}
		}
	});
	
	
	//选中所有的子节点
	var checkChildren = function(node,checked){
		if(node.isLeaf()){						//如果是子节点,则直接返回
			return;
		}
		if(checked){
			var len = node.childNodes.length;
			for(var i=0;i<len;i++){
				if(node.childNodes[i].attributes.checked==false){
				node.childNodes[i].ui.toggleCheck();
				}
			}
		}else{
			var len = node.childNodes.length;
			for(var i=0;i<len;i++){
				if(node.childNodes[i].attributes.checked==true){
					node.childNodes[i].ui.toggleCheck();
				}
			}
		}
	}
	
	//依次选中父节点,同时,当取消了某个子节点的选中状态后,应该判断其所有同级节点是否都为取消状态,如果是则取消父节点的选中状态,
	var checkParent = function(node,checked){
		var parentNode = node.parentNode;
		if(!parentNode || parentNode.id=='ROOT'){
			menuTree.addListener('checkchange',nodeCheckChange);					//重新绑定
			return;
		}
		if(checked){
			if(parentNode.attributes.checked==false){
				parentNode.ui.toggleCheck();
			}
		}else{					//必须判断所有子节点都没有选中的情况下下才可以取消选中状态
			var boo = false;
			var len = parentNode.childNodes.length;
			for(var i=0;i<len;i++){
				if(parentNode.childNodes[i].attributes.checked==true){
					boo = true;
					break;
				}
			}
			if(boo==false){
				if(parentNode.attributes.checked==true){
					parentNode.ui.toggleCheck();
				}
			}
		}
		checkParent(parentNode,checked);						//注意这儿有一个递归调用
	}
});