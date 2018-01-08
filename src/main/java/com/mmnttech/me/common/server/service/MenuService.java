package com.mmnttech.me.common.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mmnttech.me.common.server.common.entity.QueryEntity;
import com.mmnttech.me.common.server.common.entity.RtnMessage;
import com.mmnttech.me.common.server.database.entity.Menu;
import com.mmnttech.me.common.server.database.entity.MenuExample;
import com.mmnttech.me.common.server.database.mappers.MenuMapper;
import com.mmnttech.me.common.server.util.StringUtil;
import com.mmnttech.me.common.server.util.Validator;

/**
 * @类名 MenuService
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年12月27日 下午2:06:54
 * @版本 v1.0
 * 
 */
@Service("menuService")
public class MenuService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MenuMapper menuMapper;

	public List<Map<String, Object>> queryMenuLst(QueryEntity queryEntity) {
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT menu.*, menuGroup.name groupName FROM (SELECT * FROM t_menu WHERE 1 = 1");
		
		if(Validator.isNotBlank(queryEntity.getQueryName())) {
			sql.append(" AND name = ?");
			paramLst.add(queryEntity.getQueryName());
		}

		if(Validator.isNotBlank(queryEntity.getMenuGroupId())) {
			sql.append(" AND menu_group_id = ?");
			paramLst.add(queryEntity.getMenuGroupId());
		}
		
		sql.append(") menu LEFT JOIN (SELECT * FROM t_menu_group) menuGroup ON menu.menu_group_id = menuGroup.rec_id");
		
		int offset = (queryEntity.getPage() - 1) * queryEntity.getRows();
		
		sql.append(" ORDER BY sequence LIMIT ?, ?");
		paramLst.add(offset);
		paramLst.add(queryEntity.getRows());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		
		List<Map<String, Object>> records = jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
		for(Map<String, Object> record : records) {
			record.put("create_date", format.format(record.get("create_date")));
		}
		
		return records;
	}

	public RtnMessage createMenuGroup(Menu menu) {
		RtnMessage rtnMsg = new RtnMessage();
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(1) cnt FROM t_menu WHERE menu_group_id = ? AND (name = ? OR identity = ? OR html = ?)");
		
		paramLst.add(menu.getMenuGroupId());
		paramLst.add(menu.getName());
		paramLst.add(menu.getIdentity());
		paramLst.add(menu.getHtml());
		
		if(jdbcTemplate.queryForObject(sql.toString(), Integer.class, paramLst.toArray()) == 0) {
			MenuExample example = new MenuExample();
			example.createCriteria().andMenuGroupIdEqualTo(menu.getMenuGroupId());
			
			int sequence = menuMapper.countByExample(example);
			
			menu.setRecId(StringUtil.getUUID());
			menu.setSequence(sequence + 1);
			menu.setCreateDate(new Date());
			
			menuMapper.insert(menu);
		} else {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("创建Menu失败: 相同的菜单组下菜单名称/菜单标识/关联页面重复");
		}
		return rtnMsg;
	}

	public RtnMessage updateMenuGroup(Menu menu) {
		RtnMessage rtnMsg = new RtnMessage();
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(1) cnt FROM t_menu WHERE menu_group_id = ? AND (name = ? OR identity = ? OR html = ?) AND rec_id != ?");
		
		paramLst.add(menu.getMenuGroupId());
		paramLst.add(menu.getName());
		paramLst.add(menu.getIdentity());
		paramLst.add(menu.getHtml());
		paramLst.add(menu.getRecId());
		
		if(jdbcTemplate.queryForObject(sql.toString(), Integer.class, paramLst.toArray()) == 0) {
			menuMapper.updateByPrimaryKeySelective(menu);
		} else {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("修改Menu失败: 相同的菜单组下菜单名称/菜单标识/关联页面重复");
		}
		return rtnMsg;
	}

	public void deleteMenuGroup(Menu menu) {
		menuMapper.deleteByPrimaryKey(menu.getRecId());
	}

	public List<String> queryMenuIdByRoleId(String menuGroupId) {
		MenuExample example = new MenuExample();
		example.createCriteria().andMenuGroupIdEqualTo(menuGroupId);
		example.setOrderByClause("sequence");
		
		List<String> menuIdLst = new ArrayList<String>();
		List<Menu> menuLst = menuMapper.selectByExample(example);
		for(Menu menu : menuLst) {
			menuIdLst.add(menu.getRecId());
		}
		return menuIdLst;
	}

	public void txBatchUpdateMenuSeq(List<String> menuIdLst) {
		for(int i = 0; i < menuIdLst.size(); i++) {
			String menuId = menuIdLst.get(i);
			
			Menu menu = new Menu();
			menu.setRecId(menuId);
			menu.setSequence(i + 1);
			
			menuMapper.updateByPrimaryKeySelective(menu);
		}
	}

}
