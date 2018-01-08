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
import com.mmnttech.me.common.server.database.entity.Menu;
import com.mmnttech.me.common.server.service.MenuService;


/**
 * @类名 MenuController
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月8日 上午9:34:30
 * @版本 v1.0
 * 
 */

@Controller
public class MenuController {

	private Logger logger = LoggerFactory.getLogger(MenuController.class);
	

	@Autowired
	private MenuService menuService;

	@ResponseBody
	@RequestMapping(value = "queryMenuLst")
	public RtnMessage queryMenuLst(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("queryEntity") QueryEntity queryEntity) {
		RtnMessage rtnMsg = new RtnMessage();
		
		try {
			List<Map<String, Object>> records = menuService.queryMenuLst(queryEntity);
			rtnMsg.setRtnObj(records);
		} catch (Exception e) {
			logger.error("queryMenuLst 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_QUERY_1);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "createMenu")
	public RtnMessage createMenu(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("menuGroup") Menu menu) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			rtnMsg = menuService.createMenuGroup(menu);
		} catch (Exception e) {
			logger.error("createMenu 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_CREATE_3);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "updateMenu")
	public RtnMessage updateMenu(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("menu") Menu menu) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			menuService.updateMenuGroup(menu);
		} catch (Exception e) {
			logger.error("updateMenu 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_UPDATE_1);
		}
		return rtnMsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteMenu")
	public RtnMessage deleteMenu(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("menuGroup") Menu menu) {
		RtnMessage rtnMsg = new RtnMessage();
		try {
			menuService.deleteMenuGroup(menu);
		} catch (Exception e) {
			logger.error("deleteMenu 出现异常：", e);
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_UPDATE_1);
		}
		return rtnMsg;
	}
	

	@ResponseBody
	@RequestMapping(value = "updateMenuOrder")
	public RtnMessage updateMenuOrder(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("menu") Menu menu) {
		RtnMessage rtnMsg = new RtnMessage();
		
		String type = request.getParameter("type");
		if(type != null) {
			int index = -1;
			List<String> menuIdLst = menuService.queryMenuIdByRoleId(menu.getMenuGroupId());
			for (int i = 0; i < menuIdLst.size(); i++) {
				String menuId = menuIdLst.get(i);
				if (menuId.equals(menu.getRecId())) {
					index = i;
					break;
				}
			}
			
			if(index != -1) {
				if ("UP".equals(type) && index > 0) {
					String tmp = menuIdLst.get(index - 1);
					menuIdLst.set(index - 1, menu.getRecId());
					menuIdLst.set(index, tmp);
				} else if ("DOWN".equals(type) && index < menuIdLst.size() - 1) {
					String tmp = menuIdLst.get(index + 1);
					menuIdLst.set(index + 1, menu.getRecId());
					menuIdLst.set(index, tmp);
				}
			}
			menuService.txBatchUpdateMenuSeq(menuIdLst);
		}
		return rtnMsg;
	}
	
	
}
