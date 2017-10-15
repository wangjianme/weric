package com.myexam.domain;

/**
 * Examques entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Examques implements java.io.Serializable {
	private String eqId;
	// private Examcate examcate;
	private String eqEcid;

	public String getEqEcid() {
		return eqEcid;
	}

	public void setEqEcid(String eqEcid) {
		this.eqEcid = eqEcid;
	}

	private String eqQuesid;
	private String eqCate;
	private Integer eqSeq;
	private String eqTitle;
	private String eqBody;
	private String eqSolu;
	private String eqChoose;
	private String eqNote;
	private String eqLevel;
	private String eqMaker;
	private Integer eqScore;

	// Constructors

	public Examques() {
	}

	/** minimal constructor */
	public Examques(String eqId) {
		this.eqId = eqId;
	}

	// Property accessors

	public String getEqId() {
		return this.eqId;
	}

	public void setEqId(String eqId) {
		this.eqId = eqId;
	}

	public String getEqQuesid() {
		return this.eqQuesid;
	}

	public void setEqQuesid(String eqQuesid) {
		this.eqQuesid = eqQuesid;
	}

	public String getEqCate() {
		return this.eqCate;
	}

	public void setEqCate(String eqCate) {
		this.eqCate = eqCate;
	}

	public Integer getEqSeq() {
		return this.eqSeq;
	}

	public void setEqSeq(Integer eqSeq) {
		this.eqSeq = eqSeq;
	}

	public String getEqTitle() {
		return this.eqTitle;
	}

	public void setEqTitle(String eqTitle) {
		this.eqTitle = eqTitle;
	}

	public String getEqBody() {
		return this.eqBody;
	}

	public void setEqBody(String eqBody) {
		this.eqBody = eqBody;
	}

	public String getEqSolu() {
		return this.eqSolu;
	}

	public void setEqSolu(String eqSolu) {
		this.eqSolu = eqSolu;
	}

	public String getEqChoose() {
		return this.eqChoose;
	}

	public void setEqChoose(String eqChoose) {
		this.eqChoose = eqChoose;
	}

	public String getEqNote() {
		return this.eqNote;
	}

	public void setEqNote(String eqNote) {
		this.eqNote = eqNote;
	}

	public String getEqLevel() {
		return this.eqLevel;
	}

	public void setEqLevel(String eqLevel) {
		this.eqLevel = eqLevel;
	}

	public String getEqMaker() {
		return this.eqMaker;
	}

	public void setEqMaker(String eqMaker) {
		this.eqMaker = eqMaker;
	}

	public Integer getEqScore() {
		return this.eqScore;
	}

	public void setEqScore(Integer eqScore) {
		this.eqScore = eqScore;
	}

}