package com.topsen.financial.po;

public class RebursementDetail {
	private int detailId;
	private TemplateItem item;
	private String detailValue;
	private String rebId;
	private int groupId;
	private int typeId;
	private int orderValue;
	private String flag;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public TemplateItem getItem() {
		return item;
	}
	public void setItem(TemplateItem item) {
		this.item = item;
	}
	public int getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(int orderValue) {
		this.orderValue = orderValue;
	}
	private Template template;
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public int getDetailId() {
		return detailId;
	}
	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}
	public String getDetailValue() {
		return detailValue;
	}
	public void setDetailValue(String detailValue) {
		this.detailValue = detailValue;
	}
	public String getRebId() {
		return rebId;
	}
	public void setRebId(String rebId) {
		this.rebId = rebId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
}
