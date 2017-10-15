package cn.grades.domain;

public class Teacher {
	private String id;
	private String nm;
	private String sex;
	private String tel;
	private String institute;
	private String edu;
	private String rank;
	private String date;
	private String sub;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", nm=" + nm + ", sex=" + sex + ", tel=" + tel + ", institute=" + institute
				+ ", edu=" + edu + ", rank=" + rank + ", date=" + date + ", sub=" + sub + "]";
	}

}
