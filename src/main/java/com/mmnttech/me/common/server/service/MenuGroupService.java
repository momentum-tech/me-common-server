package com.mmnttech.me.common.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmnttech.me.common.server.common.entity.QueryEntity;
import com.mmnttech.me.common.server.common.entity.RtnMessage;
import com.mmnttech.me.common.server.database.entity.MenuExample;
import com.mmnttech.me.common.server.database.entity.MenuGroup;
import com.mmnttech.me.common.server.database.entity.MenuGroupExample;
import com.mmnttech.me.common.server.database.entity.RoleMenuGroupExample;
import com.mmnttech.me.common.server.database.mappers.MenuGroupMapper;
import com.mmnttech.me.common.server.database.mappers.MenuMapper;
import com.mmnttech.me.common.server.database.mappers.RoleMenuGroupMapper;
import com.mmnttech.me.common.server.util.StringUtil;
import com.mmnttech.me.common.server.util.Validator;

/**
 * @类名 MenuGroupService
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年12月26日 下午5:09:22
 * @版本 v1.0
 * 
 */
@Service("menuGroupService")
public class MenuGroupService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MenuGroupMapper menuGroupMapper;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private RoleMenuGroupMapper roleMenuGroupMapper;
	
	public List<Map<String, Object>> queryMenuGroupLst(QueryEntity queryEntity) {
		List<Object> paramLst = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		if(Validator.isNotBlank(queryEntity.getRoleId())) {
			sql.append("SELECT mg.* FROM ((SELECT * FROM t_role_menu_group WHERE role_id = ?) rmp LEFT JOIN t_menu_group mg ON rmp.menu_group_id = mg.rec_id)");
			paramLst.add(queryEntity.getRoleId());
		} else {
			sql.append("SELECT * FROM t_menu_group mg WHERE 1 = 1");
			
			if(Validator.isNotBlank(queryEntity.getQueryName())) {
				sql.append(" AND name = ?");
				paramLst.add(queryEntity.getQueryName());
			}
		}
		
		int offset = (queryEntity.getPage() - 1) * queryEntity.getRows();
		
		sql.append(" ORDER BY mg.sequence LIMIT ?, ?");
		paramLst.add(offset);
		paramLst.add(queryEntity.getRows());
		return jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
	}
	
	public RtnMessage createMenuGroup(MenuGroup menuGroup) {
		RtnMessage rtnMsg = new RtnMessage();
		
		MenuGroupExample example = new MenuGroupExample();
		example.createCriteria().andNameEqualTo(menuGroup.getName());
		if(menuGroupMapper.countByExample(example) == 0) {
			menuGroup.setRecId(StringUtil.getUUID());
			menuGroup.setCreateDate(new Date());
			menuGroup.setSequence(100);
			
			menuGroupMapper.insert(menuGroup);
		} else {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_CREATE_2);
		}
		return rtnMsg;
	}

	public void updateMenuGroup(MenuGroup menuGroup) {
		menuGroupMapper.updateByPrimaryKeySelective(menuGroup);
	}
	
	public RtnMessage deleteMenuGroup(MenuGroup menuGroup) {
		RtnMessage rtnMsg = new RtnMessage();
		
		MenuExample example = new MenuExample();
		example.createCriteria().andMenuGroupIdGreaterThan(menuGroup.getRecId());
		if(menuMapper.countByExample(example) == 0) {
			RoleMenuGroupExample roleMenuGroupExample = new RoleMenuGroupExample();
			roleMenuGroupExample.createCriteria().andMenuGroupIdEqualTo(menuGroup.getRecId());
			
			if(roleMenuGroupMapper.countByExample(roleMenuGroupExample) == 0) {
				menuGroupMapper.deleteByPrimaryKey(menuGroup.getRecId());
			} else {
				rtnMsg.setIsSuccess(false);
				rtnMsg.setMessage(RtnMessage.ERROR_DELETE_3 + "(请先删除关联菜单数据)");
			}
		} else {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_DELETE_3 + "(请先删除关联角色数据)");
		}
		return rtnMsg;
	}

	public List<String> queryMenuGroupIdByRoleId(String roleId) {
		List<String> menuGroupIdLst = new ArrayList<String>();
		
		List<Object> paramLst = new ArrayList<Object>();
		
		String sql = "SELECT mg.rec_id FROM ((SELECT * FROM t_role_menu_group WHERE role_id = ?) rmp LEFT JOIN t_menu_group mg ON rmp.menu_group_id = mg.rec_id) ORDER BY mg.sequence";
		paramLst.add(roleId);
		
		List<Map<String, Object>> records = jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
		for(Map<String, Object> record : records) {
			menuGroupIdLst.add(String.valueOf(record.get("rec_id")));
		}
		
		return menuGroupIdLst;
	}

	@Transactional
	public void txBatchUpdateMenuGroupSeq(List<String> menuGroupIdLst) {
		for(int i = 0; i < menuGroupIdLst.size(); i++) {
			String menuGroupId = menuGroupIdLst.get(i);
			
			MenuGroup menuGroup = new MenuGroup();
			menuGroup.setRecId(menuGroupId);
			menuGroup.setSequence(i + 1);
			
			menuGroupMapper.updateByPrimaryKeySelective(menuGroup);
		}
	}
	
	
	
	
	
	
	
}
