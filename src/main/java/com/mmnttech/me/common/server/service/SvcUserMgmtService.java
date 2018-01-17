package com.mmnttech.me.common.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mmnttech.me.common.server.common.entity.CommonDictionary;
import com.mmnttech.me.common.server.common.entity.QueryEntity;
import com.mmnttech.me.common.server.common.entity.RtnMessage;
import com.mmnttech.me.common.server.common.entity.TreeMenuItem;
import com.mmnttech.me.common.server.common.entity.TreeNode;
import com.mmnttech.me.common.server.database.entity.Menu;
import com.mmnttech.me.common.server.database.entity.MenuExample;
import com.mmnttech.me.common.server.database.entity.SvcUser;
import com.mmnttech.me.common.server.database.entity.SvcUserExample;
import com.mmnttech.me.common.server.database.mappers.MenuMapper;
import com.mmnttech.me.common.server.database.mappers.SvcUserMapper;
import com.mmnttech.me.common.server.util.StringUtil;
import com.mmnttech.me.common.server.util.Validator;

/**
 * @类名 SvcUserMgmtService
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年9月16日 下午8:38:49
 * @版本 v1.0
 * 
 */
@Service("svcUserMgmtService")
public class SvcUserMgmtService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SvcUserMapper svcUserMapper;
	
	@Autowired
	private MenuMapper menuMapper;
	
	public RtnMessage doLogin(SvcUser svcUser) {
		RtnMessage rtnMsg = new RtnMessage();
		
		SvcUserExample svcUserExample = new SvcUserExample();
		svcUserExample.createCriteria().andUserTelEqualTo(svcUser.getUserTel()).andUserPwdEqualTo(StringUtil.MD5(svcUser.getUserPwd()))
			.andStatusEqualTo(CommonDictionary.TSvcUser.STATUS_NORMAL);
		
		List<SvcUser> records = svcUserMapper.selectByExample(svcUserExample);
		if(records != null && !records.isEmpty()) {
			if(records.size() == 1) {
				SvcUser record = records.get(0);
				rtnMsg.setRtnObj(record);
			} else {
				rtnMsg.setIsSuccess(false);
				rtnMsg.setMessage(RtnMessage.ERROR_LOGIN_1);
			}
		} else {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_LOGIN_1);
		}
		
		return rtnMsg;
	}
	
	public SvcUser querySvcUser(SvcUser svcUser) {
		SvcUserExample svcUserExample = new SvcUserExample();
		svcUserExample.createCriteria().andUserIdEqualTo(svcUser.getUserId())
			.andStatusEqualTo(CommonDictionary.TSvcUser.STATUS_NORMAL);
		
		List<SvcUser> svcUserLst = svcUserMapper.selectByExample(svcUserExample);
		if(svcUserLst != null && svcUserLst.size() > 0) {
			return svcUserLst.get(0);
		}
		return null;
	}
	
	public RtnMessage createSvcUser(SvcUser svcUser) {
		RtnMessage rtnMsg = new RtnMessage();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(1) cnt FROM t_svc_user WHERE ");
		sql.append(" user_tel = ?");
		
		Map<String, Object> record = jdbcTemplate.queryForMap(sql.toString(), svcUser.getUserTel());
		if(Integer.parseInt(String.valueOf(record.get("cnt"))) != 0) {
			rtnMsg.setIsSuccess(false);
			rtnMsg.setMessage(RtnMessage.ERROR_REGISTER_1 + "(用户手机号)");
		} else {
			svcUser.setUserId(StringUtil.getUUID());
			svcUser.setUserPwd(StringUtil.MD5(svcUser.getUserPwd()));
			svcUser.setStatus(CommonDictionary.TSvcUser.STATUS_NORMAL);
			svcUser.setCreateDate(new Date());
			
			svcUserMapper.insert(svcUser);
		}
		
		return rtnMsg;
	}

	public List<Map<String, Object>> querySvcUserInfoLst(QueryEntity queryEntity) {
		List<Object> paramLst = new ArrayList<Object>();
		
		int offset = (queryEntity.getPage() - 1) * queryEntity.getRows();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM t_svc_user WHERE 1 = 1");
		
		if(Validator.isNotBlank(queryEntity.getUserTel())) {
			sql.append(" AND user_tel like ?");
			paramLst.add("%" + queryEntity.getUserTel() + "%");
		}
		
		if(Validator.isNotBlank(queryEntity.getUserName())) {
			sql.append(" AND user_name like ?");
			paramLst.add("%" + queryEntity.getUserName() + "%");
		}

		sql.append(" ORDER BY create_date DESC LIMIT ?, ?");
		paramLst.add(offset);
		paramLst.add(queryEntity.getRows());
		
		List<Map<String, Object>> records = jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
		if(records != null && !records.isEmpty()) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
			
			for(Map<String, Object> record : records) {
//				record.put("role_info_dic", record.get("role_info") == null?"":DictionaryUtil.getSvcUserRoleInfoDicValue(String.valueOf(record.get("role_info"))));
//				record.put("status", record.get("status") == null?"":DictionaryUtil.getSvcStatusInfoDicValue(String.valueOf(record.get("status"))));
				record.put("create_date", record.get("create_date") == null?"":format.format(record.get("create_date")));
			}
		}
		return records;
	}
	
	public void deleteUser(String userId) {
		svcUserMapper.deleteByPrimaryKey(userId);
	}

	public void doUpdateSvcUser(SvcUser svcUser) {
		svcUserMapper.updateByPrimaryKeySelective(svcUser);
	}
	
	public List<TreeMenuItem> queryUserTreeMenuItem(String roleId, String treeNodeId) {
		List<TreeMenuItem> records = new ArrayList<TreeMenuItem>();
		Map<String, TreeMenuItem> menuGroupMap = new HashMap<String, TreeMenuItem>();
		List<String> menuGroupIdLst = new ArrayList<String>();
		
		List<Object> paramLst = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT mg.* FROM ((SELECT * FROM t_role_menu_group WHERE role_id = ?");
		paramLst.add(roleId);
		
		sql.append(")rmg LEFT JOIN t_menu_group mg ON rmg.menu_group_id = mg.rec_id) ORDER BY mg.sequence");
		
		List<Map<String, Object>> menuGroupLst = jdbcTemplate.queryForList(sql.toString(), paramLst.toArray());
		
		if(menuGroupLst != null && !menuGroupLst.isEmpty()) {
			for(Map<String, Object> menuGroup : menuGroupLst) {
				TreeMenuItem paymentTMI = new TreeMenuItem();
				paymentTMI.setLabel(String.valueOf(menuGroup.get("name")));
				
				records.add(paymentTMI);
				menuGroupIdLst.add(String.valueOf(menuGroup.get("rec_id")));
				menuGroupMap.put(String.valueOf(menuGroup.get("rec_id")), paymentTMI);
			}
			
			MenuExample menuExample = new MenuExample();
			menuExample.createCriteria().andMenuGroupIdIn(menuGroupIdLst);
			menuExample.setOrderByClause("sequence");
			
			List<Menu> menuLst = menuMapper.selectByExample(menuExample);
			for(Menu menu : menuLst) {
				TreeNode treeNode = new TreeNode();
				treeNode.setLabel(menu.getName());
				treeNode.setRecId(menu.getIdentity());
				treeNode.setHref(menu.getHtml());
				
				if(menu.getIdentity().equals(treeNodeId)) {
					treeNode.setIsSelected(true);
				}
				
				TreeMenuItem paymentTMI = menuGroupMap.get(menu.getMenuGroupId());
				paymentTMI.addItem(treeNode);
			}
		}
		return records;
	}
	
	
	
	
	
	
	
	
	
}