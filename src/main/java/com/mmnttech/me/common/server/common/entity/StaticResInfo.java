package com.mmnttech.ma.merchant.server.common.entity;

/**
 * @类名 StaticResInfo
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年11月1日 下午4:51:11
 * @版本 v1.0
 * 
 */
public class StaticResInfo {

	private String fileNamePre;
	
	private String fileRelativePrefixStr;

	public String getFileNamePre() {
		return fileNamePre;
	}

	public void setFileNamePre(String fileNamePre) {
		this.fileNamePre = fileNamePre;
	}

	public String getFileRelativePrefixStr() {
		return fileRelativePrefixStr;
	}

	public void setFileRelativePrefixStr(String fileRelativePrefixStr) {
		this.fileRelativePrefixStr = fileRelativePrefixStr;
	}
	
}
