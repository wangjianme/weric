Ext.onReady(function(){
	addOtherBtn = function(){
		var editor = Ext.getCmp('quesBody');
		var tb = editor.getToolbar();
		tb.add('-',
			   {
			   		text:'',
			   		cls : "x-btn-icon",
			   		iconCls:'input_txt',
			   		tooltip:'插入输入框',
			   		handler:function(){
			   			editor.insertAtCursor('<input name="values" style="border-left:0px;border-right:0px;border-top:0px;border-bottom:1px solid blue;background: transparent;" type="text" width="100"/>');
			   		}
			   },
			   {
			   		text:'',
			   		cls:'x-btn-icon',
			   		iconCls:'icon-uploadpic',
			   		handler:function(){
			   			imgWin.show();
			   		}
			   }
		);
		tb.doLayout();
	}
	
	//以下是上传相片的实现
	var imgFile = new Ext.ux.form.FileUploadField({
		name:'file',
		fieldLabel:'图片',
		labelSeparator:'：',
		allowBlank:false,
		width:150,
		buttonText:'选择'
	});
	var imgForm = new Ext.form.FormPanel({
		width:300,
		frame:true,
		labelWidth:60,
		fileUpload:true,
		labelAlign:'right',
		items:[imgFile]
	});
	var imgWin = new Ext.Window({
		modal:true,
		title:'上传相片',
		width:300,
		closeAction:'hide',
		items:[imgForm],
		buttons:[
			{
				text:'上传',
				handler:function(){
					var boo = imgForm.getForm().isValid();
					if(boo){
						var fileName = imgFile.getValue();
						var extName = fileName.substring(fileName.lastIndexOf('.')+1,fileName.length);
						extName = extName.toLowerCase();
						//alert(extName);
						if(extName!='jpg' && extName!='png' && extName!='bmp'){
							Ext.Msg.alert('提示','请上传jpg,png,bmp格式的文件!');
						}else{
							imgForm.getForm().doAction('submit',{
								url:path+'quesupimg.action',
								method:'post',
								success:function(f,opt){
									if(opt.result.success==true){
										if(opt.result.allow==true){
											fileName = opt.result.fileName;
											var editor = Ext.getCmp('quesBody');
											var img = '<img width=100 height=100 src="'+path+'images/ques/'+fileName+'"></img>';
											editor.insertAtCursor(img);
											imgWin.hide();
										}else{
											Ext.Msg.alert('提示','请您上传jpg,png,bmp格式的文件!');
										}
									}else{
										Ext.Msg.alert('提示','上传不成功.');
									}
								},
								failure:function(f,opt){
									Ext.Msg.alert('MSG','上传不成功.');
								}
								
								
							});
						}
					}
				}
			},
			{
				text:'关闭',
				handler:function(){
					imgWin.hide();
				}
			}
		]
	});
	
});