package com.mmnttech.ma.merchant.server.common.entity;

import java.util.List;

/**
 * @类名 RoleMenuBatchCreateEntity
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年12月28日 下午4:51:37
 * @版本 v1.0
 * 
 */
public class RoleMenuBatchCreateEntity {

	private List<String> rec_id;
	
	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<String> getRec_id() {
		return rec_id;
	}

	public void setRec_id(List<String> rec_id) {
		this.rec_id = rec_id;
	}

}
