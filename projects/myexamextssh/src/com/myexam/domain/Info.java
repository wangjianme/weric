package com.myexam.domain;

@SuppressWarnings("serial")
public class Info implements java.io.Serializable {
	private String infoId;
	private String infoOe;
	private String infoStud;
	private String infoBtime;
	private String infoEtime;
	private Integer infoScore;
	private String infoRate;
	private String infoState;
	private Integer infoTimein;
	private Integer infoType;
	private String infoResit;

	public Info() {
	}

	public String getInfoOe() {
		return infoOe;
	}

	public void setInfoOe(String infoOe) {
		this.infoOe = infoOe;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}

	public String getInfoStud() {
		return infoStud;
	}

	public void setInfoStud(String infoStud) {
		this.infoStud = infoStud;
	}

	public String getInfoEtime() {
		return infoEtime;
	}

	public void setInfoEtime(String infoEtime) {
		this.infoEtime = infoEtime;
	}

	public String getInfoResit() {
		return infoResit;
	}

	public void setInfoResit(String infoResit) {
		this.infoResit = infoResit;
	}

	public Integer getInfoTimein() {
		return infoTimein;
	}

	public void setInfoTimein(Integer infoTimein) {
		this.infoTimein = infoTimein;
	}

	public String getInfoState() {
		return infoState;
	}

	public void setInfoState(String infoState) {
		this.infoState = infoState;
	}

	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInfoBtime() {
		return this.infoBtime;
	}

	public void setInfoBtime(String infoBtime) {
		this.infoBtime = infoBtime;
	}

	public Integer getInfoScore() {
		return this.infoScore;
	}

	public void setInfoScore(Integer infoScore) {
		this.infoScore = infoScore;
	}

	public String getInfoRate() {
		return this.infoRate;
	}

	public void setInfoRate(String infoRate) {
		this.infoRate = infoRate;
	}
}