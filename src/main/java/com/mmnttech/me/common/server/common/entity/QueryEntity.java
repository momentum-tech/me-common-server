package com.mmnttech.me.common.server.common.entity;

/**
 * @类名 QueryEntity
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2018年1月3日 下午5:24:55
 * @版本 v1.0
 * 
 */
public class QueryEntity {

	private String recId;
	
	private String userId;
	
	private String merchId;
	
	private String storeId;
	
	private String menuGroupId;
	
	private String brchId;
	
	private String instId;
	
	private String roleId;
	
	private String payName;
	
	private String queryName;
	
	private String queryDate;
	
	private String startDate;
	
	private String endDate;
	
	private String amt;
	
	private String code;
	
	private String userName;
	
	private String userTel;
	
	private String memo;
	
	private int rows = 20;
	
	private int page = 1;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getMenuGroupId() {
		return menuGroupId;
	}

	public void setMenuGroupId(String menuGroupId) {
		this.menuGroupId = menuGroupId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getBrchId() {
		return brchId;
	}

	public void setBrchId(String brchId) {
		this.brchId = brchId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}
	
}
