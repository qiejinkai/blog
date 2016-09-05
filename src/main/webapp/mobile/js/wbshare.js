$(function(){
     	$(".wb").on('click',function(){
     		var a = $(this);
     		var domain = "http://"+window.location.host;
      		var uri = a.attr("uri");
     		var appkey = 668417419;
     		var title = a.attr("title");
     		var pic = a.attr("pic");
     		var language = "zh_cn";
     		var href = "http://service.weibo.com/share/share.php?url="+domain+uri+"&appkey="+appkey+"&title="+title+"&pic="+domain+pic+"&ralateUid="+5866776911+"&language="+language+"";
//     		alert(href);
     		a.attr("href",href);    		
     	});
     
});