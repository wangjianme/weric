Ext.onReady(function(){
	Ext.QuickTips.init();
	var currentNode = null;					//当前选中的节点
	var deptTree = new Ext.tree.TreePanel({
		//rootVisible:false,
		//height:320,
		//frame:true,
		//title:'组织结构',
		region:'center',
		autoScroll: true,
		useArrows: false,						//是否使用箭头
	    animate: true,							//动画效果
	    enableDD: false,						//能否拖拽
		dataUrl:path+'dept!query.action',
		root:{
			nodeType:'async',
			text:'组织结构',
			id:'ROOT'
		},
		listeners:{
			beforeload:function(node){
				deptTree.loader.dataUrl=path+'dept!query.action?deptId='+node.id;
			},
			click:function(node){
				currentNode = node;
			},
			//TODO:必须添加这样的事件,否则 不能修改图标,不知道为什么,以前3.1时可以自行修改
			expandnode:function(node){
				//alert(node.text);
				node.getUI().getIconEl().src=path+'ext/resources/images/default/tree/folder-open.gif';
			},
			collapsenode:function(node){
				node.getUI().getIconEl().src=path+'ext/resources/images/default/tree/folder.gif';
			}
		},
		buttons:[
		{
			text:'增加下级',
			handler:function(){
				if(!currentNode){
					Ext.Msg.alert('提示','请选择一个部门!');
					return;
				}
				addWin.show();
			}		
		},
		{
			text:'修改',
			handler:function(){
				if(!currentNode){
					Ext.Msg.alert('提示','请选择一个部门!');
					return;
				}
				if(currentNode.id=='ROOT'){
					Ext.Msg.alert('提示','根不能被修改!');
					return;
				}
				addWin.show(null,function(){
					deptForm.getForm().findField('deptId').setValue(currentNode.id);
					deptForm.getForm().findField('deptName').setValue(currentNode.text);
					deptForm.getForm().findField('deptDesc').setValue(currentNode.attributes.deptDesc);
				});
			}
		},
		{
			text:'删除',
			handler:function(){
				if(!currentNode){
					Ext.Msg.alert('提示','请选择一个部门!');
					return;
				}
				if(currentNode.id=='ROOT'){
					Ext.Msg.alert('提示','根不能被删除!');
					return;
				}
				if(currentNode.hasChildNodes()){
					Ext.Msg.alert('提示','请先删除子节点!',function(){
						return;
					});
				}else{
					Ext.MessageBox.confirm('提示','删除此部门会将此部门下的所有员工,<br/>自动转至此部门的上级部门!<br/>确定要删除吗?',function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({
								url:path+'dept!del.action',
								method:'post',
								params:{deptId:currentNode.id},
								success:function(res,opt){
									var r = Ext.util.JSON.decode(res.responseText);
									if(r.success==true){
										var tmpNode = currentNode.parentNode;			//在删除节点以后，为了更加人性化，应该将上级节点设置为选中状态
										tmpNode.removeChild(currentNode);
										currentNode = tmpNode;
										//alert(">>:"+currentNode.hasChildNodes());
										if(currentNode.hasChildNodes()==false){				//判断是否还有子节点
											currentNode.leaf=true;
											//TODO:使用以下方式修改图片.不知道怎么了以前3.1版本是自动修改图标,现在确要求自己手工修改
											currentNode.getUI().getIconEl().src=path+'ext/resources/images/default/tree/leaf.gif';
										}
										currentNode.select();
									}else{
										Ext.Msg.alert('提示',r.msg);
									}
								},
								failure:function(res,opt){
									alert("删除不成功..");
								}
							});
						}
					});
				}
			}
		}
		]
	});
	
	
	
//	var deptWin = new Ext.Window({
//		title:'选择部门',
//		width:440,
//		height:350,
//		closable:false,
//		items:[deptTree],
//
//	});
//	deptWin.show();
	var deptView = new Ext.Viewport({
		layout:'border',
		items:[deptTree]
	});
	
	
	
	//增加页面
	var deptForm = new Ext.form.FormPanel({
		url:path+'dept!save.action',
		method:'post',
		frame:true,
		labelSeparator:'：',
		items:[
		{
			xtype:'hidden',
			name:'deptId',
			fieldLabel:'部门ID'
		},
		{
			xtype:'textfield',
			name:'deptName',
			fieldLabel:'部门名称'
		},
		{
			xtype:'textarea',
			name:'deptDesc',
			width:120,
			height:30,
			fieldLabel:'说明'
		}
		]
	});
	var addWin = new Ext.Window({
		modal:true,
		title:'增加部门',
		width:300,
		height:200,
		autoHeight:true,
		closable:true,
		closeAction:'hide',
		items:[deptForm],
		buttons:[
		{
			text:'保存',
			handler:function(){
				deptForm.getForm().doAction('submit',{
					waitTitle:'正在保存',
					waitMsg:'正在保存,请稍候...',
					params:{deptParent:currentNode.id},
					success:function(frm,opt){
						if(opt.result.success==true){
							addWin.hide();
							if(opt.result.save==true){
								var node = new Ext.tree.TreeNode({
									id:opt.result.deptId,
									text:opt.result.text,
									desc:opt.result.desc,
									leaf:true
								});
								currentNode.appendChild(node);
								if(currentNode.isLeaf()){
									currentNode.leaf=false;
									currentNode.getUI().getIconEl().src=path+'ext/resources/images/default/tree/folder.gif';
								}
								if(!currentNode.isExpanded()){
									currentNode.expand();
								}
							}else{
								currentNode.setText(opt.result.text);
								currentNode.attributes.deptDesc=opt.result.desc;
							}
						}else{
							Ext.Msg.alert('提示','保存不成功.');
						}
					},
					failure:function(frm,opt){
						alert("访问服务器不成功.")
					}
				});
			}
		},
		{
			text:'关闭',
			handler:function(){
				addWin.hide();
			}
		}
		],
		listeners:{
			show:function(){
				deptForm.getForm().reset();
			}
		}
	});
	
});