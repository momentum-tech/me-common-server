package com.mmnttech.ma.merchant.server.common.entity;

import java.util.ArrayList;
import java.util.List;

public class TreeMenuItem {
	
	private String recId;

    private String img;

    private String label;

    private List<TreeNode> nodes = new ArrayList<TreeNode>();

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}

	public void addItem(TreeNode treeNode) {
		nodes.add(treeNode);
	}
    
}
