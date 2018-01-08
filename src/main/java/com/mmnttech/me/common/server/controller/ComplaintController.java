package com.mmnttech.ma.merchant.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmnttech.ma.merchant.server.common.entity.QueryEntity;
import com.mmnttech.ma.merchant.server.common.entity.RtnMessage;
import com.mmnttech.ma.merchant.server.service.ComplaintService;

/**
 * @类名 ComplaintController
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月6日 下午5:05:24
 * @版本 v1.0
 * 
 */

@Controller
public class ComplaintController {

	@Autowired
	private ComplaintService complaintService;

	private Logger logger = LoggerFactory.getLogger(ComplaintController.class);
	
	//商户投诉查询
	@ResponseBody
	@RequestMapping(value = "queryComplaintInfos")
	public RtnMessage queryComplaintInfos(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("queryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			//TODO
			
		} catch (Exception e) {
			logger.error("queryComplaintInfos 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("商户投诉查询：请稍后再试");
		}
		
		return rtnMsg;
	}
	
}
