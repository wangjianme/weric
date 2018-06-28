package com.topsen.financial.po;

public class TemplateItem {
	private int tempItemId;
	private String tempItemName;
	private Template template;
	private int funId;
	private String valueType;
	private String sumValue;
	private int orderType;
	private String totalValue;
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public String getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	public String getSumValue() {
		return sumValue;
	}
	public void setSumValue(String sumValue) {
		this.sumValue = sumValue;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public int getTempItemId() {
		return tempItemId;
	}
	public void setTempItemId(int tempItemId) {
		this.tempItemId = tempItemId;
	}
	public String getTempItemName() {
		return tempItemName;
	}
	public void setTempItemName(String tempItemName) {
		this.tempItemName = tempItemName;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public int getFunId() {
		return funId;
	}
	public void setFunId(int funId) {
		this.funId = funId;
	}
}
