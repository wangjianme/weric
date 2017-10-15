package com.myexam.domain;
//import java.util.HashSet;
//import java.util.Set;
/**
 * Exam entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class Exam implements java.io.Serializable {
	private String examId;
	//private Cour cour;
	private String examCour;
	private String examName;
	private String examMktime;
	private String examNote;
	private Integer examTime;
	private String examMaker;
	private Integer examScore;
	private String examState;
	private Integer examPass;
	//private Set examcates = new HashSet(0);

	// Constructors

	public Integer getExamPass() {
		return examPass;
	}

	public void setExamPass(Integer examPass) {
		this.examPass = examPass;
	}

	public String getExamState() {
		return examState;
	}

	public void setExamState(String examState) {
		this.examState = examState;
	}

	/** default constructor */
	public Exam() {
	}
	
	/** minimal constructor */
	public Exam(String examId) {
		this.examId = examId;
	}

	/** full constructor */
//	public Exam(String examId, Cour cour, String examName, String examMktime,
//			String examNote, Integer examTime, String examMaker,
//			Integer examScore, Set examcates) {
//		this.examId = examId;
//		this.cour = cour;
//		this.examName = examName;
//		this.examMktime = examMktime;
//		this.examNote = examNote;
//		this.examTime = examTime;
//		this.examMaker = examMaker;
//		this.examScore = examScore;
//		this.examcates = examcates;
//	}

	// Property accessors
	public String getExamCour() {
		return examCour;
	}

	public void setExamCour(String examCour) {
		this.examCour = examCour;
	}
	public String getExamId() {
		return this.examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

//	public Cour getCour() {
//		return this.cour;
//	}
//
//	public void setCour(Cour cour) {
//		this.cour = cour;
//	}

	public String getExamName() {
		return this.examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamMktime() {
		return this.examMktime;
	}

	public void setExamMktime(String examMktime) {
		this.examMktime = examMktime;
	}

	public String getExamNote() {
		return this.examNote;
	}

	public void setExamNote(String examNote) {
		this.examNote = examNote;
	}

	public Integer getExamTime() {
		return this.examTime;
	}

	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
	}

	public String getExamMaker() {
		return this.examMaker;
	}

	public void setExamMaker(String examMaker) {
		this.examMaker = examMaker;
	}

	public Integer getExamScore() {
		return this.examScore;
	}

	public void setExamScore(Integer examScore) {
		this.examScore = examScore;
	}

//	public Set getExamcates() {
//		return this.examcates;
//	}
//
//	public void setExamcates(Set examcates) {
//		this.examcates = examcates;
//	}

}