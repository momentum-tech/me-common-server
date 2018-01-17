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
import com.mmnttech.me.common.server.database.entity.MenuGroup;
import com.mmnttech.me.common.server.service.MenuGroupService;

/**
 * @类名 MenuGroupController
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年12月27日 下午2:04:24
 * @版本 v1.0
 * 
 */

@Controller
public class MenuGroupController {
	
	private Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private MenuGroupService menuGroupService;

	@ResponseBody
	@RequestMapping(value = "queryMenuGroupLst")
	public RtnMessage queryMenuGroupLst(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("queryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		
		try {
			List<Map<String, Object>> records = menuGroupService.queryMenuGroupLst(queryEntity);
			rtnMsg.setRtnObj(records);
		} catch (Exception e) {
			logger.error("queryMenuGroupLst 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_QUERY_1);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "createMenuGroup")
	public RtnMessage createMenuGroup(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("menuGroup") MenuGroup menuGroup) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			rtnMsg = menuGroupService.createMenuGroup(menuGroup);
		} catch (Exception e) {
			logger.error("createMenuGroup 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_CREATE_3);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "updateMenuGroup")
	public RtnMessage updateMenuGroup(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("menuGroup") MenuGroup menuGroup) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			menuGroupService.updateMenuGroup(menuGroup);
		} catch (Exception e) {
			logger.error("updateMenuGroup 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_UPDATE_1);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteMenuGroup")
	public RtnMessage deleteMenuGroup(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("menuGroup") MenuGroup menuGroup) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			rtnMsg = menuGroupService.deleteMenuGroup(menuGroup);
		} catch (Exception e) {
			logger.error("deleteMenuGroup 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_UPDATE_1);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "updateMenuGroupOrder")
	public RtnMessage updateMenuGroupOrder(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("queryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		
		String type = request.getParameter("type");
		if(type != null) {
			int index = -1;
			List<String> menuGroupIdLst = menuGroupService.queryMenuGroupIdByRoleId(queryEntity.getRoleId());
			for (int i = 0; i < menuGroupIdLst.size(); i++) {
				String menuGroupId = menuGroupIdLst.get(i);
				if (menuGroupId.equals(queryEntity.getMenuGroupId())) {
					index = i;
					break;
				}
			}
			
			if(index != -1) {
				if ("UP".equals(type) && index > 0) {
					String tmp = menuGroupIdLst.get(index - 1);
					menuGroupIdLst.set(index - 1, queryEntity.getMenuGroupId());
					menuGroupIdLst.set(index, tmp);
				} else if ("DOWN".equals(type) && index < menuGroupIdLst.size() - 1) {
					String tmp = menuGroupIdLst.get(index + 1);
					menuGroupIdLst.set(index + 1, queryEntity.getMenuGroupId());
					menuGroupIdLst.set(index, tmp);
				}
			}
			menuGroupService.txBatchUpdateMenuGroupSeq(menuGroupIdLst);
		}
		return rtnMsg;
	}
	
	
	
}
