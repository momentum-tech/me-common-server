package com.mmnttech.me.common.server.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmnttech.me.common.server.common.entity.QueryEntity;
import com.mmnttech.me.common.server.common.entity.RtnMessage;
import com.mmnttech.me.common.server.service.CommonService;

@Controller
public class CommonController {

	private Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private CommonService commonService;
	
	@ResponseBody
	@RequestMapping(value = "queryAreaCodeLst")
	public RtnMessage queryAreaCodeLst(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("queryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			List<Map<String, Object>> records = commonService.queryAreaCodeLst(queryEntity);
			rtnMsg.setRtnObj(records);
		} catch (Exception e) {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("查询区域异常: 请稍后再试");
			logger.error("queryAreaCodeLst异常" + e);
		}
		
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryIndustryCodeLst")
	public RtnMessage queryIndustryCodeLst(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("queryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			List<Map<String, Object>> records = commonService.queryIndustryCodeLst(queryEntity);
			rtnMsg.setRtnObj(records);
		} catch(Exception e) {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("查询行业异常: 请稍后再试");
			logger.error("queryIndustryCodeLst异常" + e);
		}
		
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryCategoryCodeLst")
	public RtnMessage queryCategoryCodeLst(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("QueryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			List<Map<String, Object>> records = commonService.queryCategoryCodeLst(queryEntity);
			rtnMsg.setRtnObj(records);
		} catch(Exception e) {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("查询行业异常: 请稍后再试");
			logger.error("queryCategoryCodeInfos异常" + e);
		}
		return rtnMsg;
	}
	
	
}
