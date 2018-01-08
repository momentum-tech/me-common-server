package com.mmnttech.me.common.server.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @类名 TimeUtil
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月6日 下午5:11:27
 * @版本 v1.0
 * 
 */
public class TimeUtil {

	public static String getBillDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		return format.format(calendar.getTime());
	}
	
	public static String getAlipayBillDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		return format.format(calendar.getTime());
	}
}
