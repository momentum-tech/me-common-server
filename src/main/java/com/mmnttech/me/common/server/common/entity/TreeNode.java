package com.mmnttech.me.common.server.common.entity;

import java.util.Date;

/**
 * @类名 TreeNode
 * @描述: 用于界面树菜单
 *   TODO
 * @版权: Copyright (c) 2017 easepay
 * @创建人 James
 * @创建时间 2017年9月3日 下午5:03:36
 * @版本 v1.0
 * 
 */
public class TreeNode {
    private String recId;

    private String img;

    private String label;
    
    private boolean isSelected = false;

    private Date createDate;

    private String treeMenuId;

    private String href;

    public boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId == null ? null : recId.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTreeMenuId() {
        return treeMenuId;
    }

    public void setTreeMenuId(String treeMenuId) {
        this.treeMenuId = treeMenuId == null ? null : treeMenuId.trim();
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href == null ? null : href.trim();
    }
}