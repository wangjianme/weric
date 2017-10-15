package com.myexam.domain;
/**
 * Edusys entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Edusys implements java.io.Serializable {
	private String edusysId;
	private String edusysName;
	private Integer edusysMonth;
	private String edusysDesc;
	//private Set majors = new HashSet(0);

	// Constructors

	/** default constructor */
	public Edusys() {
	}

	/** minimal constructor */
	public Edusys(String edusysId) {
		this.edusysId = edusysId;
	}

	/** full constructor */
//	public Edusys(String edusysId, String edusysName, Integer edusysYear,
//			Integer edusysMonth, String edysysLength, String edusysDesc,
//			Set majors) {
//		this.edusysId = edusysId;
//		this.edusysName = edusysName;
//		this.edusysYear = edusysYear;
//		this.edusysMonth = edusysMonth;
//		this.edysysLength = edysysLength;
//		this.edusysDesc = edusysDesc;
//		this.majors = majors;
//	}

	// Property accessors

	public String getEdusysId() {
		return this.edusysId;
	}

	public void setEdusysId(String edusysId) {
		this.edusysId = edusysId;
	}

	public String getEdusysName() {
		return this.edusysName;
	}

	public void setEdusysName(String edusysName) {
		this.edusysName = edusysName;
	}


	public Integer getEdusysMonth() {
		return this.edusysMonth;
	}

	public void setEdusysMonth(Integer edusysMonth) {
		this.edusysMonth = edusysMonth;
	}


	public String getEdusysDesc() {
		return this.edusysDesc;
	}

	public void setEdusysDesc(String edusysDesc) {
		this.edusysDesc = edusysDesc;
	}

//	public Set getMajors() {
//		return this.majors;
//	}
//
//	public void setMajors(Set majors) {
//		this.majors = majors;
//	}

}