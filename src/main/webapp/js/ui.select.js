
$(function(){
	
	var els = $("select[data-value]");
	
	for(var i=0;i<els.size();i++) {
		var el =els.eq(i);
		el.val(el.attr("data-value"));
	}
	
});
