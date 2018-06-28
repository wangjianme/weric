var curFrame = false;//当前操作窗体，全局变量
var zIndex = 1;
var preFrame = false; 
var curLayerBean = false;
//父级组件
  function Component(tagName,style){
	  this.style = style;
	  this.child=false;
	  this.target = $(tagName);
	  this.getTarget=function(){
		  return this.target;
  	  }
	  this.initStyle=function (){
		  this.target.addClass(this.style);
      }
	  this.addComp=function (comp){
		  this.child = comp;
		  this.target.append(comp.getTarget());
      }
	  this.addJQueryObject=function(obj){
		  this.target.append(obj);
	  }
	  this.addDOMObject=function(obj){
		  this.target.get(0).appendChild(obj);
	  }
	  this.getDOMTarget=function(){
		  return this.target.get(0);
	  }
	  this.remove=function (){
		  this.target.remove();
	  }
	  this.addHTML=function (html){
		  this.getTarget().html("");
    	  this.getTarget().html(html);
      }
	  this.appendHTML=function (html){
    	  this.getTarget().html(this.getTarget().html()+html);
      }
	  this.removeComp=function(){
		  this.child.remove();
	  }
	  this.initStyle();
  }
  //文本框 A  B 
  function JContainer(containerStyle){
      this.containerStyle = containerStyle ? containerStyle:"uidialog-uitable-container";
      Component.call(this,"<div>",this.containerStyle);
      this.toString=function(){
    	  return this.target.html();
      }
  }
  //表格组件
  function JTable(json,tableStyle){
      this.tableStyle = tableStyle ? tableStyle:"uitable";
      Component.call(this,"<table>",this.tableStyle);
      this.json = json;
      this.createHead=function(){
    	  var headData = this.json.tableHead;
          var head = $("<thead>");
    	  var headTr = $("<tr>");
    	  for (var index in headData){
    		  var headTd = $("<th>");
    		  headTd.html(headData[index]);
    		  headTr.append(headTd);
          }
    	  head.append(headTr);
    	  this.target.append(head);
      }
      this.tableData = function (fn){
    	  fn(this.json.dataList,this.getDOMTarget());
      }
	  this.insertRow=function (json){
		  var pageTr = $("<tr>");
		  var td = $("<td>");
		  td.attr({colspan:"4"});
		  td.css({"text-align":"right"});
		  for (var i = 1;i < 10;i++){
			  var pageHref = $("<a>");
			  pageHref.html("["+i+"]");
			  pageHref.attr({href:"#"});
			  td.append(pageHref);
		  }
		  pageTr.append(td);
		  this.target.append(pageTr);
	  }
  	  
  }
  //容器组件
  function JContainer(containerStyle){
      this.containerStyle = containerStyle ? containerStyle:"uidialog-uitable-container";
      Component.call(this,"<div>",this.containerStyle);
      
  }
  //最小化图标
  function MinIcon(){
	  
  }
  //任务条
  function TaskBar(){
	  
  }
  /*
   * 窗体效果类
   * 参数:
   * 1.titleValue : 标题内容
   * 2.minIcon : 最小化图标对象
   * 3.styleJson : 窗体样式效果
   * 
   */
  function Frame(titleValue,parent,styleJson){
	  this.parent = parent;
	  this.onclose=false;
	  this.test = 0;
	  this.resizeble=true;
	  this.child=false;
	  this.curIndex = 0;
      this.defaultStyle={
   		  opbtnStyle:"fs13",
   		  dialogStyle:"uidialog",
   		  titlebarStyle:"uidialog-titlebar",
   		  titleStyle:"uidialog-titlebar-title",
   		  opbtnContainerStyle:"uidialog-titlebar-btncontainer",
      };
      this.borderType={
    	  right:{type:0,cursor:"col-resize",fade:1},
    	  bottom:{type:1,cursor:"row-resize",fade:1},
    	  corner:{type:2,cursor:"col-resize",fade:1},
    	  title:{type:3,cursor:"move",fade:0.6},
    	  normal:{type:-1,cursor:"auto",fade:1}
      };
      this.topFrame = false;//设定为浮动顶级窗体
      this.curBorderType = -1;
      this.minIcon = false;
      this.titleValue = titleValue;
      this.dialog = $("<div>");
	  this.titleBar = $("<div>");
	  this.title = $("<div>");
	  this.container = $("<div>");
	  this.operButtonContainer = $("<div>");
	  this.min = $("<a>");
	  this.max = $("<a>");
	  this.close = $("<a>");
	  this.width = 0;
	  this.height = 0;
	  this.x = 0;
	  this.y = 0;
	  this.xOffset = 0;
	  this.yOffset = 0;
	  this.isMove = false;
	  this.preX = 0;
	  this.preY = 0;
	  this.statu={min:false,show:true,max:false};
	  this.setResizeble=function(resizeble){
		  this.resizeble = resizeble;
	  }
	  this.init=function(){
		  this.initAttr();
		  this.initClass();
		  this.initComp();
		  this.initListener();
		  if (this.parent){
			  this.parent.curIndex = this.parent.dialog.css("z-index");
			  this.parent.dialog.css({"z-index":zIndex++});
		  }
	  }
	  this.initAttr=function (){
		  //设置属性
		  this.title.html(this.titleValue);
		  this.min.html("-");
		  this.max.html("+");
		  this.close.html("×");
		  this.min.attr({href:"#"});
		  this.max.attr({href:"#"});
		  this.close.attr({href:"#"});
	  }
	  this.initClass=function (){
		  //设置样式
		  this.min.addClass(styleJson && styleJson.opbtnStyle ? styleJson.opbtnStyle:this.defaultStyle.opbtnStyle );
		  this.max.addClass(styleJson && styleJson.opbtnStyle ? styleJson.optnStyle:this.defaultStyle.opbtnStyle );
		  this.close.addClass(styleJson && styleJson.opbtnStyle ? styleJson.optnStyle:this.defaultStyle.opbtnStyle );
		  this.dialog.addClass(styleJson && styleJson.dialogStyle ? styleJson.dialogStyle:this.defaultStyle.dialogStyle);
		  this.titleBar.addClass(styleJson && styleJson.titlebarStyle ? styleJson.titlebarStyle:this.defaultStyle.titlebarStyle);
		  this.title.addClass(styleJson && styleJson.titleStyle ? styleJson.titleStyle:this.defaultStyle.titleStyle);
		  this.operButtonContainer.addClass(styleJson && styleJson.opbtnContainerStyle ? styleJson.opbtnContainerStyle:this.defaultStyle.opbtnContainerStyle);
	  }
	  this.frameToCenter=function (){
		  this.dialog.css({"left":document.documentElement.clientWidth/2-this.dialog.css("width").replace("px","")/2});
		  this.dialog.css({"top":document.documentElement.clientHeight/2-this.dialog.css("width").replace("px","")/2});
	  }
	  this.initComp=function (){
		//添加元素
		  if (!this.topFrame){
			  this.operButtonContainer.append(this.min);
			  this.operButtonContainer.append(this.max);
		  }
		  this.operButtonContainer.append(this.close);
		  this.titleBar.append(this.title);
		  this.titleBar.append(this.operButtonContainer);
		  this.dialog.append(this.titleBar);
	  }
	  this.showFrame=function (){
		  var frame = this;
		  var taskBar = $("#taskBar");
		  var span = $("<span>");
		  span.html(this.titleValue);
		  span.addClass("selected");
		  taskBar.append(span);
		  span.click(function (){
	          if (frame == preFrame){
	        	  frame.toggleFrame();
			  }else{
				  frame.show();
				  frame.setForeground();
				  preFrame = frame;
			  }
		  });
		  this.minIcon = span;
		  $("body").append(this.dialog);
	  }
	  this.setFrameBounds=function(width,height){
		  this.dialog.css({"width":width,"height":height});
	  }
	  //添加组件
	  this.addComp=function(comp){
		  this.child = comp;
		  this.dialog.append(comp.getTarget());
	  }
	  //删除内部容器
	  this.removeComp=function(){
		  this.child.target.remove();
	  }
	  //改变窗体位置
	  this.moveTo=function (e){
		  curFrame.dialog.css({left:e.clientX-this.xOffset,top:e.clientY-this.yOffset});
	  }
	  //改变窗体宽度
	  this.changeWidth=function (e){
		  if (!this.resizeble){
			  return ;
		  }
		  if (this.getWidth() <= 150 && e.clientX < this.preX){
			  return;
		  }
		  this.preX = e.clientX;
		  this.dialog.css({width:(e.clientX-this.x+(this.width-this.xOffset))});
		  
	  }
	  //改变窗体高度
	  this.changeHeight=function (e){
		  if (!this.resizeble){
			  return ;
		  }
		  if (this.getHeight() <= 250 && e.clientY < this.preY){
			  return;
		  }
		  this.preY = e.clientY;
		  curFrame.dialog.css({height:(e.clientY-this.y+(this.height-this.yOffset))});
	  }
	  //宽度高度一起变化
	  this.changeAround=function (e){
		  this.changeWidth(e);
		  this.changeHeight(e);
	  }
	  //添加事件监听器
	  this.initListener=function (){
		  var min = this.min;
		  var dialog = this.dialog;
		  var frame = this;
		  this.dialog.mousedown(function (e){
              frame.curBorderType = frame.mouseOperaOnDiv(e);
              frame.clickStyle(frame.curBorderType);
              curFrame = frame;
              frame.setForeground();
              if (!frame.topFrame){
            	  preFrame = frame;
              }
	      }); 
		  this.dialog.mouseup(function (e){
			  var borderType = frame.borderType.normal;
			  frame.clickStyle(borderType);
	      });
		  this.dialog.dblclick(function(e){
			 if (frame.mouseOperaOnDiv(e) == frame.borderType.title && this.resizeble){
				 frame.maxFrame();
			 }
		  }); 
		  this.min.click(function (e){
			  frame.toggleFrame();
			  return false;
	  	  });
	  	  this.close.click(function (e){
	  		  if (frame.onclose){
	  			 frame.onclose();
	  		  }
	  		  frame.dispose();
	  		  return false;
		  });
		  this.max.click(function (e){
			  frame.maxFrame();
			  return false;
		  });
	  }
	  this.clickStyle=function(borderType){
		  this.dialog.css({cursor:borderType.cursor});
		  this.dialog.fadeTo(1,borderType.fade);
		  this.isMove = !this.isMove;
	  }
	  
	  this.mouseOperaOnDiv=function(e){
		  this.curLocation(e);
		  var borderType = this.borderType.normal;
		  if (this.getFrameEndX() - e.clientX <= 8 && this.getFrameEndY() - e.clientY <= 8){
			  borderType = this.borderType.corner;
		  }else if (this.getFrameEndY() - e.clientY <= 8 && this.getFrameEndX() - e.clientX > 8){
			  borderType = this.borderType.bottom;
		  }else if (this.getFrameEndX() - e.clientX <= 8 && this.getFrameEndY() - e.clientY > 8){
			  borderType = this.borderType.right;
		  }else if (e.clientY - this.y < 35){
			  borderType = this.borderType.title;
		  }
		  return borderType;
	  }
	  this.curLocation=function(e){
		  this.x = this.getFrameX();
		  this.y = this.getFrameY();
		  this.width = this.getWidth();
		  this.height = this.getHeight();
		  if (e){
			  this.xOffset = e.clientX - this.x;
	          this.yOffset = e.clientY - this.y;
		  }
		  
	  };
	  this.maxFrame=function(){
		  if (!this.statu.max){
			  this.curLocation();
		      this.dialog.animate({width:"100%",height:"100%",left:"0px",top:"0px"},100);
		      this.max.html("⿻");
		      this.statu.min=false;
		      this.statu.show=true;
			  this.statu.max=true;
		  }else{
			  this.restoreToDefault();
			  this.max.html("+");
			  this.statu.min=false;
		      this.statu.show=true;
			  this.statu.max=false;
		  }
		  
	  }
	  this.toggleFrame=function(){
	      if (!this.statu.min){
			  this.hidFrame();
		  }else{
			  this.show();
		  }
	      this.setForeground();
	  }
	  this.hidFrame=function(){
		  var div = this.dialog;
		  this.curLocation();
	      this.dialog.animate({width:"0",height:"0",left:"0",top:"700"},200,function (){
			    div.css({display:"none"});
		  });
	      this.statu.min=true;
	      this.statu.show=false;
	  }
	  this.show=function(){
		if (!this.statu.show){
			this.dialog.css({display:"block"});
			this.minIcon.addClass("selected");
			this.restore();
			this.statu.show=true;
			this.statu.min=false;
		}
	  }
	  this.restore=function(){
		  this.dialog.animate({width:this.width,height:this.height,left:this.x,top:this.y},200);
	  }
	  this.restoreToDefault=function(){
		  this.dialog.animate({width:"800px",height:"300px",left:"20%",top:"20%"},200);
	  }
	  this.dispose=function(){
		  if (this.minIcon){
			  this.minIcon.remove();
		  }
		  if (this.parent){
  			  this.parent.dialog.css({"z-index":this.parent.curIndex});
  		  }
		  this.dialog.remove();
	  }
	  this.setForeground=function(){
		  if (this.dialog.css("z-index") != 9999){
			  this.dialog.css({"z-index":zIndex++});
		  }
		  this.setMinIconSelected();
	  }
	  this.setMinIconSelected=function(){
		  if (this.minIcon){
			  if (preFrame && preFrame.minIcon){
				  preFrame.minIcon.removeClass("selected");
			  }
			  this.minIcon.addClass("selected");
		  }
		  
	  }
	  this.getFrameX=function(){
		  var x =  this.dialog.css("left").replace("px","");
		  return new Number(x);
	  }
	  this.getFrameY=function(){
		  var y =  this.dialog.css("top").replace("px","");
		  return new Number(y);
	  }
	  this.getHeight=function(){
		  var h =  this.dialog.css("height").replace("px","");
		  return new Number(h);
	  }
	  this.getWidth=function(){
		  var w =  this.dialog.css("width").replace("px","");
		  return new Number(w);
	  }
	  this.getFrameEndX=function(){
		  return this.getFrameX()+this.getWidth();
	  }
	  this.getFrameEndY=function(){
		  return this.getFrameY()+this.getHeight();
	  }
  }
  function Dialog(titleValue,parent,styleJson,fn){
	  Frame.call(this,titleValue,parent,styleJson);
	  this.initDialog=function (){
		  var frame = this;
		  this.topFrame=true;
		  this.init();
		  this.setFrameBounds(200,100);
		  this.frameToCenter();
		  this.setResizeble(false);
		  var container1 = new JContainer();
  	      var text = $("<div>");
  	      text.attr({align:"center"});
  	      text.html("操作完成");
  	      var btnDiv = $("<div>");
  	      btnDiv.attr({align:"center"});
  	      var btn = $("<input>");
  	      btn.attr({value:"确定",type:"button"});
  	      btnDiv.append(btn);
  	      btn.click(function (){
  	    	 frame.dispose();
  	    	 if (fn){
  	    		fn(frame);
  	    	 }
  	      });
  	      container1.addJQueryObject(text);
  	      container1.addJQueryObject(btnDiv);
  	      this.addComp(container1);
	  }
  }
  function AjaxCURD(){
	  this.ajaxUpdate=function (method,url,params,callBack){
		  $.ajax({
			   type: method,
			   url: url,
			   dataType:"html",
			   data:params,
			   beforeSend : function (){
				   
			   },
			   success: function(html){
				   if (callBack){
					   var frame = callBack(html);
					   if (frame){
						   preFrame = frame;
					   }
				   }
			   },
			   error : function (e){
				   alert("程序访问错误"+e);
				   
			   }
		});
	  }
	  this.ajaxQueryWithShadowDivClose=function(method,url,params,callBack){
		  var layerBean = false;
		  $.ajax({
			   type: method,
			   url: url,
			   dataType:"html",
			   data:params,
			   beforeSend : function (){
				  layerBean = $.fn.layer();
			   },
			   success: function(html){
				   var frame = callBack(html);
				   if (frame){
					   preFrame = frame;
				   }
				   layerBean.layer.remove();
				   layerBean.frame.remove();
			   },
			   error : function (e){
				   alert("错误处理"+e.printStackTrace());
				   layerBean.layer.remove();
				   layerBean.frame.remove();
			   }
		});
	  }
	  this.ajaxQueryWithShadowDiv=function(method,url,callBack){
		  var layerBean = false;
		  $.ajax({
			   type: method,
			   url: url,
			   dataType:"html",
			   beforeSend : function (){
				  layerBean = $.fn.layer();
			   },
			   success: function(html){
				   var frame = callBack(html,layerBean);
				   if (frame){
					   preFrame = frame;
				   }
			   },
			   error : function (e){
				   alert("错误处理"+e.printStackTrace());
				   layerBean.layer.remove();
				   layerBean.frame.remove();
			   }
		});
	  }
	  
	  this.ajaxQueryWithOutShadowDiv=function(method,url,callBack){
		  $.ajax({
			   type: method,
			   url: url,
			   dataType:"html",
			   success: function(html){
				   var frame = callBack(html);
				   preFrame = frame;
			   },
			   error : function (e){
				   alert("错误处理"+e.printStackTrace());
			   }
		});
	  }
  }
  function UI(){
	  this.init=function(fn){
		  $(document).ready(function (){
	    	  var isclose = true;
	  		  $("body").mousemove(function (e){
	  			  if (curFrame.isMove){
	  				  var borderType = curFrame.curBorderType.type;
		              switch (borderType){
		              case curFrame.borderType.right.type:
		            	  curFrame.changeWidth(e);
		            	  break;
		              case curFrame.borderType.bottom.type:
		            	  curFrame.changeHeight(e);
		            	  break;
		              case curFrame.borderType.corner.type:
		            	  curFrame.changeAround(e);
		            	  break;
		              case curFrame.borderType.title.type:
		            	  curFrame.moveTo(e);
		            	  break;
		              } 
	  			  }
	  			  
	  		  });
	  		  /*
	  		  $("body").mouseup(function (e){
	  			  var borderType = curFrame.borderType.normal;
	  			  curFrame.clickStyle(borderType);
	  		  });
	  		  */
			  $("#menu").click(function (){
				  if (isclose){
					  $(".navbar").animate({left:"0px"},500);
					  isclose = false;
				  }else{
					  $(".navbar").animate({left:"-180px"},500);
					  isclose = true;
				  }
			  });
			  fn();
	      });
	  }
  }
  function HashSet(){
	  this.values = [];
	  this.add=function (e){
		  
	  }
  }