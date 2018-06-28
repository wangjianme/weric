suspendcode="<DIV id=lovexin1 style='Z-INDEX: 10; LEFT: 6px; POSITION: absolute; TOP: 105px; width: 140; height: 145px;'><img src='images/close.gif' onClick='javascript:window.hide()' width='100' height='14' border='0' vspace='3'><a href='News/ShowInfo.aspx?CategoryID=15&ID=79' target='_blank'><img src='images/fudong.jpg' width='140' height='131' border='0'></a></DIV>"
document.write(suspendcode);

//flash格式调用方法
//<EMBED src='flash.swf' quality=high  WIDTH=100 HEIGHT=300 TYPE='application/x-shockwave-flash' id=ad wmode=opaque></EMBED>

lastScrollY=0;
function heartBeat(){
diffY=document.body.scrollTop;
percent=.3*(diffY-lastScrollY);
if(percent>0)percent=Math.ceil(percent);
else percent=Math.floor(percent);
document.all.lovexin1.style.pixelTop+=percent;
lastScrollY=lastScrollY+percent;
}
function hide()  
{   
lovexin1.style.visibility="hidden"; 
}
window.setInterval("heartBeat()",1);
