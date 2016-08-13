$(function(){
	$('.share_button').bind("click",function(){
		if($(this).attr('show') == 'false'){
			$(this).addClass('active').siblings('a').show();
			$(this).attr('show','true');
			$('.share_p').addClass('share_p_b');
		}else{
			$(this).removeClass('active').siblings('a').hide();
			$(this).attr('show','false');
			$('.share_p').removeClass('share_p_b');
			
			}
		
		});
	});