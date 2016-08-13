package com.qjk.qblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qjk.qblog.data.AGroup;
import com.qjk.qblog.service.IAGroupSerivce;

@Controller
@RequestMapping("/group")
public class GroupController {

	@Resource
	IAGroupSerivce groupSerivce;
	
	@RequestMapping(value={"/list","/list/"},method=RequestMethod.POST,params="json")
	@ResponseBody
	public Object list(){
		
//		System.out.println("调用我啦");
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			List<AGroup> groups = groupSerivce.findGroupList();
			result.put("groups", groups);
		} catch (Exception e) {
			result.put("error",e.getMessage());
		}		
		return result;
	}
}
