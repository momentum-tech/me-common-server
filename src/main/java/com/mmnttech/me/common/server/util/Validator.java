package com.mmnttech.me.common.server.util;

/**
 * @类名 Validator
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月6日 下午5:11:21
 * @版本 v1.0
 * 
 */
public class Validator {

	public static boolean isBlank(String param) {
		if (param == null || "".equals(param)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotBlank(String param) {
		return !isBlank(param);
	}

}
