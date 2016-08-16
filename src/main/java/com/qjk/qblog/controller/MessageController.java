package com.qjk.qblog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qjk.qblog.data.Message;
import com.qjk.qblog.data.Pager;
import com.qjk.qblog.data.User;
import com.qjk.qblog.service.IMessageService;
import com.qjk.qblog.util.RequestUtil;

@Controller
@RequestMapping(value="/message")
public class MessageController {
	
	@Resource IMessageService messageService;
	
	@RequestMapping(value="/create",method=RequestMethod.POST,params = { "json" })
	@ResponseBody
	public Object create(long articleId,String content,long quoteId,HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		Object object = request.getSession().getAttribute("user");
		if(object == null){
			result.put("error", "请先登录");
		}
		try {
			User user = (User) object;
			messageService.addMessage(content, user.getUid(), quoteId, articleId);
			result.put("success", "success");
		} catch (Exception e) {
			result.put("error", e.getMessage());
		}
		return result;
		
	}

	@RequestMapping(value={"/",""},method=RequestMethod.GET)
	public String message(Model model,HttpServletRequest request){
		
		return message(1, model, request);
	}

	@RequestMapping(value={"/{pageIndex}","/{pageIndex}"},method=RequestMethod.GET)
	public String message(@PathVariable int pageIndex , Model model,HttpServletRequest request){
		
		
		Pager<Message> messages = messageService.selectPager(0, 0, pageIndex);
		model.addAttribute("messages", messages);		
		if(RequestUtil.isMobile(request)){
			return "mobile/message";
		}
		return "message";
	}
}
