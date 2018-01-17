package com.mmnttech.me.common.server.controller;

import java.util.ArrayList;
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
import com.mmnttech.me.common.server.common.entity.RoleMenuBatchCreateEntity;
import com.mmnttech.me.common.server.common.entity.RtnMessage;
import com.mmnttech.me.common.server.database.entity.Role;
import com.mmnttech.me.common.server.database.entity.RoleMenuGroup;
import com.mmnttech.me.common.server.service.RoleService;

/**
 * @类名 RoleController
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年12月28日 上午10:06:52
 * @版本 v1.0
 * 
 */
@Controller
public class RoleController {

	private Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private RoleService roleService;
	
	@ResponseBody
	@RequestMapping(value = "queryRoleLst")
	public RtnMessage queryRoleLst(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("queryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			List<Map<String, Object>> records = roleService.queryRoleLst(queryEntity);
			rtnMsg.setRtnObj(records);
		} catch (Exception e) {
			logger.error("queryRoleLst 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_QUERY_1);
		}
		return rtnMsg;
	}

	@ResponseBody
	@RequestMapping(value = "createRole")
	public RtnMessage createRole(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("role") Role role) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			rtnMsg = roleService.createRole(role);
		} catch (Exception e) {
			logger.error("createRole 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_CREATE_3);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "updateRole")
	public RtnMessage updateRole(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("role") Role role) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			roleService.updateRole(role);
		} catch (Exception e) {
			logger.error("updateRole 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_UPDATE_1);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteRole")
	public RtnMessage deleteRole(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("role") Role role) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			rtnMsg = roleService.deleteRole(role);
		} catch (Exception e) {
			logger.error("deleteMenuGroup 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_UPDATE_1);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryRoleMenuInfos")
	public RtnMessage queryRoleMenuInfos(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("role") Role role) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			List<String> records = roleService.queryRoleMenuInfos(role);
			rtnMsg.setRtnObj(records);
		} catch (Exception e) {
			logger.error("deleteMenuGroup 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_UPDATE_1);
		}
		return rtnMsg;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "createRoleMenuInfos")
	public RtnMessage createRoleMenuInfos(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("roleMenuBatchCreateEntity") RoleMenuBatchCreateEntity roleMenuBatchCreateEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			List<String> menuGroupIdLst = roleMenuBatchCreateEntity.getRec_id();
			List<RoleMenuGroup> roleMenuGroupLst = new ArrayList<RoleMenuGroup>();
			
			if(menuGroupIdLst != null) {
				for(String menuGroupId : menuGroupIdLst) {
					RoleMenuGroup roleMenuGroup = new RoleMenuGroup();
					roleMenuGroup.setRoleId(roleMenuBatchCreateEntity.getRoleId());
					roleMenuGroup.setMenuGroupId(menuGroupId);
					
					roleMenuGroupLst.add(roleMenuGroup);
				}
			}
			roleService.txCreatePaymentStatement(roleMenuGroupLst, roleMenuBatchCreateEntity.getRoleId());
		} catch (Exception e) {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_CREATE_1);
		}
		return rtnMsg;
	}
	
}
