package com.myexam.domain;

/**
 * Ques entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Ques implements java.io.Serializable {
	private String quesId;
	private String quesCate;
	private String quesCour;
	private String quesTitle;
	private String quesBody;
	private String quesChoose;

	private String quesSolu;
	private Integer quesScore;
	private String quesNote;
	private String quesLevel;
	private String quesTime;
	private String quesMaker;
	private String quesState;

	public Ques() {
	}

	public String getQuesChoose() {
		return quesChoose;
	}

	public void setQuesChoose(String quesChoose) {
		this.quesChoose = quesChoose;
	}

	public Ques(String quesId) {
		this.quesId = quesId;
	}

	public String getQuesCate() {
		return quesCate;
	}

	public void setQuesCate(String quesCate) {
		this.quesCate = quesCate;
	}

	public String getQuesCour() {
		return quesCour;
	}

	public void setQuesCour(String quesCour) {
		this.quesCour = quesCour;
	}

	// Property accessors

	public String getQuesId() {
		return this.quesId;
	}

	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}

	public String getQuesTitle() {
		return this.quesTitle;
	}

	public void setQuesTitle(String quesTitle) {
		this.quesTitle = quesTitle;
	}

	public String getQuesBody() {
		return this.quesBody;
	}

	public void setQuesBody(String quesBody) {
		this.quesBody = quesBody;
	}

	public String getQuesSolu() {
		return this.quesSolu;
	}

	public void setQuesSolu(String quesSolu) {
		this.quesSolu = quesSolu;
	}

	public Integer getQuesScore() {
		return this.quesScore;
	}

	public void setQuesScore(Integer quesScore) {
		this.quesScore = quesScore;
	}

	public String getQuesNote() {
		return this.quesNote;
	}

	public void setQuesNote(String quesNote) {
		this.quesNote = quesNote;
	}

	public String getQuesLevel() {
		return this.quesLevel;
	}

	public void setQuesLevel(String quesLevel) {
		this.quesLevel = quesLevel;
	}

	public String getQuesTime() {
		return this.quesTime;
	}

	public void setQuesTime(String quesTime) {
		this.quesTime = quesTime;
	}

	public String getQuesMaker() {
		return this.quesMaker;
	}

	public void setQuesMaker(String quesMaker) {
		this.quesMaker = quesMaker;
	}

	public String getQuesState() {
		return this.quesState;
	}

	public void setQuesState(String quesState) {
		this.quesState = quesState;
	}

}