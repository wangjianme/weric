package com.myexam.teac.examadd.domain;

public class ExamAdd {
	private String examName;
	private String examCour;
	private Integer examScore;
	private Integer examPass;
	private Integer examTime;
	private String examMktime;
	private String examMaker;
	private String examNote;
	private String[] ecCate;
	private String[] ecCateName;
	private Integer[] ecQty;
	private Integer[] ecScore;
	public Integer getExamPass() {
		return examPass;
	}
	public void setExamPass(Integer examPass) {
		this.examPass = examPass;
	}
	public String getExamNote() {
		return examNote;
	}
	public void setExamNote(String examNote) {
		this.examNote = examNote;
	}
	public String getExamMaker() {
		return examMaker;
	}
	public void setExamMaker(String examMaker) {
		this.examMaker = examMaker;
	}
	public String getExamMktime() {
		return examMktime;
	}
	public void setExamMktime(String examMktime) {
		this.examMktime = examMktime;
	}
	public Integer getExamTime() {
		return examTime;
	}
	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamCour() {
		return examCour;
	}
	public void setExamCour(String examCour) {
		this.examCour = examCour;
	}
	public Integer getExamScore() {
		return examScore;
	}
	public void setExamScore(Integer examScore) {
		this.examScore = examScore;
	}
	public String[] getEcCate() {
		return ecCate;
	}
	public void setEcCate(String[] ecCate) {
		this.ecCate = ecCate;
	}
	public String[] getEcCateName() {
		return ecCateName;
	}
	public void setEcCateName(String[] ecCateName) {
		this.ecCateName = ecCateName;
	}
	public Integer[] getEcQty() {
		return ecQty;
	}
	public void setEcQty(Integer[] ecQty) {
		this.ecQty = ecQty;
	}
	public Integer[] getEcScore() {
		return ecScore;
	}
	public void setEcScore(Integer[] ecScore) {
		this.ecScore = ecScore;
	}
}
