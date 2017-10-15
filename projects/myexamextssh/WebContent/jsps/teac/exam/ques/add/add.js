Ext.onReady(function(){
	Ext.QuickTips.init();
	
	var quesCate = new Ext.form.ComboBox({
		fieldLabel:'类型',
		mode:'local',
		triggerAction:'all',
		editable:false,
		store:new Ext.data.ArrayStore({
			idIndex:0,
			fields:['id','text'],
			data:[['1','填空题'],['2','判断题'],['3','单选题'],['4','多选题'],['5','主观题']]
		}),
		value:'1',
		valueField:'id',
		displayField:'text',
		hiddenName:'quesCate',
		name:'quesCate',
		listeners:{
			select:function(){
				addChoose();
			}
		}
	});
	
	
	
	addForm = new Ext.form.FormPanel({
		region:'center',
		frame:true,
		labelAlign:'right',
		autoScroll:true,
		labelWidth:80,
		labelSeparator:'：',
		method:'post',
		items:[
			{
				xtype:'panel',
				layout:'column',
				items:[
					{
						xtype:'panel',
						columnWidth:.3,
						labelWidth:80,
						labelAlign:'right',
						labelSeparator:'：',
						defaults:{width:100},
						layout:'form',
						items:[
							quesCate
						]
					},
					{
						xtype:'panel',
						columnWidth:.3,
						layout:'column',
						items:[
							{
								xtype:'panel',
								layout:'form',
								columnWidth:.9,
								labelWidth:80,
								labelAlign:'right',
								labelSeparator:'：',
								defaults:{width:130},
								items:[
									{
										xtype:'textfield',
										fieldLabel:'课程',
										allowBlank:false,
										name:'courName',
										readOnly:true
									}
								]
							},
							{
								xtype:'button',
								columnWidth:.1,
								text:'...',
								handler:function(){
									courWin.show();
								}
							},
							{
								xtype:'textfield',
								name:'quesCour',
								hidden:true
							}
						]
					}
				]
			},
			{
				xtype:'panel',
				layout:'column',
				items:[
					{
						xtype:'panel',
						columnWidth:.3,
						labelWidth:80,
						labelAlign:'right',
						labelSeparator:'：',
						defaults:{width:100},
						layout:'form',
						items:[
							{
								xtype:'combo',
								fieldLabel:'难度',
								mode:'local',
								triggerAction:'all',
								editable:false,
								store:new Ext.data.ArrayStore({
									idIndex:0,
									fields:['id','text'],
									data:[['0','简单'],['1','一般'],['2','难'],['3','很难']]
								}),
								value:'1',
								valueField:'id',
								displayField:'text',
								name:'quesLevel',
								//id:'quesLevel',
								hiddenName:'quesLevel'
							}
						]
					},
					{
						xtype:'panel',
						columnWidth:.3,
						layout:'column',
						items:[
							{
								xtype:'panel',
								layout:'form',
								columnWidth:.9,
								labelWidth:80,
								labelAlign:'right',
								labelSeparator:'：',
								defaults:{width:130},
								items:[
									{
										xtype:'numberfield',
										fieldLabel:'分值',
										allowBlank:false,
										allowDecimals:false,
										maxValue:100,
										minValue:1,
										name:'quesScore',
										id:'quesScore',
										value:5
									}
								]
							}
						]
					}
				]
			},
			{
				xtype:'panel',
				layout:'column',
				items:[
					{
						xtype:'panel',
						columnWidth:.8,
						labelWidth:80,
						labelAlign:'right',
						labelSeparator:'：',
						defaults:{width:380},
						layout:'form',
						items:[
							{
								xtype:'textfield',
								fieldLabel:'知识点',
								allowBlank:false,
								name:'quesTitle',
								id:'quesTitle'
							}
						]
					}
				]
			},
			{
				xtype:'htmleditor',
				fieldLabel:'题干',
				id:'quesBody',
				name:'quesBody',
				allowBlank:false,
				enableFont:false,
				enableLinks:false,
				width:600,
				height:140,
				listeners:{//在这个富文本编辑框渲染完成以后添加两个新按扭
					afterrender:function(){
						addOtherBtn();
					}
				}
			},
			{
				xtype:'textfield',
				fieldLabel:'正确答案',
				width:600,
				id:'quesSolu',
				name:'quesSolu'
			}
		],
		buttons:[
			{
				text:'保存',
				handler:function(){
					var boo = addForm.getForm().isValid();
					if(!boo){
						return;
					}
					if(Ext.getCmp('quesBody').getValue().trim()==""){
						Ext.Msg.alert('提示','题干必须输入!');
						return;
					}
					
					addForm.getForm().submit({
						url:'ques!save.action',
						method:'post',
						waitTitle:'Saving',
						waitMsg:'Please Waiting...',
						success:function(f,opts){
							if(opts.result.success==true){
								Ext.Msg.alert('提示','保存成功。',function(){
									Ext.getCmp("quesBody").reset();
									//Ext.getCmp("quesTitle").reset();
									var tb = Ext.getDom("_tb");
									if(tb){
										for(var i=tb.rows.length-1;i>=0;i--){
											tb.rows[i].cells[0].children[0].checked=false;
											tb.rows[i].cells[0].children[1].value='';
										}
									}
									Ext.getCmp("quesSolu").setValue('');
								});
							}else{
								Ext.Msg.alert('提示','保存不成功。');
							}
						},
						failure:function(f,opts){
							Ext.Msg.alert('服务提示','保存不成功。');
						}
					});
				}
			},
			{
				text:'重置',
				handler:function(){
					addForm.getForm().reset();
				}
			},
			{
				text:'返回',
				handler:function(){
					window.history.go(-1);
				}
			}
//			,
//			{
//				text:'测试添加',
//				handler:function(){
////					addForm.add({
////						xtype:'textfield',
////						fieldLabel:'Hello'
////					});
////					addForm.doLayout();
//					var len = addForm.items.length;
//					addForm.insert(len-1,{
//						xtype:'textfield',
//						fieldLabel:'Hello'+len
//					})
//					addForm.doLayout();
//				}
//			}
//			,
//			{
//				text:'测试删除',
//				handler:function(){
//					var len = addForm.items.length;
//					addForm.remove(addForm.items.get(len-2));
//					addForm.doLayout();
//				}
//			},
//			{
//				text:'根据题型添加',
//				handler:function(){
//					addChoose();	
//				}
//			}
		]
	});
	
	var addView = new Ext.Viewport({
		layout:'border',
		items:[addForm]
	});
	
	//根据题型动态添加内容
	var addChoose = function(){
		var len = addForm.items.length;
		var cate = quesCate.getValue();
		if(cate=='1'){						//填空
			var ch = Ext.get("choose");
			if(ch){
				addForm.remove('choose',true);
				addForm.doLayout();
				var solu = Ext.getCmp('quesSolu');
				solu.reset();
				solu.setReadOnly(false);
			}
		}else if(cate=='2'){			//判断
			var ch = Ext.get("choose");
			if(ch){
				addForm.remove('choose',true);
				addForm.doLayout();
				var solu = Ext.getCmp('quesSolu');
				solu.reset();
				solu.setReadOnly(false);
			}
			var choose = {
				xtype:'panel',
				title:'选项区',
				fieldLabel:'选项区',
				id:'choose',
				border:true,
				anchor:'90%',
				autoHeight:true,
				items:[
					{
						xtype:'radiogroup',
						id:'quesChoose',
						items:[
							{
								xtype:'radio',
								boxLabel:'对',
								inputValue:'1',
								name:'quesChoose',
								checked:true
							},
							{
								xtype:'radio',
								boxLabel:'错',
								inputValue:'0',
								name:'quesChoose'
							}
						],
						listeners:{
							change:function(ths,cke){
								//alert(ths.getValue().getValue());
								if(ths.getValue().getGroupValue()=='1'){
									Ext.getCmp('quesSolu').setValue('对');
								}else{
									Ext.getCmp('quesSolu').setValue('错');
								}
							}
						}
					}
				]
			};
			addForm.insert(4,choose);
			addForm.doLayout();
			var solu = Ext.getCmp('quesSolu');
			solu.reset();
			solu.setReadOnly(true);
			solu.setValue('对');
		}else if(cate=='3'){			//单选
			var ch = Ext.get("choose");
			if(ch){
				addForm.remove('choose',true);
				addForm.doLayout();
				var solu = Ext.getCmp('quesSolu');
				solu.reset();
				solu.setReadOnly(false);
			}
			var choose = {
				xtype:'panel',
				title:'选项区',
				border:true,
				fieldLabel:'选项区',
				id:'choose',
				anchor:'90%',
				bodyBorder:true,
				height:200,
				//autoHeight:true,
				autoScroll:true,
				html:'<table id="_tb" border="0" style="font-size:9pt;" width="70%">'+
					 '<tr><td><input type="radio" onClick="setValue(this)" name="_n" value="A">A、<input type="text" style="width:80%" name="values"/></td></tr>'+
					 '<tr><td><input type="radio" onClick="setValue(this)" name="_n" value="B">B、<input type="text" style="width:80%" name="values"/></td></tr>'+
					 '<tr><td><input type="radio" onClick="setValue(this)" name="_n" value="C">C、<input type="text" style="width:80%" name="values"/></td></tr>'+
					 '<tr><td><input type="radio" onClick="setValue(this)" name="_n" value="D">D、<input type="text" style="width:80%" name="values"/></td></tr></table>'
				,
				tools:[
					{
						id:'plus',
						handler:function(event,el,p,toolConfig){
							//var e = Ext.DomHelper;
							//alert(p.body.dom.all.length);
							//p.body.dom.innerHTML=p.body.dom.innerHTML+'<p><input type="radio" onClick="setValue(this)" name="_n" value="D">D、<input type="text" name="_value"/></p>';
							//Ext.DomHelper.append(p.body.dom.innerHTML,{tag:'h1',html:'hello'});
							var tb = Ext.getDom('_tb');
							if(tb.rows.length>=10){
								Ext.Msg.alert('提示','最多支持10个可选项!');
								return;
							}
							var row = tb.insertRow();
							//alert(row.rowIndex);
							var ch = String.fromCharCode(65+row.rowIndex);
							//alert(ch);
							var cell = row.insertCell();
							cell.innerHTML='<input type="radio" onClick="setValue(this)" name="_n" value="'+ch+'">'+ch+'、<input type="text" style="width:80%" name="values"/>';
						}
					},
					{
						id:'minus',
						handler:function(){
							var tb = Ext.getDom('_tb');
							var len = tb.rows.length;
							if(len==2){
								Ext.Msg.alert('提示','至少应该有两个选项.');
								return;
							}
							if(tb.rows[len-1].cells[0].children[0].checked==true){
								tb.rows[0].cells[0].children[0].checked=true;
								var solu = Ext.getCmp('quesSolu');
								solu.setValue('A');
							}
							tb.deleteRow(len-1);
						}
					}
				]	
			};
			addForm.insert(4,choose);
			addForm.doLayout();
			var solu = Ext.getCmp('quesSolu');
			solu.setValue("A");
			solu.setReadOnly(true);
		}else if(cate=='4'){			//多选
			var ch = Ext.get("choose");
			if(ch){
				addForm.remove('choose',true);
				addForm.doLayout();
				var solu = Ext.getCmp('quesSolu');
				solu.reset();
				solu.setReadOnly(false);
			}
			var choose = {
				xtype:'panel',
				title:'选项区',
				border:true,
				fieldLabel:'选项区',
				id:'choose',
				anchor:'90%',
				bodyBorder:true,
				height:200,
				//autoHeight:true,
				autoScroll:true,
				html:'<table id="_tb" border="0" style="font-size:9pt;" width="70%">'+
					 '<tr><td><input type="checkbox" onClick="setCheckValue(this)" name="_n" value="A">A、<input type="text" style="width:80%" name="values"/></td></tr>'+
					 '<tr><td><input type="checkbox" onClick="setCheckValue(this)" name="_n" value="B">B、<input type="text" style="width:80%" name="values"/></td></tr>'+
					 '<tr><td><input type="checkbox" onClick="setCheckValue(this)" name="_n" value="C">C、<input type="text" style="width:80%" name="values"/></td></tr>'+
					 '<tr><td><input type="checkbox" onClick="setCheckValue(this)" name="_n" value="D">D、<input type="text" style="width:80%" name="values"/></td></tr></table>'
				,
				tools:[
					{
						id:'plus',
						handler:function(event,el,p,toolConfig){
							//var e = Ext.DomHelper;
							//alert(p.body.dom.all.length);
							//p.body.dom.innerHTML=p.body.dom.innerHTML+'<p><input type="radio" onClick="setValue(this)" name="_n" value="D">D、<input type="text" name="_value"/></p>';
							//Ext.DomHelper.append(p.body.dom.innerHTML,{tag:'h1',html:'hello'});
							var tb = Ext.getDom('_tb');
							if(tb.rows.length>=10){
								Ext.Msg.alert('提示','最多支持10个可选项!');
								return;
							}
							var row = tb.insertRow();
							//alert(row.rowIndex);
							var ch = String.fromCharCode(65+row.rowIndex);
							//alert(ch);
							var cell = row.insertCell();
							cell.innerHTML='<input type="checkbox" onClick="setCheckValue(this)" name="_n" value="'+ch+'">'+ch+'、<input type="text" style="width:80%" name="values"/>';
						}
					},
					{
						id:'minus',
						handler:function(){
							var tb = Ext.getDom('_tb');
							var len = tb.rows.length;
							if(len==2){
								Ext.Msg.alert('提示','至少应该有两个选项.');
								return;
							}
							if(tb.rows[len-1].cells[0].children[0].checked==true){
								tb.rows[len-1].cells[0].children[0].checked=false;
								setCheckValue(tb.rows[len-1].cells[0].children[0]);//调用一下
							}
							tb.deleteRow(len-1);
						}
					}
				]	
			};
			addForm.insert(4,choose);
			addForm.doLayout();
			var solu = Ext.getCmp('quesSolu');
			solu.setValue('');
			solu.setReadOnly(true);
		}else{							//主观题
			var ch = Ext.get("choose");
			if(ch){
				addForm.remove('choose',true);
				addForm.doLayout();
				var solu = Ext.getCmp('quesSolu');
				solu.reset();
				solu.setReadOnly(false);
			}
		}
	}
	
	setValue = function(obj){
		var solu = Ext.getCmp('quesSolu');
		solu.setValue(obj.value);
	}
	setCheckValue = function(obj){
		var solu = Ext.getCmp('quesSolu');
		if(obj.checked==true){
			solu.setValue(solu.getValue()+","+obj.value);
		}else{
			var v = obj.value;
			var re = new RegExp(v,'g');
			var vv = solu.getValue();
			vv = vv.replace(re,'');
			solu.setValue(vv);
		}
		var vv = solu.getValue();
		var re = new RegExp(",,",'g');
		vv = vv.replace(re,',');
		re = new RegExp('^,');
		vv = vv.replace(re,'');
		re = new RegExp(',$')
		vv = vv.replace(re,'');
		var arryStr = vv.split(',');		//注意要进行排序
		arryStr.sort();						//使用sort方法进行排序
		solu.setValue(arryStr);
		
	}
});