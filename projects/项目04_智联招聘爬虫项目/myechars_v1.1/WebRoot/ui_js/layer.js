/*******************************
 * 遮蔽层效果，可以带进度条效果，或者可以单独支持一个弹出窗体效果
 *******************************/
(function($){
    $.fn.layer = function(cFrame,frameOnly){
    	var layerBean = {layer:false,frame:false};
        var isIE = (document.all) ? true : false;
        var isIE6 = isIE && !window.XMLHttpRequest;
        var position = !isIE6 ? "fixed" : "absolute";
        var layer=jQuery("<div>");
        layer.css({"width":"100%","height":"100%","position":position,"top":"0px","left":"0px","background-color":"#000","z-index":"9998","opacity":"0.6"});
        var frame = false;
        if (cFrame){
        	frame = cFrame.dialog;
        }else{
        	frame = $("<div>");
        	frame.html("<div id='caseBlanche'><div id='rond'><div id='test'></div></div><div id='load'><p>loading...</p></div></div>");
            frame.css({"padding-left":document.documentElement.clientWidth/2-180,"padding-top":document.documentElement.clientHeight/2-100});
        }
        frame.css({"z-index":9999});
        jQuery("body").append(frame);
        if (!frameOnly){
        	jQuery("body").append(layer);
        	layerBean.layer=layer;
        }
        
        layerBean.frame=frame;
        function layer_iestyle(){
            var maxWidth = Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px";
            var maxHeight = Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";
            layer.css({"width" : maxWidth , "height" : maxHeight });
        }
        function containerBox_iestyle(){
            var marginTop = jQuery(document).scrollTop - containerBox.height()/ 2 + "px";
            var marginLeft = jQuery(document).scrollLeft - containerBox.width()/ 2 + "px";
            containerBox.css({"margin-top" : marginTop , "margin-left" : marginLeft });
        }
        if(isIE){
            layer.css("filter","alpha(opacity=60)");
        }
        if(isIE6){
            layer_iestyle();
            containerBox_iestyle();
        }
        jQuery("window").resize(function(){
            layer_iestyle();
        });
        return layerBean;
    };
})(jQuery);