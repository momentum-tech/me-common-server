package com.mmnttech.me.common.server.common.entity;

/**
 * @类名 UploadFileEntity
 * @描述:
 *   TODO
 * @版权: Copyright (c) 2017 doughbbs
 * @创建人 James
 * @创建时间 2017年12月15日 下午2:50:46
 * @版本 v1.0
 * 
 */
public class UploadFileEntity {

	private boolean isLast = false;
	
	private boolean isSuccess = true;
	
	private String data;
	
	private String fileName;
	
	private String fullFilePath;
	
	private String relativeFilePath;
	
	private String message;

	public boolean getIsLast() {
		return isLast;
	}

	public void setIsLast(boolean isLast) {
		this.isLast = isLast;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullFilePath() {
		return fullFilePath;
	}

	public void setFullFilePath(String fullFilePath) {
		this.fullFilePath = fullFilePath;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRelativeFilePath() {
		return relativeFilePath;
	}

	public void setRelativeFilePath(String relativeFilePath) {
		this.relativeFilePath = relativeFilePath;
	}
}
