$(function(){
	$.tip = function(message,tip,duration){
		if(duration === undefined){
			duration = 3000;
		}
		if(tip == true){
			var div = $("<div class='tip tip_ok'><em></em></div>");
			}else{
				var div = $("<div class='tip'><em></em></div>");
				}
		
		div.children("em").text(message);
		div.appendTo(document.body).show('fast');
		window.setTimeout(function(){
			div.animate({"height":"0px","opacity":0},'normal',null,function(){
				div.remove();
			});
		},duration);
		
		 
	};
});