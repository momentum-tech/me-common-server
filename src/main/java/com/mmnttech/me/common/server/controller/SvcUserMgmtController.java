package com.mmnttech.me.common.server.controller;

import java.util.HashMap;
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
import com.mmnttech.me.common.server.common.entity.TreeMenuItem;
import com.mmnttech.me.common.server.database.entity.Role;
import com.mmnttech.me.common.server.database.entity.SvcUser;
import com.mmnttech.me.common.server.service.RoleService;
import com.mmnttech.me.common.server.service.SvcUserMgmtService;

/**
 * @类名 SvcUserMgmtController
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月8日 上午9:52:35
 * @版本 v1.0
 * 
 */
@Controller
public class SvcUserMgmtController {
	
	private Logger logger = LoggerFactory.getLogger(SvcUserMgmtController.class);
	
	@Autowired
	private SvcUserMgmtService svcUserMgmtService;
	
	@Autowired
	private RoleService roleService;
	
	@ResponseBody
	@RequestMapping(value = "doLogin")
	public RtnMessage doLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") SvcUser svcUser) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			rtnMsg = svcUserMgmtService.doLogin(svcUser);
		} catch (Exception e) {
			logger.error("doLogin 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_LOGIN_2);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "createSvcUser")
	public RtnMessage createSvcUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") SvcUser svcUser) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			rtnMsg = svcUserMgmtService.createSvcUser(svcUser);
		} catch (Exception e) {
			logger.error("createSvcUser 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_REGISTER_2);
		}
		return rtnMsg;
	}
	
	
	//用于查询用户基础信息，之后也可以用于用户的鉴权
	@ResponseBody
	@RequestMapping(value = "queryUserBaseInfo")
	public RtnMessage queryUserBaseInfo(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") SvcUser svcUser) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			SvcUser record = svcUserMgmtService.querySvcUser(svcUser);
			if(record != null) {
				Role role = roleService.queryRoleById(record.getRoleId());
				String treeNodeId = request.getParameter("treeNodeId");
				
				Map<String, Object> userBaseInfo = new HashMap<String, Object>();
				
				List<TreeMenuItem> datas = svcUserMgmtService.queryUserTreeMenuItem(record.getRoleId(), treeNodeId);
				userBaseInfo.put("menuInfo", datas);
				userBaseInfo.put("roleInfo", role.getName() + "(" + role.getPlatform() + ")");
				
				rtnMsg.setRtnObj(userBaseInfo);
			} else {
				rtnMsg.setIsSuccess(false);
				rtnMsg.setMessage("获取用户信息失败，您的用户不可用或提供的信息不正确，请联系客服人员");
			}
		} catch (Exception e) {
			logger.error("queryUserBaseInfo 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_QUERY_1);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "querySvcUserInfoLst")
	public RtnMessage querySvcUserInfoLst(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("queryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			List<Map<String, Object>> records = svcUserMgmtService.querySvcUserInfoLst(queryEntity);
			if(records != null) {
				rtnMsg.setRtnObj(records);
			}
		} catch (Exception e) {
			logger.error("querySvcUserInfoLst 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_QUERY_1);
		}
		return rtnMsg;
	}

	@ResponseBody
	@RequestMapping(value = "doUpdateSvcUser")
	public RtnMessage doUpdateSvcUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("svcUser") SvcUser svcUser) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			svcUserMgmtService.doUpdateSvcUser(svcUser);
		} catch (Exception e) {
			logger.error("deleteUser 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_UPDATE_1);
		}
		return rtnMsg;
	}

	@ResponseBody
	@RequestMapping(value = "deleteSvcUser")
	public RtnMessage deleteSvcUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("svcUser") SvcUser svcUser) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			svcUserMgmtService.deleteUser(svcUser.getUserId());
		} catch (Exception e) {
			logger.error("deleteUser 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_DELETE_2);
		}
		return rtnMsg;
	}
	
	
}
