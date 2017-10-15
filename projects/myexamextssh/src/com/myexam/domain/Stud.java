package com.myexam.domain;
@SuppressWarnings("serial")
public class Stud implements java.io.Serializable {
	private String studId;
	
	//private Major major;
	//private Cls cls;
	public String getStudCls() {
		return studCls;
	}

	public void setStudCls(String studCls) {
		this.studCls = studCls;
	}

	private String studCls;
	
	private String studName;
	private String studPwd;
	private String studSex;
	private String studSid;
	private String studRtime;
	private String studPic;
	private String studAddr;
	private String studTel;
	private String studFather;
	private String studMother;
	private String studQq;
	private String studEmail;
	private String studDesc;
	private String studQues;
	private String studAnswer;
	private String studNo;
	private String studBirth;
	private String studState;
	public String getStudState() {
		return studState;
	}

	public void setStudState(String studState) {
		this.studState = studState;
	}

	public String getStudBirth() {
		return studBirth;
	}

	public void setStudBirth(String studBirth) {
		this.studBirth = studBirth;
	}

	public String getStudNo() {
		return studNo;
	}

	public void setStudNo(String studNo) {
		this.studNo = studNo;
	}

	public String getStudQues() {
		return studQues;
	}

	public void setStudQues(String studQues) {
		this.studQues = studQues;
	}

	public String getStudAnswer() {
		return studAnswer;
	}

	public void setStudAnswer(String studAnswer) {
		this.studAnswer = studAnswer;
	}

	/** default constructor */
	public Stud() {
	}

	/** minimal constructor */
	public Stud(String studId) {
		this.studId = studId;
	}

	/** full constructor */
//	public Stud(String studId, Major major, Cls cls, String studName,
//			String studPwd, String studSex, String studSid, String studRtime,
//			String studPic, String studAddr, String studTel, String studFather,
//			String studMother, String studQq, String studEmail, String studDesc) {
//		this.studId = studId;
//		this.major = major;
//		this.cls = cls;
//		this.studName = studName;
//		this.studPwd = studPwd;
//		this.studSex = studSex;
//		this.studSid = studSid;
//		this.studRtime = studRtime;
//		this.studPic = studPic;
//		this.studAddr = studAddr;
//		this.studTel = studTel;
//		this.studFather = studFather;
//		this.studMother = studMother;
//		this.studQq = studQq;
//		this.studEmail = studEmail;
//		this.studDesc = studDesc;
//	}

	// Property accessors

	public String getStudId() {
		return this.studId;
	}

	public void setStudId(String studId) {
		this.studId = studId;
	}

//	public Major getMajor() {
//		return this.major;
//	}
//
//	public void setMajor(Major major) {
//		this.major = major;
//	}
//
//	public Cls getCls() {
//		return this.cls;
//	}
//
//	public void setCls(Cls cls) {
//		this.cls = cls;
//	}

	public String getStudName() {
		return this.studName;
	}

	public void setStudName(String studName) {
		this.studName = studName;
	}

	public String getStudPwd() {
		return this.studPwd;
	}

	public void setStudPwd(String studPwd) {
		this.studPwd = studPwd;
	}

	public String getStudSex() {
		return this.studSex;
	}

	public void setStudSex(String studSex) {
		this.studSex = studSex;
	}

	public String getStudSid() {
		return this.studSid;
	}

	public void setStudSid(String studSid) {
		this.studSid = studSid;
	}

	public String getStudRtime() {
		return this.studRtime;
	}

	public void setStudRtime(String studRtime) {
		this.studRtime = studRtime;
	}

	public String getStudPic() {
		return this.studPic;
	}

	public void setStudPic(String studPic) {
		this.studPic = studPic;
	}

	public String getStudAddr() {
		return this.studAddr;
	}

	public void setStudAddr(String studAddr) {
		this.studAddr = studAddr;
	}

	public String getStudTel() {
		return this.studTel;
	}

	public void setStudTel(String studTel) {
		this.studTel = studTel;
	}

	public String getStudFather() {
		return this.studFather;
	}

	public void setStudFather(String studFather) {
		this.studFather = studFather;
	}

	public String getStudMother() {
		return this.studMother;
	}

	public void setStudMother(String studMother) {
		this.studMother = studMother;
	}

	public String getStudQq() {
		return this.studQq;
	}

	public void setStudQq(String studQq) {
		this.studQq = studQq;
	}

	public String getStudEmail() {
		return this.studEmail;
	}

	public void setStudEmail(String studEmail) {
		this.studEmail = studEmail;
	}

	public String getStudDesc() {
		return this.studDesc;
	}

	public void setStudDesc(String studDesc) {
		this.studDesc = studDesc;
	}

}