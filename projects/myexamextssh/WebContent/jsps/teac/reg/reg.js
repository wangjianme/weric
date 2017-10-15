Ext.onReady(function(){
	Ext.QuickTips.init();
	var name = new Ext.form.TextField({
		name:'teacName',
		fieldLabel:'姓名',
		allowBlank:false,
		blankMsg:'姓名不能为空',
		listeners:{
			blur:function(){
				Ext.Ajax.request({
					url:path+'teacReg!valid.action',
					method:'post',
					params:{teacName:name.getValue().trim()},
					success:function(res,op){
						var res = Ext.util.JSON.decode(res.responseText);
						if(res.success==true){
							Ext.MessageBox.alert("MSG","此用用户名已经注册过,请更换一个.",function(){
								name.setValue('');								
							});
						}
					}
				});
			}
		}
	});
	
	var pwd = new Ext.form.TextField({
		name:'teacPwd',
		fieldLabel:'密码第一次',
		allowBlank:false,
		blankMsg:'密码不能为空',
		inputType:'password'
	});
	var rePwd = new Ext.form.TextField({
		name:'reTeacPwd',
		fieldLabel:'密码第二次',
		allowBlank:false,
		inputType:'password'
	});
	
	var sex = new Ext.form.RadioGroup({
		name:'teacSex',
		fieldLabel:'性别',
		width:150,
		items:[
		{
			name:'teacSex',
			boxLabel:'男',
			width:50,
			checked:true,
			inputValue:'1'
		},
		{
			name:'teacSex',
			boxLabel:'女',
			width:50,
			inputValue:'0'
		}
		]
	});
	var mail = new Ext.form.TextField({
		name:'teacEmail',
		fieldLabel:'E-mail'
	});
	var phone = new Ext.form.NumberField({
		name:'teacTel',
		width:150,
		fieldLabel:'电话'
	});
	
	var panel = new Ext.Panel({
		fieldLabel:'部门',
		layout:'column',
		items:[
		{
			id:'teacDeptName',
			xtype:'textfield',
			readOnly:true
		},
		{
			xtype:'button',
			text:'...',
			handler:function(){
				deptWin.show();
			}
		},
		{
			id:'teacDept',
			xtype:'hidden'
		}
		]
	});
	
	
	//表单
	var form = new Ext.form.FormPanel({
		id:'form1',
		labelSeparator:'：',
		labelAlign:'right',
		frame:true,
		url:path+'teacReg!reg.action',
		method:'post',
		items:[name,pwd,rePwd,sex,mail,phone,panel,
			
			{
				xtype:'textfield',
				name:'teacQues',
				fieldLabel:'密码提示问题 ',
				allowBlank:false
			},
			{
				xtype:'textfield',
				name:'teacAnswer',
				fieldLabel:'提示问题答案',
				allowBlank:false
			}
		]
	});

	//显示
	var win = new Ext.Window({
		title:'教师注册',
		width:400,
		height:300,
		autoHeight:true,
		items:[form],
		closable:false,
		buttons:[
		{
			text:'注册',
			handler:function(){
				var boo = form.getForm().isValid();
				if(!boo){
					return;
				}
				if(pwd.getValue().trim()!=rePwd.getValue().trim()){
					Ext.Msg.alert("MSG","两次密码输入不一致!");
					return;
				}
				form.getForm().doAction("submit",{
					success:function(f,op){
						if(op.result.success==true){
							Ext.Msg.alert("MSG","注册成功.<br/>现在转向登录页面!",function(){
								window.location.href=path+'admin.action';
							});
						}else{
							Ext.Msg.alert("MSG","注册不成功.");						
						}
					},
					failure:function(f,op){
						alert("DD::"+op.failureType+','+op.type+','+op.result);
					}
				});
			}
		},
		{
			text:'重置',
			handler:function(){
				
			}
		}
		]
	});
	win.show();
	
	//用于组织框架的帮助
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
				Ext.getCmp("teacDept").setValue(node.id);
				Ext.getCmp("teacDeptName").setValue(node.text);
			}
		}
	});
	var deptWin = new Ext.Window({
		modal:true,
		title:'选择部门',
		width:240,
		height:300,
		closable:false,
		items:[deptTree],
		buttons:[
		{
			text:'确定',
			handler:function(){
				deptWin.hide();
			}
		},
		{
			text:'关闭',
			handler:function(){
				Ext.getCmp("teacDept").setValue('');
				Ext.getCmp("teacDeptName").setValue('');
				deptWin.hide();
			}		
		}
		]
	});
	
});