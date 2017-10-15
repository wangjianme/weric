package com.myexam.domain;
@SuppressWarnings("serial")
public class Answ implements java.io.Serializable {
	private String answId;
	private String answInfo;
	private String answEq;
	private String answAnswer;
	private Integer answScore;

	// Constructors

	/** default constructor */
	public Answ() {
	}

	/** minimal constructor */
	public Answ(String answId, String answInfo, String answEq) {
		this.answId = answId;
		this.answInfo = answInfo;
		this.answEq = answEq;
	}
	public String getAnswId() {
		return this.answId;
	}

	public void setAnswId(String answId) {
		this.answId = answId;
	}

	public String getAnswInfo() {
		return this.answInfo;
	}

	public void setAnswInfo(String answInfo) {
		this.answInfo = answInfo;
	}

	public String getAnswEq() {
		return this.answEq;
	}

	public void setAnswEq(String answEq) {
		this.answEq = answEq;
	}
	public String getAnswAnswer() {
		return this.answAnswer;
	}

	public void setAnswAnswer(String answAnswer) {
		this.answAnswer = answAnswer;
	}

	public Integer getAnswScore() {
		return this.answScore;
	}

	public void setAnswScore(Integer answScore) {
		this.answScore = answScore;
	}

}