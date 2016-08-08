
(function($){
	
	$.fn.city = function(province,city){
		
		for(var i=0;i<this.size();i++){
			
			var el = this.eq(i);
			
			var provinceEl = $("select.province",el);
			var cityEl = $("select.city",el);
			var citys = window.Citys ? window.Citys : [];
			
			if( ! provinceEl.attr("loaded") ){
				for(var i=0;i<citys.length;i++){
					var c = citys[i];
					$("<option></option>").text(c.text)
						.attr("value",c.text)
						.attr("objectId",c.objectId).appendTo(provinceEl);
				}
				provinceEl.bind("change",function(e){
					el.city($(this).val());
				}).attr("loaded","loaded").trigger("datachange");
			}
			
			if(province !== undefined){
				provinceEl.val(province).trigger("datachange");
			}
			
			province = provinceEl.val();
			
			if(cityEl.attr("province") != province){
				
				$("option[objectId]",cityEl).remove();
				
				for(var i=0;i<citys.length;i++){
					var c = citys[i];
					if(c.text == province){
						var items = c["items"];
						for(var n=0;n<items.length;n++){
							var item = items[n];
							$("<option></option>").text(item.text)
								.attr("value",item.text)
								.attr("objectId",item.objectId).appendTo(cityEl);
						}
						break;
					}
					
				}
				
				cityEl.trigger("datachange");
			}
			
			if(city !== undefined){
				cityEl.val(city).trigger("datachange");
			}
			
			
		}
		
		return this;
	};
	
})($);
