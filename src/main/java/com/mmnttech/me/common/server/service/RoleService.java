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
import com.mmnttech.me.common.server.database.entity.MenuGroup;
import com.mmnttech.me.common.server.database.entity.MenuGroupExample;
import com.mmnttech.me.common.server.database.entity.Role;
import com.mmnttech.me.common.server.database.entity.RoleMenuGroup;
import com.mmnttech.me.common.server.database.entity.RoleMenuGroupExample;
import com.mmnttech.me.common.server.database.mappers.MenuGroupMapper;
import com.mmnttech.me.common.server.database.mappers.RoleMapper;
import com.mmnttech.me.common.server.database.mappers.RoleMenuGroupMapper;
import com.mmnttech.me.common.server.util.StringUtil;
import com.mmnttech.me.common.server.util.Validator;

/**
 * @类名 RoleService
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年12月28日 上午9:54:16
 * @版本 v1.0
 * 
 */
@Service("roleService")
public class RoleService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleMenuGroupMapper roleMenuGroupMapper;
	
	@Autowired
	private MenuGroupMapper menuGroupMapper;
	
	public List<Map<String, Object>> queryRoleLst(QueryEntity queryEntity) {
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT t2.* FROM (");
		sql.append("SELECT t1.*, AC.area FROM (");
		sql.append("SELECT ROLE.*, IDC.industry FROM (SELECT * FROM t_role WHERE 1 = 1");

		if(Validator.isNotBlank(queryEntity.getQueryName())) {
			sql.append(" AND name = ?");
			paramLst.add(queryEntity.getQueryName());
		}

		sql.append(") ROLE LEFT JOIN (SELECT * FROM t_industry_code)IDC ON ROLE.industry_code = IDC.industry_code");
		sql.append(")t1 LEFT JOIN (SELECT * FROM t_area_code) AC ON t1.area_code = AC.area_code");
		sql.append(")t2");

		int offset = (queryEntity.getPage() - 1) * queryEntity.getRows();
		sql.append(" ORDER BY t2.create_date desc LIMIT ?, ?");
		paramLst.add(offset);
		paramLst.add(queryEntity.getRows());
		
		return jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
	}
	
	public Role queryRoleById(String roleId) {
		return roleMapper.selectByPrimaryKey(roleId);
	}
	
	public RtnMessage createRole(Role role) {
		RtnMessage rtnMsg = new RtnMessage();
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(1) cnt FROM t_role WHERE name = ? AND platform = ? ");
		
		paramLst.add(role.getName());
		paramLst.add(role.getPlatform());
		
		if(jdbcTemplate.queryForObject(sql.toString(), Integer.class, paramLst.toArray()) == 0) {
			role.setRecId(StringUtil.getUUID());
			role.setCreateDate(new Date());
			
			roleMapper.insert(role);
		} else {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("创建角色失败: 相同的角色名称重复");
		}
		return rtnMsg;
	}
	
	public RtnMessage updateRole(Role role) {
		RtnMessage rtnMsg = new RtnMessage();
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(1) cnt FROM t_menu WHERE name = ? AND flatform = ? AND rec_id != ?");
		
		paramLst.add(role.getName());
		paramLst.add(role.getPlatform());
		paramLst.add(role.getRecId());
		
		if(jdbcTemplate.queryForObject(sql.toString(), Integer.class, paramLst.toArray()) == 0) {
			roleMapper.updateByPrimaryKeySelective(role);
		} else {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("修改角色失败: 相同的角色名称重复");
		}
		return rtnMsg;
	}
	
	public RtnMessage deleteRole(Role role) {
		RtnMessage rtnMsg = new RtnMessage();
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(1) cnt FROM t_role_menu_group WHERE role_id = ? ");
		
		paramLst.add(role.getRecId());
		
		if(jdbcTemplate.queryForObject(sql.toString(), Integer.class, paramLst.toArray()) == 0) {
			roleMapper.deleteByPrimaryKey(role.getRecId());
		} else {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage("删除角色失败：该角色关联了菜单组");
		}
		
		return rtnMsg;
	}
	
	
	@Transactional
	public void txCreatePaymentStatement(List<RoleMenuGroup> roleMenuGroupLst, String roleId) {
		List<String> menuGroupIdLst = new ArrayList<String>();
		
		RoleMenuGroupExample example = new RoleMenuGroupExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		roleMenuGroupMapper.deleteByExample(example);
		
		if(roleMenuGroupLst != null && !roleMenuGroupLst.isEmpty()) {
			for(RoleMenuGroup roleMenuGroup : roleMenuGroupLst) {
				menuGroupIdLst.add(roleMenuGroup.getMenuGroupId());
				
				roleMenuGroup.setRecId(StringUtil.getUUID());
				roleMenuGroup.setCreateDate(new Date());
				
				roleMenuGroupMapper.insert(roleMenuGroup);
			}
			
			MenuGroupExample menuGroupExample = new MenuGroupExample();
			menuGroupExample.createCriteria().andRecIdIn(menuGroupIdLst);
			menuGroupExample.setOrderByClause("sequence");
			
			List<MenuGroup> menuGroupLst = menuGroupMapper.selectByExample(menuGroupExample);
			for(int i = 0; i < menuGroupLst.size(); i ++) {
				MenuGroup record = menuGroupLst.get(i);
				record.setSequence(i + 1);
				
				menuGroupMapper.updateByPrimaryKeySelective(record);
			}
		}
	}

	public List<String> queryRoleMenuInfos(Role role) {
		List<String> menuGroupIdLst = new ArrayList<String>();
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT menu_group_id FROM t_role_menu_group WHERE role_id = ?");
		paramLst.add(role.getRecId());

		List<Map<String, Object>> records = jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
		for(Map<String, Object> record : records) {
			menuGroupIdLst.add(String.valueOf(record.get("menu_group_id")));
		}
		
		return menuGroupIdLst;
	}
	
	
}
