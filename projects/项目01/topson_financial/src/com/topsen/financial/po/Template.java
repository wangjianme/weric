package com.topsen.financial.po;

import java.util.List;

public class Template {
	private int tempId;
	private String tempName;
	private TemplateType type;
	private List<TemplateItem> itemList;
	private String tempDesc;
	public String getTempDesc() {
		return tempDesc;
	}
	public void setTempDesc(String tempDesc) {
		this.tempDesc = tempDesc;
	}
	public List<TemplateItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<TemplateItem> itemList) {
		this.itemList = itemList;
	}
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public int getTempId() {
		return tempId;
	}
	public void setTempId(int tempId) {
		this.tempId = tempId;
	}
	public TemplateType getType() {
		return type;
	}
	public void setType(TemplateType type) {
		this.type = type;
	}
}
