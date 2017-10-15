package com.myexam.domain;
@SuppressWarnings("serial")
public class Teac implements java.io.Serializable {
	private String teacId;
	private String teacName;
	private String teacPwd;
	private String teacSex;
	private String teacTime;
	private String teacState;
	private String teacDept;
	private String teacEmail;
	private String teacTel;
	
	private String teacQues;
	private String teacAnswer;
	
	public Teac() {
	}
	
	public String getTeacQues() {
		return teacQues;
	}
	public void setTeacQues(String teacQues) {
		this.teacQues = teacQues;
	}
	public String getTeacAnswer() {
		return teacAnswer;
	}
	public void setTeacAnswer(String teacAnswer) {
		this.teacAnswer = teacAnswer;
	}
	
	
	
	public Teac(String teacId, String teacName, String teacPwd) {
		this.teacId = teacId;
		this.teacName = teacName;
		this.teacPwd = teacPwd;
	}
	public String getTeacId() {
		return this.teacId;
	}

	public void setTeacId(String teacId) {
		this.teacId = teacId;
	}

	public String getTeacName() {
		return this.teacName;
	}

	public void setTeacName(String teacName) {
		this.teacName = teacName;
	}

	public String getTeacPwd() {
		return this.teacPwd;
	}

	public void setTeacPwd(String teacPwd) {
		this.teacPwd = teacPwd;
	}

	public String getTeacSex() {
		return this.teacSex;
	}

	public void setTeacSex(String teacSex) {
		this.teacSex = teacSex;
	}

	public String getTeacTime() {
		return this.teacTime;
	}

	public void setTeacTime(String teacTime) {
		this.teacTime = teacTime;
	}

	public String getTeacState() {
		return this.teacState;
	}

	public void setTeacState(String teacState) {
		this.teacState = teacState;
	}
	public String getTeacDept() {
		return this.teacDept;
	}

	public void setTeacDept(String teacDept) {
		this.teacDept = teacDept;
	}
	public String getTeacEmail() {
		return this.teacEmail;
	}

	public void setTeacEmail(String teacEmail) {
		this.teacEmail = teacEmail;
	}

	public String getTeacTel() {
		return this.teacTel;
	}

	public void setTeacTel(String teacTel) {
		this.teacTel = teacTel;
	}
	@Override
	public String toString() {
		return "id:"+getTeacId()+",name:"+getTeacName();
	}
}