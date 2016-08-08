(function($){
	
	
	$.fn.image = function() {
		
		for(var i=0;i<this.size();i++){
			
			var el = this.eq(i);
			 var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
			(function(el){
				
				$("iframe",el).remove();
				
				var iframe =  document.createElement("iframe");
				
				iframe.style.display = 'none';
				iframe.src = 'javascript:;';
				
				$(el).append(iframe);
				
				var input = [],form;
				var del = $('.image_del',el);
				var load = $('.loading_div',el);
				var datasize = el.attr('data-size')?el.attr('data-size'):2;
				var setDocument = function(doc) {

					var user = el.attr("data-user");
					
					var action = "/image/create";
					
					doc.open();
					
					doc.write("<html><head></head><body>");
					
					doc.write("<form id='form' action='" + action +"' method='post' enctype='multipart/form-data'>");
					
					var v = el.attr("data-clips");
					
					if(v){
						doc.write("<input type='hidden' name='clips' value='"+v+"' />")
					}
					
					v = el.attr("data-width");
					
					if(v){
						doc.write("<input type='hidden' name='width' value='"+v+"' />")
					}
					
					v = el.attr("data-height");
					
					if(v){
						doc.write("<input type='hidden' name='height' value='"+v+"' />")
					}
					
					v = el.attr("data-left");
					
					if(v){
						doc.write("<input type='hidden' name='left' value='"+v+"' />")
					}
					
					v = el.attr("data-top");
					
					if(v){
						doc.write("<input type='hidden' name='top' value='"+v+"' />")
					}

					v = el.attr("data-scale");
					
					if(v){
						doc.write("<input type='hidden' name='scale' value='"+v+"' />")
					}
					
					doc.write("<input id='document' type='file' name='image' value='' />");
					
					doc.write("</form>");
					
					doc.write("</body></html>");
					
					doc.close();
					
					input = doc.getElementById("document");
					form = doc.getElementById("form");
					
					$(input).change(function(e) {
						
						var target = this;
						 var fileSize = 0;  
						 var canGet = false;
						    if (isIE && !target.files) {      
						      var filePath = target.value;  
						      if(navigator.userAgent.indexOf('MSIE 9.0')>0 || navigator.userAgent.indexOf('MSIE 8.0')>0 || navigator.userAgent.indexOf('MSIE 7.0')>0 || navigator.userAgent.indexOf('MSIE 6.0')>0){
						    	  canGet =true;  
						      }else{
						      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");         
						      var file_path = fileSystem.GetFile (filePath);      
						      fileSize = file_path.Size;   
						      }
						    } else {     
						     fileSize = target.files[0].size;      
						     }
						    if(canGet == false){
						     var fileSize = fileSize / 1024; 
						     if(datasize>=1){
						    	 msg = '文件不能大于'+datasize+'M';
						     }else{
						    	 msg = '文件不能大于'+datasize*1000+'KB';
						     }
						    }
						     if(fileSize>datasize*1024){   
						      alert(msg);   
						      return false;  
						        
						     }
						     
						     
						if($(input).val()){
							
							$(iframe).attr("loading","loading");
							load.show();
							form.submit();
							
						}
						
					});
				};
				
				setDocument(iframe.contentWindow.document);
				
				$(iframe).bind("load",function(e) {
					
					if($(iframe).attr("loading")){
						
						$(iframe).removeAttr("loading");
						
						window.setTimeout(function(){
						
							eval("var data=" + $(iframe.contentWindow.document.body).text());
							
							if(data && data["error"]){
								alert(data["error"]);
							}
							else if(data && data["url"]){
								
								var url = data["url"];
								
								//alert(url);
								
								$("input.value",el).val(url);
								$("img.view",el).attr("src",url).show();
								
								el.trigger("image",[url]);
								del.show();
								
							}
							load.hide();
							setDocument(iframe.contentWindow.document);
							
						},100);
					
					}
					
				});
				
				el.unbind('click').bind("click",function(e) {
					
					if(! $(iframe).attr("loading")){
						
						$(input).click();
					}
				});
				del.unbind('click').bind("click",function(e) {
					$("input.value",el).val('');
					$("img.view",el).attr("src",'').hide();
					del.hide();
					e.stopPropagation();
					
				});
				
			})(el);
			
		}
		
		return true;
	};
	
	$.fn.imageinput = function() {
		
		var els = $("input[type=file].ui-image",this);
		
		var vs = [];
		
		for(var i=0;i<els.size();i++){
			
			var el = els.eq(i);
			
			el.attr("type","hidden").addClass("value");
			
			var p = el.parent();
			
			var l = $("<input type='hidden' />");
			
			l.attr("name",el.attr("name"));
			
			if(el.attr("value")) {
				l.attr("value",el.attr("value"));
			}
			else {
				l.attr("value",el.attr("data-value"));
			}
			
			l.attr("vname",el.attr("vname"));
			l.addClass("value");
			
			if(l.attr("value")) {
				$("img.view",p).attr("src",l.attr("value"));
			}
			
			p.append(l);
			
			el.remove();
			
			p.image();
			
			vs.push(p[0]);
		}
		
		return $(vs);
	};
	
	$(function() {
		$(document.body).imageinput().image();
	});

})(window.jQuery || window.Zepto);
