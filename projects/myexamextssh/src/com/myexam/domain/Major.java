package com.myexam.domain;
@SuppressWarnings("serial")
public class Major implements java.io.Serializable {
	private String majorId;
	private String majorName;
	private String majorDesc;
	private String majorInuse;
	private String majorDept;
	private String majorEdusys;
	public String getMajorEdusys() {
		return majorEdusys;
	}

	public void setMajorEdusys(String majorEdusys) {
		this.majorEdusys = majorEdusys;
	}

	public String getMajorDept() {
		return majorDept;
	}

	public void setMajorDept(String majorDept) {
		this.majorDept = majorDept;
	}

	public Major() {
	}

	/** minimal constructor */
	public Major(String majorId) {
		this.majorId = majorId;
	}


	public String getMajorId() {
		return this.majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	public String getMajorName() {
		return this.majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getMajorDesc() {
		return this.majorDesc;
	}

	public void setMajorDesc(String majorDesc) {
		this.majorDesc = majorDesc;
	}

	public String getMajorInuse() {
		return this.majorInuse;
	}

	public void setMajorInuse(String majorInuse) {
		this.majorInuse = majorInuse;
	}
}