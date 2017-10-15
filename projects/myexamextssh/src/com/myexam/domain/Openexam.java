package com.myexam.domain;

@SuppressWarnings("serial")
public class Openexam implements java.io.Serializable {
	private String oeId;
	private String oeExam;
	private String oeStud;
	private String oeState;
	private String oeTeac;
	private String oeRate;
	private String oeTime;
	private Integer oeType;
	
	private String oeInfo;
	
	public String getOeInfo() {
		return oeInfo;
	}

	public void setOeInfo(String oeInfo) {
		this.oeInfo = oeInfo;
	}

	public String getOeStud() {
		return oeStud;
	}

	public void setOeStud(String oeStud) {
		this.oeStud = oeStud;
	}

	public Integer getOeType() {
		return oeType;
	}

	public void setOeType(Integer oeType) {
		this.oeType = oeType;
	}

	public Openexam() {
	}

	public String getOeTime() {
		return oeTime;
	}

	public void setOeTime(String oeTime) {
		this.oeTime = oeTime;
	}

	public String getOeExam() {
		return oeExam;
	}

	public void setOeExam(String oeExam) {
		this.oeExam = oeExam;
	}

	public String getOeId() {
		return this.oeId;
	}

	public void setOeId(String oeId) {
		this.oeId = oeId;
	}

	public String getOeState() {
		return this.oeState;
	}

	public void setOeState(String oeState) {
		this.oeState = oeState;
	}

	public String getOeTeac() {
		return this.oeTeac;
	}

	public void setOeTeac(String oeTeac) {
		this.oeTeac = oeTeac;
	}

	public String getOeRate() {
		return this.oeRate;
	}

	public void setOeRate(String oeRate) {
		this.oeRate = oeRate;
	}

}