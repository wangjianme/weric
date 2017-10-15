Ext.onReady(function(){
	Ext.QuickTips.init();
	addExamForm = new Ext.form.FormPanel({
		frame:true,
		flex:1,
		labelSeparator:'：',
		labelWidth:100,
		labelAlign:'right',
		items:[
			{
				xtype:'textfield',
				fieldLabel:'试卷名称',
				name:'examName',
				allowBlank:false,
				width:160
			},
			{
				xtype:'panel',
				fieldLabel:'课程',
				layout:'hbox',
				items:[
					{
						xtype:'textfield',
						width:140,
						readOnly:true,
						allowBlank:false,
						name:'courName'
					},
					{
						xtype:'button',
						text:'...',
						width:20,
						handler:function(){
							courWin.show();
						}
					},
					{
						xtype:'textfield',
						name:'examCour',
						hidden:true
					}
				]
			},
			{
				xtype:'spinnerfield',
				fieldLabel:'试卷总分',
				name:'examScore',
				id:'examScore',
				allowBlank:false,
				minValue: 0,
            	maxValue:500,
            	//decimalPrecision: 1,//小数位
            	//incrementValue: 0.4,//新次增加或是减少的数字
				width:160
			},
			{
				xtype:'spinnerfield',
				fieldLabel:'及格分数线',
				name:'examPass',
				id:'examPass',
				allowBlank:false,
				minValue: 0,
				value:0,
            	maxValue:500,
            	//decimalPrecision: 1,//小数位
            	//incrementValue: 0.4,//新次增加或是减少的数字
				width:160
			},
			{
				xtype:'numberfield',
				fieldLabel:'时间长度(分钟)',
				name:'examTime',
				allowBlank:false,
				id:'examTime',
				width:160
			},
			{
				xtype:'fieldset',
				id:'cateArea',
				fieldLabel:'题型区',
				title:'题型区',
				width:600,
				height:240,
				layout:'vbox',
				border:true,
				bodyBorder:true,
				autoScroll:true,
				html:'<table id="_tb" border="0" class="tb">'+
					 '<tr><td width="10">1</td>' +
					 '<td><select name="ecCate" onChange="selectCate(this);">'+
					 '<option value="1" selected>填空题</option>'+
					 '<option value="2">判断题</option>'+
					 '<option value="3">单选题</option>'+
					 '<option value="4">多选题</option>'+
					 '<option value="5">主观题</option>'+
					 '</select>，' +
					 '别名：<input class="txt" value="填空题" type="text" name="ecCateName"/>' +
					 '，题目数量：<input style="width:30px;" value="0" type="text" class="txt" name="ecQty"/>'+
					 '，该题型总分：<input type="text" name="ecScore" value="0" class="txt" style="width:30px;"/>'+
					 '。<button class="btn" onClick="deleteCate(this)">删除</button>' +
					 '</td></tr></table>'
				,
				buttons:[
					{
						text:'增加题型',
						handler:function(){
							var tb = Ext.getDom("_tb");
							var html = tb.rows[0].cells[1].innerHTML;
							var row = tb.insertRow();
							var cell = row.insertCell();
							cell.innerText=row.rowIndex+1;
							cell = row.insertCell();
							cell.innerHTML=html;
						}
					},
					{
						text:'计算/验证总分',
						handler:function(){
							setSumScore();
						}
					}
				]
			},
			{
				xtype:'textarea',
				fieldLabel:'说明',
				width:600,
				height:60,
				name:'examNote',
				id:'examNote'
			}
		],
		buttons:[
			{
				text:'自动生成',
				handler:function(){
					var boo = setSumScore();
					if(!boo){
						return;
					}
					boo = addExamForm.getForm().isValid();
					if(boo){
						addExamForm.getForm().doAction('submit',{
							url:path+'examadd!save.action',
							method:'post',
							waitTitle:'正在保存',
							waitMsg:'正在保存，请稍候...',
							success:function(f,opt){
								if(opt.result.success==true){
									if(opt.result.msg){
										Ext.Msg.alert('提示',opt.result.msg);
									}else{
										Ext.Msg.alert('提示','试卷生成成功!');
									}
								}else{
									Ext.Msg.alert('提示','保存不成功!');
								}
							},
							failure:function(f,opt){
								Ext.Msg.alert('服务提示','保存不成功!');
							}
						});
					}
				}
			},
			{
				text:'手工生成',
				handler:function(){
					alert("开发中...");
				}
			}
		]
	});
	
	//选中一个题型时的处理
	selectCate = function(obj){
		var cell = obj.parentNode;
		//cell.children[1].value=
		var index = obj.selectedIndex;
		var option = obj.options[index];
		cell.children[1].value=option.innerText;
	}
	//删除一个数据时的处理
	deleteCate = function(obj){
		var tb = Ext.getDom("_tb");
		if(tb.rows.length<=1){
			Ext.Msg.alert('提示','至少包含一种题型');
			return;
		}else{
			var row = obj.parentNode;
			while(row.tagName!='TR'){
				row = row.parentNode;
			}
			var idx = row.rowIndex;
			tb.deleteRow(idx);
			if(idx!=tb.rows.length){	//不是最后一行
				for(var i=0;i<tb.rows.length;i++){
					tb.rows[i].cells[0].innerText=i+1;
				}
			}
		}
	}
	//计算和验证总分
	setSumScore = function(){
		var tb = Ext.getDom('_tb');
		var reg =/^\d+$/;			//正则表达式验证是否是数字
		var sum = 0;
		for(var i=0;i<tb.rows.length;i++){
			var cell = tb.rows[i].cells[1];
			var qty = cell.children[2].value;
			var boo = reg.test(qty);
			var msg = '';
			if(!boo){
				msg = '第['+tb.rows[i].cells[0].innerText+']行的['+qty+']不是有效的数字!';
				alert(msg);
				cell.children[2].focus();
				return false;
			}
			if(qty.trim()=='0'){
				msg = '第['+tb.rows[i].cells[0].innerText+']行的题目数量不能为0!';
				alert(msg);
				cell.children[2].focus();
				return false;
			}
			var score = cell.children[3].value;
			boo = reg.test(score);
			if(!boo){
				msg = '第['+tb.rows[i].cells[0].innerText+']行的['+score+']不是有效的数字!';
				alert(msg);
				cell.children[3].focus();
				return false;
			}
			if(score.trim()=='0'){
				msg = '第['+tb.rows[i].cells[0].innerText+']行的题型的分数不能为0!';
				alert(msg);
				cell.children[3].focus();
				return false;
			}
			if(score%qty!=0){
				msg = '第['+tb.rows[i].cells[0].innerText+']行的总分与数量不能整除!';
				alert(msg);
				return false;
			}
			sum = parseInt(sum)+parseInt(score);
		}
		Ext.getCmp('examScore').setValue(sum);
		if(Ext.getCmp('examPass').getValue()=="" || Ext.getCmp('examPass').getValue()==0){
			Ext.getCmp('examPass').setValue(sum*0.6);	
		}
		return true;
	}
	var addExamView = new Ext.Viewport({
		id:'addExamView',
		layout:'hbox',
		layoutConfig:{
			align:'stretch',
			pack:'start'
		},
		items:[addExamForm]
	});
	
});