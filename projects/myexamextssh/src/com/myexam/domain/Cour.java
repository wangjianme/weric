package com.myexam.domain;
//import java.util.HashSet;
//import java.util.Set;
/**
 * Cour entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Cour implements java.io.Serializable {
	private String courId;
	private String courName;
	private String courDesc;
	private Integer courHour;
	private String courIspubs;
	public String getCourIspubs() {
		return courIspubs;
	}
	public void setCourIspubs(String courIspubs) {
		this.courIspubs = courIspubs;
	}
	public String getCourInuse() {
		return courInuse;
	}
	public void setCourInuse(String courInuse) {
		this.courInuse = courInuse;
	}

	private String courInuse;
	//private Set exams = new HashSet(0);
	//private Set queses = new HashSet(0);
	public Cour() {
	}
	public Cour(String courId) {
		this.courId = courId;
	}

	/** full constructor */
//	public Cour(String courId, String courName, String courMajor,
//			String courDesc, Integer courHour, String courPus,
//			String courInuse, Set exams, Set queses) {
//		this.courId = courId;
//		this.courName = courName;
//		this.courMajor = courMajor;
//		this.courDesc = courDesc;
//		this.courHour = courHour;
//		this.courPus = courPus;
//		this.courInuse = courInuse;
//		this.exams = exams;
//		this.queses = queses;
//	}

	// Property accessors

	public String getCourId() {
		return this.courId;
	}

	public void setCourId(String courId) {
		this.courId = courId;
	}

	public String getCourName() {
		return this.courName;
	}

	public void setCourName(String courName) {
		this.courName = courName;
	}
	public String getCourDesc() {
		return this.courDesc;
	}

	public void setCourDesc(String courDesc) {
		this.courDesc = courDesc;
	}

	public Integer getCourHour() {
		return this.courHour;
	}

	public void setCourHour(Integer courHour) {
		this.courHour = courHour;
	}

//	public String getCourPus() {
//		return this.courPus;
//	}
//
//	public void setCourPus(String courPus) {
//		this.courPus = courPus;
//	}

//	public String getCourInuse() {
//		return this.courInuse;
//	}
//
//	public void setCourInuse(String courInuse) {
//		this.courInuse = courInuse;
//	}

//	public Set getExams() {
//		return this.exams;
//	}
//
//	public void setExams(Set exams) {
//		this.exams = exams;
//	}
//
//	public Set getQueses() {
//		return this.queses;
//	}
//
//	public void setQueses(Set queses) {
//		this.queses = queses;
//	}

}