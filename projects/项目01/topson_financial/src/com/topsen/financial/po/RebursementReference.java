package com.topsen.financial.po;

public class RebursementReference {
	private int id;
	private String refName;
	private int refColumnId;
	public int getRefColumnId() {
		return refColumnId;
	}
	public void setRefColumnId(int refColumnId) {
		this.refColumnId = refColumnId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
}
