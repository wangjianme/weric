Ext.onReady(function(){
	selectedForm = null;			//因为两个地方都用到了此弹出框进行查询,所以,在这儿进行一个声明,在点不同的按扭时,用的哪一个表单以给它赋值
	//用于组织框架的帮助
	var selectNode = null;						//临时变量,保存选中节点的对像
	var deptTree = new Ext.tree.TreePanel({
		rootVisible:false,
		height:300,
		autoScroll: true,
		dataUrl:path+'dept!query.action',
		root:{
			nodeType:'async',
			text:'组织框架',
			id:'ROOT'
		},
		listeners:{
			beforeload:function(node){
				deptTree.loader.dataUrl=path+'dept!query.action?deptId='+node.id;
			},
			click:function(node){
				selectNode = node;
			}
		}
	});
	deptWin = new Ext.Window({
		modal:true,
		title:'选择部门',
		width:240,
		height:300,
		closable:true,
		closeAction:'hide',
		items:[deptTree],
		buttons:[
		{
			text:'确定',
			handler:function(){
				if(selectNode){					//如果有选中节点则设置值
					selectedForm.getForm().findField('majorDept').setValue(selectNode.id);
					selectedForm.getForm().findField('deptName').setValue(selectNode.text);	
				}
				deptWin.hide();
			}
		},
		{
			text:'关闭',
			handler:function(){
				deptWin.hide();
			}		
		}
		]
	});
	
});