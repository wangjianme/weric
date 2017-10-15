package com.myexam.domain;
//import java.util.HashSet;
//import java.util.Set;
/**
 * Cls entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Cls implements java.io.Serializable {
	// Fields

	private String clsId;
	//private Major major;
	private String clsMajor;
	public String getClsMajor() {
		return clsMajor;
	}

	public void setClsMajor(String clsMajor) {
		this.clsMajor = clsMajor;
	}

	private String clsName;
	private String clsHead;
	private String clsBtime;
	private String clsState;
	private String clsEtime;
	//private Set studs = new HashSet(0);

	// Constructors

	/** default constructor */
	public Cls() {
	}

	/** minimal constructor */
	public Cls(String clsId) {
		this.clsId = clsId;
	}

	/** full constructor */
//	public Cls(String clsId, Major major, String clsName, String clsHead,
//			String clsBtime, String clsState, String clsEtime, Set studs) {
//		this.clsId = clsId;
//		this.major = major;
//		this.clsName = clsName;
//		this.clsHead = clsHead;
//		this.clsBtime = clsBtime;
//		this.clsState = clsState;
//		this.clsEtime = clsEtime;
//		this.studs = studs;
//	}

	// Property accessors

	public String getClsId() {
		return this.clsId;
	}

	public void setClsId(String clsId) {
		this.clsId = clsId;
	}

//	public Major getMajor() {
//		return this.major;
//	}
//
//	public void setMajor(Major major) {
//		this.major = major;
//	}

	public String getClsName() {
		return this.clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public String getClsHead() {
		return this.clsHead;
	}

	public void setClsHead(String clsHead) {
		this.clsHead = clsHead;
	}

	public String getClsBtime() {
		return this.clsBtime;
	}

	public void setClsBtime(String clsBtime) {
		this.clsBtime = clsBtime;
	}

	public String getClsState() {
		return this.clsState;
	}

	public void setClsState(String clsState) {
		this.clsState = clsState;
	}

	public String getClsEtime() {
		return this.clsEtime;
	}

	public void setClsEtime(String clsEtime) {
		this.clsEtime = clsEtime;
	}

//	public Set getStuds() {
//		return this.studs;
//	}
//
//	public void setStuds(Set studs) {
//		this.studs = studs;
//	}
}