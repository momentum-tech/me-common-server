package com.mmnttech.ma.merchant.server.common.entity;

public class RtnMessage {
	
	public static final String ERROR_QUERY_1 = "查询出现异常：请稍后或者联系客服人员";
	
	public static final String ERROR_CREATE_1 = "创建不成功：请稍后再试或联系客服人员";
	public static final String ERROR_CREATE_2 = "创建不成功：关键信息出现重复";
	public static final String ERROR_CREATE_3 = "创建出现异常：请稍后再试或联系客服人员";
	
	public static final String ERROR_DELETE_1 = "删除不成功：请刷新数据并重新确认删除信息";
	public static final String ERROR_DELETE_2 = "删除出现异常：请稍后再试或联系客服人员";
	public static final String ERROR_DELETE_3 = "删除不成功：存在关联数据";
	
	public static final String ERROR_UPDATE_1 = "修改不成功：请刷新数据并重新确认修改信息";
	public static final String ERROR_UPDATE_2 = "修改出现异常：请稍后再试或联系客服人员";
	
	public static final String ERROR_REGISTER_1 = "注册不成功：注册信息重复";
	public static final String ERROR_REGISTER_2 = "注册出现异常：请稍后再试或联系客服人员";
	
	public static final String ERROR_LOGIN_1 = "登录不成功：登录信息错误";
	public static final String ERROR_LOGIN_2 = "登录出现异常：请稍后再试或联系客服人员";
	
	private String message = "";
	
	private boolean isSuccess = true;
	
	private Object rtnObj;

	public Object getRtnObj() {
		return rtnObj;
	}

	public void setRtnObj(Object rtnObj) {
		this.rtnObj = rtnObj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}
