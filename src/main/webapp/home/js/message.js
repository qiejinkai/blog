$(function() {
	$(".ds-post-repost").bind('click', function() {
		var id = $(this).attr('data-id');
		var nick = $(this).attr('data-nick');
		$("#quoteMsg").text("引用[" + nick + "]的发言");
		$("input[name=quoteId]").val(id);

	});

	var submiting = false;

	$("#messageButton").bind('click', function() {
		if (submiting) {
			return;
		}
		var form =$("#messageForm");
		submiting = true;
		var uid = $("input[name=uid]", form).val();
		var articleId = $("input[name=articleId]", form).val();
		var quoteId = $("input[name=quoteId]", form).val();

		if (uid == null || uid == '') {
			$.tip("请先登录");
			submiting = false;
			return;
		}

		var content = $("#content").val();
		if (content == null || content == '') {
			$.tip("请输入留言内容");
			submiting = false;
			return ;
		}
		if (content.length < 5) {
			$.tip("多说点呗");
			submiting = false;
			return ;
		}
		if (content.length > 1024) {
			$.tip("内容太长了");
			submiting = false;
			return ;
		}

		var action = form.attr('action');

		$.ajax({
			url : action,
			type : 'POST',
			data : {
				json : 'json',
				articleId : articleId,
				quoteId : quoteId,
				content : content
			},
			dataType : 'json',
			success : function(res) {
				if (res.error) {
					$.tip(res.error);
					submiting = false;
					return ;
				} else {

					$.tip("发表成功", true);
					window.setTimeout(function() {
						window.location.reload();
					}, 2000);

				}
				submiting = false;
				return ;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				submiting = false;
				return ;
			}
		});

		submiting = false;
		return ;
	});
	
	$(".ds-qq").bind('click',function(){
		var uri = window.location.pathname;
		uri = "/close";
		var A=window.open("/qquser/login/?r="+uri,"TencentLogin", 
				   "width=450,height=320,menubar=0,scrollbars=1,resizable=1,status=1,titlebar=0,toolbar=0,location=1");
		
		var loop = setInterval(function() {     
		    if(A.closed) {    

				$.tip("登录成功", true);
		        window.location.reload();  
		    }    
		}, 1000); 
	
	});
	
	$(".ds-weixin").bind('click',function(){
		var uri = window.location.pathname;
		var A=window.open("/wxuser/login/?r="+uri,"WEIXINLogin", 
				   "width=450,height=320,menubar=0,scrollbars=1,resizable=1,status=1,titlebar=0,toolbar=0,location=1");
	});

});