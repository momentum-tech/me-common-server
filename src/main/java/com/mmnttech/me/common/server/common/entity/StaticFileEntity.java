package com.mmnttech.me.common.server.common.entity;

/**
 * @类名 StaticFileEntity
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 doughbbs
 * @创建人 James
 * @创建时间 2017年12月13日 下午5:46:21
 * @版本 v1.0
 * 
 */
public class StaticFileEntity {

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
