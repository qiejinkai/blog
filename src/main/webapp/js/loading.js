
(function($){
	
	$.fn.loading = function(name,css) {
		
		for(var i=0;i<this.size();i++){
			
			var el = this.eq(i);
			
			(function(el){
				
				var v = $(".ui-loading",el);
				
				if(v.size() == 0) {
					v = $("<div class='ui-loading'><span class='i1'></span><span class='i2'></span><span class='i3'></span></div>").appendTo(el);
					if(name) {
						$(">span",v).addClass(name);
					}
					if(css) {
						v.css(css);
					}
				}
				
			})(el);
			
		}
		
		return this;
	};
	
	$.fn.unloading = function() {
		
		$(".ui-loading",this).remove();
		
		return this;
	};
	
})(jQuery);
