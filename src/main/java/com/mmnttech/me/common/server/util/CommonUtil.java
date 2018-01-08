package com.mmnttech.ma.merchant.server.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import com.mmnttech.ma.merchant.server.common.entity.StaticResInfo;

/**
 * @类名 CommonUtil
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 云南动量科技有限公司
 * @创建人 James
 * @创建时间 2018年1月6日 下午5:13:48
 * @版本 v1.0
 * 
 */
public class CommonUtil {
	
	public static String getAmtValue(String originalValue) {
		DecimalFormat format = new DecimalFormat("##0.00");
		return format.format(Float.valueOf(originalValue));
	}

	public static String getAmtValue(Float originalValue) {
		DecimalFormat format = new DecimalFormat("##0.00");
		return format.format(originalValue);
	}

	public static String getAmtValue(BigDecimal originalValue) {
		DecimalFormat format = new DecimalFormat("##0.00");
		return format.format(originalValue);
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static String createRandomString(int length) {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			int number = random.nextInt(36);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
	
	public static int getRandomIntValue(int max, int min) {
        Random random = new Random();

        return random.nextInt(max)%(max-min+1) + min;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
	
	//用于生成对账单下载目录
	public static String createFilePath(String basePath, String payType) {
		Calendar cal = Calendar.getInstance();
		StringBuffer filePathPre = new StringBuffer();

		filePathPre.append(basePath).append(File.separator).append(cal.get(Calendar.YEAR));
		createFileFolderIfNotExist(filePathPre.toString());

		filePathPre.append(File.separator).append(cal.get(Calendar.MONTH) + 1);
		createFileFolderIfNotExist(filePathPre.toString());

		filePathPre.append(File.separator).append(cal.get(Calendar.DATE));
		createFileFolderIfNotExist(filePathPre.toString());

		filePathPre.append(File.separator).append(payType);
		createFileFolderIfNotExist(filePathPre.toString());
		
		filePathPre.append(File.separator);
		
		return filePathPre.toString();
	}
	
	public static String getSmallFileFilePath(String filePath) {
		StringBuffer filePathName = new StringBuffer();
		filePathName.append(filePath).append("small");
		
		return filePathName.toString();
	}
	
	private static void createFileFolderIfNotExist(String filePath) {
		File folder = new File(filePath.toString());
		if (!folder.exists() && !folder.isDirectory()) {
			folder.mkdir();
		}
	}
	
	
	public static StaticResInfo getStaticResInfo(String rootPath, String resourceType) {
		StaticResInfo staticResInfo = new StaticResInfo();

		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		
		StringBuffer fileNamePre = new StringBuffer();
		StringBuffer fileRelativePrefixStr = new StringBuffer();

		fileNamePre.append(rootPath).append(File.separator).append(resourceType).append(File.separator).append(year);
		createFileFolderIfNotExist(fileNamePre.toString());

		fileNamePre.append(File.separator).append(month);
		createFileFolderIfNotExist(fileNamePre.toString());

		fileNamePre.append(File.separator).append(day);
		createFileFolderIfNotExist(fileNamePre.toString());

		fileRelativePrefixStr.append(resourceType).append(File.separator).append(year);
		fileRelativePrefixStr.append(File.separator).append(month);
		fileRelativePrefixStr.append(File.separator).append(day);
		
		staticResInfo.setFileNamePre(fileNamePre.toString());
		staticResInfo.setFileRelativePrefixStr(fileRelativePrefixStr.toString());
		
		return staticResInfo;
	}
	
	/**
	 * BASE64编码为二进制
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static byte[] base64Decode(final byte[] bytes) {
		return Base64.decodeBase64(bytes);
	}
	
}
