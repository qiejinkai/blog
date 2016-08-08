
(function($){

	/**
	 * limit-regexp="" 正则表达式验证
	 */
	
	var fn = function(e){
		
		var self = $(this);
		
		window.setTimeout(function(){
			
			var regexp = self.attr("limit-regexp");
			var length = self.attr("limit-length");
			var format = self.attr("limit-format");
			var v = self.val();
			
			if(regexp){
				var exp = new RegExp(regexp,"g");
				v = v.match(exp).join('');
			}
			
			if(format) {

				var l0 = v.length;
				var l1 = format.length;
				var i0 = 0;
				var i1 = 0;
				var vv = '';
				
				while(i1 < l1 || i0 < l0) {

					if(i1 >= l1 || format.charAt(i1) == '#'){
						if(i0 < l0) {
							vv = vv + v.charAt(i0);
							i0 ++;
						}
						else {
							break;
						}
						i1 ++;
					}
					else if(i0 < l0){
						
						if(v.charAt(i0) == format.charAt(i1)) {
							i0 ++;
						}
						
						vv = vv + format.charAt(i1);
						i1 ++;
					}
					else {
						break;
					}
				}
				
				v = vv;
				
			}
			
			if(length){
				
				length = parseInt(length);
				
				var l = v.length;
				
				for(var i=0;i<l;i++){
					var code = v.charCodeAt(i);
					if(code < 128){
						if( length - 1 < 0 ){
							v = v.substr(0,i);
							break;
						}
						length -= 1;
					}
					else {
						
						if( length - 2 < 0 ){
							v = v.substr(0,i);
							break;
						}
						
						length -= 2;
					}
				}
				
				
			}
			
			if(v != self.val()) {
				self.val(v);
			}
			
		},0);
		
	};
	
	var bind = function(el) {
		
		if(el.attr("limit-blur")) {
			el.bind("blur",fn);
		}
		else {
			el.bind("keyup",fn).bind("change",fn);
		}
	};
	
	var unbind = function(el) {
		
		if(el.attr("limit-blur")) {
			el.unbind("blur",fn);
		}
		else {
			el.unbind("keyup",fn).unbind("change",fn);
		}
		
	};
	
	$.fn.inputlimit = function(){
		
		var q = "input[type='text'],input[type='number'],textarea";
		
		for(var i=0;i<this.size();i++){
			var el = this.eq(i);
			if(el.is(q)){
				bind(el);
			}
			else {
				var els = $(q,el);
				for(var n=0;n<els.size();n++){
					bind(els.eq(n));
				}
			}
		}
		
		return this;
		
	};
	
	$.fn.uninputlimit = function(){
		
		var q = "input[type='text'],input[type='number'],textarea";
		
		for(var i=0;i<this.size();i++){
			var el = this.eq(i);
			if(el.is(q)){
				bind(el);
			}
			else {
				var els = $(q,el);
				for(var n=0;n<els.size();n++){
					bind(els.eq(n));
				}
			}
		}
		
		return this;
	}
	
})($);
