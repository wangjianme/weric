package com.myexam.domain;
@SuppressWarnings("serial")
public class Dept implements java.io.Serializable {
	private String deptId;
	private String deptName;
	private String deptParent;
	private boolean deptIsleaf;
	private String deptDesc;
	public boolean isDeptIsleaf() {
		return deptIsleaf;
	}

	public void setDeptIsleaf(boolean deptIsleaf) {
		this.deptIsleaf = deptIsleaf;
	}
	public Dept() {
	}

	/** minimal constructor */
	public Dept(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptParent() {
		return this.deptParent;
	}

	public void setDeptParent(String deptParent) {
		this.deptParent = deptParent;
	}
	public String getDeptDesc() {
		return this.deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
}