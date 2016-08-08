
(function($){

	var cfg = {
		cls : {
			ok : 'icon-ok ui-green',
			fail : 'icon-info-circled ui-red'
		},
		query : '>span.ui-icon'
	};
	
	var fn = function(e){
		
		var self = $(this);
		var el = $(cfg.query,self.parent());
		
		var regexp = self.attr("autovalidate-regexp");
		var required = self.attr("autovalidate-required");
		var v = self.val();
		
		el.removeClass(cfg.cls.fail).removeClass(cfg.cls.ok);
		
		if(required) {
			if(v) {
				el.addClass(cfg.cls.ok);
			}
			else{
				el.addClass(cfg.cls.fail);
				return;
			}
		}
		
		if(regexp){
			
			var exp = new RegExp(regexp);
			var mv = v.match(exp);
			
			if(mv != v){
				el.removeClass(cfg.cls.ok).addClass(cfg.cls.fail);
				return ;
			}
			else {
				el.removeClass(cfg.cls.fail).addClass(cfg.cls.ok);
			}
		}
		
	};
	
	$.fn.autovalidate = function(){
		
		var q = "input[type='text'],input[type='number'],textarea";
		
		for(var i=0;i<this.size();i++){
			var el = this.eq(i);
			if(el.is(q)){
				el.bind("blur",fn);
			}
			else {
				$(q,el).bind("blur",fn);
			}
		}
		
		return this;
		
	};
	
	$.fn.clearautovalidate = function(){
		
		var q = "input[type='text'],input[type='number'],textarea";
		
		for(var i=0;i<this.size();i++){
			var el = this.eq(i);
			if(el.is(q)){
				el.removeClass(cfg.cls.fail).removeClass(cfg.cls.ok);
			}
			else {
				$(q,el).removeClass(cfg.cls.fail).removeClass(cfg.cls.ok);
			}
		}
		
		return this;
		
	};
	
	$.fn.unautovalidate = function(){
		
		var q = "input[type='text'],input[type='number'],textarea";
		
		for(var i=0;i<this.size();i++){
			var el = this.eq(i);
			if(el.is(q)){
				el.unbind("blur",fn);
			}
			else {
				$(q,el).unbind("blur",fn);
			}
		}
		
		return this;
	}
	
})($);
