package cn.grades.domain;

public class Course {
	private String id;
	private String deptid;
	private String name;
	private String majorid;
	private String gradeid;
	private int score;
	private String dsc;
	private String must;
	private String pub;
	private String sign;
	private String courseid;
	private String _id;

	

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getDsc() {
		return dsc;
	}
	
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMajorid() {
		return majorid;
	}

	public void setMajorid(String majorid) {
		this.majorid = majorid;
	}

	public String getGradeid() {
		return gradeid;
	}

	public void setGradeid(String gradeid) {
		this.gradeid = gradeid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getMust() {
		return must;
	}

	public void setMust(String must) {
		this.must = must;
	}

	public String getPub() {
		return pub;
	}

	public void setPub(String pub) {
		this.pub = pub;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", deptid=" + deptid + ", name=" + name + ", majorid=" + majorid + ", gradeid="
				+ gradeid + ", score=" + score + ", must=" + must + ", pub=" + pub + "]";
	}

}
