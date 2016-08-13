/*fh-top*/
$(function($){
   /* var gotop = $('<a  class="returnTop" href="javascript:;"></a>').hide().appendTo(document.body);
    var visiable = false;
    gotop.bind('click',function(){
        $('html,body').animate({
            scrollTop : 0
        });
        return false;
    });
    $(window).scroll(function(){
        var top = $(window).scrollTop();
        if(top > 10){
            if(!visiable){
                gotop.show();
                visiable = 1;
            }
        }else{
            if(visiable){
                gotop.hide();
                visiable = 0;
            }
        }
    });*/
});
/*
local
 */


var showProject = function(url){
		url = encodeURIComponent(url);
		var page = Number($('#more_project').attr('page'));
		if($('#more_project').attr("loaded") != 'true'){
			$.ajax({
				   type: "GET",
				   url: '/ajax/getProject/',
				   data: "api="+url,
				   success: function(msg){
					   if(msg){
						   
						   $('.m_product').append(msg);
						   $('#more_project').attr('page',page+1);
						   $('#more_project').removeAttr("loaded");
						   $('#more_project').text('加载更多');
						   
					   }else{
						   $('#more_project').text('到底了');
						   $('#more_project').attr("loaded",'true');
						   
					   }
				    
				   }
				});
			
		}
};
$(function() {
	
    var dd = $('#dd') ;

    dd.click(function() {
	
		if($(this).attr('loading') == 1){
			$('.m_local_zz').show();
        	$('.dropdown').show();
			$(this).attr('loading','0');
			$('.slider').hide();
			$('span',$(this)).addClass('active');
			
			
			
		}else{
			$(this).attr('loading','1');
			$('.m_local_zz').hide();
        	$('.dropdown').hide();
			$('.slider').show();
			$('span',$(this)).removeClass('active');
		
			}
        // all dropdowns
        
    });
	
	

});