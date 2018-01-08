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
import com.mmnttech.ma.merchant.server.service.AccountService;

/**
 * @类名 AccountController
 * @描述: 用于行业自律公约签约，账户填写，账户确认等工作
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月6日 下午5:04:39
 * @版本 v1.0
 * 
 */

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	private Logger logger = LoggerFactory.getLogger(AccountController.class);

	//行业自律公约签约
	@ResponseBody
	@RequestMapping(value = "signSelfRegulation")
	public RtnMessage signSelfRegulation(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("queryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			//TODO
			
		} catch (Exception e) {
			logger.error("signSelfRegulation 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("行业自律公约签约：请稍后再试");
		}
		
		return rtnMsg;
	}
	
	//诚信账户签约
	public RtnMessage signHonestyAccount() {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			//TODO
			
		} catch (Exception e) {
			logger.error("signHonestyAccount 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("诚信账户签约：请稍后再试");
		}
		
		return rtnMsg;
	}
	

	//对公账户确认
	public RtnMessage comfirmAccount() {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			//TODO
			
		} catch (Exception e) {
			logger.error("comfirmAccount 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("对公账户确认：请稍后再试");
		}
		
		return rtnMsg;
	}
	
	
}
