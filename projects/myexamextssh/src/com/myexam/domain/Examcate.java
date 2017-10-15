package com.myexam.domain;
//import java.util.HashSet;
//import java.util.Set;

/**
 * Examcate entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Examcate implements java.io.Serializable {
	private String ecId;
	//private Exam exam;
	private String ecExam;
	private String ecCate;
	private String ecCatename;
	private Integer ecSeq;
	private Integer ecScore;
	private Integer ecQty;
	private String ecOrder;
	//private Set examqueses = new HashSet(0);

	// Constructors

	/** default constructor */
	public Examcate() {
	}

	
	// Property accessors

	public String getEcId() {
		return this.ecId;
	}

	public void setEcId(String ecId) {
		this.ecId = ecId;
	}
	public String getEcExam() {
		return ecExam;
	}

	public void setEcExam(String ecExam) {
		this.ecExam = ecExam;
	}
//	public Exam getExam() {
//		return this.exam;
//	}
//
//	public void setExam(Exam exam) {
//		this.exam = exam;
//	}

	public String getEcCate() {
		return this.ecCate;
	}

	public void setEcCate(String ecCate) {
		this.ecCate = ecCate;
	}

	public String getEcCatename() {
		return this.ecCatename;
	}

	public void setEcCatename(String ecCatename) {
		this.ecCatename = ecCatename;
	}

	public Integer getEcSeq() {
		return this.ecSeq;
	}

	public void setEcSeq(Integer ecSeq) {
		this.ecSeq = ecSeq;
	}

	public Integer getEcScore() {
		return this.ecScore;
	}

	public void setEcScore(Integer ecScore) {
		this.ecScore = ecScore;
	}

	public Integer getEcQty() {
		return this.ecQty;
	}

	public void setEcQty(Integer ecQty) {
		this.ecQty = ecQty;
	}

	public String getEcOrder() {
		return this.ecOrder;
	}

	public void setEcOrder(String ecOrder) {
		this.ecOrder = ecOrder;
	}

//	public Set getExamqueses() {
//		return this.examqueses;
//	}
//
//	public void setExamqueses(Set examqueses) {
//		this.examqueses = examqueses;
//	}

}