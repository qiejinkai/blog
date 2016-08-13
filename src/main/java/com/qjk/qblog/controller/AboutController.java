package com.qjk.qblog.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.service.IAGroupSerivce;
import com.qjk.qblog.util.RequestUtil;

@Controller
public class AboutController {
	@Resource IAGroupSerivce groupService;
	
	@RequestMapping(value="/about",method=RequestMethod.GET)
	public String toAbout(Model model,HttpServletRequest request){
		if(RequestUtil.isMobile(request)){
			List<AGroup> groups = groupService.findGroupList();
			model.addAttribute("groups", groups);
			return "mobile/about";
		}
		
		
		return "about";
	}
}
